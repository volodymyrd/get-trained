package online.gettrained.backend.utils;

/**
 * Application info
 */
public class AppInfo {
    private final String version;
    private final String releasesDate;
    private final String title;

    public AppInfo(String version, String releasesDate, String title) {
        this.version = version;
        this.releasesDate = releasesDate;
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public String getReleasesDate() {
        return releasesDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<strong>title: </strong>" + title);
        builder.append("</br><strong>version: </strong>" + version);
        builder.append("</br><strong>releasesDate: </strong>" + releasesDate);
        return builder.toString();
    }
}
