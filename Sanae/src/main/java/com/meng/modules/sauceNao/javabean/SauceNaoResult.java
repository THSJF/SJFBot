package com.meng.modules.sauceNao.javabean;

import java.util.ArrayList;
import java.util.Collections;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class SauceNaoResult {

    private final String CLASS_RESULT_CONTENT_COLUMN = "resultcontentcolumn";
    private final String CLASS_RESULT_IMAGE = "resultimage";
    private final String CLASS_RESULT_MATCH_INFO = "resultmatchinfo";
    private final String CLASS_RESULT_SIMILARITY_INFO = "resultsimilarityinfo";
    private final String CLASS_RESULT_TABLE = "resulttable";
    private final String CLASS_RESULT_TITLE = "resulttitle";
    private final String URL_LOOKUP_SUBSTRING = "https://saucenao.com/info.php?lookup_type=";

    private ArrayList<Result> mResults = new ArrayList<>();

    public SauceNaoResult(Document document) {
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

    public class Result {
        public String mSimilarity;
        public String mThumbnail;
        public String mTitle;
        public ArrayList<String> mExtUrls = new ArrayList<>();
        public ArrayList<String> mColumns = new ArrayList<>();

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
