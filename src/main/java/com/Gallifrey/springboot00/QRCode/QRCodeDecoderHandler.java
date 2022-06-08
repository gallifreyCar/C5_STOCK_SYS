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
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeDecoderHandler {


    public static void main(String[] args) throws Exception{

        QRCodeDecoderHandler handler = new QRCodeDecoderHandler();
        String imgPath = "E:/QRCodeImg/qr";

        String decoderContent = handler.decoderQRCode(imgPath);

        System.out.println("解析结果如下：");

        //String input = new String(decoderContent.getBytes("gbk"), "UTF-8");

        System.out.println(decoderContent);

        //System.out.println("========decoder success!!!");

    }


    public String decoderQRCode(String imgPath) {
        // QRCode 二维码图片的文件

        File imageFile = new File(imgPath);
        BufferedImage bufImg = null;
        String decodedData = null;

        try {

            bufImg = ImageIO.read(imageFile);
            QRCodeDecoder decoder = new QRCodeDecoder();
            decodedData = new String(decoder.decode(new J2SEImage(bufImg)));

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

        } catch (DecodingFailedException dfe) {
            System.out.println("Error: " + dfe.getMessage());
            dfe.printStackTrace();
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
