package io.summer.tool.echo.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.summer.tool.echo.util.ServerUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

import static io.summer.tool.echo.util.ServerUtil.traceMessage;

/**
 * @author ChangWei Li
 * @version 2018-03-07 15:52
 */
@Slf4j
public class NettyUDPServer {

    public static void init() {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioDatagramChannel.class);
        bootstrap.group(eventLoopGroup);
        bootstrap.handler(new ChannelInitializer<NioDatagramChannel>() {
            @Override
            protected void initChannel(NioDatagramChannel nioDatagramChannel) {
                ChannelPipeline pipeline = nioDatagramChannel.pipeline();
                pipeline.addLast(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) {
                        traceMessage(datagramPacket.content());
                        channelHandlerContext.writeAndFlush(new DatagramPacket(datagramPacket.content().copy(), datagramPacket.sender()));
                    }
                });
            }
        });

        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress("0.0.0.0", 8888));
        ServerUtil.await(channelFuture);
    }

    public static void main(String[] args) {
        init();
    }

}
