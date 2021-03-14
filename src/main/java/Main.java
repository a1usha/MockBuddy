import ru.nsu.sd.MockBuddy.MockBuddy;
import ru.nsu.sd.MockBuddy.internal.MockingInfo;
import static ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers.*;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.ArgumentMatcher;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Test test = MockBuddy.mock(Test.class);

        // (5 or 9) & (not (9 or 2) or 11) -> 5
        MockBuddy.when(test.foo(and(or(equalsTo(5),equalsTo(9)), or(not(or(equalsTo(9),equalsTo(2))), equalsTo(11))))).thenReturn("mocked");

        System.out.println(test.foo(5));
        System.out.println(test.foo(11));
        System.out.println(test.foo(7));

    }
}
