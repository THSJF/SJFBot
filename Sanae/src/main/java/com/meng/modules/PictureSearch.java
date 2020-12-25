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
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
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

    public PictureSearch(SBot sb) {
        super(sb);
    }

    private HashSet<Long> ready = new HashSet<>();

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
        String url = entity.queryImageUrl(img);
        String msg = event.getMessage().contentToString();
        long groupId = event.getGroup().getId();
        long qqId = event.getSender().getId();
        if (img != null && (msg.toLowerCase().startsWith("sp"))) {
            try {
                entity.sendGroupMessage(groupId, new At(event.getSender()).plus("土豆折寿中……"));
                SJFExecutors.execute(new SearchRunnable(event.getGroup(), event.getSender(), url));
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                entity.sendGroupMessage(groupId, e.toString());
            }
            return true;
        } else if (img == null && msg.equals("sp")) {
            ready.add(qqId);
            entity.sendGroupMessage(groupId, new At(event.getSender()).plus("需要一张图片"));
            return true;
        } else if (img != null && ready.contains(qqId)) {
            try {
                entity.sendGroupMessage(groupId, new At(event.getSender()).plus("土豆折寿中……"));
                SJFExecutors.execute(new SearchRunnable(event.getGroup(), event.getSender(), url));
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                entity.sendGroupMessage(groupId, e.toString());
            }
            ready.remove(qqId);
            return true;
        }
        return false;
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

        private Member member;
        private Group group;
        private String url;
        private PicResults mResults;

        public SearchRunnable(Group group, Member member, String url) {
            this.group = group;
            this.member = member;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                check(url);
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                entity.sendMessage(group, "查找失败");
            }
        }

        private void check(String url) throws Exception {
            Connection.Response response = Jsoup.connect("https://saucenao.com/search.php?db=999").timeout(60000).data("url", url).method(Connection.Method.POST).execute();
            if (response.statusCode() != 200) {
                entity.sendMessage(group, "错误:" + response.statusMessage());
                return;
            }
            mResults = new PicResults(Jsoup.parse(response.body()));
            int size = mResults.getResults().size();
            if (size < 1) {
                entity.sendMessage(group, "没有相似度较高的图片");
                return;
            }
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            for (int i = 0; i < size; i++) {
                PicResults.Result tmpr = mResults.getResults().get(i);
                Image img = group.uploadImage(new URL(tmpr.mThumbnail));
                String[] titleAndMetadata = tmpr.mTitle.split("\n", 2);
                if (titleAndMetadata.length > 0) {
                    messageChainBuilder.append("\n").append(titleAndMetadata[0]).append("\n");
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
            }
            entity.sendMessage(group,
                               messageChainBuilder.asMessageChain().contentToString().contains("sankakucomplex") ?
                               messageChainBuilder.append("\n小哥哥注意身体哦").asMessageChain() :
                               messageChainBuilder.asMessageChain());            
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
