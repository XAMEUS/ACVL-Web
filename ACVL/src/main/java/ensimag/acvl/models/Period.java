package ensimag.acvl.models;

import java.sql.Date;

public class Period {

    private final int id;
    private final Date limit;
    private final Date start;
    private final Date end;

    public Period(int id, Date limit, Date start, Date end) {
        this.id = id;
        this.limit = limit;
        this.start = start;
        this.end = end;
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

    @Override
    public String toString() {
        return "Period{" + "id=" + id + ", limit=" + limit + ", start=" + start + ", end=" + end + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Period) {
            return this.id == ((Period) obj).id;
        }
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
}
