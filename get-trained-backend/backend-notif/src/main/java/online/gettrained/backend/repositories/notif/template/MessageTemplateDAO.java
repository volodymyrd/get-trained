package online.gettrained.backend.repositories.notif.template;

import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.templates.MessageTemplate;
import online.gettrained.backend.domain.notif.templates.MessageTemplateLocal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * MessageTemplate DAO.
 */
@Repository
public class MessageTemplateDAO extends BaseRepository {

  private final static String SELECT_ALL_FIELDS =
      "SELECT t.ID t_id, t.EVENT t_e, t.CHANNEL t_ch, t.DATE_CREATE t_dc, t.DATE_UPDATE t_du, t.DESCRIPTION t_desc, "
          + " t.AUTHOR_ID t_a, t.USER_LAST_CHANGED_ID t_ac, l.LANG_CODE l_lang, l.TITLE l_t, l.BODY l_b,"
          + " e.DEFAULT_DESCRIPTION e_dd, el.DESCRIPTION el_desc "
          + " FROM NOTIF_MESSAGE_TEMPLATES t "
          + " INNER JOIN NOTIF_EVENTS e ON e.EVENT=t.EVENT "
          + " LEFT JOIN NOTIF_MESSAGE_TEMPLATE_LOCALS l ON t.ID=l.REF_TEMPLATE_ID "
          + " LEFT JOIN NOTIF_EVENT_LOCALS el ON el.EVENT=e.EVENT AND el.LANG_CODE=:langCode ";

  @SuppressWarnings("unchecked")
  public Optional<MessageTemplate> findOneTemplateByEventAndChannelIfSetAndLang(
      NotificationEvent.Code eventCode,
      NotificationChannel.Code channelCode,
      String langCode) {

    if (eventCode == null) {
      throw new IllegalArgumentException("Parameter eventCode must be set");
    }

    if (langCode == null) {
      throw new IllegalArgumentException("Parameter langCode must be set");
    }

    String channelWhereClause = "";
    if (channelCode != null) {
      channelWhereClause = " AND t.CHANNEL=:channelCode ";
    }

    Query query = getEntityManager().createNativeQuery(SELECT_ALL_FIELDS
        + " WHERE t.EVENT=:eventCode "
        + channelWhereClause
        + " AND l.LANG_CODE=:langCode")
        .setParameter("eventCode", eventCode.name())
        .setParameter("langCode", langCode);

    if (channelCode != null) {
      query.setParameter("channelCode", channelCode.name());
    }

    List<Object[]> results = query.getResultList();
    if (results != null && !results.isEmpty()) {
      return Optional
          .of(new MessageTemplate(
              ((BigInteger) results.get(0)[0]).longValue(),
              NotificationEvent.Code.valueOf((String) results.get(0)[1]),
              (String) (results.get(0)[12] != null ? results.get(0)[12] : results.get(0)[11]),
              NotificationChannel.Code.valueOf((String) results.get(0)[2]),
              (String) results.get(0)[5],
              (String) results.get(0)[8],
              new MessageTemplateLocal(
                  results.get(0)[9] != null ? (String) results.get(0)[9] : "",
                  (String) results.get(0)[10])));
    } else {
      return Optional.empty();
    }
  }

  public int countLocalsByEventAndChannel(
      NotificationEvent.Code event, NotificationChannel.Code channel) {
    return ((BigInteger) getEntityManager()
        .createNativeQuery("SELECT COUNT(*) FROM NOTIF_MESSAGE_TEMPLATE_LOCALS l"
            + " WHERE l.REF_TEMPLATE_ID = (SELECT t.ID FROM NOTIF_MESSAGE_TEMPLATES t "
            + " WHERE t.EVENT=:event AND t.CHANNEL=:channel) ")
        .setParameter("event", event.name())
        .setParameter("channel", channel.name())
        .getSingleResult()).intValue();
  }

  public int deleteByEventAndChannelAndLangCode(
      NotificationEvent.Code event, NotificationChannel.Code channel, String langCode) {
    return getEntityManager()
        .createNativeQuery("DELETE FROM NOTIF_MESSAGE_TEMPLATE_LOCALS "
            + " WHERE REF_TEMPLATE_ID IN (SELECT t.ID FROM NOTIF_MESSAGE_TEMPLATES t "
            + " WHERE t.EVENT=:event AND t.CHANNEL=:channel) "
            + " AND LANG_CODE=:langCode")
        .setParameter("event", event.name())
        .setParameter("channel", channel.name())
        .setParameter("langCode", langCode)
        .executeUpdate();
  }
}
