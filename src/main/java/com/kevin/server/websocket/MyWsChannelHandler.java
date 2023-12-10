package com.kevin.server.websocket;

import cn.hutool.json.JSONUtil;
import com.kevin.domain.InfoProtocol;
import com.kevin.util.CacheUtils;
import com.kevin.util.MsgUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wang
 * @create 2023-12-10-19:15
 */
public class MyWsChannelHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MyWsChannelHandler.class);

    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        logger.info("has a client connect to the server, the channel id is{} and the ip is :{}, and the prot is {},",channel.id().toString(),channel.localAddress().getHostString(),channel.localAddress().getPort());

        CacheUtils.wsChannelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("has a client disconnect to the server, the channel id is {}",ctx.channel().id().toString());
        CacheUtils.wsChannelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            if(!fullHttpRequest.decoderResult().isSuccess()){
                DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                if(httpResponse.status().code() != 200){
                    ByteBuf buf = Unpooled.copiedBuffer(httpResponse.status().toString(), CharsetUtil.UTF_8);
                    httpResponse.content().writeBytes(buf);
                    buf.release();
                }
                ChannelFuture future = ctx.channel().write(httpResponse);
                if(httpResponse.status().code()!=200){
                    future.addListener(ChannelFutureListener.CLOSE);
                }
                return;
            }
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws:/" + ctx.channel() + "/websocket", null, false);
            handshaker = wsFactory.newHandshaker(fullHttpRequest);
            if (null == handshaker) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), fullHttpRequest);
            }
            return;
        }
        if(msg instanceof WebSocketFrame){
            WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
            if(webSocketFrame instanceof CloseWebSocketFrame){
                handshaker.close(ctx.channel(),(CloseWebSocketFrame)webSocketFrame);
                return;
            }
            if(webSocketFrame instanceof PingWebSocketFrame){
                ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
                return;
            }
            if(!(webSocketFrame instanceof TextWebSocketFrame)){
                throw new RuntimeException("only support the text mode");
            }
            String request = ((TextWebSocketFrame) webSocketFrame).text();
            logger.info("the server has receive the message :{}",request);

            InfoProtocol infoProtocol = JSONUtil.toBean(request, InfoProtocol.class);

            String channelId = infoProtocol.getChannelId();
            Channel channel = CacheUtils.cacheChannelMap.get(channelId);
            if(null == channel) return;
            channel.writeAndFlush(MsgUtils.buildMsg(infoProtocol));

            ctx.writeAndFlush(MsgUtils.buildWsMsgText(ctx.channel().toString(),"success"));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        CacheUtils.wsChannelGroup.remove(ctx.channel());
        logger.error("the server has error, the error is {}",cause.getMessage());
    }
}
