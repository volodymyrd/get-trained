package online.gettrained.backend.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Keeps all application properties.
 */
@Component
public class ApplicationCommonProperties {

  private final String logFile;

  private final String corsAllowedOrigins;
  private final Integer maxBadLoginAttempts;

  private final Integer batchPoolSize;
  private final Integer tasksBatchPoolSize;
  private final Integer tasksMaxBatchPoolSize;
  private final Integer appThreadPoolSize;
  private final Integer appMaxThreadPoolSize;

  private final String mailNoreplayAddress;
  private final String mailBccAddresses;
  private final String mailAdminEmails;

  private final String multipartMaxFileSize;

  private final String helpDeskUrl;

  private final String userPropertyHost;
  private final String userPropertyClientEnvironment;
  private final String userPropertyActiveTheme;

  private final String tempDir;

  private final String pathToFfmpegExec;

  private final String pathToLuceneDir;

  private final String pathToReportTemplatesDir;

  private final boolean fileStorageEnabled;
  private final String fsPathToLocalStorageCache;
  // in hours
  private final int fsLocalStorageCacheExpire;

  private final String fsGcpProjectId;
  private final String fsGcpPathToCredential;
  private final String fsGcpBucket;

  private final boolean dataWarehouseEnabled;
  private final String dwhGcpPathToCredential;
  private final String dwhGcpDatasetName;
  private final String dwhGcpPathToTableSchemes;
  private final String dwhGcpAssessmentTableName;

  public ApplicationCommonProperties(
      @Value("${logging.file:}") String logFile,
      @Value("${sec.cors.allowed_origins:*}") String corsAllowedOrigins,
      @Value("${sec.max_bad_login_attempts:3}") Integer maxBadLoginAttempts,
      @Value("${user.prop.scheduling.batch.pool_size:}") Integer batchPoolSize,
      @Value("${user.prop.scheduling.batch.tasks.pool_size:}") Integer tasksBatchPoolSize,
      @Value("${user.prop.scheduling.batch.tasks.max_pool_size:}") Integer tasksMaxBatchPoolSize,
      @Value("${user.prop.app.thread.pool_size:}") Integer appThreadPoolSize,
      @Value("${user.prop.app.max.thread.pool_size:}") Integer appMaxThreadPoolSize,
      @Value("${user.prop.mail.noreplay.address}") String mailNoreplayAddress,
      @Value("${user.prop.mail.bcc.addresses}") String mailBccAddresses,
      @Value("${user.prop.admin_emails:}") String mailAdminEmails,
      @Value("${spring.servlet.multipart.max-file-size}") String multipartMaxFileSize,
      @Value("${help_desk_url:}") String helpDeskUrl,
      @Value("${user.prop.host:undefined}") String userPropertyHost,
      @Value("${user.prop.environment:berize}") String userPropertyClientEnvironment,
      @Value("${user.prop.theme:berize}") String userPropertyActiveTheme,
      @Value("${user.prop.temp_dir:/tmp}") String tempDir,
      @Value("${user.prop.path_to_ffmpeg:}") String pathToFfmpegExec,
      @Value("${user.prop.path_to_lucene_dir:lucene}") String pathToLuceneDir,
      @Value("${user.prop.path_to_report_templates_dir:report_templates}") String pathToReportTemplatesDir,
      @Value("${user.prop.fs.enabled:false}") boolean fileStorageEnabled,
      @Value("${user.prop.fs.path_to_local_storage_cache:}") String fsPathToLocalStorageCache,
      @Value("${user.prop.fs.local_storage_cache_expire:1}") int fsLocalStorageCacheExpire,
      @Value("${user.prop.fs.gcp_project_id:}") String fsGcpProjectId,
      @Value("${user.prop.fs.gcp_path_to_credential:}") String fsGcpPathToCredential,
      @Value("${user.prop.fs.gcp_bucket:}") String fsGcpBucket,
      @Value("${user.prop.dwh.enabled:false}") boolean dataWarehouseEnabled,
      @Value("${user.prop.dwh.gcp_path_to_credential:}") String dwhGcpPathToCredential,
      @Value("${user.prop.dwh.gcp_dataset_name:}") String dwhGcpDatasetName,
      @Value("${user.prop.dwh.gcp_path_to_table_schemes:}") String dwhGcpPathToTableSchemes,
      @Value("${user.prop.dwh.gcp_assessment_table_name:}") String dwhGcpAssessmentTableName
  ) {

    this.logFile = logFile;
    this.corsAllowedOrigins = corsAllowedOrigins;
    this.maxBadLoginAttempts = maxBadLoginAttempts;
    this.batchPoolSize = batchPoolSize;
    this.tasksBatchPoolSize = tasksBatchPoolSize;
    this.tasksMaxBatchPoolSize = tasksMaxBatchPoolSize;
    this.appThreadPoolSize = appThreadPoolSize;
    this.appMaxThreadPoolSize = appMaxThreadPoolSize;
    this.mailNoreplayAddress = mailNoreplayAddress;
    this.mailBccAddresses = mailBccAddresses;
    this.mailAdminEmails = mailAdminEmails;
    this.multipartMaxFileSize = multipartMaxFileSize;
    this.helpDeskUrl = helpDeskUrl;
    this.userPropertyClientEnvironment = userPropertyClientEnvironment;
    this.userPropertyActiveTheme = userPropertyActiveTheme;
    this.userPropertyHost = userPropertyHost;
    this.tempDir = tempDir;
    this.pathToFfmpegExec = pathToFfmpegExec;
    this.pathToLuceneDir = pathToLuceneDir;
    this.pathToReportTemplatesDir = pathToReportTemplatesDir;
    this.fileStorageEnabled = fileStorageEnabled;
    this.fsPathToLocalStorageCache = fsPathToLocalStorageCache;
    this.fsLocalStorageCacheExpire = fsLocalStorageCacheExpire;
    this.fsGcpProjectId = fsGcpProjectId;
    this.fsGcpPathToCredential = fsGcpPathToCredential;
    this.fsGcpBucket = fsGcpBucket;
    this.dataWarehouseEnabled = dataWarehouseEnabled;
    this.dwhGcpPathToCredential = dwhGcpPathToCredential;
    this.dwhGcpDatasetName = dwhGcpDatasetName;
    this.dwhGcpAssessmentTableName = dwhGcpAssessmentTableName;
    this.dwhGcpPathToTableSchemes = dwhGcpPathToTableSchemes;
  }

