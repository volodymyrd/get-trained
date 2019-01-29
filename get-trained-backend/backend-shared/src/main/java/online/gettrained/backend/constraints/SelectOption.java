package online.gettrained.backend.constraints;

import static online.gettrained.backend.constraints.SelectOption.Option.GE;
import static online.gettrained.backend.constraints.SelectOption.Option.GT;
import static online.gettrained.backend.constraints.SelectOption.Option.LE;
import static online.gettrained.backend.constraints.SelectOption.Option.LS;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Advanced filter.
 */
public class SelectOption<T> implements Serializable {

  private static final long serialVersionUID = -4670595914130497769L;

  public enum Sign {
    /**
     * Include
     */
    I,
    /**
     * Exclude
     */
    E
  }

  public enum Option {
    /**
     * Equal.
     */
    EQ,
    /**
     * Greater or equal.
     */
    GE,
    /**
     * Greater.
     */
    GT,
    /**
     * Less or equal.
     */
    LE,
    /**
     * Less.
     */
    LS,
    /**
     * Between.
     */
    BT,
    /**
     * Contains a pattern.
     */
    CP
  }

  public static final class ParametrizedSQLConstraint {

    private final String sql;
    private final Map<String, Object> parameters;

    public static ParametrizedSQLConstraint empty() {
      return new ParametrizedSQLConstraint("", new HashMap<>());
    }

    public ParametrizedSQLConstraint(String sql, Map<String, Object> parameters) {
      if (sql == null) {
        this.sql = "";
      } else {
        this.sql = sql;
      }
      this.parameters = parameters;
    }

    public boolean isEmpty() {
      return sql.isEmpty();
    }

    public String getSql() {
      return sql;
    }

    public String getSql(String sqlClause) {
      if (sql.length() > 0) {
        return sqlClause + sql;
      } else {
        return sql;
      }
    }

    public Map<String, Object> getParameters() {
      return parameters;
    }

