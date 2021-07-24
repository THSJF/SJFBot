package com.meng.modules.qq.modules;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import com.meng.bot.Functions;
import com.meng.config.CommandDescribe;
import com.meng.config.ConfigManager;
import com.meng.help.HelpGenerator;
import com.meng.help.Permission;
import com.meng.modules.DeepDanbooruApi;
import com.meng.modules.ImageFactory;
import com.meng.modules.Youtu;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.sauceNao.SauceNaoApi;
import com.meng.modules.sauceNao.javabean.SauceNaoResult;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import com.meng.tools.SJFPathTool;
import com.meng.tools.SJFRandom;
import com.meng.tools.SeijaImageFactory;
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
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.QuoteReply;

public class ImageProcess extends BaseModule implements IGroupMessageEvent {

    public ImageProcess(SBot entity) {
        super(entity);
    }

    private ImageProcessQqAvatar qqAvatar = new ImageProcessQqAvatar();
    private ImageProcessLocal local = new ImageProcessLocal();
    private ImageProcessNetwok network = new ImageProcessNetwok();

    @Override
    public String getModuleName() {
        return "图片处理";
    }

    @Override
    public BaseModule load() {
        HelpGenerator.Item mainMenu = HelpGenerator.getInstance().newItem(Permission.Normal, getModuleName());
        mainMenu.arg("精神支柱").arg("at用户").arg("生成表情包");
        mainMenu.arg("神触").arg("at用户").arg("生成表情包");
        mainMenu.arg("sp").arg("图片").arg("搜图");
        mainMenu.arg("tag").arg("图片").arg("图片内容识别");
        mainMenu.arg("porn").arg("图片").arg("色情度识别");
        mainMenu.arg("ocr").arg("图片").arg("光学字符识别");
        mainMenu.arg("url").arg("图片").arg("获取图片链接");
        mainMenu.arg("dtag").arg("图片").arg("danbooru标签");
        mainMenu.arg("灰度图").arg("图片").arg("生成灰度图");
        mainMenu.arg("图片旋转").arg("图片").arg("图片旋转");
        mainMenu.arg("上下翻转").arg("图片").arg("翻转图片");
        mainMenu.arg("左右翻转").arg("图片").arg("翻转图片");
        return super.load();
    }

