package online.gettrained.backend.repositories;

import static online.gettrained.backend.repositories.BaseRepository.LogicalOperator.AND;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

/**
 * Base repository injected EntityManager
 */
@Repository
public class BaseRepository {

  private static final Logger LOG = LoggerFactory.getLogger(BaseRepository.class);

  public enum LogicalOperator {
    AND, OR;

    @Override
    public String toString() {
      return " " + name() + " ";
    }
  }

  public enum SupportedDialect {
    MYSQL_5_INNO("org.hibernate.dialect.MySQL5InnoDBDialect"),
    MYSQL_5("org.hibernate.dialect.MySQL5Dialect"),
    POSTGRE_SQL("org.hibernate.dialect.PostgreSQLDialect");

    private String dialect;

    SupportedDialect(String dialect) {
      this.dialect = dialect;
    }

    public String getDialect() {
      return dialect;
    }

    public static SupportedDialect fromString(String dialect) {
      for (SupportedDialect d : SupportedDialect.values()) {
        if (d.dialect.equals(dialect)) {
          return d;
        }
      }
      throw new IllegalArgumentException("Not found a dialect.");
    }
  }

  @PersistenceContext
  private EntityManager entityManager;

  protected String buildOrderByClause(Sort sort) {
    return buildOrderByClause(sort, this::propertyNameMatcher);
  }

  protected String buildOrderByClause(Sort sort, Function<String, String> propertyNameMatcher) {
    return buildOrderByClause(sort, null, propertyNameMatcher);
  }

  protected String buildOrderByClause(
      Sort sort,
      Set<String> orderedColumns,
      Function<String, String> propertyNameMatcher) {
    StringBuilder orderClause = new StringBuilder("");
    if (sort != null) {
      for (Order order : sort) {
        if (orderedColumns == null || orderedColumns.isEmpty()
            || orderedColumns.contains(order.getProperty())) {
          String matchedProp = propertyNameMatcher.apply(order.getProperty());
          LOG.debug("SortPropertyNameMatcher: the property {} matched to the {}",
              order.getProperty(), matchedProp);
          orderClause
              .append(matchedProp)
              .append(" ")
              .append(order.getDirection())
              .append(",");
        }
      }
      if (orderClause.length() > 0) {
        orderClause.delete(orderClause.length() - 1, orderClause.length());
        orderClause.insert(0, " ORDER BY ");
      }
    }
    return orderClause.toString();
  }

  protected String propertyNameMatcher(String propertyName) {
    return propertyName;
  }


  public EntityManager getEntityManager() {
    return entityManager;
  }

  protected SupportedDialect getDialect() {
    String dialect = (String) entityManager
        .getEntityManagerFactory()
        .getProperties()
        .get("hibernate.dialect");

    try {
      return SupportedDialect.fromString(dialect);
    } catch (IllegalArgumentException ex) {
      throw new RuntimeException("Unsupported dialect: " + dialect);
    }
  }

  protected String buildSQLClause(StringBuilder whereClause, String tempSql) {
    return this.buildSQLClause(whereClause, tempSql, AND);
  }

  protected String buildSQLClause(
      StringBuilder whereClause, String tempSql, LogicalOperator operator) {
    return whereClause.length() == 0 ? tempSql : operator + tempSql;
  }

//  public void t() throws ClassNotFoundException {
//    entityManager.getEntityManagerFactory().getMetamodel()
//        .entity(Class.forName("com.berize.backend.domain.contents.course.Course"))
//        .getAttribute("published");
//    SessionFactory sessionFactory = entityManager.getEntityManagerFactory()
//        .unwrap(SessionFactory.class);
//
//    entityManager.getEntityManagerFactory()
//        .unwrap(SessionFactory.class)
//        .getClassMetadata(Class.forName("com.berize.backend.domain.contents.course.Course"))
//        .getPropertyType("published").sqlTypes((Mapping) entityManager.getEntityManagerFactory()
//        .unwrap(SessionFactory.class));
//  }

  protected String toString(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream))
        .lines().collect(Collectors.joining("\n"));
  }
}
