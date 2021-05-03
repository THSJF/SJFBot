package com.meng.modules.qq.modules;

import com.meng.bot.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.imageFactory.ImageFactory;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.sauceNao.SauceNaoApi;
import com.meng.modules.sauceNao.javabean.SauceNaoResult;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import com.meng.tools.SJFExecutors;
import com.meng.tools.Youtu;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class ImageProcess extends BaseModule implements IGroupMessageEvent {

    private ConcurrentHashMap<Long,TYPE> ready = new ConcurrentHashMap<>();

    public ImageProcess(SBot entity) {
        super(entity);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        ConfigManager configManager = ConfigManager.getInstance();
        if (!configManager.isFunctionEnabled(event.getGroup(), Functions.ImageProcess)) {
            return false;
        }
        String msg = event.getMessage().contentToString().toLowerCase();
        long qqId = event.getSender().getId();
        long at = entity.getAt(event.getMessage());
        if (msg.contains("神触") && at != -1) {
            NormalMember nm = event.getGroup().get(at);
            File avatar;
            try {
                avatar = FileTool.createFile(new File(SBot.appDirectory + "imageFactory/avatar/" + System.currentTimeMillis() + ".jpg"));
            } catch (IOException e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                return false;
            }
            byte[] fileBytes = Network.httpGetRaw(nm.getAvatarUrl());
            FileTool.saveFile(avatar, fileBytes);
            Image img = entity.toImage(ImageFactory.getInstance().generateShenChu(avatar), event.getGroup());
            sendQuote(event, img);
            avatar.delete();
            return true;
        }
        if (msg.contains("精神支柱") && at != -1) {
            NormalMember nm = event.getGroup().get(at);
            File avatar;
            try {
                avatar = FileTool.createFile(new File(SBot.appDirectory + "imageFactory/avatar/" + System.currentTimeMillis() + ".jpg"));
            } catch (IOException e) {
                return false;
            }
            byte[] fileBytes = Network.httpGetRaw(nm.getAvatarUrl());
            FileTool.saveFile(avatar, fileBytes);
            Image img = entity.toImage(ImageFactory.getInstance().generateJingShenZhiZhu(avatar), event.getGroup());
            sendQuote(event, img);
            avatar.delete();
            return true;
        }
        Image img = event.getMessage().get(Image.Key);
        if (img == null) {
            FlashImage fi = event.getMessage().get(FlashImage.Key);
            if (fi != null) {
                img = fi.getImage();
            }
        }
        if (configManager.isFunctionEnabled(event.getGroup(), Functions.PictureSearch) && (ready.get(qqId) == TYPE.Search || ready.get(qqId) == null)) {
            if (img != null && msg.startsWith("sp")) {
                runPictureSearch(img, event);
                return true;
            } else if (img == null && msg.equals("sp")) {
                ready.put(qqId, TYPE.Search);
                sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.containsKey(qqId)) {
                runPictureSearch(img, event);
                return true;
            }
        }
//        if (configManager.isFunctionEnabled(event.getGroup().getId(), Functions.EuropeDogs) && img != null) {
//            try {
//                byte[] fileBytes = Jsoup.connect(entity.getUrl(img)).ignoreContentType(true).method(Connection.Method.GET).execute().bodyAsBytes();
//                File file = new File(SBot.appDirectory + "/image/Europe/" + Hash.getMd5Instance().calculate(fileBytes) + "." + FileFormat.getFileType(fileBytes));
//                FileTool.saveFile(file, fileBytes);
//                BufferedImage bfi = ImageIO.read(file);
//                float h = bfi.getHeight(null);
//                float w = bfi.getWidth(null);
//                float s = w / h;
//                if (s > 1.7f) {
//                    runSeekDog(bfi.getSubimage(0, 0, (int)(w / 2), (int)h), event);
//                }
//                file.delete();
//            } catch (Exception e) {
//                //     ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
//            }
//        }
        if (configManager.isFunctionEnabled(event.getGroup().getId(), Functions.ImageTag) && (ready.get(qqId) == TYPE.Tag || ready.get(qqId) == null)) {
            if (img != null && (msg.startsWith("tag"))) {
                getImageTag(img, event);
                return true;
            } else if (img == null && msg.equals("tag")) {
                ready.put(qqId, TYPE.Tag);
                sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.containsKey(qqId)) {
                getImageTag(img, event);
                return true;
            }
        }
        if (ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.OCR) && (ready.get(qqId) == TYPE.Ocr || ready.get(qqId) == null)) {
            if (img != null && (msg.toLowerCase().startsWith("ocr"))) {
                getOcrResult(img, event);
                return true;
            } else if (img == null && msg.equals("ocr")) {
                ready.put(qqId, TYPE.Ocr);
                sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.containsKey(qqId)) {
                getOcrResult(img, event);
                return true;
            }
        } 
        if (ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.Porn) && (ready.get(qqId) == TYPE.Porn || ready.get(qqId) == null)) {
            if (img != null && (msg.toLowerCase().startsWith("porn"))) {
                getPornValue(img, event);
                return true;
            } else if (img == null && msg.equals("porn")) {
                ready.put(qqId, TYPE.Porn);
                sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.containsKey(qqId)) {
                getPornValue(img, event);
                return true;
            }
        }
        if (ready.get(qqId) == TYPE.Url || ready.get(qqId) == null) {
            if (img != null && (msg.toLowerCase().startsWith("url"))) {
                getUrl(img, event);
                return true;
            } else if (img == null && msg.equals("url")) {
                ready.put(qqId, TYPE.Porn);
                sendQuote(event, "发送一张图片吧");
                return true;
            } else if (img != null && ready.containsKey(qqId)) {
                getUrl(img, event);
                return true;
            }
        } 
        return false;
    }

    private enum TYPE {
        Search,
        Tag,
        Ocr,
        Porn,
        Url
        }

    private void getPornValue(final Image img, final GroupMessageEvent event) {
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    ready.remove(event.getSender().getId());
                    sendQuote(event, "正在识别……");
                    try {
                        Youtu.PornResult response = Youtu.getFaceYoutu().doPornWithUrl(entity.getUrl(img));
                        ArrayList<Youtu.PornResult.Tag> items = response.tags;
                        StringBuilder sb = new StringBuilder();
                        for (Youtu.PornResult.Tag tag : items) {
                            sb.append(switchTagName(tag.tag_name)).append(":").append(tag.tag_confidence).append("%\n");
                        }
                        sb.setLength(sb.length() - 1);
                        sendQuote(event, sb.toString());
                    } catch (Exception e) {
                        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                        sendQuote(event, e.toString());
                    }
                }
            });
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

    private void getOcrResult(final Image img, final GroupMessageEvent event) {
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    ready.remove(event.getSender().getId());
                    sendQuote(event, "正在识别……");
                    try {
                        Youtu.OcrResult response = Youtu.getFaceYoutu().doOcrWithUrl(entity.getUrl(img));
                        StringBuilder sb = new StringBuilder();
                        ArrayList<Youtu.OcrResult.Items> items = response.items;
                        sb.append("结果:");
                        for (Youtu.OcrResult.Items s : items) {
                            sb.append("\n").append(s.itemstring);
                        }
                        sendMessage(event.getGroup(), sb.toString());
                    } catch (Exception e) {
                        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                        sendQuote(event, e.toString());
                    }
                }
            });
    }

    private void getImageTag(final Image img, final GroupMessageEvent event) {
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    ready.remove(event.getSender().getId());
                    sendQuote(event, "正在识别……");
                    try {
                        Youtu.TagResult response = Youtu.getFaceYoutu().doTagWithUrl(entity.getUrl(img));
                        ArrayList<Youtu.TagResult.Tag> items = response.tags;
                        StringBuilder sb = new StringBuilder();
                        for (Youtu.TagResult.Tag tag : items) {
                            sb.append(tag.tag_name).append("\n");
                        }
                        sb.setLength(sb.length() - 1);
                        sendQuote(event, sb.toString());
                    } catch (Exception e) {
                        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                        sendQuote(event, e.toString());
                    }
                }
            });
    }

    private void runSeekDog(final BufferedImage img, final GroupMessageEvent event) {
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
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
                            sendMessage(event.getGroup(), items.get(1).itemstring + ",去你大爷的欧洲狗");
                        }
                    } catch (Exception e) {
                        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                        sendQuote(event, e.toString());
                    } finally {
                        tmp.delete();   
                    }
                }
            });
    }

    private void runPictureSearch(final Image img, final GroupMessageEvent event) {
        ready.remove(event.getSender().getId());
        sendQuote(event, "正在搜索……");
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        SauceNaoResult mResults = SauceNaoApi.getSauce(entity.getUrl(img));
                        int size = mResults.getResults().size();
                        if (size < 1) {
                            sendQuote(event, "没有相似度较高的图片");
                            return;
                        }
                        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                        SauceNaoResult.Result tmpr = mResults.getResults().get(0);
                        Image img = entity.toImage(new URL(tmpr.mThumbnail), event.getGroup());
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
                        sendMessage(event.getGroup(), messageChainBuilder.asMessageChain());     
                    } catch (IOException e) {
                        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                        sendMessage(event, "搜索出错");
                    }
                }
            });
    }

    private void getUrl(final Image img, final GroupMessageEvent event) {
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    ready.remove(event.getSender().getId());
                    sendQuote(event, "正在识别……");
                    sendMessage(event, entity.getUrl(img));
                }
            });
    }

    @Override
    public ImageProcess load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "imageProcess";
    }
}
