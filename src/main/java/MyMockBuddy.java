import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class MyMockBuddy {

    private static BuddyMockInvocationHandler lastMockInvocationHandler = new BuddyMockInvocationHandler();

    public static <T> T mock(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass(clazz)
//                .defineField("generalInterceptor", BuddyMockInvocationHandler.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(lastMockInvocationHandler))
                .make()
                .load(ClassLoader.getSystemClassLoader())
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
        public void invokeRealMethod() {
            lastMockInvocationHandler.setRealMethodInvocation();
        }
    }

    public static class BuddyMockInvocationHandler {
        private Method lastMethod;
        private Object[] lastArgs;
        private final List<DataHolder> dataHolders = new ArrayList<>();

        @RuntimeType
        public Object invoke(@SuperCall Callable zuper, @Origin Method method, @AllArguments Object[] args) throws Throwable {
            lastMockInvocationHandler = this;
            lastMethod = method;
            lastArgs = args;

            // checks if the method was already called with the given arguments
            for (DataHolder dataHolder : dataHolders) {
                if (dataHolder.getMethod().equals(method) && Arrays.deepEquals(dataHolder.getArgs(), args)) {
                    if (dataHolder.getRealMethod().equals(true)) {

                            System.out.println("real method");
                            return zuper.call();

                    } else {
                        return dataHolder.getRetObj();
                    }
                }
            }

            // otherwise return null
            return null;
        }

        public void setRetObj(Object retObj) {
            dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));
            dataHolders.add(new DataHolder(lastMethod, lastArgs, retObj));
        }

        public void setRealMethodInvocation() {
            dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));
            dataHolders.add(new DataHolder(lastMethod, lastArgs, null, true));
        }

        private class DataHolder {
            private final Object[] args;
            private final Method method;
            private final Object retObj;
            private final Boolean realMethod;

            private DataHolder(Method method, Object[] args, Object retObj) {
                this.args = args;
                this.method = method;
                this.retObj = retObj;
                this.realMethod = false;
            }

            private DataHolder(Method method, Object[] args, Object retObj, Boolean realMethod) {
                this.args = args;
                this.method = method;
                this.retObj = retObj;
                this.realMethod = realMethod;
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

            private Boolean getRealMethod() {
                return realMethod;
            }
        }
    }


//    private static class MockInvocationHandler implements InvocationHandler {
//
//        private Method lastMethod;
//        private Object[] lastArgs;
//        private final List<DataHolder> dataHolders = new ArrayList<>();
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            lastMockInvocationHandler = this;
//            lastMethod = method;
//            lastArgs = args;
//
//            // checks if the method was already called with the given arguments
//            for (DataHolder dataHolder : dataHolders) {
//                if (dataHolder.getMethod().equals(method) && Arrays.deepEquals(dataHolder.getArgs(), args)) {
//                    if (dataHolder.getRealMethod().equals(true)) {
//
////                            System.out.println("real method");
//                            return method.invoke(args);
//
//                    } else {
//                        return dataHolder.getRetObj();
//                    }
//                }
//            }
//
//            // otherwise return null
//            return null;
//        }
//
//        public void setRetObj(Object retObj) {
//            dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));
//            dataHolders.add(new DataHolder(lastMethod, lastArgs, retObj));
//        }
//
//        public void setRealMethodInvocation() {
//            dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));
//            dataHolders.add(new DataHolder(lastMethod, lastArgs, null, true));
//        }
//
//        private class DataHolder {
//            private final Object[] args;
//            private final Method method;
//            private final Object retObj;
//            private final Boolean realMethod;
//
//            private DataHolder(Method method, Object[] args, Object retObj) {
//                this.args = args;
//                this.method = method;
//                this.retObj = retObj;
//                this.realMethod = false;
//            }
//
//            private DataHolder(Method method, Object[] args, Object retObj, Boolean realMethod) {
//                this.args = args;
//                this.method = method;
//                this.retObj = retObj;
//                this.realMethod = realMethod;
//            }
//
//            private Object[] getArgs() {
//                return args;
//            }
//
//            private Method getMethod() {
//                return method;
//            }
//
//            private Object getRetObj() {
//                return retObj;
//            }
//
//            private Boolean getRealMethod() {
//                return realMethod;
//            }
//        }
//
//    }

}
