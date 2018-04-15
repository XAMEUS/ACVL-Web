package ensimag.acvl.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Child {

    private final int id;

    private final String firstname;
    private final String lastname;
    private final char gender;
    private final String grade;
    private final Date birthdate;
    private final List<String> diet = new ArrayList<>();

    public Child(int id, String firstname, String lastname,  char gender, String grade, Date birthdate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.grade = grade;
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

    public char getGender() {
        return gender;
    }

    public String getGrade() {
        return grade;
    }

    public Date getBirthdate() {
        return birthdate;
    }
    
    public List<String> getDiet() {
        return this.diet;
    }

    @Override
    public String toString() {
        return "Child{" + "firstname=" + firstname + ", lastname=" + lastname + ", birthdate=" + birthdate + '}';
    }
    
}
