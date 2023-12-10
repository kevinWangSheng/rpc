package com.kevin.util;

import cn.hutool.json.JSONUtil;
import com.kevin.domain.InfoProtocol;
import com.kevin.domain.enums.MsgTypeEnum;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author wang
 * @create 2023-12-10-18:21
 */
public class MsgUtils {

    public static String buildMsg(InfoProtocol infoProtocol){
        return JSONUtil.toJsonStr(infoProtocol)+"\r\n";
    }

    public static InfoProtocol parseMsg(String msg){
        return JSONUtil.toBean(msg, InfoProtocol.class);
    }

    public static TextWebSocketFrame buildWsMsgText(String channelId, String msgInfo){
        InfoProtocol infoProtocol = new InfoProtocol();
        infoProtocol.setChannelId(channelId);
        infoProtocol.setMsgObj(msgInfo);
        infoProtocol.setMsgType(MsgTypeEnum.SUCCESS.getType());

        return new TextWebSocketFrame(JSONUtil.toJsonStr(infoProtocol));
    }
}
