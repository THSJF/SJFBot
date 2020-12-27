package com.meng.modules;

import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.SJFExecutors;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class PictureSearch extends BaseModule implements IGroupMessageEvent {

    private HashSet<Long> ready = new HashSet<>();

    public PictureSearch(SBot sb) {
        super(sb);
    }

    @Override
    public PictureSearch load() {
        return this;
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (!entity.configManager.isFunctionEnabled(event.getGroup(), Functions.PictureSearch)) {
            return false;
        }
        Image img = event.getMessage().firstOrNull(Image.Key);
        if (img == null) {
            FlashImage fi = event.getMessage().firstOrNull(FlashImage.Key);
            if (fi != null) {
                img = fi.getImage();
            }
        }
        String msg = event.getMessage().contentToString();
        long qqId = event.getSender().getId();
        if (img != null && (msg.toLowerCase().startsWith("sp"))) {
            processImage(img, event);
            return true;
        } else if (img == null && msg.equals("sp")) {
            ready.add(qqId);
            entity.sendQuote(event, "发送一张图片吧");
            return true;
        } else if (img != null && ready.contains(qqId)) {
            processImage(img, event);
            ready.remove(qqId);
            return true;
        }
        return false;
    }

    private void processImage(Image img, GroupMessageEvent event) {
        try {
            entity.sendQuote(event, "正在搜索……");
            SJFExecutors.execute(new SearchRunnable(event, entity.queryImageUrl(img)));
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString().replace("java", "jvav"));
        }
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public String getModuleName() {
        return getClass().getName();
    }

    private class SearchRunnable implements Runnable {

        private GroupMessageEvent event;
        private String url;
        private PicResults mResults;

        public SearchRunnable(GroupMessageEvent event, String url) {
            this.event = event;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                check(url);
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                entity.sendQuote(event, "查找失败");
            }
        }

        private void check(String url) throws Exception {
            Connection.Response response = Jsoup.connect("https://saucenao.com/search.php?db=999").timeout(60000).data("url", url).method(Connection.Method.POST).execute();
            if (response.statusCode() != 200) {
                entity.sendQuote(event, "错误:" + response.statusMessage());
                return;
            }
            mResults = new PicResults(Jsoup.parse(response.body()));
            int size = mResults.getResults().size();
            if (size < 1) {
                entity.sendQuote(event, "没有相似度较高的图片");
                return;
            }
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            PicResults.Result tmpr = mResults.getResults().get(0);
            Image img = event.getGroup().uploadImage(new URL(tmpr.mThumbnail));
            String[] titleAndMetadata = tmpr.mTitle.split("\n", 2);
            if (titleAndMetadata.length > 0) {
                messageChainBuilder.append(titleAndMetadata[0]).append("\n");
                if (titleAndMetadata.length == 2) {
                    tmpr.mColumns.add(0, titleAndMetadata[1]);
                }
                for (String string : tmpr.mColumns) {
                    messageChainBuilder.append(string).append("\n");
                }
            }
            messageChainBuilder.append(img).append("\n");
            if (tmpr.mExtUrls.size() == 2) {
                messageChainBuilder.append("图片&画师:").append(tmpr.mExtUrls.get(1)).append("\n");
                messageChainBuilder.append(tmpr.mExtUrls.get(0)).append("\n");
            } else if (tmpr.mExtUrls.size() == 1) {
                messageChainBuilder.append("链接:").append(tmpr.mExtUrls.get(0)).append("\n");
            }
            if (!tmpr.mSimilarity.isEmpty()) {
                messageChainBuilder.append("相似度:").append(tmpr.mSimilarity).append("\n");
            }
            entity.sendMessage(event.getGroup(), messageChainBuilder.asMessageChain());     
        }

        private class PicResults {

            private final String CLASS_RESULT_CONTENT_COLUMN = "resultcontentcolumn";
            private final String CLASS_RESULT_IMAGE = "resultimage";
            private final String CLASS_RESULT_MATCH_INFO = "resultmatchinfo";
            private final String CLASS_RESULT_SIMILARITY_INFO = "resultsimilarityinfo";
            private final String CLASS_RESULT_TABLE = "resulttable";
            private final String CLASS_RESULT_TITLE = "resulttitle";
            private final String URL_LOOKUP_SUBSTRING = "https://saucenao.com/info.php?lookup_type=";

            private ArrayList<Result> mResults = new ArrayList<>();

            public PicResults(Document document) {
                for (Element result : document.getElementsByClass(CLASS_RESULT_TABLE)) {
                    Element resultImage = result.getElementsByClass(CLASS_RESULT_IMAGE).first();
                    Element resultMatchInfo = result.getElementsByClass(CLASS_RESULT_MATCH_INFO).first();
                    Element resultTitle = result.getElementsByClass(CLASS_RESULT_TITLE).first();
                    Elements resultContentColumns = result.getElementsByClass(CLASS_RESULT_CONTENT_COLUMN);
                    Result newResult = new Result();
                    newResult.loadSimilarityInfo(resultMatchInfo);
                    newResult.loadThumbnail(resultImage);
                    newResult.loadTitle(resultTitle);
                    newResult.loadExtUrls(resultMatchInfo, resultContentColumns);
                    newResult.loadColumns(resultContentColumns);
                    mResults.add(newResult);
                }
            }

            public ArrayList<Result> getResults() {
                return mResults;
            }

            private class Result {
                String mSimilarity;
                String mThumbnail;
                String mTitle;
                ArrayList<String> mExtUrls = new ArrayList<>();
                ArrayList<String> mColumns = new ArrayList<>();

                private void loadSimilarityInfo(Element resultMatchInfo) {
                    try {
                        mSimilarity = resultMatchInfo.getElementsByClass(CLASS_RESULT_SIMILARITY_INFO).first().text();
                    } catch (NullPointerException e) {
                        System.out.println("Unable to load similarity info");
                    }
                }

                private void loadThumbnail(Element resultImage) {
                    try {
                        Element img = resultImage.getElementsByTag("img").first();

                        if (img.hasAttr("data-src")) {
                            mThumbnail = img.attr("data-src");
                        } else if (img.hasAttr("src")) {
                            mThumbnail = img.attr("src");
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Unable to load thumbnail");
                    }
                }

                private void loadTitle(Element resultTitle) {
                    try {
                        mTitle = new HtmlToPlainText().getPlainText(resultTitle);
                    } catch (NullPointerException e) {
                        System.out.println("Unable to load title");
                    }
                }

                private void loadExtUrls(Element resultMatchInfo, Elements resultContentColumns) {
                    try {
                        for (Element a : resultMatchInfo.getElementsByTag("a")) {
                            String href = a.attr("href");

                            if (!href.isEmpty() && !href.startsWith(URL_LOOKUP_SUBSTRING)) {
                                mExtUrls.add(href);
                            }
                        }

                        for (Element resultContentColumn : resultContentColumns) {
                            for (Element a : resultContentColumn.getElementsByTag("a")) {
                                String href = a.attr("href");
                                if (!href.isEmpty() && !href.startsWith(URL_LOOKUP_SUBSTRING)) {
                                    mExtUrls.add(href);
                                }
                            }
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Unable to load external URLs");
                    }
                    Collections.sort(mExtUrls);
                }

                private void loadColumns(Elements resultContentColumns) {
                    try {
                        for (Element resultContentColumn : resultContentColumns) {
                            mColumns.add(new HtmlToPlainText().getPlainText(resultContentColumn));
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Unable to load columns");
                    }
                }
            }
        }
    }

    private class HtmlToPlainText {

        public String getPlainText(Element element) {
            FormattingVisitor formatter = new FormattingVisitor();
            NodeTraversor.traverse(formatter, element);

            return formatter.toString().trim();
        }

        private class FormattingVisitor implements NodeVisitor {
            private static final int mMaxWidth = 80;
            private int mWidth = 0;
            private StringBuilder mAccum = new StringBuilder();

            @Override
            public void head(Node node, int depth) {
                String name = node.nodeName();
                if (node instanceof TextNode) {
                    append(((TextNode) node).text());
                } else if (name.equals("li")) {
                    append("\n * ");
                } else if (name.equals("dt")) {
                    append("  ");
                } else if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5", "tr")) {
                    append("\n");
                } else if (name.equals("strong")) {
                    append(" ");
                }
            }

            // Hit when all of the node's children (if any) have been visited
            @Override
            public void tail(Node node, int depth) {
                String name = node.nodeName();
                if (StringUtil.in(name, "br", "dd", "dt", "p", "h1", "h2", "h3", "h4", "h5")) {
                    append("\n");
                }
            }

            // Appends text to the string builder with a simple word wrap method
            private void append(String text) {
                // Reset com.meng.counter if starts with a newline. only from formats above,
                // not in natural text
                if (text.startsWith("\n")) {
                    mWidth = 0;
                }

                // Don't accumulate long runs of empty spaces
                if (text.equals(" ")
                    && (mAccum.length() == 0 || StringUtil.in(mAccum.substring(mAccum.length() - 1), " ", "\n"))) {
                    return;
                }

                // Won't fit, needs to wrap
                if (text.length() + mWidth > mMaxWidth) {
                    String[] words = text.split("\\s+");

                    for (int i = 0; i < words.length; i++) {
                        String word = words[i];
                        boolean last = i == words.length - 1;
                        // Insert a space if not the last word
                        if (!last) {
                            word += " ";
                        }
                        // Wrap and reset com.meng.counter
                        if (word.length() + mWidth > mMaxWidth) {
                            mAccum.append("\n").append(word);
                            mWidth = word.length();
                        } else {
                            mAccum.append(word);
                            mWidth += word.length();
                        }
                    }
                } else {
                    // Fits as is, without need to wrap text
                    mAccum.append(text);
                    mWidth += text.length();
                }
            }

            @Override
            public String toString() {
                return mAccum.toString();
            }
        }
    }
}
