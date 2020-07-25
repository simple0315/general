package com.simple.general.utils;

import net.coobird.thumbnailator.Thumbnails;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64Utils {
    // 加密
    public static String getBase64(byte[] b) {
        String s;
        s = new BASE64Encoder().encode(b);
        return s.replace("\r\n", "").replace("\r", "").replace("\n", "");
    }

    // 解密
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     *  通过图片的url获取图片的base64字符串
     * @param imgUrl 图片url
     * @return java.lang.String 返回图片base64的字符串
     * @author Mr.Wu
     * @date 2020/4/19 20:38
     */
    public static String imgUrlToBase64(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return encode(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return imgUrl;
    }

    /**
     *  图片转字符串
     * @param image 图片byte数组
     * @return java.lang.String
     * @author Mr.Wu
     * @date 2020/4/19 20:38
     */
    public static String encode(byte[] image) {
        BASE64Encoder decoder = new BASE64Encoder();
        return replaceEnter(decoder.encode(image));
    }

    public static String replaceEnter(String str) {
        String reg = "[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 图片存入指定目录
     *
     * @param imgStr 图片Base64编码
     * @return boolean
     * @author wcy
     * @date 2019/8/28 15:03
     */
    public static boolean generateImage(String imgStr, String imagePath) {
        //对字节数组字符串进行Base64解码并生成图片
        //图像数据为空
        if (imgStr == null || "".equals(imgStr)) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        //新生成的图片
        File file = new File(imagePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            boolean b = parentFile.mkdirs();
            if (!b) {
                System.out.println("生成目录失败");
            }
        }
        try (OutputStream out = new FileOutputStream(file)) {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
            out.flush();
            Thumbnails.of(file).scale(1).toFile(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据图片路径将图片转换为base64编码字符串
     *
     * @param imgFile 图片地址
     * @return java.lang.String
     * @author wcy
     * @date 2019/7/31 17:43
     */
    public static String getImgBase64Str(String imgFile) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imgFile);
            byte[] data = new byte[inputStream.available()];
            int read = inputStream.read(data);
            if (read > 0) {
                // 加密
                BASE64Encoder encoder = new BASE64Encoder();
                String encode = encoder.encode(data);
                return replaceEnter(encode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;
    }

    /**
     * 根据路径下载图片
     *
     * @param urlList 图片路径
     * @param path    图片存储位置
     * @author wcy
     * @date 2019/8/2 10:48
     */
    public static void imageDownload(String urlList, String path) {
        URL url;
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(urlList));

             FileOutputStream fileOutputStream = new FileOutputStream(path);) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] bytes = new byte[1024];
            int length;

            while ((length = dataInputStream.read(bytes)) > 0) {
                byteArrayOutputStream.write(bytes, 0, length);
            }
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] base64ToByteArray(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = new byte[0];
        try {
            bytes = decoder.decodeBuffer(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static void main(String[] args) {
        byte[] bytes = base64ToByteArray("fLG4+By7g9zQj7hhmUkpuYnHwMAruPTzqfUViYttYCLgFqLCMVEzCd7Nc78Z/yuTEgOsgu2oRwVWfFaoep9AYdTRUJBbdSsww6tkiIHqkePhoUALNrB+73j+Ft0iHUQWFmL+9PWVaUrLayFmgqhGUbX2TaNqBsCOK9oS5b4gpsQN0L5/HqD3xmyEHdd2ege4sW1UeIExZUqHPztKEM11CpBE1ykGtaxpGeSBTTDdPEsnKqKOvfuq5NVYYCJwFiuU+jwUlJf7A3019Wkd8HBZX6L91xRxvOkTRv/8m7f4qwUiIVjqJAFhbv/Q4BMJZ/+oZNeSm6EgL9COLrPabyZGLA==");
        System.out.println(bytes.length);
    }
}
