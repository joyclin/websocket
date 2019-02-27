import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.aop.support.AopUtils;

/**
 * @author joyce
 * @date 2019/2/27
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

  public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame o)
      throws Exception {
    channels.writeAndFlush(new TextWebSocketFrame("广播消息："+o.text()));
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("加入链接");
    channels.add(ctx.channel());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("断开");
    channels.remove(ctx.channel());
  }

}
