package Allies;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AlliesManager {
    private final Set<String> usersSet;


    public AlliesManager() {
        usersSet = new HashSet<>();
    }

    public synchronized void addUser(String alliesname) {
        usersSet.add(alliesname);
    }

    public synchronized void removeUser(String alliesname) {
        usersSet.remove(alliesname);
    }

    public synchronized Set<String> getUsers() {
        return Collections.unmodifiableSet(usersSet);
    }

    public boolean isUserExists(String alliesname) {
        return usersSet.contains(alliesname);
    }
}
