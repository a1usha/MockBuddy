package ru.nsu.sd.MockBuddy.internal;

import ru.nsu.sd.MockBuddy.internal.handling.InvocationHolder;
import ru.nsu.sd.MockBuddy.internal.handling.MockInvocationHandler;
import ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatcherStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MockingInfo {

    private static final ArgumentMatcherStorage argumentMatcherStorage;
    private static MockInvocationHandler lastMockInvocationHandler;

    private static HashMap<Integer, List<InvocationHolder>> hashMap;

    static {
        argumentMatcherStorage = new ArgumentMatcherStorage();
        hashMap = new HashMap<>();
    }

    public static MockInvocationHandler getLastMockInvocationHandler() {
        return lastMockInvocationHandler;
    }

    public static void setLastMockInvocationHandler(MockInvocationHandler handler) {
        lastMockInvocationHandler = handler;
    }

    public static ArgumentMatcherStorage getArgumentMatcherStorage() {
        return argumentMatcherStorage;
    }

    public static void addObjectToHashMup(Object obj, InvocationHolder holder) {

        if (hashMap.containsKey(System.identityHashCode(obj))) {

            if (hashMap.get(System.identityHashCode(obj)).stream().noneMatch(x -> x.getMethod().equals(holder.getMethod()) && Arrays.deepEquals(x.getArgs(), holder.getArgs()))) {
                List<InvocationHolder> newHolders = hashMap.get(System.identityHashCode(obj));
                newHolders.add(holder);
                hashMap.put(System.identityHashCode(obj), newHolders);
            } else {
                List<InvocationHolder> newHolders = hashMap.get(System.identityHashCode(obj));
                newHolders.add(holder);
                hashMap.put(System.identityHashCode(obj), newHolders);
            }

        } else {
            List<InvocationHolder> newHolders = new ArrayList<>();
            newHolders.add(holder);
            hashMap.put(System.identityHashCode(obj), newHolders);
        }
    }

    public static void addHolders(Object obj, List<InvocationHolder> holders) {
        hashMap.put(System.identityHashCode(obj), holders);
    }

    public static List<InvocationHolder> getListOfHolders(Object obj) {
        return hashMap.get(System.identityHashCode(obj));
    }
}
