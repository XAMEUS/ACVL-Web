package ensimag.acvl.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cancel {
    
    HashMap<Date, HashMap<Integer, List<Integer>>> map = new HashMap<>();
    
    public boolean isCanceled(Date date, int codeType, int code) {
        if (!map.containsKey(date)) return false;
        if (!map.get(date).containsKey(codeType)) return false;
        return map.get(date).get(codeType).contains(code);
    }
    
    public boolean isCanceled(LocalDate date, int codeType, int code) {
        return isCanceled(Date.valueOf(date), codeType, code);
    }
    
    public void addCancel(Date date, int codeType, int code) {
        if (!map.containsKey(date)) map.put(date, new HashMap<Integer, List<Integer>>());
        if (!map.get(date).containsKey(codeType)) map.get(date).put(codeType, new ArrayList<Integer>());
        map.get(date).get(codeType).add(code);
    }

    @Override
    public String toString() {
        return "Cancel{" + "map=" + map + '}';
    }
}
