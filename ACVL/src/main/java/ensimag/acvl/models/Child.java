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
    private List<Period> registeredPeriods = new ArrayList<>();
    private List<Period> unregisteredPeriods = new ArrayList<>();

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
    
    public int getCodeGrade() {
        switch(this.grade) {
            case "PS ": return 1;
            case "MS ": return 2;
            case "GS ": return 4;
            case "CP ": return 8;
            case "CE1": return 16;
            case "CE2": return 32;
            case "CM1": return 64;
            case "CM2": return 128;
            default: return 0;
        }
    }

    public Date getBirthdate() {
        return birthdate;
    }
    
    public List<String> getDiet() {
        return this.diet;
    }

    @Override
    public String toString() {
        return "Child{" + "id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", gender=" + gender + ", grade=" + grade + ", birthdate=" + birthdate + ", diet=" + diet + ", registeredPeriods=" + registeredPeriods + ", unregisteredPeriods=" + unregisteredPeriods + '}';
    }
    
    public void setRegisteredPeriods(List<Period> registeredPeriods) {
        System.out.println(this + " " + registeredPeriods);
        this.registeredPeriods = registeredPeriods;
    }

    public void setUnregisteredPeriods(List<Period> unregisteredPeriods) {
        this.unregisteredPeriods = unregisteredPeriods;
    }

    public List<Period> getRegisteredPeriods() {
        return registeredPeriods;
    }

    public List<Period> getUnregisteredPeriods() {
        return unregisteredPeriods;
    }
    
}
