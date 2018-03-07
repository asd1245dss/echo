package io.summer.tool.echo.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.summer.tool.echo.util.ServerUtil;
import lombok.extern.slf4j.Slf4j;

import static io.summer.tool.echo.util.ServerUtil.traceMessage;

/**
 * @author ChangWei Li
 * @version 2018-03-07 16:20
 */
@Slf4j
public class NettyTCPServer {

    public static void init() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(eventLoopGroup, workerEventLoopGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

                        traceMessage(msg);

                        ctx.writeAndFlush(msg.copy());
                    }
                });
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(8888);
        ServerUtil.await(channelFuture);
    }

    public static void main(String[] args) {
        init();
    }

}