  public String getLogFile() {
    return logFile;
  }

  public String getCorsAllowedOrigins() {
    return corsAllowedOrigins;
  }

  public Integer getMaxBadLoginAttempts() {
    return maxBadLoginAttempts;
  }

  public Integer getBatchPoolSize() {
    return batchPoolSize;
  }

  public Integer getTasksBatchPoolSize() {
    return tasksBatchPoolSize;
  }

  public Integer getTasksMaxBatchPoolSize() {
    return tasksMaxBatchPoolSize;
  }

  public Integer getAppThreadPoolSize() {
    return appThreadPoolSize;
  }

  public Integer getAppMaxThreadPoolSize() {
    return appMaxThreadPoolSize;
  }

  public String getMailNoreplayAddress() {
    return mailNoreplayAddress;
  }

  public String getMailBccAddresses() {
    return mailBccAddresses;
  }

  public String getMailAdminEmails() {
    return mailAdminEmails;
  }

  public String getMultipartMaxFileSize() {
    return multipartMaxFileSize;
  }

  public String getHelpDeskUrl() {
    return helpDeskUrl;
  }

  public String getUserPropertyHost() {
    return userPropertyHost;
  }

  public String getUserPropertyClientEnvironment() {
    return userPropertyClientEnvironment;
  }

  public String getUserPropertyActiveTheme() {
    return userPropertyActiveTheme;
  }

  public String getTempDir() {
    return removeLastSlashIfExistInNameOfDir(tempDir);
  }

  public String getPathToFfmpegExec() {
    return removeLastSlashIfExistInNameOfDir(pathToFfmpegExec);
  }

  public String getPathToLuceneDir() {
    return removeLastSlashIfExistInNameOfDir(pathToLuceneDir);
  }

  public String getPathToReportTemplatesDir() {
    return removeLastSlashIfExistInNameOfDir(pathToReportTemplatesDir);
  }

  public boolean isFileStorageEnabled() {
    return fileStorageEnabled;
  }

  public String getFsPathToLocalStorageCache() {
    return fsPathToLocalStorageCache;
  }

  public int getFsLocalStorageCacheExpire() {
    return fsLocalStorageCacheExpire;
  }

  public String getFsGcpProjectId() {
    return fsGcpProjectId;
  }

  public String getFsGcpPathToCredential() {
    return fsGcpPathToCredential;
  }

  public String getFsGcpBucket() {
    return fsGcpBucket;
  }

  public boolean isDataWarehouseEnabled() {
    return dataWarehouseEnabled;
  }

  public String getDwhGcpPathToCredential() {
    return dwhGcpPathToCredential;
  }

  public String getDwhGcpDatasetName() {
    return dwhGcpDatasetName;
  }

  public String getDwhGcpAssessmentTableName() {
    return dwhGcpAssessmentTableName;
  }

  public String getDwhGcpPathToTableSchemes() {
    return removeLastSlashIfExistInNameOfDir(dwhGcpPathToTableSchemes);
  }

  private static String removeLastSlashIfExistInNameOfDir(String dir) {
    if (dir.length() < 2) {
      return dir;
    }
    if (dir.endsWith("\\") || dir.endsWith("/")) {
      return dir.substring(0, dir.length() - 1);
    } else {
      return dir;
    }
  }
}
