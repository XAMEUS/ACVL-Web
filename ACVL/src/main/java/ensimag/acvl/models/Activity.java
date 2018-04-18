package ensimag.acvl.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ensimag
 */
public class Activity {

    private final int id;
    private final int capacity;
    private final List<Period> periods;
    private final int codeGrades;
    private final int codeDays;
    private final String title;
    private final String description;
    private final String animators;

    public Activity(int id, int capacity, List<Period> periods, int codeGrades, int codeDays, String title, String description, String animators) {
        this.id = id;
        this.capacity = capacity;
        this.periods = periods;
        this.codeGrades = codeGrades;
        this.codeDays = codeDays;
        this.title = title;
        this.description = description;
        this.animators = animators;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Period> getPeriod() {
        return periods;
    }

    public int getCodeGrades() {
        return codeGrades;
    }

    public int getCodeDays() {
        return codeDays;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAnimators() {
        return animators;
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + id + ", periods=" + periods + ", codeGrades=" + codeGrades + ", codeDays=" + codeDays + ", title=" + title + ", description=" + description + ", animators=" + animators + '}';
    }

}
