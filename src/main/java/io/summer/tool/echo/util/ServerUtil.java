package io.summer.tool.echo.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author ChangWei Li
 * @version 2018-03-07 16:23
 */
@Slf4j
public final class ServerUtil {

    private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

    public static void traceMessage(ByteBuf byteBuf) {
        log.info("接收消息：{}", byteBuf.toString(CHARSET_UTF_8));
    }

    public static void await(ChannelFuture channelFuture, EventLoopGroup... eventLoopGroups) {
        try {
            channelFuture.channel().closeFuture().await();
        } catch (Exception e) {
            log.error("服务器发生异常", e);
        } finally {
            for (EventLoopGroup eventLoopGroup : eventLoopGroups) {
                eventLoopGroup.shutdownGracefully();
            }
        }
    }

}
