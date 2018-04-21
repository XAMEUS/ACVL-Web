package ensimag.acvl.models;

;
import java.util.ArrayList;
import java.util.List;



public class Registration {

    private int child;
    private int period;
    private final int codeCantine;
    private final int codeGarderie;
    private final String infos;
    private final List<Activity> activities = new ArrayList<>();

    public Registration(int child, int period, int codeCantine, int codeGarderie, String infos) {
        this.child = child;
        this.period = period;
        this.codeCantine = codeCantine;
        this.codeGarderie = codeGarderie;
        this.infos = infos;
    }

    public int getChild() {
        return child;
    }

    public int getPeriod() {
        return period;
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

    @Override
    public String toString() {
        return "Registration{" + "child=" + child + ", period=" + period + ", codeCantine=" + codeCantine + ", codeGarderie=" + codeGarderie + ", infos=" + infos + ", activities=" + activities + '}';
    }

}
