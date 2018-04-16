/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ensimag.acvl.models;

import java.sql.Date;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ensimag
 */
public class Activity {

    private final int id;

    private final String name;

    private final Period period;
    
    private final List<String> animateurs;

    public Activity(int id, String name, Period period, List<String> animateurs) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.animateurs = animateurs;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Period getPeriod() {
        return period;
    }

    public List<String> getAnimateurs() {
        return animateurs;
    }


    @Override
    public String toString() {
        String tostring = "Activity num "+id+": "+name+", periode: "+period+ ", animateur(s): ";
        for (String animateur : animateurs){
            tostring += animateur+", "; 
        }
       tostring = tostring.substring(0, tostring.length()-2);
       return tostring;
    }
    
    
}
