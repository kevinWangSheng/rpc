package com.kevin.util;

import com.kevin.domain.Devices;
import com.kevin.domain.ServerInfo;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wang
 * @create 2023-12-10-18:21
 */
public class CacheUtils {

    public static ChannelGroup wsChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static Map<Integer, ServerInfo> serverInfoMap = Collections.synchronizedMap(new HashMap<>());

    public static Map<String, Channel> cacheChannelMap = Collections.synchronizedMap(new HashMap<>());

    public static Map<String, Devices> devicesGroup = Collections.synchronizedMap(new HashMap<>());
}
