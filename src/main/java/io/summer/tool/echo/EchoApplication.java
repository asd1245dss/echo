package io.summer.tool.echo;

import io.summer.tool.echo.tcp.NettyTCPServer;
import io.summer.tool.echo.udp.NettyUDPServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Echo服务器
 * @author Li ChangWei
 * @since 2018-03-07 16:14
 */
@SpringBootApplication
public class EchoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EchoApplication.class, args);
	}

    @Override
    public void run(String... args) {
        NettyUDPServer.init();
    }
}
