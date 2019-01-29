package online.gettrained.frontend;

import online.gettrained.backend.utils.AppInfo;
import online.gettrained.backend.utils.CommonUtils;
import java.net.URL;

/**
 * Get common info about module
 */
public class FrontendModuleInfo {

  public static AppInfo getAppInfo() throws Exception {
    return CommonUtils.getAppInfo(CommonUtils.getManifestInJar(new URL(FrontendModuleInfo.class
        .getResource(FrontendModuleInfo.class.getSimpleName() + ".class").toString())));
  }
}
