import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author joyce
 * @date 2019/2/27
 */
@Slf4j
public class Service {

  private static NioEventLoopGroup bossGroup;
  private static NioEventLoopGroup workGroup;
  private static int port;

  public static void main(String[] args) throws InterruptedException {
    init();
    statr0();
  }


  public void shutdown() {
    bossGroup.shutdownGracefully();
    workGroup.shutdownGracefully();

  }

  private static void statr0() throws InterruptedException {
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(bossGroup, workGroup)
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 1024)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childOption(ChannelOption.TCP_NODELAY, true)
        .childHandler(new Init());
    bind(serverBootstrap, port);
  }

  private static void bind(ServerBootstrap serverBootstrap, int port) throws InterruptedException {
    ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
    channelFuture.channel().closeFuture().sync();
  }

  private static void init() {
    bossGroup = new NioEventLoopGroup(1);
    workGroup = new NioEventLoopGroup();
    port = 8090;
  }
}
