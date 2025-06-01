# Supplier

A **Supplier** is a functional interface in Java (introduced in Java 8) that represents a supplier of results. It does not take any input arguments and returns a result of a specified type. It is part of the `java.util.function` package and is commonly used for lazy generation or retrieval of values.

## Key Points
- **Functional Interface**: Has a single abstract method `get()`.
- **No Input, Only Output**: Used for operations that supply or generate a value without requiring any input.
- **Common Use**: Useful for deferred or repeated value generation, such as random numbers, timestamps, or object creation.
- **No compose/andThen Methods**: Unlike `Function` and `Consumer`, `Supplier` does not provide `compose` or `andThen` methods, as it does not accept input or chain actions.

## Method Signature
```java
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

## Example Usage
```java
import java.util.function.Supplier;

Supplier<Double> randomSupplier = () -> Math.random();
System.out.println(randomSupplier.get()); // prints a random number

Supplier<String> greetingSupplier = () -> "Hello, World!";
System.out.println(greetingSupplier.get()); // prints "Hello, World!"
```

## Summary
The `Supplier` interface is ideal for scenarios where a value needs to be generated or supplied on demand, without any input. It is simple, concise, and widely used in Java's functional programming features.