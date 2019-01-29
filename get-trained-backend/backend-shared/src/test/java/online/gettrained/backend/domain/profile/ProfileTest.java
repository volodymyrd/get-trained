package online.gettrained.backend.domain.profile;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Volodymyr Dotsenko on 7/8/17.
 */
public class ProfileTest {
    private final String firstName = "Volodymyr";
    private final String firstNameWithSpace = "Volodymyr ";
    private final String lastName = "Dotsenko";
    private final String fullName = "Volodymyr Dotsenko";
    private final String fullNameWithoutLastName = "Volodymyr";
    private final String fullNameWithoutFirstName = "Dotsenko";

    @Test
    public void testSetFullNameViaConstructorWithFirstName() {
        Assert.assertEquals(fullNameWithoutLastName, new Profile(firstName).getFullName());
    }

    @Test
    public void testSetFullNameViaConstructorWithFirstNameWithSpace() {
        Assert.assertEquals(fullNameWithoutLastName, new Profile(firstNameWithSpace).getFullName());
    }

    @Test
    public void testSetFullNameViaConstructorWithFirstNameAndLastName() {
        Assert.assertEquals(fullName, new Profile(firstName, lastName).getFullName());
    }

    @Test
    public void testSetFullNameViaConstructorWithFirstNameWithSpaceAndLastName() {
        Assert.assertEquals(fullName, new Profile(firstNameWithSpace, lastName).getFullName());
    }

    @Test
    public void testSetFullNameViaConstructorWithFirstNameAndLastNameWithoutFirstName() {
        Assert.assertEquals(fullNameWithoutFirstName, new Profile("", lastName).getFullName());
    }
}
