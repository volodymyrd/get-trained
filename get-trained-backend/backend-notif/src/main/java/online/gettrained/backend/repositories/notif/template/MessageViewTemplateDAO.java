package online.gettrained.backend.repositories.notif.template;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.templates.MessageViewTemplate;
import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link MessageViewTemplate}.
 */
@Repository
public class MessageViewTemplateDAO extends BaseRepository {

  @SuppressWarnings("unchecked")
  public Optional<MessageViewTemplate> findByCompanyIdAndChannelAndLangCode(
      NotificationChannel.Code channel, String langCode) {

    Query query = getEntityManager().createNativeQuery(
        "SELECT v.ID v_id, v.VIEW_TEMPLATE v_t, vl.FOOTER vl_f FROM NOTIF_MESSAGE_VIEW_TEMPLATES v "
            + " LEFT JOIN NOTIF_MESSAGE_VIEW_TEMPLATE_LOCALS vl ON v.ID = vl.REF_TEMPLATE_ID AND vl.LANG_CODE=:langCode "
            + " WHERE v.CHANNEL=:channel");

    query.setParameter("channel", channel.name());
    query.setParameter("langCode", langCode);

    List<Object[]> result = (List<Object[]>) query.getResultList();
    if (result != null && !result.isEmpty()) {
      MessageViewTemplate template = new MessageViewTemplate();
      template.setId(((BigInteger) result.get(0)[0]).longValue());
      template.setViewTemplate((String) result.get(0)[1]);
      if (result.get(0)[2] != null) {
        template.setFooter((String) result.get(0)[2]);
      }
      return Optional.of(template);
    } else {
      return Optional.empty();
    }
  }
}
