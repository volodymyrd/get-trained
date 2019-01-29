package online.gettrained.backend.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import online.gettrained.backend.utils.CommonUtils;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * Tests for {@link BaseRepository}
 */
public class BaseRepositoryTest {

  @Test
  public void testBuildOrderByClauseIfSortNull() {
    assertTrue(new BaseRepository().buildOrderByClause(null).isEmpty());
  }

  @Test
  public void testBuildOrderByClauseIfSortListOrder() {
    // Arrange
    Sort sort = new Sort(CommonUtils.immutableListOf(
        new Order(Direction.ASC, "prop1"),
        new Order(Direction.DESC, "prop2"),
        new Order(Direction.ASC, "prop3"),
        new Order(Direction.DESC, "prop4")
    ));
    // Act
    String orderClause = new BaseRepository().buildOrderByClause(sort);
    // Assert
    assertFalse(orderClause.isEmpty());
    assertEquals(" ORDER BY prop1 ASC,prop2 DESC,prop3 ASC,prop4 DESC", orderClause);
  }

  @Test
  public void testBuildOrderByClauseWithSpecificMatcher() {
    // Arrange
    Sort sort = new Sort(CommonUtils.immutableListOf(
        new Order(Direction.ASC, "prop1"),
        new Order(Direction.DESC, "prop2"),
        new Order(Direction.ASC, "prop3"),
        new Order(Direction.DESC, "prop4")
    ));
    // Act
    String orderClause = new BaseRepository().buildOrderByClause(sort, String::toUpperCase);
    // Assert
    assertFalse(orderClause.isEmpty());
    assertEquals(" ORDER BY PROP1 ASC,PROP2 DESC,PROP3 ASC,PROP4 DESC", orderClause);
  }
}
