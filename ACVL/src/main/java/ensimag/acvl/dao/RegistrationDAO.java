package ensimag.acvl.dao;

import ensimag.acvl.models.Activity;
import ensimag.acvl.models.Period;
import ensimag.acvl.models.Registration;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import javax.sql.DataSource;

public class RegistrationDAO extends AbstractDataBaseDAO {

    private class Wish implements Comparable<Wish> {

        protected int date;
        protected int child;
        protected int activity;
        protected int rank;
        protected Date birthdate;
        protected double value = Math.random();

        public Wish(int date, int child, int activity, int rank, Date birthdate) {
            this.date = date;
            this.child = child;
            this.activity = activity;
            this.rank = rank;
            this.birthdate = birthdate;
        }

        @Override
        public boolean equals(Object obj) {
            return child == ((Wish) obj).child;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName()+"{" + "date=" + date + ", child=" + child + ", activity=" + activity + ", rank=" + rank + ", birthdate=" + birthdate + ", value=" + value + '}';
        }

        @Override
        public int compareTo(Wish o) {
            if (rank < o.rank)
                return -1;
            if (rank > o.rank)
                return 1;
            return Double.compare(value, o.value);
        }
    }

    private class WishDate extends Wish {

        public WishDate(int date, int child, int activity, int day, Date birthdate) {
            super(date, child, activity, day, birthdate);
        }

        @Override
        public int compareTo(Wish o) {
            if (rank < o.rank)
                return -1;
            if (rank > o.rank)
                return 1;
            if (birthdate.before(o.birthdate)) {
                return -1;
            } else if (birthdate.after(o.birthdate)) {
                return 1;
            }
            return 0;
        }
    }

    private class WishFirst extends Wish {

        public WishFirst(int date, int child, int activity, int day, Date birthdate) {
            super(date, child, activity, day, birthdate);
        }

        @Override
        public int compareTo(Wish o) {
            if (rank < o.rank)
                return -1;
            if (rank > o.rank)
                return 1;
            return date - o.date;
        }
    }

    public RegistrationDAO(DataSource ds) {
        super(ds);
    }

    public void registerChild(int child, int period, int codeCantine, int codeGarderie, String infos) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Registrations (child, period, codeCantine, codeGarderie, infos) VALUES (?, ?, ?, ?, ?)");) {
            st.setInt(1, child);
            st.setInt(2, period);
            st.setInt(3, codeCantine);
            st.setInt(4, codeGarderie);
            st.setString(5, infos);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public void registerWish(int child, int period, int activity, int day, int rank) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Wishes (child, period, activity, rank, day) VALUES (?, ?, ?, ?, ?)");) {
            st.setInt(1, child);
            System.out.println("ensimag.acvl.dao.RegistrationDAO.registerWish()" + child + ":" + activity + ":" + day);
            st.setInt(2, period);
            st.setInt(3, activity);
            st.setInt(4, rank);
            st.setInt(5, day);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }
    
    public List<Activity> getActivities(int child, int period, int day) {
        List<Activity> activities = new ArrayList<>();
        try (
                Connection conn = getConn(); // TODO USE ACVL_ActivitiesRegistrations
                PreparedStatement st = conn.prepareStatement("Select * from ACVL_Wishes WHERE child = ? AND period = ? AND day = ?");) {
            st.setInt(1, child);
            st.setInt(2, period);
            st.setInt(3, day);
            ResultSet rs = st.executeQuery();
            ActivityDAO activityDAO = new ActivityDAO(dataSource);
            while (rs.next()) {
                activities.add(activityDAO.getActivity(rs.getInt("activity")));
            }
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
        return activities;
    }
    
    public Registration getRegistration(int child, int period) {
        List<Activity> activities = new ArrayList<>();
        try (
                Connection conn = getConn(); // TODO USE ACVL_ActivitiesRegistrations
                PreparedStatement st = conn.prepareStatement("Select * from ACVL_Registrations WHERE child = ? AND period = ?");) {
            st.setInt(1, child);
            st.setInt(2, period);
            ResultSet rs = st.executeQuery();
            rs.next();
            Registration r = new Registration(rs.getInt("codeCantine"), rs.getInt("codeGarderie"), rs.getString("infos"));
            return r;
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public List<HashMap<Integer, HashMap<Integer, PriorityQueue<Wish>>>> moulinette() {
        System.out.println("==============");
        System.out.println("MOULINETTE !!!");
        System.out.println("==============");
        ChildDAO childDAO = new ChildDAO(dataSource);
        List<Period> periods = childDAO.getPeriods(false);
        System.out.println("periods: " + periods);
        List<HashMap<Integer, HashMap<Integer, PriorityQueue<Wish>>>> l = new ArrayList<>();
        for (Period p : periods) {
            l.add(moulinette(p));
        }
        return l;
    }

    public HashMap<Integer, HashMap<Integer, PriorityQueue<Wish>>> moulinette(Period p) {
        System.out.println("MOULINETTE ! " + p);
        HashMap<Integer, HashMap<Integer, PriorityQueue<Wish>>> map = new HashMap<>(); // map(jour, map(activité, voeux triés))
        map.put(0, new HashMap<Integer, PriorityQueue<Wish>>()); // lundi
        map.put(1, new HashMap<Integer, PriorityQueue<Wish>>()); // mardi
        map.put(2, new HashMap<Integer, PriorityQueue<Wish>>()); // mercredi
        map.put(3, new HashMap<Integer, PriorityQueue<Wish>>()); // jeudi
        map.put(4, new HashMap<Integer, PriorityQueue<Wish>>()); // vendredi
        HashMap<Integer, Integer> capacities = new HashMap<>();
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("SELECT codeStrategy, activity, capacity, day, child, w.id, birthdate "
                        + "FROM ACVL_Wishes w, ACVL_Activities a, ACVL_Children c WHERE w.activity = a.id AND w.child = c.id");) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int codeStrategy = rs.getInt("codeStrategy");
                int activity = rs.getInt("activity");
                int day = rs.getInt("day");
                int child = rs.getInt("child");
                int date = rs.getInt("id");
                int capacity = rs.getInt("capacity");
                Date birthdate = rs.getDate("birthdate");
                Wish w = new Wish(date, child, activity, day, birthdate);
                System.out.println(w);
                /* 1 = FIFS, 2 = random, 3 = Age */
                if (!map.get(day).keySet().contains(activity)) {
                    map.get(day).put(activity, new PriorityQueue<Wish>());
                    capacities.put(activity, capacity);
                }
                if (codeStrategy == 1) {
                    w = new WishFirst(date, child, activity, day, birthdate);
                    map.get(day).get(activity).add(w);
                }
                if (codeStrategy == 2) {
                    w = new Wish(date, child, activity, day, birthdate);
                    map.get(day).get(activity).add(w);
                }
                if (codeStrategy == 3) {
                    w = new WishDate(date, child, activity, day, birthdate);
                    map.get(day).get(activity).add(w);
                }
            }
            System.out.println(map);
            while (true) {
                // TODO tant qu'on arrive à faire poper des trucs, et qu'on respecte les règles.
                if (true)
                    break;
            }
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
        return map;
    }

}
