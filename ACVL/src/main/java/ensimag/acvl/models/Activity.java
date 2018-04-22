package ensimag.acvl.models;

import com.sun.xml.internal.ws.util.StringUtils;
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
    private final float price;
    private final int codeGrades;
    private final int codeDays;
    private final int codeStrategy;
    private final String title;
    private final String description;
    private final String animators;

    public Activity(int id, int capacity, List<Period> periods, float price, int codeGrades, int codeDays, int codeStrategy, String title, String description, String animators) {
        this.id = id;
        this.capacity = capacity;
        this.periods = periods;
        this.price = price;
        this.codeGrades = codeGrades;
        this.codeDays = codeDays;
        this.codeStrategy = codeStrategy;
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

    public float getPrice() {
        return price;
    }

    public int getCodeGrades() {
        return codeGrades;
    }

    public int getCodeDays() {
        return codeDays;
    }

    public int getCodeStrategy() {
        return codeStrategy;
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
        return "Activity{" + "id=" + id + ", capacity=" + capacity + ", periods=" + periods + ", price=" + price + ", codeGrades=" + codeGrades + ", codeDays=" + codeDays + ", codeStrategy=" + codeStrategy + ", title=" + title + ", description=" + description + ", animators=" + animators + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Activity)
            return id == ((Activity) obj).id;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getStringGrades() {
        String[] grades = {"PS", "MS", "GS", "CP", "CE1", "CE2", "CM1", "CM2"};
        List<String> activityGrades = new ArrayList();
        
        for (int i = 0; i < grades.length; i++) {
            if ((this.codeGrades >> i) % 2 == 1) {
                activityGrades.add(grades[i]);
            }
        }
        return activityGrades.toString().replace("[", "").replace("]", "");
    }
}
