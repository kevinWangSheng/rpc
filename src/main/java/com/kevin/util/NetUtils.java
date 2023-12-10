package com.kevin.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author wang
 * @create 2023-12-10-18:22
 */
public class NetUtils {

    public static Integer getPort(){
        int port =7369;
        while(true){
            if(!isUse(port)){
                break;
            }
            port++;
        }
        return port;
    }

    /**
     * 这里主要通过创建socket的方式进行测试对应的端口是否能够进行使用，进行对应的结果返回。
     * @param port
     * @return
     */
    public static boolean isUse(int port){
        try {
            Socket socket = new Socket("localhost",port);
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static String getHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
