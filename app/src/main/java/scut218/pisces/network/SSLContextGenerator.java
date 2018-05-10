//package scut218.pisces.network;
//import android.content.res.AssetManager;
//import android.util.Log;
//
//import org.apache.mina.filter.ssl.KeyStoreFactory;
//import org.apache.mina.filter.ssl.SslContextFactory;
//
//import javax.net.ssl.SSLContext;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.security.KeyStore;
//
//import scut218.pisces.base.MyApplication;
//
//
//public class SSLContextGenerator {
//    public SSLContext getSslContext()
//    {
//
//        SSLContext sslContext = null;
//        String newPath="/data/data/pisces/key";
//        try
//        {
//            InputStream is=MyApplication.getContext().getAssets().open("keystore.jks");
//            FileOutputStream fos = new FileOutputStream(new File(newPath+'/'+"keystore.jks"));
//            byte[] buffer = new byte[1024];
//            int byteCount=0;
//            while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节
//                fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
//            }
//            fos.flush();//刷新缓冲区
//            is.close();
//            fos.close();
//
//            is=MyApplication.getContext().getAssets().open("truststore.jks");
//            fos = new FileOutputStream(new File(newPath+'/'+"truststore.jks"));
//            buffer = new byte[1024];
//            byteCount=0;
//            while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节
//                fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
//            }
//            fos.flush();//刷新缓冲区
//            is.close();
//            fos.close();
//
//
//
//            File keyStoreFile = new File(newPath+"/keystore.jks");
//            File trustStoreFile = new File(newPath+"/truststore.jks");
//
//            if (keyStoreFile.exists() && trustStoreFile.exists()) {
//                final KeyStoreFactory keyStoreFactory = new KeyStoreFactory();
//                keyStoreFactory.setDataFile(keyStoreFile);
//                keyStoreFactory.setPassword("610119");
//
//                final KeyStoreFactory trustStoreFactory = new KeyStoreFactory();
//                trustStoreFactory.setDataFile(trustStoreFile);
//                trustStoreFactory.setPassword("610119");
//
//                final SslContextFactory sslContextFactory = new SslContextFactory();
//                final KeyStore keyStore = keyStoreFactory.newInstance();
//                sslContextFactory.setKeyManagerFactoryKeyStore(keyStore);
//
//                final KeyStore trustStore = trustStoreFactory.newInstance();
//                sslContextFactory.setTrustManagerFactoryKeyStore(trustStore);
//                sslContextFactory.setKeyManagerFactoryKeyStorePassword("610119");
//                sslContext = sslContextFactory.newInstance();
//                System.out.println("SSL provider is: " + sslContext.getProvider());
//                Log.d("fuck","ssl success");
//            } else {
//                System.out.println("Keystore or Truststore file does not exist");
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return sslContext;
//    }
//}
