package ensimag.acvl.models;
;
import java.util.ArrayList;
import java.util.List;

public class Registration {

    private Child child = null;
    private Period period = null;
    private final int codeCantine;
    private final int codeGarderie;
    private final String infos;
    private final List<Activity> activities = new ArrayList<>();

    public Registration(int codeCantine, int codeGarderie, String infos) {
        this.codeCantine = codeCantine;
        this.codeGarderie = codeGarderie;
        this.infos = infos;
    }

    public Child getChild() {
        return child;
    }

    public Period getPeriod() {
        return period;
    }
    
    public void setChild(Child child) {
        this.child = child;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public int getCodeCantine() {
        return codeCantine;
    }

    public int getCodeGarderie() {
        return codeGarderie;
    }

    public String getInfos() {
        return infos;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
