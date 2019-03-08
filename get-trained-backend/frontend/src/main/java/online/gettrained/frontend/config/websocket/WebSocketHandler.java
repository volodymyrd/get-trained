package online.gettrained.frontend.config.websocket;

import static com.google.common.collect.Multimaps.synchronizedMultimap;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.concurrent.ConcurrentHashMap;
import online.gettrained.backend.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandler.class);

  private final ConcurrentHashMap<String, String> sessions = new ConcurrentHashMap<>();
  private final Multimap<String, WebSocketSession> userSessions =
      synchronizedMultimap(HashMultimap.create());

  private final UserService userService;

  public WebSocketHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) {
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    if (session.getPrincipal() != null) {
      userService.findOneByEmail(session.getPrincipal().getName()).ifPresent(
          user -> {
            sessions.put(session.getId(), user.getEmail());
            userSessions.put(user.getEmail(), session);
          });
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    userSessions.remove(sessions.get(session.getId()), session);
    sessions.remove(session.getId());
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    LOG.error("Websocket transport error for user:", session.getPrincipal());
    LOG.error("Websocket transport error", exception);
  }
}
