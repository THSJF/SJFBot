package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.SJFExecutors;
import com.meng.tools.Youtu;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import net.mamoe.mirai.event.events.GroupMessageEvent;
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

public class ImageProcess extends BaseModule implements IGroupMessageEvent {

    private Set<Long> ready = new HashSet<>();

    public ImageProcess(SBot entity) {
        super(entity);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        String msg = event.getMessage().contentToString().toLowerCase();
        long qqId = event.getSender().getId();
        Image img = event.getMessage().get(Image.Key);
        if (img == null) {
            FlashImage fi = event.getMessage().get(FlashImage.Key);
            if (fi != null) {
                img = fi.getImage();
            }
        }
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.isFunctionEnabled(event.getGroup(), Functions.PictureSearch)) {
            if (img != null && msg.startsWith("sp")) {
                runPictureSearch(img, event);
                return true;
            } else if (img == null && msg.equals("sp")) {
                ready.add(qqId);
                entity.sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.contains(qqId)) {
                runPictureSearch(img, event);
                ready.remove(qqId);
                return true;
            }
        }
        if (configManager.isFunctionEnabled(event.getGroup().getId(), Functions.EuropeDogs) && img != null) {
            try {
                byte[] fileBytes = Jsoup.connect(entity.getUrl(img)).ignoreContentType(true).method(Connection.Method.GET).execute().bodyAsBytes();
                File folder = new File(SBot.appDirectory + "/image/Europe/");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File file = new File(folder.getAbsolutePath() + "/" + Hash.getMd5Instance().calculate(fileBytes) + "." + FileFormat.getFileType(fileBytes));
                FileTool.saveFile(file, fileBytes);
                BufferedImage bfi = ImageIO.read(file);
                float h = bfi.getHeight(null);
                float w = bfi.getWidth(null);
                float s = w / h;
                if (s > 1.7f) {
                    runSeekDog(bfi.getSubimage(0, 0, (int)(w / 2), (int)h), event);
                }
                file.delete();
            } catch (Exception e) {
                //     ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }
        if (configManager.isFunctionEnabled(event.getGroup().getId(), Functions.ImageTag)) {
            if (img != null && (msg.startsWith("tag"))) {
                getImageTag(img, event);
                return true;
            } else if (img == null && msg.equals("tag")) {
                ready.add(qqId);
                entity.sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.contains(qqId)) {
                getImageTag(img, event);
                ready.remove(qqId);
                return true;
            }
        }
        if (ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.OCR)) {
            if (img != null && (msg.toLowerCase().startsWith("ocr"))) {
                getOcrResult(img, event);
                return true;
            } else if (img == null && msg.equals("ocr")) {
                ready.add(qqId);
                entity.sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.contains(qqId)) {
                getOcrResult(img, event);
                ready.remove(qqId);
                return true;
            }
        } 
        if (ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.Porn)) {
            if (img != null && (msg.toLowerCase().startsWith("porn"))) {
                getPornValue(img, event);
                return true;
            } else if (img == null && msg.equals("porn")) {
                ready.add(qqId);
                entity.sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.contains(qqId)) {
                getPornValue(img, event);
                ready.remove(qqId);
                return true;
            }
        }
        return false;
    }

    private void getPornValue(Image img, GroupMessageEvent event) {
        try {
            entity.sendQuote(event, "正在识别……");
            Youtu.PornResult response = Youtu.getFaceYoutu().doPornWithUrl(entity.getUrl(img));
            ArrayList<Youtu.PornResult.Tag> items = response.tags;
            StringBuilder sb = new StringBuilder();
            for (Youtu.PornResult.Tag tag : items) {
                sb.append(switchTagName(tag.tag_name)).append(":").append(tag.tag_confidence).append("%\n");
            }
            sb.setLength(sb.length() - 1);
            entity.sendQuote(event, sb.toString());
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString().replace("java", "jvav"));
        }
    }

    private String switchTagName(String s) {
        switch (s) {
            case "normal":
                return "普通";
            case "hot":
                return "性感";
            case "porn":
                return "色情";
            case "female-genital":
                return "女性阴部";
            case "female-breast":
                return "女性胸部";
            case "male-genital":
                return "男性阴部";
            case "pubes":
                return "阴毛";
            case "anus":
                return "肛门";
            case "sex":
                return "性行为";
            case "normal_hot_porn":
                return "色情综合值";
            default:
                return null;
        }
    }

    private void getOcrResult(Image img, GroupMessageEvent event) {
        try {
            Youtu.OcrResult response = Youtu.getFaceYoutu().doOcrWithUrl(entity.getUrl(img));
            StringBuilder sb = new StringBuilder();
            ArrayList<Youtu.OcrResult.Items> items = response.items;
            sb.append("结果:");
            for (Youtu.OcrResult.Items s : items) {
                sb.append("\n").append(s.itemstring);
            }
            entity.sendMessage(event.getGroup(), sb.toString());
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString());
        }
    }

    private void getImageTag(Image img, GroupMessageEvent event) {
        try {
            entity.sendQuote(event, "正在识别……");
            Youtu.TagResult response = Youtu.getFaceYoutu().doTagWithUrl(entity.getUrl(img));
            ArrayList<Youtu.TagResult.Tag> items = response.tags;
            StringBuilder sb = new StringBuilder();
            for (Youtu.TagResult.Tag tag : items) {
                sb.append(tag.tag_name).append("\n");
            }
            sb.setLength(sb.length() - 1);
            entity.sendQuote(event, sb.toString());
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString().replace("java", "jvav"));
        }
    }

    private void runSeekDog(BufferedImage img, GroupMessageEvent event) {
        File tmp = new File(SBot.appDirectory + "/image/Europe/" + System.currentTimeMillis() + ".jpg");
        try {  
            ImageIO.write(img, "jpg", tmp);
            StringBuilder sb = new StringBuilder();
            Youtu.OcrResult response = Youtu.getFaceYoutu().doOcrWithFile(tmp.getAbsolutePath());
            ArrayList<Youtu.OcrResult.Items> items = response.items;
            sb.append("Europe结果:");
            for (Youtu.OcrResult.Items s : items) {
                sb.append("\n").append(s.itemstring);
            }
            System.out.println(sb.toString());
            if (items.size() == 3 && items.get(0).itemstring.equals("极品") && (items.get(2).itemstring.equals("角色卡") || items.get(2).itemstring.equals("圣痕") || items.get(2).itemstring.equals("武器"))) {
                entity.sendMessage(event.getGroup(), items.get(1).itemstring + ",去你大爷的欧洲狗");
            }
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString());
        } finally {
            tmp.delete();   
        }
    }

    private void runPictureSearch(Image img, GroupMessageEvent event) {
        try {
            entity.sendQuote(event, "正在搜索……");
            SJFExecutors.execute(new SearchRunnable(event, entity.getUrl(img)));
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString().replace("java", "jvav"));
        }
    }

    @Override
    public ImageProcess load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "imageProcess";
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
            Image img = entity.toImage(new URL(tmpr.mThumbnail).openStream(), event.getGroup());
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
