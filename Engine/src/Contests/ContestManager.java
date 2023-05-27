package Contests;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ContestManager {
    private final Set<String> contestsSet;

    public ContestManager() {
        contestsSet = new HashSet<>();
    }

    public synchronized void addUser(String contestname) {
        contestsSet.add(contestname);
    }

    public synchronized void removeUser(String contestname) {
        contestsSet.remove(contestname);
    }

    public synchronized Set<String> getUsers() {
        return Collections.unmodifiableSet(contestsSet);
    }

    public boolean isUserExists(String contestname) {
        return contestsSet.contains(contestname);
    }
    public void removeNameUBoat(String name){
        contestsSet.remove(name);
    }
}