    @Override
    public String toString() {
      return "ParametrizedSQLConstraint{" +
          "sql='" + sql + '\'' +
          ", parameters=" + parameters +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof ParametrizedSQLConstraint)) {
        return false;
      }
      ParametrizedSQLConstraint that = (ParametrizedSQLConstraint) o;
      return Objects.equals(sql, that.sql) &&
          Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {

      return Objects.hash(sql, parameters);
    }
  }

  public static <T> ParametrizedSQLConstraint toParametrizedSQLConstraint(
      Set<? extends SelectOption<T>> selectOptionSet, String fieldName) {
    return toParametrizedSQLConstraint(selectOptionSet, fieldName, "");
  }

  /**
   * Parametrized sql query string.
   *
   * @param parameterPrefix adding to the parameter name to make latter is unique
   */
  @SuppressWarnings("unchecked")
  public static <T> ParametrizedSQLConstraint toParametrizedSQLConstraint(
      Set<? extends SelectOption<T>> selectOptionSet, String fieldName, String parameterPrefix) {

    if (selectOptionSet == null || selectOptionSet.isEmpty()
        || fieldName == null || fieldName.isEmpty()) {
      return ParametrizedSQLConstraint.empty();
    }

    StringBuilder sqlBuilder = new StringBuilder();
    Map<String, Object> parameters = new HashMap<>();

    Set<Object> includeEq = new HashSet<>();
    Set<Object> includeGt = new HashSet<>();
    Set<Object> includeGe = new HashSet<>();
    Set<Object> includeLs = new HashSet<>();
    Set<Object> includeLe = new HashSet<>();
    Set<SelectOption<T>> includeBt = new HashSet<>();
    Set<SelectOption<String>> includeCp = new HashSet<>();

    Set<Object> excludeEq = new HashSet<>();

    for (SelectOption<T> selectOption : selectOptionSet) {
      if (selectOption.sign == Sign.I) {
        if (selectOption.option == Option.EQ) {
          includeEq
              .add(isEnum(selectOption.low) ? enumToString(selectOption.low) : selectOption.low);
        } else if (selectOption.option == GT) {
          includeGt.add(selectOption.low);
        } else if (selectOption.option == GE) {
          includeGe.add(selectOption.low);
        } else if (selectOption.option == LS) {
          includeLs.add(selectOption.low);
        } else if (selectOption.option == LE) {
          includeLe.add(selectOption.low);
        } else if (selectOption.option == Option.BT) {
          includeBt.add(selectOption);
        } else if (selectOption.option == Option.CP) {
          includeCp.add((SelectOption<String>) selectOption);
        } else {
          throw new UnsupportedOperationException(
              "Unsupported option: " + selectOption.option + " for sign: " + selectOption.sign);
        }
      } else if (selectOption.sign == Sign.E) {
        if (selectOption.option == Option.EQ) {
          excludeEq
              .add(isEnum(selectOption.low) ? enumToString(selectOption.low) : selectOption.low);
        } else {
          throw new UnsupportedOperationException(
              "Unsupported option: " + selectOption.option + " for sign: " + selectOption.sign);
        }
      }
    }

    String fieldNameForParameterName = fieldName.replace(".", "_");

    if (!includeEq.isEmpty()) {
      String parameterName = parameterPrefix + "_" + fieldNameForParameterName + "_includeEq";
      if (includeEq.size() == 1) {
        sqlBuilder.append(fieldName).append("=:").append(parameterName);
        parameters.put(parameterName,
            includeEq.stream().findFirst().orElseThrow(IllegalStateException::new));
      } else {
        sqlBuilder.append(fieldName).append(" IN :").append(parameterName);
        parameters.put(parameterName, includeEq);
      }
    }

    buildForGEorGTorLEorLS(
        GT,
        sqlBuilder,
        fieldName,
        fieldNameForParameterName,
        parameterPrefix,
        parameters,
        includeGt);

    buildForGEorGTorLEorLS(
        GE,
        sqlBuilder,
        fieldName,
        fieldNameForParameterName,
        parameterPrefix,
        parameters,
        includeGe);

    buildForGEorGTorLEorLS(
        LS,
        sqlBuilder,
        fieldName,
        fieldNameForParameterName,
        parameterPrefix,
        parameters,
        includeLs);

    buildForGEorGTorLEorLS(
        LE,
        sqlBuilder,
        fieldName,
        fieldNameForParameterName,
        parameterPrefix,
        parameters,
        includeLe);

    if (!includeBt.isEmpty()) {
      if (sqlBuilder.length() > 0) {
        sqlBuilder.append(" OR ");
      }
      sqlBuilder.append(" (");
      StringJoiner joiner = new StringJoiner(" OR ");
      int betweenParamIndex = 1;
      for (SelectOption<T> so : includeBt) {
        String betweenParamNameLow =
            parameterPrefix + "_" + fieldNameForParameterName + "_betweenParamLow_"
                + betweenParamIndex;
        String betweenParamNameHigh =
            parameterPrefix + "_" + fieldNameForParameterName + "_betweenParamHigh_"
                + betweenParamIndex;
        String s =
            "(" + fieldName + " BETWEEN :" + betweenParamNameLow + " AND :" + betweenParamNameHigh
                + ")";
        joiner.add(s);
        parameters.put(betweenParamNameLow, isEnum(so.low) ? enumToString(so.low) : so.low);
        parameters.put(betweenParamNameHigh, isEnum(so.high) ? enumToString(so.high) : so.high);
        betweenParamIndex++;
      }
      sqlBuilder.append(joiner.toString());
      sqlBuilder.append(") ");
    }

    if (!includeCp.isEmpty()) {
      if (sqlBuilder.length() > 0) {
        sqlBuilder.append(" OR ");
      }
      StringJoiner joiner = new StringJoiner(" OR ");
      int cpParamIndex = 1;
      for (SelectOption<String> so : includeCp) {
        String cpParamName =
            parameterPrefix + "_" + fieldNameForParameterName + "_includeCp_" + cpParamIndex;
        String s = "LOWER(" + fieldName + ") LIKE :" + cpParamName;
        joiner.add(s);
        parameters.put(cpParamName, so.low.toLowerCase().replace("*", "%"));
        cpParamIndex++;
      }
      sqlBuilder.append(joiner.toString());
    }

    if (!excludeEq.isEmpty()) {
      if (sqlBuilder.length() > 0) {
        sqlBuilder.insert(0, "(");
        sqlBuilder.append(")");
      }

      if (sqlBuilder.length() > 0) {
        sqlBuilder.append(" AND ");
      }
      String parameterName = parameterPrefix + "_" + fieldNameForParameterName + "_excludeEq";
      sqlBuilder.append(fieldName).append(" NOT IN :").append(parameterName);
      parameters.put(parameterName, excludeEq);
    }

    return new ParametrizedSQLConstraint(sqlBuilder.toString(), parameters);
  }

  private static void buildForGEorGTorLEorLS(
      Option option,
      StringBuilder sqlBuilder,
      String fieldName,
      String fieldNameForParameterName,
      String parameterPrefix,
      Map<String, Object> parameters,
      Set<Object> include) {

    if (include.isEmpty()) {
      return;
    }

    String parameterName = parameterPrefix + "_" + fieldNameForParameterName;
    String sign;
    switch (option) {
      case GE:
        parameterName += "_includeGe_";
        sign = " >= :";
        break;
      case GT:
        parameterName += "_includeGt_";
        sign = " > :";
        break;
      case LE:
        parameterName += "_includeLe_";
        sign = " <= :";
        break;
      case LS:
        parameterName += "_includeLs_";
        sign = " < :";
        break;
      default:
        return;
    }
    int index = 1;
    for (Object i : include) {
      if (sqlBuilder.length() > 0) {
        sqlBuilder.append(" OR ");
      }
      String currentParameterName = parameterName + index++;
      sqlBuilder.append(fieldName).append(sign).append(currentParameterName);
      parameters.put(currentParameterName, i);
    }
  }

  private static <T> boolean isEnum(T value) {
    return value instanceof Enum<?>;
  }

  private static <T> String enumToString(T value) {
    return ((Enum<?>) value).name();
  }

  private Sign sign;
  private Option option;
  private T low;
  private T high;

  public SelectOption() {
  }

  public SelectOption(Sign sign, Option option, T low) {
    this.sign = sign;
    this.option = option;
    this.low = low;
  }

  public SelectOption(Sign sign, Option option, T low, T high) {
    this.sign = sign;
    this.option = option;
    this.low = low;
    this.high = high;
  }

  public SelectOption(SelectOption<T> selectOption) {
    this.sign = selectOption.sign;
    this.option = selectOption.option;
    this.low = selectOption.low;
    this.high = selectOption.high;
  }

  public Sign getSign() {
    return sign;
  }

  public Option getOption() {
    return option;
  }

  public T getLow() {
    return low;
  }

  public T getHigh() {
    return high;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SelectOption)) {
      return false;
    }
    SelectOption<?> that = (SelectOption<?>) o;
    return sign == that.sign &&
        option == that.option &&
        Objects.equals(low, that.low) &&
        Objects.equals(high, that.high);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sign, option, low, high);
  }

  @Override
  public String toString() {
    return "{" + "sign=" + sign
        + ", option=" + option
        + ", low=" + low
        + ", high=" + high
        + '}';
  }
}
