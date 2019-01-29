package online.gettrained.backend.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import online.gettrained.backend.constraints.DateConstraint;
import online.gettrained.backend.domain.blob.BlobData;
import online.gettrained.backend.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

/**
 * Utils
 */
public class Utils {

  private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

  public static void buildSqlFromSort(StringBuilder sqlBuilder, StringBuilder sqlBuilderCount,
      Sort sort,
      Map<String, Object> valuesForWhereClause,
      List<String> allowedSortedColumns) {
    if (sort != null && sort.iterator().hasNext()) {
      sqlBuilder.append(" ORDER BY ");
      sort.forEach(s -> {
        if (allowedSortedColumns.contains(s.getProperty())) {
          sqlBuilder.append(s.getProperty());
          sqlBuilder.append(" ");
          sqlBuilder.append(s.getDirection().name());
          sqlBuilder.append(", ");
        }
      });

      sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
    }
  }

  public static void buildSqlFromDateConstraint(
      StringBuilder sqlBuilder,
      StringBuilder sqlBuilderCount,
      DateConstraint dateConstraint,
      Map<String, Object> valuesForWhereClause) {

    if (!dateConstraint.isEmpty() && dateConstraint.getDateCreate() != null) {
      switch (dateConstraint.getDateCreate().getSign()) {
        case I:
          switch (dateConstraint.getDateCreate().getOption()) {
            case EQ:
              if (dateConstraint.getDateCreate().getLow() != null) {
                String sql = " AND c.DATE_CREATE=:dateCreate ";
                sqlBuilder.append(sql);
                sqlBuilderCount.append(sql);
                valuesForWhereClause.put("dateCreate", dateConstraint.getDateCreate().getLow());
              }
              break;
            case BT:
              if (dateConstraint.getDateCreate().getLow() != null
                  && dateConstraint.getDateCreate().getHigh() != null) {
                String sql = " AND c.DATE_CREATE BETWEEN :dateCreate1 AND :dateCreate2 ";
                sqlBuilder.append(sql);
                sqlBuilderCount.append(sql);
                valuesForWhereClause.put("dateCreate1", dateConstraint.getDateCreate().getLow());
                valuesForWhereClause.put("dateCreate2", dateConstraint.getDateCreate().getHigh());
              }
              break;
            default:
              LOG.warn("Unsupported filter for {} option",
                  dateConstraint.getDateCreate().getOption());
          }
          break;
        case E:
          LOG.warn("Unsupported filter for excluding date");
          break;
      }
    }

    if (dateConstraint.getDateUpdate() != null) {
      switch (dateConstraint.getDateUpdate().getSign()) {
        case I:
          switch (dateConstraint.getDateUpdate().getOption()) {
            case EQ:
              if (dateConstraint.getDateUpdate().getLow() != null) {
                String sql = " AND c.DATE_UPDATE=:dateUpdate ";
                sqlBuilder.append(sql);
                sqlBuilderCount.append(sql);
                valuesForWhereClause.put("dateUpdate", dateConstraint.getDateUpdate().getLow());
              }
              break;
            case BT:
              if (dateConstraint.getDateUpdate().getLow() != null
                  && dateConstraint.getDateUpdate().getHigh() != null) {
                String sql = " AND c.DATE_UPDATE BETWEEN :dateUpdate1 AND :dateUpdate2 ";
                sqlBuilder.append(sql);
                sqlBuilderCount.append(sql);
                valuesForWhereClause.put("dateUpdate1", dateConstraint.getDateUpdate().getLow());
                valuesForWhereClause.put("dateUpdate2", dateConstraint.getDateUpdate().getHigh());
              }
              break;
            default:
              LOG.warn("Unsupported filter for {} option",
                  dateConstraint.getDateUpdate().getOption());
          }
          break;
        case E:
          LOG.warn("Unsupported filter for excluding date");
          break;
      }
    }
  }

  private static Pattern patternSearchingOfFileIdInFileUrl = Pattern.compile("(\\d+)$");

  private static Pattern patternSearchingOfCompanyIdInFileUrl = Pattern.compile("/(-?\\d+)");

  private static Boolean isMediaType(Map map) {
    return Arrays.stream(BlobData.Type.values())
        .map(BlobData.Type::toString)
        .anyMatch(s -> s.equals(map.get("type")));
  }

  public static Set<Long> getListBlobIdFromCourseContentLocalInJsonFormat(String json) {
    try {
      ObjectMapper mapper = CommonUtils.getObjectMapper();
      Map<String, Object> map = mapper.readValue(json, HashMap.class);
      List<Map> objects = (List) map.get("elements");
      return objects.stream()
          .filter(Utils::isMediaType)
          .map(m -> (String) ((Map) m.get("value")).get("url"))
          .flatMap(
              u -> {
                List<Long> longs = new ArrayList<>();
                Matcher matcher = patternSearchingOfFileIdInFileUrl.matcher(u);
                if (matcher.find()) {
                  longs.add(Long.parseLong(matcher.group(0)));
                }
                return longs.stream();
              }
          )
          .collect(Collectors.toSet());
    } catch (IOException e) {
      LOG.error(e.getMessage());
      return new HashSet<>();
    }
  }

  public static String replaceCompanyIdAndBlobIdInCourseContentLocalInJsonFormat(
      String json, Long companyId, Optional<List<Map<Long, Long>>> bindBlobIdList) {

    if (!bindBlobIdList.isPresent()) {
      return json;
    }

    try {
      ObjectMapper mapper = CommonUtils.getObjectMapper();
      Map<String, Object> map = mapper.readValue(json, HashMap.class);
      List<Map> objects = (List) map.get("elements");
      objects.stream()
          .filter(Utils::isMediaType)
          .forEach(
              m -> {
                Map value = (Map) m.get("value");
                value.put(
                    "url",
                    replaceCompanyIdInUrl(
                        replaceBlobIdInUrl((String) value.get("url"), bindBlobIdList),
                        companyId
                    )
                );
                value.put(
                    "fullFileUrl",
                    replaceCompanyIdInUrl(
                        replaceBlobIdInUrl((String) value.get("fullFileUrl"), bindBlobIdList),
                        companyId
                    )
                );
              }
          );
      return mapper.writeValueAsString(map);
    } catch (Exception e) {
      LOG.error(e.getMessage());
      return json;
    }
  }

  private static String replaceCompanyIdInUrl(String url, Long companyId) {
    Matcher matcher = patternSearchingOfCompanyIdInFileUrl.matcher(url);
    if (matcher.find()) {
      return matcher.replaceFirst("/" + companyId.toString());
    }
    return url;
  }

  private static String replaceBlobIdInUrl(
      String url, Optional<List<Map<Long, Long>>> bindBlobIdList) {
    if (bindBlobIdList.isPresent()) {
      Matcher matcher = patternSearchingOfFileIdInFileUrl.matcher(url);
      if (matcher.find()) {
        Long id = Long.parseLong(matcher.group(0));
        Optional<Map<Long, Long>> map = bindBlobIdList.get().stream()
            .filter(b -> b.containsKey(id))
            .findFirst();
        if (map.isPresent()) {
          return matcher.replaceFirst(map.get().get(id).toString());
        }
      }
    }
    return url;
  }
}
