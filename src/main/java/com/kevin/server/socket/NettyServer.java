package com.kevin.server.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * @author wang
 * @create 2023-12-10-19:03
 */
public class NettyServer implements Callable<Channel> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private InetSocketAddress address;

    public NettyServer(InetSocketAddress address) {
        this.address = address;
    }

    private EventLoopGroup parentLoop = new NioEventLoopGroup();
    private EventLoopGroup childLoop = new NioEventLoopGroup();

    private Channel channel;

    @Override
    public Channel call(){
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture future = null;
        try {
            bs.group(parentLoop,childLoop)
                    .channel(NioSctpServerChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new MyChannelInitializer());

            future = bs.bind(address).syncUninterruptibly();
            this.channel = future.channel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if(null != future && future.isSuccess()){
                logger.info("the netty socket server has done");
            }else{
                logger.error("the netty socket server has error");
            }
        }
        return channel;
    }

    public void destory(){
        if(null != channel){
            channel.close();
        }
        parentLoop.shutdownGracefully();
        childLoop.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }
}
