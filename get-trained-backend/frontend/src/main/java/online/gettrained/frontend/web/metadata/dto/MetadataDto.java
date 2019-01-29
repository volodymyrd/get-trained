package online.gettrained.frontend.web.metadata.dto;

import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Language;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Metadata
 */
public class MetadataDto implements Serializable {
    private static final long serialVersionUID = 6880337907739149483L;

    private final String lang;
    private final ELocalModule module;
    private final List<Language> languages;
    private final Map<String, String> localizations;
    private final Map<String, String> settings;

    public MetadataDto(String lang, ELocalModule module, List<Language> languages,
                       Map<String, String> localizations, Map<String, String> settings) {
        this.lang = lang;
        this.module = module;
        this.languages = languages;
        this.localizations = localizations;
        this.settings = settings;
    }

    public String getLang() {
        return lang;
    }

    public ELocalModule getModule() {
        return module;
    }

    public Map<String, String> getLocalizations() {
        return localizations;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public Map<String, String> getSettings() {
        return settings;
    }
}
