# MockBuddy

An academic project that aims to create a framework for unit tests in Java programming language such as [Mockito](https://site.mockito.org/), [jMock](http://jmock.org/), [EasyMock](https://easymock.org/), [PowerMock](https://powermock.github.io/). 

A *mock object* is a dummy implementation for an interface or a class. It allows to define the output of certain method calls. They typically record the interaction with the system and tests can validate that.

User can create mock objects manually (via code) or use a mock framework to simulate these classes. Mock frameworks allow user to create mock objects at runtime and define their behavior.

## Hey, MockBuddy, what can you do?

#### Mock creation
```java
// Mock creation using mock() method
TestClass testObj = MockBuddy.mock(TestClass.class);

// Alternativelly, you can use @Mock annotation 
@Mock
TestClass testObj;
```

#### Spy creation
Spy is a partial mocking, real methods are invoked but still can be stubbed
```java
// Spy creation using spy() method
List<String> stringList = new ArrayList<>();
List<String> spiedStringList = MockBuddy.spy(stringList);

// Alternativelly, you can use @Spy annotation 
@Spy
List<String> stringList = new ArrayList<>();
```

#### Method stubbing
```java
// Stub List.get method in spy-object
spiedStringList.add("one");
MockBuddy.when(spiedStringList.get(0)).thenReturn("stubbed one");

// Should return "stubbed one" instead of "one"
System.out.println(spiedStringList.get(0));
```

#### Argument matching
```java
// Stub method only if argument equals 255
MockBuddy.when(testClass.foo(equalsTo(255))).thenReturn("mocked");

// Should return "mocked"
System.out.println(testClass.foo(255));

// The following prints "null" because foo(11) was not stubbed 
System.out.println(testClass.foo(11));


// Of course, you can combine matchers to create more complicated things...
// (5 or 9) & (not (9 or 2) or 11) -> 5
MockBuddy.when(testClass.foo(
        and(or(
                equalsTo(5),
                equalsTo(9)),
            or(
                not(or(
                        equalsTo(9),
                        equalsTo(2))),
                equalsTo(11)))))
        .thenReturn("Mocked")

Assert.assertEquals("Mocked", testClass.foo(5));
Assert.assertNull(testClass.foo(11));


// We also have matchers such as geq (Greater or equals), leq (Less or equals),
// gt (Greater than) and lt (Less than)
MockBuddy.when(testClass.foo(leq(90))).thenReturn("leq");
```

#### And other interesting things
```java
// Excpetion throwing
MockBuddy.when(foo.bar()).thenThrow(new IllegalArgumentException());

// Calling real method
MockBuddy.when(foo.bar()).invokeRealMethod();
```

## How is this all implemented internally?
We use dynamic proxies with a bit of bytecode manipulation using the [ByteBuddy library](https://bytebuddy.net/#/).
