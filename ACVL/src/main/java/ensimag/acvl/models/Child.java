package ensimag.acvl.models;

import java.sql.Date;
import java.util.Arrays;

public class Child {

    private final int id;

    private final String firstname;
    private final String lastname;
    private final Date birthdate;

    public Child(int id, String firstname, String lastname, Date birthdate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }
    
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    @Override
    public String toString() {
        return "Child{" + "firstname=" + firstname + ", lastname=" + lastname + ", birthdate=" + birthdate + '}';
    }
    
}
