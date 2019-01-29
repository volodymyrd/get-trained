package online.gettrained.backend;

import online.gettrained.backend.utils.AppInfo;
import online.gettrained.backend.utils.CommonUtils;

import java.net.URL;

/**
 * Get common info about module
 */
public class SharedModuleInfo {
    public static AppInfo getAppInfo() throws Exception {
        return CommonUtils.getAppInfo(CommonUtils.getManifestInJar(new URL(SharedModuleInfo.class
                .getResource(SharedModuleInfo.class.getSimpleName() + ".class").toString())));
    }
}
