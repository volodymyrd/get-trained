package online.gettrained.frontend.web.auth.dto;

import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.user.User;

import java.io.Serializable;

/**
 *
 */
public class UserRegistrationDto implements Serializable {
    private static final long serialVersionUID = 8230667472232394079L;

    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);
        return user;
    }

    public Profile toProfile(Language language) {
        Profile profile;
        if (lastName == null)
            profile = new Profile(firstName);
        else
            profile = new Profile(firstName, lastName);

        profile.setLanguage(language);

        return profile;
    }

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String email, String newPassword, String firstName, String lang) {
        this.email = email;
        this.newPassword = newPassword;
        this.firstName = firstName;
        this.lang = lang;
    }

    private String email;
    private String newPassword;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String lang;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
