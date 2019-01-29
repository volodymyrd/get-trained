package online.gettrained.backend.constraints;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Basic class for all constraints
 */
public abstract class AbstractConstraint implements Serializable {

  private static final long serialVersionUID = -3836335250356062587L;

  protected <T> Set<T> getSet(Set<T> set) {
    return set == null ? null : new HashSet<>(set);
  }

  protected <T> SelectOption<T> getSelectOption(SelectOption<T> selectOption) {
    return selectOption == null ? null : new SelectOption<>(selectOption);
  }
}
