package com.Gallifrey.springboot00.QRCode;
/**
 * Copyright (C) 2018 ChenShaojian (mrcsj@tom.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.swetake.util.Qrcode;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class QRCodeEncoder {

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //String imgPath = "D:/101.jpg";
        StringBuilder imgPath = new StringBuilder() ;
        imgPath.append("E:/QRCodeImg/qrTest").append(1).append(".png");
        StringBuilder content = new StringBuilder();
        content.append("http://101.43.65.22:9001/info?id=1");


        //生成普通二维码：名片
        QRCodeEncoder encoder = new QRCodeEncoder();
        encoder.encoderQRCode(String.valueOf(content), String.valueOf(imgPath), "png", "UTF-8", 4, null, null, null, 6, null, null);

        //加上logo
        //1@param content 2@param imgPath 3@param imgType 4@param charater 5@param size 6@param border 7@param front 8@param back 9@param scale 10@param logoPath 11@param animtePath
        //encoder.encoderQRCode(content, imgPath, "gif", "utf-8", 5, 10, Color.BLACK, Color.WHITE, 4, "d:/g.gif",null); //"d:/test2.gif"
        //encoder.encoderQRCode(content, imgPath, null, null, 5, null, Color.BLACK, null, 6, "D:/logo.jpg", "", "", null, "D:/图片模板/bg2.jpg");
        System.out.println("ok");
        //加背景图生成
        //File bkfile = new File("d:/00.jpg");
        //BufferedImage bk = ImageIO.read(bkfile);
        ////1@param content 2@param imgPath 3@param imgType 4@param charater 5@param size 6@param front 7@param back 8@param dotColor 9@param bkfile 10@param regStr 11@param isCheck 12@param rount
        //boolean ret = encoder.encoderQRCode(content, imgPath, null, null, 9, Color.BLUE, null, null, bk, null, 0, 1); //size=9-11
        //System.out.println("encoder QRcode success:"+ret);

    }

    /**
     * 生成普通二维码
     * @param content 内容
     * @param imgPath 生成文件路径
     * @param imgType 文件类型(默认png)
     * @param charater 字符集（默认UTF-8）
     * @param size 二维码大小（默认4）
     * @param border 边框大小（默认10）
     * @param front 前景色（默认黑色）
     * @param back 背景色（默认白色）
     * @param scale 图标比例（默认1：4）
     * @param logoPath 图标路径
     * @param animtePath 生成动画路径（gif文件）
     */
    public void encoderQRCode(String content, String imgPath, String imgType, String charater,
                              Integer size, Integer border, Color front, Color back,
                              Integer scale, String logoPath, String animtePath) {
        try {
            if(isNull(content)) content = "test";
            if(isNull(imgPath)) imgPath = "c:/test.png";
            if(isNull(imgType)) imgType = "png";
            if(isNull(charater)) charater = "UTF-8";
            if(isNull(size)) size = 4;
            if(isNull(border)) border = 10;
            if(isNull(front)) front = Color.BLACK;
            if(isNull(back)) back = Color.WHITE;
            if(isNull(scale)) scale = 4;

            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            //qrcodeHandler.setQrcodeVersion(3);

            byte[] cb = content.getBytes(charater);

            int lineWidth = size;
            int width = 0;
            boolean[][] s = qrcodeHandler.calQrcode(cb);
            for (int j = 0; j < s.length; j++) {
                if (s[j][0] != false) {
                    width = j * lineWidth + border;
                }
            }
            width += border + lineWidth;

            BufferedImage bi = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.setBackground(back);
            g2d.clearRect(0, 0, width, width);
            g2d.setColor(front);

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        g2d.fillRect(j * lineWidth + border, i * lineWidth + border, lineWidth, lineWidth);
                    }
                }
            }

            g2d.dispose();
            bi.flush();

            //放入图标
            if(!isNull(logoPath) && isNull(animtePath)){
                File inputFile = new File(logoPath);
                BufferedImage logo = ImageIO.read(inputFile);
                bi = modifyImagetogeter(logo, bi, scale);
            }

            if(!isNull(logoPath) && !isNull(animtePath)){
                //生成动画
                File inputFile = new File(logoPath);
                BufferedImage logo = ImageIO.read(inputFile);
                generateGif(logo, bi, animtePath, scale);
            }else{
                // 生成二维码QRCode图片
                File imgFile = new File(imgPath);
                ImageIO.write(bi, imgType, imgFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码（顶底可带文字,定点变色)
     * @param content 内容
     * @param imgPath 生成文件路径
     * @param imgType 文件类型
     * @param charater 字符集（默认UTF-8）
     * @param size 二维码大小（默认4）
     * @param border 边框大小（默认20）
     * @param front 前景色（默认黑色）
     * @param back 背景色（默认白色）
     * @param scale 图标比例（默认1：4）
     * @param logoPath 图标路径
     * @param topText 顶上文本
     * @param downText 底部文本
     * @param dotChange 定点变色
     */
    public void encoderQRCode(String content, String imgPath, String imgType, String charater,
                              Integer size, Integer border, Color front, Color back,
                              Integer scale, String logoPath, String topText, String downText, Integer dotChange) {
        try {
            if(isNull(content)) content = "test";
            if(isNull(imgPath)) imgPath = "c:\test.jpg";
            if(isNull(imgType)) imgType = "png";
            if(isNull(charater)) charater = "UTF-8";
            if(isNull(size)) size = 4;
            if(isNull(border)) border = 20;
            if(isNull(front)) front = Color.BLACK;
            if(isNull(back)) back = Color.WHITE;
            if(isNull(scale)) scale = 4;

            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            //qrcodeHandler.setQrcodeVersion(3); //7

            byte[] cb = content.getBytes(charater);

            int lineWidth = size;
            int width = 0;
            boolean[][] s = qrcodeHandler.calQrcode(cb);
            for (int j = 0; j < s.length; j++) {
                if (s[j][0] != false) {
                    width = j * lineWidth + border;
                }
            }
            width += border + lineWidth;

            BufferedImage bi = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.setBackground(back);
            g2d.clearRect(0, 0, width, width);
            g2d.setColor(front);

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        if(dotChange != null && dotChange ==2){
                            if(i>=2 && i<=4 && j>=2 && j<=4)
                                g2d.setColor(Color.BLUE);
                            else if(i>=2 && i<=4 && j>=s.length-5 && j<=s.length-3)
                                g2d.setColor(Color.GREEN);
                            else if(i>=s.length-5 && i<=s.length-3 && j>=2 && j<=4)
                                g2d.setColor(Color.RED);
                            else
                                g2d.setColor(front);
                        }else if(dotChange != null && dotChange ==3){
                            if(i>=2 && i<=4 && j>=2 && j<=4)
                                g2d.setColor(Color.GREEN);
                            else if(i>=2 && i<=4 && j>=s.length-5 && j<=s.length-3)
                                g2d.setColor(Color.RED);
                            else if(i>=s.length-5 && i<=s.length-3 && j>=2 && j<=4)
                                g2d.setColor(Color.BLUE);
                            else
                                g2d.setColor(front);
                        }else if(dotChange != null){
                            if(i>=2 && i<=4 && j>=2 && j<=4)
                                g2d.setColor(Color.RED);
                            else if(i>=2 && i<=4 && j>=s.length-5 && j<=s.length-3)
                                g2d.setColor(Color.BLUE);
                            else if(i>=s.length-5 && i<=s.length-3 && j>=2 && j<=4)
                                g2d.setColor(Color.GREEN);
                            else
                                g2d.setColor(front);
                        }
                        g2d.fillRect(j * lineWidth + border, i * lineWidth + border, lineWidth, lineWidth);
                    }
                }
            }

            //顶边加文本
            if(!isNull(topText)){
                //g2d.drawString(topText, border, border/2 + 5);
                g2d.drawString(topText, (width-topText.length()*12)/2, border/2 + 5);
            }

            //底边加文本
            if(!isNull(downText)){
                g2d.setFont(new Font("宋体", 0, 12));
                //g2d.drawString(downText, border, bi.getHeight() - (border/2) + 5);
                g2d.drawString(downText, (width-downText.length()*6)/2, bi.getHeight() - (border/2) + 5);
            }

            g2d.dispose();
            bi.flush();

            //放入图标
            if(!isNull(logoPath)){
                File inputFile = new File(logoPath);
                BufferedImage logo = ImageIO.read(inputFile);
                bi = modifyImagetogeter(logo, bi, scale);
            }

            // 生成二维码QRCode图片
            File imgFile = new File(imgPath);
            ImageIO.write(bi, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码（以图片做色调)
     * @param content 内容
     * @param imgPath 生成文件路径
     * @param imgType 文件类型
     * @param charater 字符集（默认UTF-8）
     * @param size 二维码大小（默认4）
     * @param border 边框大小（默认20）
     * @param front 前景色（默认黑色）
     * @param back 背景色（默认白色）
     * @param scale 图标比例（默认1：4）
     * @param logoPath 图标路径
     * @param topText 顶上文本
     * @param downText 底部文本
     * @param dotColor 标志位颜色
     * @param backFile 色调图片
     */
    public void encoderQRCode(String content, String imgPath, String imgType, String charater,
                              Integer size, Integer border, Color front, Color back, Integer scale,
                              String logoPath, String topText, String downText, Color dotColor, String backFile) {
        try {
            if(isNull(content)) content = "test";
            if(isNull(imgPath)) imgPath = "c:/test.png";
            if(isNull(imgType)) imgType = "png";
            if(isNull(charater)) charater = "UTF-8";
            if(isNull(size)) size = 4;
            if(isNull(border)) border = size * 2;
            if(isNull(front)) front = Color.BLACK;
            if(isNull(back)) back = Color.WHITE;
            if(isNull(scale)) scale = 5;


            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            //qrcodeHandler.setQrcodeVersion(2); //7

            byte[] cb = content.getBytes(charater);

            int lineWidth = size;
            int width = 0;
            boolean[][] s = qrcodeHandler.calQrcode(cb);
            for (int j = 0; j < s.length; j++) {
                if (s[j][0] != false) {
                    width = j * lineWidth + border;
                }
            }
            width += border + lineWidth;

            BufferedImage bi = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.setBackground(back);
            g2d.clearRect(0, 0, width, width);

            BufferedImage bk = null;
            BufferedImage buf = null;
            if(!isNull(backFile)){
                File bkfile = new File(backFile);
                bk = ImageIO.read(bkfile);
                if(bk != null){//按二维码大小缩放背景图
                    buf = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
                    buf.getGraphics().drawImage(bk, 0, 0, width, width, null);
                }
            }

            //g2d.setColor(front);

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        int x = j * lineWidth + border;
                        int y = i * lineWidth + border;
                        if(buf != null){//使用背景图配置前景色
                            int[] rgb = new int [3];
                            //取新图的颜色
                            int pixel = buf.getRGB(x, y);
                            rgb[0] = (pixel & 0xff0000) >> 16;
                            rgb[1] = (pixel & 0xff00) >> 8;
                            rgb[2] = (pixel & 0xff);

                            front = new Color(rgb[0],rgb[1],rgb[2]);
                        }

                        if(dotColor != null){
                            if(i>=2 && i<=4 && j>=2 && j<=4)
                                g2d.setColor(dotColor);
                            else if(i>=2 && i<=4 && j>=s.length-5 && j<=s.length-3)
                                g2d.setColor(dotColor);
                            else if(i>=s.length-5 && i<=s.length-3 && j>=2 && j<=4)
                                g2d.setColor(dotColor);
                            else
                                g2d.setColor(front);
                        }else
                            g2d.setColor(front);

                        g2d.fillRect(x, y, lineWidth, lineWidth);
                        //g2d.fillRoundRect(x, y, lineWidth, lineWidth, 3, 3);
                    }
                }
            }

            //顶边加文本
            if(!isNull(topText)){
                //g2d.drawString(topText, border, border/2 + 5);
                g2d.drawString(topText, (width-topText.length()*12)/2, border/2 + 5);
            }

            //底边加文本
            if(!isNull(downText)){
                g2d.setFont(new Font("宋体", 0, 12));
                //g2d.drawString(downText, border, bi.getHeight() - (border/2) + 5);
                g2d.drawString(downText, (width-downText.length()*6)/2, bi.getHeight() - (border/2) + 5);
            }

            g2d.dispose();
            bi.flush();

            //放入图标
            if(!isNull(logoPath)){
                File inputFile = new File(logoPath);
                BufferedImage logo = ImageIO.read(inputFile);
                bi = modifyImagetogeter(logo, bi, scale);
            }

            // 生成二维码QRCode图片
            File imgFile = new File(imgPath);
            ImageIO.write(bi, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成二维码（加入背景图生成)
     * @param content 内容
     * @param imgPath 生成文件路径
     * @param imgType 文件类型
     * @param charater 字符集（默认UTF-8）
     * @param size 二维码大小（默认4）
     * @param front 前景色（默认黑色）
     * @param back 背景色（默认白色）
     * @param dotColor 标志位颜色
     * @param backFile 背景图片
     * @param regStr 内容检测规则
     * @param isCheck 校验选项
     * @param rount 圆角选项
     */
    public boolean encoderQRCode(String content, String imgPath, String imgType, String charater,
                                 Integer size, Color front, Color back,Color dotColor, BufferedImage backFile,
                                 String regStr, Integer isCheck, Integer rount) {
        try {
            if(isNull(content)) content = "test";
            if(isNull(imgPath)) imgPath = "c:\test.png";
            if(isNull(imgType)) imgType = "png";
            if(isNull(charater)) charater = "UTF-8";
            if(isNull(size)) size = 4;
            if(isNull(front)) front = Color.BLACK;
            if(isNull(dotColor)) dotColor = front;
            if(isNull(back)) back = Color.WHITE;
            if(isNull(regStr)) regStr = "^http://(\\w*\\.){2,4}(\\w*\\:*\\d*/){1}([a-z]*/){3,4}\\d{1,}$";


            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            //qrcodeHandler.setQrcodeVersion(5); //7

            int border = size * 2;
            byte[] cb = content.getBytes(charater);

            int lineWidth = size;
            int width = 0;
            boolean[][] s = qrcodeHandler.calQrcode(cb);
            for (int j = 0; j < s.length; j++) {
                if (s[j][0] != false) {
                    width = j * lineWidth + border;
                }
            }
            width += border + lineWidth;

            BufferedImage bi = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.setBackground(back);
            g2d.clearRect(0, 0, width, width);

            //加入背景图
            BufferedImage buf = null;
            if(!isNull(backFile)){
                buf = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);//按二维码大小缩放背景图
                buf.getGraphics().drawImage(backFile, 0, 0, width, width, null);

                g2d.drawImage(buf, 0, 0, width, width, Color.WHITE, null);
            }

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        int x = j * lineWidth + border;
                        int y = i * lineWidth + border;

                        if(i>=0 && i<=6 && j>=0 && j<=6){//三个定点周边留白
                            g2d.setComposite(AlphaComposite.SrcOver.derive(0.75f));
                            g2d.setColor(dotColor);
                            g2d.fillRect(x, y, lineWidth, lineWidth);
                            //白底
                            g2d.setColor(Color.WHITE);
                            //左侧
                            if(j == 0 ){
                                //外
                                g2d.fillRect(x-lineWidth, y, lineWidth, lineWidth);
                                //内
                                if(i < 5)
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                            //上侧
                            if(i == 0 ){
                                //外
                                g2d.fillRect(x-lineWidth, y-lineWidth, lineWidth, lineWidth);
                                if(j == 6 )
                                    g2d.fillRect(x, y-lineWidth, lineWidth, lineWidth);
                                //内
                                if(j >0 && j < 5)
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                            //下侧
                            if(i == 6 ){
                                //外
                                g2d.fillRect(x-lineWidth, y+lineWidth, lineWidth, lineWidth);
                                if(j == 6 )
                                    g2d.fillRect(x, y+lineWidth, lineWidth, lineWidth);
                                //内
                                if(j > 0 && j < 5 )
                                    g2d.fillRect(x+lineWidth, y-lineWidth, lineWidth, lineWidth);
                            }
                            //右侧
                            if(j == 6 ){
                                //外
                                g2d.fillRect(x+lineWidth, y-lineWidth, lineWidth, lineWidth);
                                if(i == 6 ){
                                    g2d.fillRect(x+lineWidth, y, lineWidth, lineWidth);
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                                }
                                //内
                                if(i > 0 && i < 4)
                                    g2d.fillRect(x-lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                        }
                        else if(i>=0 && i<=6 && j>=s.length-7 && j<=s.length-1){
                            g2d.setComposite(AlphaComposite.SrcOver.derive(0.75f));
                            g2d.setColor(dotColor);
                            g2d.fillRect(x, y, lineWidth, lineWidth);
                            //白底
                            g2d.setColor(Color.WHITE);
                            //左侧
                            if(j == s.length-7 ){
                                //外
                                g2d.fillRect(x-lineWidth, y, lineWidth, lineWidth);

                                //内
                                if(i < 5 )
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                            //上侧
                            if(i == 0 ){
                                //外
                                g2d.fillRect(x-lineWidth, y-lineWidth, lineWidth, lineWidth);
                                if(j == s.length-1 )
                                    g2d.fillRect(x, y-lineWidth, lineWidth, lineWidth);
                                //内
                                if(j > s.length -7 && j < s.length-2 )
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                            //下侧
                            if(i == 6 ){
                                //外
                                g2d.fillRect(x-lineWidth, y+lineWidth, lineWidth, lineWidth);
                                if(j == s.length-1 )
                                    g2d.fillRect(x, y+lineWidth, lineWidth, lineWidth);
                                //内
                                if(j > s.length -7 && j < s.length-2 )
                                    g2d.fillRect(x+lineWidth, y-lineWidth, lineWidth, lineWidth);
                            }
                            //右侧
                            if(j == s.length-1 ){
                                //外
                                g2d.fillRect(x+lineWidth, y-lineWidth, lineWidth, lineWidth);
                                if(i == 6 ){
                                    g2d.fillRect(x+lineWidth, y, lineWidth, lineWidth);
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                                }
                                //内
                                if(i > 0 && i < 4)
                                    g2d.fillRect(x-lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                        }
                        else if(i>=s.length-7 && i<=s.length-1 && j>=0 && j<=6){
                            g2d.setComposite(AlphaComposite.SrcOver.derive(0.75f));
                            g2d.setColor(dotColor);
                            g2d.fillRect(x, y, lineWidth, lineWidth);
                            //白底
                            g2d.setColor(Color.WHITE);
                            //左侧
                            if(j == 0 ){
                                //外
                                g2d.fillRect(x-lineWidth, y, lineWidth, lineWidth);
                                //内
                                if(i < s.length-2 )
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                            //上侧
                            if(i == s.length-7 ){
                                //外
                                g2d.fillRect(x-lineWidth, y-lineWidth, lineWidth, lineWidth);
                                if(j == 6 )
                                    g2d.fillRect(x, y-lineWidth, lineWidth, lineWidth);
                                //内
                                if(j > 0 && j < 5 )
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                            //下侧
                            if(i == s.length-1 ){
                                //外
                                g2d.fillRect(x-lineWidth, y+lineWidth, lineWidth, lineWidth);
                                if(j == 6 )
                                    g2d.fillRect(x, y+lineWidth, lineWidth, lineWidth);
                                //内
                                if(j > 0 && j < 5 )
                                    g2d.fillRect(x+lineWidth, y-lineWidth, lineWidth, lineWidth);
                            }
                            //右侧
                            if(j == 6 ){
                                //外
                                g2d.fillRect(x+lineWidth, y-lineWidth, lineWidth, lineWidth);
                                if(i == s.length-1 ){
                                    g2d.fillRect(x+lineWidth, y, lineWidth, lineWidth);
                                    g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth, lineWidth);
                                }
                                //内
                                if(i > s.length-7 && i < s.length-2 )
                                    g2d.fillRect(x-lineWidth, y+lineWidth, lineWidth, lineWidth);
                            }
                        }//点阵旁边留白
                        else{
                            if(!isNull(rount) && rount == 1){//圆
                                g2d.setColor(front);
                                g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
                                g2d.fillRoundRect(x, y, lineWidth*2/3,lineWidth*2/3, lineWidth*1/3, lineWidth*1/3);

                                //白底
                                g2d.setColor(Color.WHITE);
                                g2d.fillRoundRect(x+lineWidth, y, lineWidth*2/3,lineWidth*2/3, lineWidth*1/3, lineWidth*1/3);
                                g2d.fillRoundRect(x, y+lineWidth, lineWidth*2/3,lineWidth*2/3, lineWidth*1/3, lineWidth*1/3);
                                g2d.fillRoundRect(x+lineWidth, y+lineWidth, lineWidth*2/3,lineWidth*2/3, lineWidth*1/3, lineWidth*1/3);
                            }else{
                                g2d.setColor(front);
                                g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
                                g2d.fillRect(x, y, lineWidth*2/3,lineWidth*2/3);

                                //白底
                                g2d.setColor(Color.WHITE);
                                g2d.fillRect(x+lineWidth, y, lineWidth*2/3,lineWidth*2/3);
                                g2d.fillRect(x, y+lineWidth, lineWidth*2/3,lineWidth*2/3);
                                g2d.fillRect(x+lineWidth, y+lineWidth, lineWidth*2/3,lineWidth*2/3);
                            }
                        }
                    }
                }
            }

            g2d.dispose();
            bi.flush();

            //检测二维码
            if(!isNull(isCheck) && isCheck ==1
                    && !checkCode(bi, regStr)){
                return false;
            }

            // 生成二维码QRCode图片
            File imgFile = new File(imgPath);
            ImageIO.write(bi, imgType, imgFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 增加二维码版本控制
     * @param content
     * @param imgPath
     * @param imgType
     * @param charater
     * @param size
     * @param border
     * @param front
     * @param back
     * @param scale
     * @param logoPath
     * @param topText
     * @param downText
     * @param dotChange
     * @param codeVersion //二维码版本
     */
    public void encoderQRCode(String content, String imgPath, String imgType, String charater,
                              Integer size, Integer border, Color front, Color back,
                              Integer scale, String logoPath, String topText, String downText, Integer dotChange,
                              Integer codeVersion) {
        try {
            if(isNull(content)) content = "test";
            if(isNull(imgPath)) imgPath = "c:\test.png";
            if(isNull(imgType)) imgType = "png";
            if(isNull(charater)) charater = "UTF-8";
            if(isNull(size)) size = 4;
            if(isNull(border)) border = 20;
            if(isNull(front)) front = Color.BLACK;
            if(isNull(back)) back = Color.WHITE;
            if(isNull(scale)) scale = 4;
            if(isNull(codeVersion)) codeVersion = 3;

            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            qrcodeHandler.setQrcodeVersion(codeVersion); //1-7

            byte[] cb = content.getBytes(charater);

            int lineWidth = size;
            int width = 0;
            boolean[][] s = qrcodeHandler.calQrcode(cb);
            for (int j = 0; j < s.length; j++) {
                if (s[j][0] != false) {
                    width = j * lineWidth + border;
                }
            }
            width += border + lineWidth;

            BufferedImage bi = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.setBackground(back);
            g2d.clearRect(0, 0, width, width);
            g2d.setColor(front);

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        if(dotChange != null && dotChange ==2){
                            if(i>=2 && i<=4 && j>=2 && j<=4)
                                g2d.setColor(Color.BLUE);
                            else if(i>=2 && i<=4 && j>=s.length-5 && j<=s.length-3)
                                g2d.setColor(Color.GREEN);
                            else if(i>=s.length-5 && i<=s.length-3 && j>=2 && j<=4)
                                g2d.setColor(Color.RED);
                            else
                                g2d.setColor(front);
                        }else if(dotChange != null && dotChange ==3){
                            if(i>=2 && i<=4 && j>=2 && j<=4)
                                g2d.setColor(Color.GREEN);
                            else if(i>=2 && i<=4 && j>=s.length-5 && j<=s.length-3)
                                g2d.setColor(Color.RED);
                            else if(i>=s.length-5 && i<=s.length-3 && j>=2 && j<=4)
                                g2d.setColor(Color.BLUE);
                            else
                                g2d.setColor(front);
                        }else if(dotChange != null){
                            if(i>=2 && i<=4 && j>=2 && j<=4)
                                g2d.setColor(Color.RED);
                            else if(i>=2 && i<=4 && j>=s.length-5 && j<=s.length-3)
                                g2d.setColor(Color.BLUE);
                            else if(i>=s.length-5 && i<=s.length-3 && j>=2 && j<=4)
                                g2d.setColor(Color.GREEN);
                            else
                                g2d.setColor(front);
                        }
                        g2d.fillRect(j * lineWidth + border, i * lineWidth + border, lineWidth, lineWidth);
                    }
                }
            }

            //顶边加文本
            if(!isNull(topText)){
                //g2d.drawString(topText, border, border/2 + 5);
                g2d.drawString(topText, (width-topText.length()*12)/2, border/2 + 5);
            }

            //底边加文本
            if(!isNull(downText)){
                g2d.setFont(new Font("宋体", 0, 12));
                //g2d.drawString(downText, border, bi.getHeight() - (border/2) + 5);
                g2d.drawString(downText, (width-downText.length()*8)/2, bi.getHeight() - (border/2) + 5);
            }

            g2d.dispose();
            bi.flush();

            //放入图标
            if(!isNull(logoPath)){
                File inputFile = new File(logoPath);
                BufferedImage logo = ImageIO.read(inputFile);
                bi = modifyImagetogeter(logo, bi, scale);
            }

            // 生成二维码QRCode图片
            File imgFile = new File(imgPath);
            ImageIO.write(bi, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加入图标
     * @param logo
     * @param code
     * @param scale
     * @return
     */
    public BufferedImage modifyImagetogeter(BufferedImage logo, BufferedImage code, Integer scale) {
        BufferedImage buf = new BufferedImage(code.getWidth(), code.getHeight(), BufferedImage.TYPE_INT_RGB);
        try {
            int h = code.getHeight() / scale;
            int w = logo.getWidth() * h / logo.getHeight();

            Graphics2D g = buf.createGraphics();
            g.drawImage(code, 0, 0, code.getWidth(), code.getHeight(), null);
            g.drawImage(logo, (code.getWidth()-w)/2,(code.getHeight()-h)/2, w, h, null);
            g.dispose();
            buf.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buf;
    }


    /**
     * 生成动画
     * @param logo
     * @param code
     * @param animtePath
     * @param scale
     */
    public void generateGif(BufferedImage logo, BufferedImage code, String animtePath, Integer scale){
        try{
            BufferedImage src0 = modifyImagetogeter(logo, code, scale);
            BufferedImage src1 = modifyImagetogeter(logo, code, scale/2);
            BufferedImage src2 = modifyImagetogeter(logo, code, scale/4);
            AnimatedGifEncoder e = new AnimatedGifEncoder();
            e.setRepeat(0);
            e.start(animtePath);
            e.setDelay(300); // 1 frame per sec
            e.addFrame(src0);
            e.setDelay(300);
            e.addFrame(src1);
            e.setDelay(300);
            e.addFrame(src2);
            e.finish();

        }catch(Exception er){
            er.printStackTrace();
        }
    }


    /**
     * 判断对象是否为空
     */
    public boolean isNull(Object value) {
        if (value == null) {
            return true;
        } else if(value instanceof String){
            if(((String)value).trim().replaceAll("\\s", "").equals("")){
                return true;
            }
        }else if(value instanceof Collection) {
            if(((Collection)value).isEmpty()){
                return true;
            }
        } else if(value.getClass().isArray()) {
            if(Array.getLength(value) == 0){
                return true;
            }
        } else if(value instanceof Map) {
            if(((Map)value).isEmpty()){
                return true;
            }
        }else {
            return false;
        }
        return false;

    }


    /**
     * 检测二维码
     * @param bi
     * @param regStr
     * @return
     */
    public boolean checkCode(BufferedImage bi, String regStr){
        if(isNull(regStr)) regStr = "^http://(\\w*\\.){2,4}(\\w*\\:*\\d*/){1}([a-z]*/){3,4}\\d{1,}$";

        try{
            //File inputFile = new File(codeFile);
            //BufferedImage bi = ImageIO.read(inputFile);
            QRCodeDecoder decoder = new QRCodeDecoder();
            String decodedData = new String(decoder.decode(new J2SEImage(bi)));
            //System.out.println(decodedData);

            if(isNull(decodedData))
                return false;
            else if(decodedData.length() > 0 && !Pattern.matches(regStr,decodedData))
                return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 解码
     * @param bi
     * @return
     */
    public String deCode(BufferedImage bi){
        String decodedData = null;
        try{
            QRCodeDecoder decoder = new QRCodeDecoder();
            decodedData = new String(decoder.decode(new J2SEImage(bi)));

        }catch(Exception e){
            e.printStackTrace();
        }
        return decodedData;
    }

    class J2SEImage implements QRCodeImage {

        BufferedImage bufImg;

        public J2SEImage(BufferedImage bufImg) {

            this.bufImg = bufImg;

        }

        public int getWidth() {

            return bufImg.getWidth();

        }

        public int getHeight() {

            return bufImg.getHeight();

        }



        public int getPixel(int x, int y) {

            return bufImg.getRGB(x, y);

        }

    }
}
