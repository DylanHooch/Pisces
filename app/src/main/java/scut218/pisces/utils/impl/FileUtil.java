package scut218.pisces.utils.impl;


import java.io.*;

import scut218.pisces.base.MyApplication;

import static java.lang.Thread.sleep;

public class FileUtil {
    private static String filePath= MyApplication.getContext().getFilesDir().getPath();
//    private static String filePath="F:\\";
        /**
         * 保存到手机内存设备中
         *
         * @param bytes,fileName
         */
        public static String savePhoto(byte[] bytes,String fileName) {
            // 创建String对象保存文件名路径
            try {
                // 创建指定路径的文件

                File file = new File(filePath+fileName);
                // 如果文件不存在
                if (file.exists()) {

                    file.delete();
                }
                file.createNewFile();
                // 获取文件的输出流对象
                FileOutputStream outStream = new FileOutputStream(file);
                // 获取字符串对象的byte数组并写入文件流
                outStream.write(bytes);
                // 最后关闭文件输出流
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    return filePath+fileName;
        }
        /**
         * 删除已存储的文件
         */
        public static void deletefile(String fileName) {
            try {
                // 找到文件所在的路径并删除该文件
                File file = new File(filePath+fileName);
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**
         * 读取文件里面的内容
         *
         * @return
         */
        public static byte[] getPhoto(String filePath) throws IOException {


            byte[] b = new byte[88888];
            File sourceF = new File(filePath);
            FileInputStream fis = new FileInputStream(sourceF);
            BufferedInputStream bis = new BufferedInputStream(fis);
            int offset, length = 0;
            while ((offset = bis.read(b)) != -1) {
                length += offset;
            }
            byte[] bs = new byte[length];
             System.out.println(""+length);
            System.arraycopy(b, 0, bs, 0, length);
            b=null;
            return bs;
        }
    }

