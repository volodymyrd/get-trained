package online.gettrained.backend.dto.localization;

import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import online.gettrained.backend.domain.localization.ELocalModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dto for export/import localization.
 */
public final class LocalDto {

  private static final Logger LOG = LoggerFactory.getLogger(LocalDto.class);

  private static final String PARAM_MODULE = "module";
  private static final String PARAM_KEY = "localKey";

  private final ELocalModule module;
  private final String localKey;
  private final SortedMap<String, String> localMap;

  public LocalDto(ELocalModule module, String localKey, SortedMap<String, String> localMap) {
    this.module = module;
    this.localKey = localKey;
    this.localMap = localMap;
  }

  public ELocalModule getModule() {
    return module;
  }

  public String getLocalKey() {
    return localKey;
  }

  public SortedMap<String, String> getLocalMap() {
    return localMap;
  }

  public String toCSV(String delimiter) {
    return module.name()
        + delimiter
        + localKey
        + delimiter
        + localMap.entrySet().stream().map(Entry::getValue).collect(joining(delimiter));
  }

  private static LocalDto fromCSV(
      String data, Map<String, Integer> columnsMap, String delimiter) {

    LOG.debug("Convert string {}", data);

    String[] splittedData = data.split(delimiter);
    if (splittedData.length < 3) {
      throw new IllegalArgumentException("Insufficient number of parameters");
    }

    ELocalModule module = ELocalModule.valueOf(splittedData[columnsMap.get(PARAM_MODULE)]);
    String localKey = splittedData[columnsMap.get(PARAM_KEY)];

    SortedMap<String, String> localMap = columnsMap.entrySet().stream()
        .filter(e -> !e.getKey().equals(PARAM_MODULE)
            && !e.getKey().equals(PARAM_KEY)
            && e.getValue() < splittedData.length)
        .collect(
            Collectors.toMap(Entry::getKey, e -> splittedData[e.getValue()],
                (oldValue, newValue) -> oldValue,
                TreeMap::new)
        );

    return new LocalDto(module, localKey, localMap);
  }

  public static List<LocalDto> fromCSV(List<String> data, String delimiter) {
    LOG.debug("Start converting from a CSV file with {} records and delimiter {}",
        data.size(), delimiter);
    if (data.size() < 2) {
      throw new IllegalArgumentException("Insufficient number of parameters");
    }
    String[] columns = data.get(0).split(delimiter);
    Map<String, Integer> columnsMap = new HashMap<>();
    for (int i = 0; i < columns.length; i++) {
      columnsMap.put(columns[i], i);
    }
    LOG.debug("Obtained {} columnsMap", columnsMap);

    return data.stream()
        .skip(1)
        .map(d -> fromCSV(d, columnsMap, delimiter)).collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LocalDto)) {
      return false;
    }
    LocalDto that = (LocalDto) o;
    return module == that.module &&
        Objects.equals(localKey, that.localKey) &&
        Objects.equals(localMap, that.localMap);
  }

  @Override
  public int hashCode() {

    return Objects.hash(module, localKey, localMap);
  }

  @Override
  public String toString() {
    return "LocalExportDto{" +
        "module=" + module +
        ", localKey='" + localKey + '\'' +
        ", localMap=" + localMap +
        '}';
  }
}
