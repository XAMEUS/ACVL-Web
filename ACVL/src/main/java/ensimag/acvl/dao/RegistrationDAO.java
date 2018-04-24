package ensimag.acvl.dao;

import ensimag.acvl.models.Activity;
import ensimag.acvl.models.Child;
import ensimag.acvl.models.Period;
import ensimag.acvl.models.Registration;
import java.sql.*;
import java.time.temporal.TemporalAdjusters;
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
        protected int rank2;
        protected int day;
        protected Date birthdate;
        protected double value = Math.random();

        public Wish(int date, int child, int activity, int day, int rank, int rank2, Date birthdate) {
            this.date = date;
            this.child = child;
            this.activity = activity;
            this.day = day;
            this.rank = rank;
            this.rank2 = rank2;
            this.birthdate = birthdate;
        }

        @Override
        public boolean equals(Object obj) {
            return child == ((Wish) obj).child;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "{" + "date=" + date + ", child=" + child + ", activity=" + activity + ", rank=" + rank + ", birthdate=" + birthdate + ", value=" + value + '}';
        }

        @Override
        public int compareTo(Wish o) {
            if (rank2 < o.rank2) {
                return -1;
            }
            if (rank2 > o.rank2) {
                return 1;
            }
            if (rank < o.rank) {
                return -1;
            }
            if (rank > o.rank) {
                return 1;
            }
            return Double.compare(value, o.value);
        }
    }

    private class WishDate extends Wish {

        public WishDate(int date, int child, int activity, int day, int rank, int rank2, Date birthdate) {
            super(date, child, activity, day, rank, rank2, birthdate);
        }

        @Override
        public int compareTo(Wish o) {
            if (rank2 < o.rank2) {
                return -1;
            }
            if (rank2 > o.rank2) {
                return 1;
            }
            if (rank < o.rank) {
                return -1;
            }
            if (rank > o.rank) {
                return 1;
            }
            if (birthdate.before(o.birthdate)) {
                return -1;
            } else if (birthdate.after(o.birthdate)) {
                return 1;
            }
            return 0;
        }
    }

    private class WishFirst extends Wish {

        public WishFirst(int date, int child, int activity, int day, int rank, int rank2, Date birthdate) {
            super(date, child, activity, day, rank, rank2, birthdate);
        }

        @Override
        public int compareTo(Wish o) {
            if (rank2 < o.rank2) {
                return -1;
            }
            if (rank2 > o.rank2) {
                return 1;
            }
            if (rank < o.rank) {
                return -1;
            }
            if (rank > o.rank) {
                return 1;
            }
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
            st.setInt(2, period);
            st.setInt(3, activity);
            st.setInt(4, rank);
            st.setInt(5, day);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public void registration(int child, int period, int activity, int day) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_ActivitiesRegistrations (child, period, activity, day) VALUES (?, ?, ?, ?)");) {
            st.setInt(1, child);
            st.setInt(2, period);
            st.setInt(3, activity);
            st.setInt(4, day);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public List<Activity> getActivities(int child, int period, int day) {
        List<Activity> activities = new ArrayList<Activity>();
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("Select * from ACVL_ActivitiesRegistrations WHERE child = ? AND period = ? AND day = ?");) {
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
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("Select * from ACVL_Registrations WHERE child = ? AND period = ?");) {
            st.setInt(1, child);
            st.setInt(2, period);
            ResultSet rs = st.executeQuery();
            rs.next();
            Registration r = new Registration(rs.getInt("child"), rs.getInt("period"), rs.getInt("codeCantine"), rs.getInt("codeGarderie"), rs.getString("infos"));
            return r;
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public List<Registration> getRegistrations(int period) {
        List<Registration> registrations = new ArrayList<Registration>();
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("Select * from ACVL_Registrations WHERE period = ?");) {
            st.setInt(1, period);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Registration r = new Registration(rs.getInt("child"), rs.getInt("period"), rs.getInt("codeCantine"), rs.getInt("codeGarderie"), rs.getString("infos"));
                registrations.add(r);
            }
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
        return registrations;
    }

    public int getNumberOfRegistrations(int period, int child, int activity) {
        int n = 0;
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("Select COUNT(*) from ACVL_ActivitiesRegistrations, ACVL_Periods WHERE "
                        + "period = ? AND idPeriod = ? AND child = ? AND activity = ? AND startDate between ? AND ?");) {
            st.setInt(1, period);
            st.setInt(2, period);
            st.setInt(3, child);
            st.setInt(4, activity);
            st.setDate(5, Date.valueOf(ensimag.acvl.time.Time.getDate().toLocalDate().with(TemporalAdjusters.firstDayOfYear())));
            st.setDate(6, Date.valueOf(ensimag.acvl.time.Time.getDate().toLocalDate().with(TemporalAdjusters.lastDayOfYear())));
            ResultSet rs = st.executeQuery();
            rs.next();
            n = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
        return n;
    }

    public List<HashMap<Integer, HashMap<Integer, PriorityQueue<Wish>>>> moulinette() {
        System.out.println("task: moulinette");
        ChildDAO childDAO = new ChildDAO(dataSource);
        List<Period> periods = childDAO.getPeriods(false);
        System.out.println("periods: " + periods);
        List<HashMap<Integer, HashMap<Integer, PriorityQueue<Wish>>>> l = new ArrayList<>();
        for (Period p : periods) {
            l.add(moulinette(p));
            try (
                    Connection conn = getConn();
                    PreparedStatement st = conn.prepareStatement("insert into ACVL_moulinette (period) VALUES (?)");) {
                st.setInt(1, p.getId());
                st.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Database error " + e.getMessage(), e);
            }

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
        HashMap<Integer, Integer>[] capacities = new HashMap[5];
        capacities[0] = new HashMap<>();
        capacities[1] = new HashMap<>();
        capacities[2] = new HashMap<>();
        capacities[3] = new HashMap<>();
        capacities[4] = new HashMap<>();
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("SELECT codeStrategy, activity, capacity, day, rank, child, w.id, birthdate "
                        + "FROM ACVL_Wishes w, ACVL_Activities a, ACVL_Children c WHERE w.activity = a.id AND w.child = c.id AND period=?");) {
            st.setInt(1, p.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int codeStrategy = rs.getInt("codeStrategy");
                int activity = rs.getInt("activity");
                int day = rs.getInt("day");
                int child = rs.getInt("child");
                int date = rs.getInt("id");
                int capacity = rs.getInt("capacity");
                int rank = rs.getInt("rank");
                Date birthdate = rs.getDate("birthdate");
                Wish w = new Wish(date, child, activity, day, rank, getNumberOfRegistrations(p.getId(), child, activity), birthdate);
                /* 1 = FIFS, 2 = random, 3 = Age */
                if (!map.get(day).keySet().contains(activity)) {
                    map.get(day).put(activity, new PriorityQueue<Wish>());
                    capacities[0].put(activity, capacity);
                    capacities[1].put(activity, capacity);
                    capacities[2].put(activity, capacity);
                    capacities[3].put(activity, capacity);
                    capacities[4].put(activity, capacity);
                }
                if (codeStrategy == 1) {
                    w = new WishFirst(date, child, activity, day, rank, getNumberOfRegistrations(p.getId(), child, activity), birthdate);
                    map.get(day).get(activity).add(w);
                }
                if (codeStrategy == 2) {
                    w = new Wish(date, child, activity, day, rank, getNumberOfRegistrations(p.getId(), child, activity), birthdate);
                    map.get(day).get(activity).add(w);
                }
                if (codeStrategy == 3) {
                    w = new WishDate(date, child, activity, day, rank, getNumberOfRegistrations(p.getId(), child, activity), birthdate);
                    map.get(day).get(activity).add(w);
                }
            }
            for (int i = 0; i < 5; i++) {
                HashMap<Integer, PriorityQueue<Wish>> hashMap = map.get(i);
                int rank = 1;
                while (true) {
                    for (Integer activity : hashMap.keySet()) {
                        PriorityQueue<Wish> queue = hashMap.get(activity);
                        int capacity = capacities[i].get(activity);
                        int rank2 = 0;
                        if (!queue.isEmpty()) {
                            rank2 = queue.peek().rank2;
                        }
                        while (true) {
                            if (queue.isEmpty() || capacity <= 0) {
                                hashMap.remove(activity);
                                break;
                            }
                            if (queue.peek().rank == rank && rank2 == queue.peek().rank2) {
                                Wish w = queue.poll();
                                registration(w.child, p.getId(), activity, w.day);
                                capacity--;
                                for (PriorityQueue<Wish> pq : hashMap.values()) {
                                    pq.remove(w);
                                }
                            } else {
                                break;
                            }
                        }
                        capacities[i].put(activity, capacity);
                    }
                    rank++;
                    if (hashMap.isEmpty()) {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
        return map;
    }

    public List<Child> getRegistredChilrend(int period) {
        List<Child> result = new ArrayList<>();
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("Select * from ACVL_Registrations WHERE period = ?");) {
            st.setInt(1, period);
            ResultSet rs = st.executeQuery();
            ChildDAO childDAO = new ChildDAO(dataSource);
            while (rs.next()) {
                result.add(childDAO.getChild(rs.getInt("child")));
            }
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
        return result;
    }

    public HashMap<String, Integer> recapDiets(List<Child> children) {
        HashMap<String, Integer> result = new HashMap<>();
        for (Child c : children) {
            for (String diet : c.getDiet()) {
                if (!result.containsKey(c)) {
                    result.put(diet, 1);
                } else {
                    result.put(diet, result.get(diet));
                }
            }
        }
        return result;
    }

    public List<Integer> recap(List<Registration> registrations, List<Child> childs) {
        List<Integer> result = new ArrayList<>();
        int cantineLundi = 0;
        int cantineMardi = 0;
        int cantineMercredi = 0;
        int cantineJeudi = 0;
        int cantineVendredi = 0;
        int garderieMatin = 0;
        int garderie1 = 0;
        int garderie2 = 0;
        int garderie3 = 0;
        ChildDAO childDAO = new ChildDAO(dataSource);
        for (Registration r : registrations) {
            childs.add(childDAO.getChild(r.getChild()));
            if (r.getCodeCantine() % 2 == 1) {
                cantineLundi++;
            }
            if ((r.getCodeCantine() >> 1) % 2 == 1) {
                cantineMardi++;
            }
            if ((r.getCodeCantine() >> 2) % 2 == 1) {
                cantineMercredi++;
            }
            if ((r.getCodeCantine() >> 3) % 2 == 1) {
                cantineJeudi++;
            }
            if ((r.getCodeCantine() >> 4) % 2 == 1) {
                cantineVendredi++;
            }

            // TODO remove if activity
            if ((r.getCodeGarderie()) % 2 == 1) {
                garderieMatin++;
            }
            if ((r.getCodeGarderie() >> 1) % 2 == 1) {
                garderie1++;
            }
            if ((r.getCodeGarderie() >> 2) % 2 == 1) {
                garderie2++;
            }
            if ((r.getCodeGarderie() >> 2) % 2 == 1) {
                garderie3++;
            }
        }
        result.add(cantineLundi);
        result.add(cantineMardi);
        result.add(cantineMercredi);
        result.add(cantineJeudi);
        result.add(cantineVendredi);
        result.add(garderieMatin);
        result.add(garderie1);
        result.add(garderie2);
        result.add(garderie3);
        return result;
    }

    public List<Child>[] getSubscribers(int period, int activity) {
        List<Child>[] result = new List[5];
        result[0] = new ArrayList<>();
        result[1] = new ArrayList<>();
        result[2] = new ArrayList<>();
        result[3] = new ArrayList<>();
        result[4] = new ArrayList<>();
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("Select * from ACVL_ActivitiesRegistrations WHERE period = ? AND activity = ?");) {
            st.setInt(1, period);
            st.setInt(2, activity);
            ResultSet rs = st.executeQuery();
            ChildDAO childDAO = new ChildDAO(dataSource);
            while (rs.next()) {
                result[rs.getInt("day")].add(childDAO.getChild(rs.getInt("child")));
            }
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
        return result;
    }

}
