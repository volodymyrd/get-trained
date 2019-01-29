package online.gettrained.backend.utils;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

import com.google.common.collect.ImmutableList;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * File utils.
 */
public final class FileUtils {

  public final static String CSV_SEPARATOR = ",";
  public final static String SEMICOLON_SEPARATOR = ";";

  public static File createWorkingDirectory(String locationFoWorkingDirectory) throws IOException {
    locationFoWorkingDirectory += "/";

    String workingDirectoryName = UUID.randomUUID().toString();
    File workingDirectory = new File(locationFoWorkingDirectory + workingDirectoryName);
    if (!workingDirectory.exists()) {
      if (workingDirectory.mkdirs()) {
        return workingDirectory;
      }
    }
    throw new IOException("Could not create a working directory:"
      + workingDirectory.getAbsolutePath());
  }

  public static File saveFileToDisk(File workingDirectory, byte[] data) throws IOException {
    String fileName = UUID.randomUUID().toString();
    String pathToFile = workingDirectory.getAbsolutePath() + "/" + fileName;
    File file = new File(pathToFile);
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      fileOutputStream.write(data);
      if (file.exists()) {
        return file;
      } else {
        throw new IOException("Error saving file to the disk.");
      }
    }
  }

  public static File saveFileToDiskNio(File workingDirectory, byte[] data) throws IOException {
    Path path = Paths.get(workingDirectory.getAbsolutePath() + "/" + UUID.randomUUID().toString());
    return Files.write(path, data).toFile();
  }

  public static boolean deleteWorkingDirectory(File directory) {
    if (!directory.exists()) {
      return true;
    }
    if (directory.isDirectory()) {
      for (File f : requireNonNull(directory.listFiles())) {
        if (!f.delete()) {
          return false;
        }
      }
    }
    return directory.delete();
  }

  public static byte[] fileToBytes(File file) throws IOException {
    return Files.readAllBytes(file.toPath());
  }

  public static ImmutableList<String> bytesToStringList(byte[] bytes) throws IOException {

    ImmutableList.Builder<String> resultList = ImmutableList.builder();

    if (bytes == null) {
      return resultList.build();
    }

    try (BufferedReader r = new BufferedReader(
      new InputStreamReader(new ByteArrayInputStream(bytes), UTF_8))) {
      for (String line = r.readLine(); line != null; line = r.readLine()) {
        resultList.add(line);
      }
    }

    return resultList.build();

  }

  private FileUtils() {
  }
}
