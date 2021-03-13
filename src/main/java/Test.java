public class Test implements TestInterface {

    public Test(String haha) {

    }

    public Test(Integer haha) {

    }


    @Override
    public String foo() {
        return "Foo";
    }

    @Override
    public Integer bar(Integer num) {
        return num;
    }

    @Override
    public void tmp() {

    }
}
