package com.meng.modules.qq.modules;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import com.meng.bot.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.ImageFactory;
import com.meng.modules.Youtu;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.sauceNao.SauceNaoApi;
import com.meng.modules.sauceNao.javabean.SauceNaoResult;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.imageio.ImageIO;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class ImageProcess extends BaseModule implements IGroupMessageEvent {

    
    public ImageProcess(SBot entity) {
        super(entity);
    }

    private ImageProcessQqAvatar qqAvatar = new ImageProcessQqAvatar();
    private ImageProcessLocal local = new ImageProcessLocal();
    private ImageProcessNetwok network = new ImageProcessNetwok();

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        ConfigManager configManager = ConfigManager.getInstance();
        if (!configManager.isFunctionEnabled(event.getGroup(), Functions.ImageProcess)) {
            return false;
        }
        if (qqAvatar.onGroupMessage(event)) {
            return true;
        }
        if (local.onGroupMessage(event)) {
            return true;
        }
        if (network.onGroupMessage(event)) {
            return true;
        }
        return false;
    }

    @Override
    public ImageProcess load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "imageProcess";
    }

    private class ImageProcessQqAvatar implements IGroupMessageEvent {

        private Map<String,Function<BufferedImage,BufferedImage>> functionMap;

        private ImageProcessQqAvatar() {
            functionMap = Collections.unmodifiableMap(new HashMap<String,Function<BufferedImage,BufferedImage>>(){
                    {
                        put("精神支柱", new Function<BufferedImage,BufferedImage>(){

                                @Override
                                public BufferedImage apply(BufferedImage p1) {
                                    return ImageFactory.getInstance().generateJingShenZhiZhu(p1);
                                }
                            });
                        put("神触", new Function<BufferedImage,BufferedImage>(){

                                @Override
                                public BufferedImage apply(BufferedImage p1) {
                                    return ImageFactory.getInstance().generateJingShenZhiZhu(p1);
                                }
                            }); 

                    }
                });   
        }

        @Override
        public boolean onGroupMessage(GroupMessageEvent event) {
            String cmd = event.getMessage().get(1).contentToString();
            long qqId = entity.getAt(event.getMessage());
            try {    
                if (qqId != -1 && functionMap.containsKey(cmd)) {
                    File imageFile = new File(SBot.appDirectory + "image/" + System.currentTimeMillis() + ".jpg");
                    FileTool.saveFile(imageFile, Network.httpGetRaw(event.getGroup().get(qqId).getAvatarUrl()));
                    if (FileFormat.isGif(imageFile)) {
                        runGenerateDynamic(imageFile, event, functionMap.get(cmd));
                    } else {
                        runGenerateStatic(imageFile, event, functionMap.get(cmd));          
                    }
                    return true;
                } 
            } catch (IOException e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
            return false;
        }
    }

    private class ImageProcessNetwok implements IGroupMessageEvent {
        private ConcurrentHashMap<Long,BiConsumer<Image,GroupMessageEvent>> ready = new ConcurrentHashMap<>();
        private Map<String,BiConsumer<Image,GroupMessageEvent>> functionMap;

        private enum TYPE {
            Search("sp","搜索图片","图片搜索"),
            Tag("tag","图片标签"),
            Ocr("ocr","光学字符识别"),
            Porn("porn"),
            Url("url");

            private String[] value;

            public boolean isCmd(String cmd){
                for(String s : value){
                    if(s.equals(cmd)){
                        return true;
                    }
                }
                return false;
            }

            private TYPE(String... value){
                this.value = value;
            }
        }

        private ImageProcessNetwok() {
            functionMap = Collections.unmodifiableMap(new HashMap<String,BiConsumer<Image,GroupMessageEvent>>(){
                    {
                        put("sp", new BiConsumer<Image,GroupMessageEvent>(){

                                @Override
                                public void accept(Image simg, GroupMessageEvent event) {
                                    try {
                                        SauceNaoResult mResults = SauceNaoApi.getSauce(entity.getUrl(simg));
                                        if (mResults.getResults().size() < 1) {
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
                                        sendMessage(event, e.toString());
                                    }
                                }
                            });
                        put("tag", new BiConsumer<Image,GroupMessageEvent>(){

                                @Override
                                public void accept(Image img, GroupMessageEvent event) {
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
                        put("ocr", new BiConsumer<Image,GroupMessageEvent>(){

                                @Override
                                public void accept(Image img, GroupMessageEvent event) {
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
                        put("porn", new BiConsumer<Image,GroupMessageEvent>(){

                                @Override
                                public void accept(Image img, GroupMessageEvent event) {
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
                        put("url", new BiConsumer<Image,GroupMessageEvent>(){

                                @Override
                                public void accept(Image img, GroupMessageEvent event) {
                                    sendMessage(event, entity.getUrl(img));
                                }
                            });
                    }
                });
        }

        @Override
        public boolean onGroupMessage(GroupMessageEvent event) {
            String cmd = event.getMessage().get(1).contentToString();
            long qqId = event.getSender().getId();
            Image miraiImg = event.getMessage().get(Image.Key);
            if (miraiImg != null && functionMap.containsKey(cmd)) {
                sendQuote(event, "正在识别……");
                functionMap.get(cmd).accept(miraiImg, event);
                ready.remove(qqId);
                return true;
            } else if (miraiImg == null && functionMap.containsKey(cmd)) {
                ready.put(qqId, functionMap.get(cmd));
                sendQuote(event, "发送一张图片吧");
                return true;
            } else if (miraiImg != null && ready.containsKey(qqId)) {
                sendQuote(event, "正在识别……");
                ready.get(qqId).accept(miraiImg, event);
                ready.remove(qqId);
                return true;
            }
            return false;
        }

        private String switchTagName(String s) {
            switch (s) {
                case "normal":          return "普通";
                case "hot":             return "性感";
                case "porn":            return "色情";
                case "female-genital":  return "女性阴部";
                case "female-breast":   return "女性胸部";
                case "male-genital":    return "男性阴部";
                case "pubes":           return "阴毛";
                case "anus":            return "肛门";
                case "sex":             return "性行为";
                case "normal_hot_porn": return "色情综合值";
                default:                return null;
            }
        }
    }

    private class ImageProcessLocal implements IGroupMessageEvent {

        private ConcurrentHashMap<Long,Function<BufferedImage,BufferedImage>> ready = new ConcurrentHashMap<>();
        private Map<String,Function<BufferedImage,BufferedImage>> functionMap;

        private ImageProcessLocal() {
            functionMap = Collections.unmodifiableMap(new HashMap<String,Function<BufferedImage,BufferedImage>>(){
                    {
                        put("灰度图", new Function<BufferedImage,BufferedImage>(){

                                @Override
                                public BufferedImage apply(BufferedImage p1) {
                                    return ImageFactory.getInstance().generateGray(p1);
                                }
                            });
                        put("图片旋转", new Function<BufferedImage,BufferedImage>(){

                                @Override
                                public BufferedImage apply(BufferedImage p1) {
                                    return ImageFactory.getInstance().generateRotateImage(p1, 90);
                                }
                            });
                        put("上下翻转", new Function<BufferedImage,BufferedImage>(){

                                @Override
                                public BufferedImage apply(BufferedImage p1) {
                                    return ImageFactory.getInstance().generalMirrorImage(p1, 1);
                                }
                            });
                        put("左右翻转", new Function<BufferedImage,BufferedImage>(){

                                @Override
                                public BufferedImage apply(BufferedImage p1) {
                                    return ImageFactory.getInstance().generalMirrorImage(p1, 0);
                                }
                            });
//                    put("天壤梦弓", new Function<BufferedImage,BufferedImage>(){
//
//                            @Override
//                            public BufferedImage apply(BufferedImage p1) {
//                                return ImageFactory.getInstance().generalMirrorImage(p1, 2);
//                            }
//                        });
                    }
                });  
        }

        @Override
        public boolean onGroupMessage(GroupMessageEvent event) {
            String cmd = event.getMessage().get(1).contentToString();
            long qqId = event.getSender().getId();
            Image miraiImg = event.getMessage().get(Image.Key);
            try {    
                if (miraiImg != null && functionMap.containsKey(cmd)) {
                    File imageFile = new File(SBot.appDirectory + "image/" + System.currentTimeMillis() + ".jpg");
                    FileTool.saveFile(imageFile, Network.httpGetRaw(entity.getUrl(miraiImg)));
                    if (FileFormat.isGif(imageFile)) {
                        runGenerateDynamic(imageFile, event, functionMap.get(cmd));
                        ready.remove(event.getSender().getId());
                    } else {
                        runGenerateStatic(imageFile, event, functionMap.get(cmd));          
                        ready.remove(event.getSender().getId());
                    }
                    ready.remove(qqId);
                    return true;
                } else if (miraiImg == null && functionMap.containsKey(cmd)) {
                    ready.put(qqId, functionMap.get(cmd));
                    sendQuote(event, "发送一张图片吧");
                    return true;
                } else if (miraiImg != null && ready.containsKey(qqId)) {
                    File imageFile = new File(SBot.appDirectory + "image/" + System.currentTimeMillis() + ".jpg");
                    FileTool.saveFile(imageFile, Network.httpGetRaw(entity.getUrl(miraiImg)));
                    if (FileFormat.isGif(imageFile)) {
                        runGenerateDynamic(imageFile, event, ready.get(qqId));
                        ready.remove(event.getSender().getId());
                    } else {
                        runGenerateStatic(imageFile, event, ready.get(qqId));          
                        ready.remove(event.getSender().getId());
                    }
                    ready.remove(qqId);
                    return true;
                }
            } catch (IOException e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
            return false;
        }
    }

    private void runGenerateStatic(File imageFile, GroupMessageEvent event, Function<BufferedImage,BufferedImage> function) throws IOException {
        BufferedImage grayImage = function.apply(ImageIO.read(imageFile));
        File tmp = new File(SBot.appDirectory + "image/" + System.currentTimeMillis() + ".jpg");
        FileTool.createFile(tmp);
        ImageIO.write(grayImage, "jpg", tmp);
        Image im = entity.toImage(tmp, event.getGroup());
        sendMessage(event, im);
        tmp.delete();
    }

    private void runGenerateDynamic(File imageFile, GroupMessageEvent event, Function<BufferedImage,BufferedImage> function) throws FileNotFoundException {
        GifDecoder gifDecoder = new GifDecoder();
        FileInputStream fis = new FileInputStream(imageFile);
        int statusCode = gifDecoder.read(fis);
        if (statusCode != 0) {
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
        localAnimatedGifEncoder.start(baos);//start
        localAnimatedGifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
        for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
            localAnimatedGifEncoder.setDelay(gifDecoder.getDelay(i));
            localAnimatedGifEncoder.addFrame(function.apply(gifDecoder.getFrame(i)));
        }
        localAnimatedGifEncoder.finish();
        File gifFile = new File(SBot.appDirectory + "image/" + System.currentTimeMillis() + ".gif");
        FileTool.saveFile(gifFile, baos.toByteArray());
        Image resultImage = entity.toImage(gifFile, event.getGroup());
        sendMessage(event, resultImage);
        imageFile.delete();
    }
}
