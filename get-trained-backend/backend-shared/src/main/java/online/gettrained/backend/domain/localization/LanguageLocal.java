package online.gettrained.backend.domain.localization;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LanguageLocal implements Serializable {
    private static final long serialVersionUID = -5531331419731348303L;

    /**
     * Language name
     */
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
