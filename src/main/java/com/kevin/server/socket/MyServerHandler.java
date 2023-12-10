package com.kevin.server.socket;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.kevin.domain.Devices;
import com.kevin.util.CacheUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wang
 * @create 2023-12-10-18:09
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        logger.info("has a client connect to the server, the channel id is{} and the ip is :{}, and the prot is {},",channel.id().toString(),channel.localAddress().getHostString(),channel.localAddress().getPort());

        Devices device = new Devices();
        device.setChannelId(channel.id().toString());
        device.setIp(channel.localAddress().getHostString());
        device.setPort(channel.localAddress().getPort());
        device.setNumber(UUID.randomUUID().toString());
        device.setConnectionTime(new Date());
        CacheUtils.devicesGroup.put(channel.id().toString(),device);
        CacheUtils.cacheChannelMap.put(channel.id().toString(),channel);
    }


    /**
     * 断开连接的时候将对应的缓存信息删除
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("the client has been disconnected, the channel id is {}",ctx.channel().id().toString());
        CacheUtils.devicesGroup.remove(ctx.channel().id().toString());
        CacheUtils.cacheChannelMap.remove(ctx.channel().id().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("the server has recive the message of {} ,{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),msg);
        CacheUtils.wsChannelGroup.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(msg)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("the server has error, the error is {}",cause.getMessage());
        String channelId = ctx.channel().id().toString();
        CacheUtils.devicesGroup.remove(channelId);
        CacheUtils.cacheChannelMap.remove(ctx.channel().id().toString());
        ctx.close();
    }
}
