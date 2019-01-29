package online.gettrained.backend.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Page dto for pagination response.
 */
public class Page<T> implements Serializable {

  private static final long serialVersionUID = -3916542803417759307L;

  public Page() {
    this.data = new ArrayList<>();
    this.count = 0L;
  }

  public Page(List<T> data, Long count) {
    this.data = data;
    this.count = count;
  }

  public Page(List<T> data, Long count, Set<String> sortedColumns) {
    this.data = data;
    this.count = count;
    this.sortedColumns = sortedColumns;
  }

  private List<T> data;

  private Long count;

  private Set<String> sortedColumns;

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public Set<String> getSortedColumns() {
    return sortedColumns;
  }

  public void setSortedColumns(Set<String> sortedColumns) {
    this.sortedColumns = sortedColumns;
  }
}
