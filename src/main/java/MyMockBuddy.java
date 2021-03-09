import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyMockBuddy {

    private static MockInvocationHandler lastMockInvocationHandler;

    public static <T> T mock(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass(clazz)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(new MockInvocationHandler()))
                .make()
                .load(clazz.getClassLoader())
                .getLoaded();

        return byteBuddy.getDeclaredConstructor().newInstance();
    }

    public static <T> When<T> when(T obj) {
        return new When<>();
    }

    public static class When<T> {
        public void thenReturn(T retObj) {
            lastMockInvocationHandler.setRetObj(retObj);
        }
    }

    private static class MockInvocationHandler implements InvocationHandler {

        private Method lastMethod;
        private Object[] lastArgs;
        private List<DataHolder> dataHolders = new ArrayList<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            lastMockInvocationHandler = this;
            lastMethod = method;
            lastArgs = args;

            // checks if the method was already called with the given arguments
            for (DataHolder dataHolder : dataHolders) {
                if (dataHolder.getMethod().equals(method) && Arrays.deepEquals(dataHolder.getArgs(), args)) {
                    // if so than return the stored value
                    return dataHolder.getRetObj();
                }
            }

            // otherwise return null
            return null;
        }

        public void setRetObj(Object retObj) {
            dataHolders.add(new DataHolder(lastMethod, lastArgs, retObj));
        }

        private class DataHolder {
            private final Object[] args;
            private final Method method;
            private final Object retObj;

            private DataHolder(Method method, Object[] args, Object retObj) {
                this.args = args;
                this.method = method;
                this.retObj = retObj;
            }

            private Object[] getArgs() {
                return args;
            }

            private Method getMethod() {
                return method;
            }

            private Object getRetObj() {
                return retObj;
            }
        }

    }

}
