package online.gettrained.frontend.config.websocket;

import static com.google.common.collect.Multimaps.synchronizedMultimap;
import static online.gettrained.backend.utils.CommonUtils.getObjectMapper;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import online.gettrained.backend.domain.chat.ChatMessage;
import online.gettrained.backend.domain.chat.ChatMessage.ChatMessageUser;
import online.gettrained.backend.domain.user.CurrentUser;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.services.blob.BlobDataService;
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

  private final ConcurrentHashMap<WebSocketSession, String> sessions = new ConcurrentHashMap<>();
  private final Multimap<String, WebSocketSession> userSessions =
      synchronizedMultimap(HashMultimap.create());

  private final UserService userService;
  private final BlobDataService blobDataService;

  public WebSocketHandler(UserService userService, BlobDataService blobDataService) {
    this.userService = userService;
    this.blobDataService = blobDataService;
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    sessions.forEach((k, v) -> LOG.info("Session for user: {}", k.getPrincipal()));

    LOG.info("Got a message {}", message.getPayload());

    Optional<User> optionalUser = getUser(session);

    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      LOG.info("Got message from a user: {}", user.getEmail());
      try {
        ChatMessage chatMessage = getObjectMapper()
            .readValue(message.getPayload(), ChatMessage.class);
        LOG.info("Successfully parsed a chat message payload: {}", chatMessage);
        user = userService.findByIdWithProfile(user.getId())
            .orElseThrow(IllegalStateException::new);
        chatMessage.setUser(new ChatMessageUser(
            String.valueOf(user.getId()),
            user.getProfile().getFullName(),
            blobDataService.getFileUrl(user.getProfile().getAvatarId())));

        String email = user.getEmail();
        sessions.entrySet().stream()
            .filter(e -> !e.getValue().equals(email))
            .forEach(e -> {
              try {
                e.getKey().sendMessage(
                    new TextMessage(getObjectMapper().writeValueAsString(chatMessage)));
              } catch (IOException ex) {
                ex.printStackTrace();
              }
            });
      } catch (Exception ex) {
        LOG.error("Error parsing a chat message payload to ChatMessage", ex);
      }
    } else {
      LOG.error("Got a message from undefined user, ignored it");
    }
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    LOG.info("Established connection...");
    Optional<User> optionalUser = getUser(session);

    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      LOG.info("Established a session for user: {}", user.getEmail());
      sessions.put(session, user.getEmail());
      userSessions.put(user.getEmail(), session);
    } else {
      LOG.info("Established a session for undefined user, try to close it...");
      try {
        session.close();
      } catch (IOException e) {
        LOG.error("Error close a session for undefined user: ", e);
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    LOG.info("Close a session for user: {}", sessions.get(session));
    userSessions.remove(sessions.get(session), session);
    sessions.remove(session);
    LOG.info("Remains {} sessions {} users", sessions.size(), userSessions.size());
    LOG.info("Sessions of users: {}", sessions.values());
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    LOG.error("Websocket transport error for user:", session.getPrincipal());
    LOG.error("Websocket transport error", exception);
  }

  private Optional<User> getUser(WebSocketSession session) {
    if (session.getPrincipal() != null) {
      if (session.getPrincipal() instanceof CurrentUser) {
        return Optional.of(((CurrentUser) session.getPrincipal()).getUser());
      } else if (session.getPrincipal().getName() != null) {
        return userService.findOneByEmail(session.getPrincipal().getName());
      }
    }
    return Optional.empty();
  }
}
