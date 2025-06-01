# Consumer in Java

A **Consumer** is a functional interface in Java (introduced in Java 8) that represents an operation that takes a single input argument and returns no result. It is part of the `java.util.function` package and is often used for operations that perform actions, such as printing or modifying objects, without returning a value.

## Key Points
- **Functional Interface**: Has a single abstract method `accept(T t)`.
- **No Return Value**: Used for operations that do not return a result.
- **Common Use**: Often used with lambda expressions and streams for performing actions on each element.
- **No compose Method**: Unlike the `Function` interface, `Consumer` does not have a `compose` method. However, it provides the `andThen` method to chain Consumers.

## Method Signature
```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
    // ...default andThen method...
}
```

## Example Usage
```java
import java.util.function.Consumer;

Consumer<String> print = s -> System.out.println(s);
Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());

// Chaining consumers with andThen
Consumer<String> combined = print.andThen(printUpper);
combined.accept("hello");
// Output:
// hello
// HELLO
```

## Summary
The `Consumer` interface is useful for operations that act on a single input and **do not return** a result. It does not support function composition via a `compose` method, but allows chaining with `andThen` for sequential actions.