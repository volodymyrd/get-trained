package online.gettrained.backend.dto.localization;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.utils.CommonUtils;
import java.util.TreeMap;
import org.junit.Test;

/**
 * Tests for {@link LocalDto}.
 */
public class LocalExportDtoTest {

  @Test
  public void testFromCSVToListOfLocalExportDto() {
    assertThat(LocalDto.fromCSV(CommonUtils
            .immutableListOf(
                "module;localKey;en;ua",
                "AUTH;key1;value1;значення1",
                "AUTH;key2;value2;значення2"),
        ";"),
        is(CommonUtils.immutableListOf(
            new LocalDto(ELocalModule.AUTH, "key1", new TreeMap<String, String>() {{
              put("en", "value1");
              put("ua", "значення1");
            }}),
            new LocalDto(ELocalModule.AUTH, "key2", new TreeMap<String, String>() {{
              put("en", "value2");
              put("ua", "значення2");
            }})
            )
        )
    );
  }
}
