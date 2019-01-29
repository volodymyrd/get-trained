package online.gettrained.backend.services.notif.handlers;

import java.util.Map;

/**
 *
 */
public interface NotificationHandler {
    String TO = "to";
    String FROM = "from";
    String SUBJECT = "subject";
    String MESSAGE = "message";
    String CAN_USE_BCC = "canUseBcc";


    void send(Map<String, Object> parameters) throws Exception;
}
