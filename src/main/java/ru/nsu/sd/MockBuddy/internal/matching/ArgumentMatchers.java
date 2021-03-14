package ru.nsu.sd.MockBuddy.internal.matching;

import ru.nsu.sd.MockBuddy.internal.MockingInfo;

public class ArgumentMatchers {

    public static int anyInt() {
        putMatcher(new InstanceMatcher(Integer.class));
        return 0;
    }

    public static int equalsTo(int num) {
        putMatcher(new EqualsMatcher(num));
        return 0;
    }

    private static void putMatcher(ArgumentMatcher argumentMatcher) {
        MockingInfo.getArgumentMatcherStorage().addMatcher(argumentMatcher);
    }

}
