package online.gettrained.backend.dto.localization;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Dto for export localization.
 */
public final class LocalExportDto {

  private final Set<String> header = new LinkedHashSet<>();
  private final Map<String, LocalDto> exportDtoMap = new LinkedHashMap<>();

  public LocalExportDto() {
    addColumn("module");
    addColumn("localKey");
  }

  public void addColumn(String name) {
    header.add(name);
  }

  public void put(String key, LocalDto value) {
    exportDtoMap.put(key, value);
  }

  public LocalDto get(String key) {
    return exportDtoMap.get(key);
  }

  public String toCSV(String delimiter) {
    return String.join(delimiter, new ArrayList<>(this.header))
        + "\n"
        + String.join(
        "\n",
        exportDtoMap.values().stream()
            .map(l -> l.toCSV(delimiter))
            .collect(Collectors.toList())
    );
  }
}
