package com.kevin.controller;

import cn.hutool.json.JSONUtil;
import com.kevin.domain.Devices;
import com.kevin.domain.EasyResult;
import com.kevin.domain.InfoProtocol;
import com.kevin.domain.ServerInfo;
import com.kevin.util.CacheUtils;
import com.kevin.util.MsgUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author wang
 * @create 2023-12-10-19:42
 */
@RestController
@RequestMapping("/netty")
public class NettyController {

    private Logger logger = LoggerFactory.getLogger(NettyController.class);

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/queryNettyServerList")
    @ResponseBody
    public Collection<ServerInfo> queryNettyServerList() {
        try {
            Collection<ServerInfo> serverInfos = CacheUtils.serverInfoMap.values();
            logger.info("查询服务端列表。{}", JSONUtil.toJsonStr(serverInfos));
            return serverInfos;
        } catch (Exception e) {
            logger.info("查询服务端列表失败。", e);
            return null;
        }
    }

    @RequestMapping("/queryDeviceList")
    @ResponseBody
    public Collection<Devices> queryDeviceList() {
        try {
            Collection<Devices> deviceInfos = CacheUtils.devicesGroup.values();
            logger.info("查询设备链接列表。{}", JSONUtil.toJsonStr(deviceInfos));
            return deviceInfos;
        } catch (Exception e) {
            logger.info("查询设备链接列表失败。", e);
            return null;
        }
    }

    @RequestMapping("/doSendMsg")
    @ResponseBody
    public EasyResult doSendMsg(String reqStr) {
        try {
            logger.info("向设备发送信息[可以通过另外一个Web服务调用本接口发送信息]：{}", reqStr);
            InfoProtocol infoProtocol = MsgUtils.parseMsg(reqStr);
            String channelId = infoProtocol.getChannelId();
            Channel channel = CacheUtils.cacheChannelMap.get(channelId);
            channel.writeAndFlush(MsgUtils.buildMsg(infoProtocol));
            return EasyResult.buildSuccess();
        } catch (Exception e) {
            logger.error("向设备发送信息失败：{}", reqStr, e);
            return EasyResult.buildErrResult(e);
        }
    }
}
