package online.gettrained.frontend.config.websocket;

import static com.google.common.collect.Multimaps.synchronizedMultimap;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.concurrent.ConcurrentHashMap;
import online.gettrained.backend.services.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Websocket handler.
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {

  private final ConcurrentHashMap<String, String> sessions = new ConcurrentHashMap<>();
  private final Multimap<String, WebSocketSession> userSessions =
      synchronizedMultimap(HashMultimap.create());

  private final UserService userService;

  public WebSocketHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    System.out.println(session.getPrincipal() + ": " + message);
    session.sendMessage(new TextMessage("Hi Hi!!"));
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println(session);
    if (session.getPrincipal() != null) {
      userService.findOneByEmail(session.getPrincipal().getName()).ifPresent(
          user -> {
            sessions.put(session.getId(), user.getEmail());
            userSessions.put(user.getEmail(), session);
          });
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    System.out.println(session);
    userSessions.remove(sessions.get(session.getId()), session);
    sessions.remove(session.getId());
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    System.out.println(session);
    System.out.println(exception);
  }
}
