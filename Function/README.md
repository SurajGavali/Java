# Functional Interface in Java

A **Functional Interface** is an interface that contains exactly one abstract method. They are introduced in Java 8 to support lambda expressions and functional programming.

## Key Points
- Marked with the `@FunctionalInterface` annotation (optional but recommended).
- Can have multiple default or static methods, but only one abstract method.
- Used as the basis for lambda expressions and method references.

### Example
```java
@FunctionalInterface
interface MyFunction {
    void execute();
}
```

# Function Interface and compose/andThen Methods

The `Function<T, R>` interface in `java.util.function` is a functional interface for functions that take an argument of type T and return a result of type R.

## compose, andThen, and identity Methods
- `compose(Function before)`: Returns a composed function that first applies the `before` function, then this function.
- `andThen(Function after)`: Returns a composed function that first applies this function, then the `after` function.
- `identity()`: Returns a function that always returns its input argument (an identity function).

### Example
```java
import java.util.function.Function;

Function<Integer, Integer> multiplyBy2 = x -> x * 2;
Function<Integer, Integer> add3 = x -> x + 3;

// compose: add3 first, then multiplyBy2
Function<Integer, Integer> composed = multiplyBy2.compose(add3);
System.out.println(composed.apply(5)); // (5 + 3) * 2 = 16

// andThen: multiplyBy2 first, then add3
Function<Integer, Integer> andThen = multiplyBy2.andThen(add3);
System.out.println(andThen.apply(5)); // (5 * 2) + 3 = 13

// identity: returns its input
Function<String, String> identity = Function.identity();
System.out.println(identity.apply("Hello")); // Hello
```

## Summary
Functional interfaces enable functional programming in Java. The `Function` interface's `compose` and `andThen` methods allow chaining and combining functions for more flexible code. The `identity` method provides a way to obtain a function that always returns its input, which can be useful in various programming scenarios.