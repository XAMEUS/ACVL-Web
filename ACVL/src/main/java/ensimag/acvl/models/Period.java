package ensimag.acvl.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Period {

    private final int id;
    private final Date limit;
    private final Date start;
    private final Date end;
    private final List<List<Activity>> activities = new ArrayList<>();

    public Period(int id, Date limit, Date start, Date end) {
        this.id = id;
        this.limit = limit;
        this.start = start;
        this.end = end;
        this.activities.add(new ArrayList<Activity>());
        this.activities.add(new ArrayList<Activity>());
        this.activities.add(new ArrayList<Activity>());
        this.activities.add(new ArrayList<Activity>());
        this.activities.add(new ArrayList<Activity>());
    }

    public int getId() {
        return id;
    }

    public Date getLimit() {
        return limit;
    }
    
    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public List<List<Activity>> getActivities() {
        return activities;
    }
    
    public void addActivitiy(int day, Activity activity) {
        this.activities.get(day - 1).add(activity);
    }
    
    public void addActivities(int day, List<Activity> l) {
        this.activities.get(day - 1).addAll(l);
    }

    @Override
    public String toString() {
        return "Period{" + "id=" + id + ", limit=" + limit + ", start=" + start + ", end=" + end + ", activities=" + activities + '}';
    }
    
    public String toPrettyString() {
        return "Période du " + start + " au " + end;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Period) {
            return this.id == ((Period) obj).id;
        }
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
}
