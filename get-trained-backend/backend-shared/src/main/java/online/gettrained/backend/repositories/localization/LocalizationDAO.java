package online.gettrained.backend.repositories.localization;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Query;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.constraints.SelectOption.ParametrizedSQLConstraint;
import online.gettrained.backend.constraints.frontend.localization.FrontendLocalConstraint;
import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Localization;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.repositories.BaseRepository;
import online.gettrained.backend.utils.CommonUtils;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link Localization} entity.
 */
@Repository
public class LocalizationDAO extends BaseRepository {

  private final static Set<String> sortedColumns = CommonUtils
      .immutableSetOf("localId", "localKey", "text");

  @SuppressWarnings("unchecked")
  public Page<Localization> findAll(FrontendLocalConstraint constraint) {
    Objects.requireNonNull(constraint, "Parameter 'constraint' must be set.");

    StringBuilder whereClause = new StringBuilder("");
    Map<String, Object> parameters = new HashMap<>();

    if (constraint.getLangCode() != null) {
      whereClause.append(buildSQLClause(whereClause, "l.LANG=:langCode"));
      parameters.put("langCode", constraint.getLangCode());
    }
    if (constraint.getModule() != null) {
      whereClause.append(buildSQLClause(whereClause, "l.MODULE=:module"));
      parameters.put("module", constraint.getModule().name());
    }

    ParametrizedSQLConstraint tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoLangCodes(), "l.LANG");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoModules(), "l.MODULE");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    Query dataQuery = getEntityManager().createNativeQuery(
        "SELECT l.ID localId, l.DATE_CREATE l_dc, l.DATE_UPDATE l_du, "
            + "l.LANG l_lang, l.MODULE l_mod, l.LOCAL_KEY localKey, l.LOCAL_TEXT localText"
            + " FROM LOCAL_LOCALIZATION l "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause)
            + (constraint.getPageable().getSort() != null ? buildOrderByClause(
            constraint.getPageable().getSort(), sortedColumns, name -> {
              switch (name) {
                case "text":
                  return "localText";
                default:
                  return name;
              }
            })
            : "")
    );
    Query countQuery = getEntityManager()
        .createNativeQuery("SELECT COUNT(*) FROM LOCAL_LOCALIZATION l "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause));

    parameters.forEach(dataQuery::setParameter);
    parameters.forEach(countQuery::setParameter);

    final Page<Localization> page = new Page<>();

    page.setCount(((BigInteger) countQuery.getSingleResult()).longValue());
    page.setData(((List<Object[]>) dataQuery
        .setFirstResult((int) constraint.getPageable().getOffset())
        .setMaxResults(constraint.getPageable().getPageSize())
        .getResultList())
        .stream().map(r -> {
          Localization localization = new Localization();
          localization.setLocalId(((BigInteger) r[0]).longValue());
          localization.setLangCode((String) r[3]);
          localization.setModule(ELocalModule.valueOf((String) r[4]));
          localization.setLocalKey((String) r[5]);
          localization.setText(r[6] != null ? (String) r[6] : "");
          return localization;
        }).collect(Collectors.toList()));
    page.setSortedColumns(sortedColumns);
    return page;
  }
}
