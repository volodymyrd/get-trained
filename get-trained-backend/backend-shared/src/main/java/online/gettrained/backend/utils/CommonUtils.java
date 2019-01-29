package online.gettrained.backend.utils;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.JarURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.jar.Manifest;
import java.util.stream.Stream;

/**
 * Utils
 */
public class CommonUtils {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  public static ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public static <T> List<T> immutableListOf(T... elements) {
    return Stream.of(elements).collect(collectingAndThen(toList(), Collections::unmodifiableList));
  }

  public static <T> Set<T> immutableSetOf(T... elements) {
    return Stream.of(elements).collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
  }

  public static Manifest getManifestInJar(URL url) {
    try {
      return ((JarURLConnection) url.openConnection()).getManifest();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  public static AppInfo getAppInfo(Manifest manifest) {
    return new AppInfo(
        manifest.getMainAttributes().getValue("Implementation-Version"),
        manifest.getMainAttributes().getValue("releases-date"),
        manifest.getMainAttributes().getValue("Specification-Title"));
  }

  public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public final static class Counter {

    public static Counter getInstance() {
      return new Counter();
    }

    public static Counter getInstance(int start) {
      return new Counter(start);
    }

    public int getNext() {
      return index++;
    }

    private int index;

    private Counter() {
      this(0);
    }

    private Counter(int start) {
      this.index = start;
    }
  }

  public static double[] convertFloatsToDoubles(float[] input) {
    if (input == null) {
      return null;
    }
    double[] output = new double[input.length];
    for (int i = 0; i < input.length; i++) {
      output[i] = input[i];
    }
    return output;
  }
}
