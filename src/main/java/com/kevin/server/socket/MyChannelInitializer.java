package com.kevin.server.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author wang
 * @create 2023-12-10-18:05
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));

        socketChannel.pipeline().addLast(new MyServerHandler());
    }
}