    @Override
    @CommandDescribe(cmd = "-", note = "图片处理")
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
        QuoteReply quoteReply = event.getMessage().get(QuoteReply.Key);
        if (quoteReply != null) {
            GroupMessageEvent me = (GroupMessageEvent) MessageManager.getEvent(quoteReply.getSource());
            if (local.onGroupMessage(me, event.getSender().getId(), event.getMessage().get(2).contentToString())) {
                return true;
            }
            if (network.onGroupMessage(me, event.getSender().getId(), event.getMessage().get(2).contentToString())) {
                return true;
            }
        }
        return false;
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
                                    return ImageFactory.getInstance().generateShenChu(p1);
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
                    File avatar = SJFPathTool.getImagePath("gen/" + SJFRandom.randomInt() + ".png");
                    FileTool.saveFile(avatar, Network.httpGetRaw(event.getGroup().get(qqId).getAvatarUrl()));
                    if (FileFormat.isFormat(avatar, "gif")) {
                        runGenerateDynamic(avatar, event, functionMap.get(cmd));
                    } else {
                        runGenerateStatic(avatar, event, functionMap.get(cmd));          
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

        private ImageProcessNetwok() {
            functionMap = Collections.unmodifiableMap(new HashMap<String,BiConsumer<Image,GroupMessageEvent>>(){
                    {
                        put("sp", new BiConsumer<Image,GroupMessageEvent>(){

                                @Override
                                public void accept(Image simg, GroupMessageEvent event) {
                                    try {
                                        SauceNaoResult mResults = SauceNaoApi.getSauce(new URL(entity.getUrl(simg)).openStream());
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
                        put("porn", new BiConsumer<Image,GroupMessageEvent>(){

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
                        put("ocr", new BiConsumer<Image,GroupMessageEvent>(){

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
                        put("dtag", new BiConsumer<Image,GroupMessageEvent>(){

                                @Override
                                public void accept(Image img, GroupMessageEvent event) {
                                    Map<String,Float> map = DeepDanbooruApi.search(entity.getUrl(img));
                                    if (map == null) {
                                        sendQuote(event, "连接失败");
                                        return;
                                    }
                                    StringBuilder builder = new StringBuilder();
                                    for (Map.Entry<String,Float> entry : map.entrySet()) {
                                        builder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
                                    }
                                    builder.setLength(builder.length() - 1);
                                    sendQuote(event, builder.toString());
                                }
                            });
                    }
                });
        }

        @Override
        public boolean onGroupMessage(GroupMessageEvent event) {
            return onGroupMessage(event, event.getSender().getId(), event.getMessage().get(1).contentToString());
        }

        public boolean onGroupMessage(GroupMessageEvent event, long qqId, String cmd) {
            MessageChain message = event.getMessage();
            Image miraiImg = message.get(Image.Key);
            if (miraiImg == null) {
                FlashImage fi = message.get(FlashImage.Key);
                if (fi != null) {
                    miraiImg = fi.getImage();
                }
            }
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
                                    return ImageFactory.getInstance().generateMirror(p1, 1);
                                }
                            });
                        put("左右翻转", new Function<BufferedImage,BufferedImage>(){

                                @Override
                                public BufferedImage apply(BufferedImage p1) {
                                    return ImageFactory.getInstance().generateMirror(p1, 0);
                                }
                            });
                        put("天壤梦弓", new Function<BufferedImage,BufferedImage>(){

                                @Override
                                public BufferedImage apply(BufferedImage p1) {
                                    return ImageFactory.getInstance().generateMirror(p1, 2);
                                }
                            });
                    }
                });  
        }

        @Override
        public boolean onGroupMessage(GroupMessageEvent event) {
            return onGroupMessage(event, event.getSender().getId(), event.getMessage().get(1).contentToString());
        }

        public boolean onGroupMessage(GroupMessageEvent event, long qqId, String cmd) {
            MessageChain message = event.getMessage();
            Image miraiImg = message.get(Image.Key);
            if (miraiImg == null) {
                FlashImage fi = message.get(FlashImage.Key);
                if (fi != null) {
                    miraiImg = fi.getImage();
                }
            }
            try {    
                if (miraiImg != null && functionMap.containsKey(cmd)) {
                    File imageFile = SJFPathTool.getImagePath("gen/" + SJFRandom.randomInt() + ".png");
                    FileTool.saveFile(imageFile, Network.httpGetRaw(entity.getUrl(miraiImg)));
                    if (FileFormat.isFormat(imageFile, "gif")) {
                        if (cmd.equals("天壤梦弓")) {
                            sendMessage(event, entity.toImage(SeijaImageFactory.reverseGIF(imageFile, 2), event.getGroup()));
                        } else {
                            runGenerateDynamic(imageFile, event, functionMap.get(cmd));
                        }
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
                    File imageFile = SJFPathTool.getImagePath("gen/" + SJFRandom.randomInt() + ".png");
                    FileTool.saveFile(imageFile, Network.httpGetRaw(entity.getUrl(miraiImg)));
                    if (FileFormat.isFormat(imageFile, "gif")) {
                        if (cmd.equals("天壤梦弓")) {
                            sendMessage(event, entity.toImage(SeijaImageFactory.reverseGIF(imageFile, 2), event.getGroup()));
                        } else {
                            runGenerateDynamic(imageFile, event, functionMap.get(cmd));
                        }
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
        BufferedImage result = function.apply(ImageIO.read(imageFile));
        File tmp = SJFPathTool.getImagePath("gen/" + SJFRandom.randomInt() + ".png");
        FileTool.createFile(tmp);
        ImageIO.write(result, "png", tmp);
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
        File gifFile = SJFPathTool.getImagePath("gen/" + SJFRandom.randomInt() + ".gif");
        FileTool.saveFile(gifFile, baos.toByteArray());
        Image resultImage = entity.toImage(gifFile, event.getGroup());
        sendMessage(event, resultImage);
        imageFile.delete();
    }

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
}
