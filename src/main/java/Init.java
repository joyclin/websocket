import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author joyce
 * @date 2019/2/27
 */
public class Init extends ChannelInitializer<NioSocketChannel> {

  @Override
  protected void initChannel(NioSocketChannel ch) {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast("http-codec", new HttpServerCodec());
    pipeline.addLast("http-chunked", new ChunkedWriteHandler());
    pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
    pipeline.addLast("WebSocketServiceHandler", new WebSocketServerProtocolHandler("/ws"));
    pipeline.addLast("handler", new WebSocketHandler());
  }
}
