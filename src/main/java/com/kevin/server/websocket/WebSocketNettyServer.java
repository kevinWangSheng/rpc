package com.kevin.server.websocket;

import com.kevin.server.socket.MyChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * @author wang
 * @create 2023-12-10-19:35
 */
public class WebSocketNettyServer implements Callable<Channel> {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketNettyServer.class);

    private InetSocketAddress inetAddress;

    public WebSocketNettyServer(InetSocketAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    private EventLoopGroup parentLoop = new NioEventLoopGroup();
    private EventLoopGroup childLoop = new NioEventLoopGroup();

    private Channel channel;

    @Override
    public Channel call() throws Exception {
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture future = null;
        try {
            bs.group(parentLoop,childLoop)
                    .channel(NioSctpServerChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new MyWsChannelInitializer());

            future = bs.bind(inetAddress).syncUninterruptibly();
            channel = future.channel();
        } catch (Exception e) {
            logger.error("the server has acquire the error:{}",e.getMessage());
        } finally {
            if(null != future && future.isSuccess()){
                logger.info("the websocket server has done");
            }else{
                logger.error("the websocket server has error");
            }
            return channel;
        }
    }

    public void destory(){
        if(null != channel) channel.close();
        parentLoop.shutdownGracefully();
        childLoop.shutdownGracefully();
    }
}
