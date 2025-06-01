# Functional Interface

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

# BiFunction Interface

A **BiFunction** is a functional interface in the `java.util.function` package. It represents a function that takes two arguments and produces a result. BiFunction is useful when you need to process or combine two values to produce a new value.

## Key Points
- **Functional Interface**: Has a single abstract method `apply(T t, U u)`.
- **Takes Two Arguments**: Used for operations that require two input values and return a result.
- **Common Use**: Useful for combining or transforming two values, such as adding two numbers or concatenating two strings.
- **compose and andThen Methods**: BiFunction provides an `andThen` method to chain a function after the BiFunction, but does not have a `compose` method.

## Method Signature
```java
@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
    // ...default andThen method...
}
```

## Example Usage
```java
import java.util.function.BiFunction;

BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
System.out.println(add.apply(2, 3)); // 5

BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
System.out.println(concat.apply("Hello, ", "World!")); // Hello, World!

// Chaining with andThen
BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
Function<Integer, String> toString = Object::toString;

// andThen: first multiply, then convert to String
BiFunction<Integer, Integer, String> multiplyAndString = multiply.andThen(toString);
System.out.println(multiplyAndString.apply(4, 5)); // "20"
```

## Summary
The `BiFunction` interface is ideal for scenarios where two inputs need to be processed to produce a result. It supports chaining with `andThen`, but does not have a `compose` method.

# UnaryOperator Interface

A **UnaryOperator** is a specialized form of the `Function` interface. It extends `Function<T, T>`, meaning it takes an argument and returns a result of the same type. If you need a function that operates on a single operand and returns a result of the same type, you can use `UnaryOperator<T>` instead of specifying `Function<T, T>`.

## Key Points
- **Extends Function**: Equivalent to `Function<T, T>`, but more concise and expressive.
- **Use Case**: Useful for operations like incrementing, negating, or applying transformations where input and output types are the same.

## Method Signature
```java
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T> {
    // inherits apply(T t) from Function
}
```

## Example Usage
```java
import java.util.function.UnaryOperator;

UnaryOperator<Integer> square = x -> x * x;
System.out.println(square.apply(5)); // 25

UnaryOperator<String> toUpper = String::toUpperCase;
System.out.println(toUpper.apply("hello")); // HELLO
```

## Summary
Use `UnaryOperator` when both the input and output types are the same, making your code more readable and intention clear compared to using `Function<T, T>`.

# BinaryOperator Interface

A **BinaryOperator** is a specialized form of the `BiFunction` interface. It extends `BiFunction<T, T, T>`, meaning it takes two arguments of the same type and returns a result of the same type. If you need a function that operates on two operands of the same type and returns a result of that type, you can use `BinaryOperator<T>` instead of specifying `BiFunction<T, T, T>`.

## Key Points
- **Extends BiFunction**: Equivalent to `BiFunction<T, T, T>`, but more concise and expressive.
- **Use Case**: Useful for operations like addition, multiplication, or finding the maximum/minimum where all operands and the result are of the same type.
- **Static Utility Methods**: Provides static methods like `minBy` and `maxBy` to create comparators based on a given Comparator.

## Method Signature
```java
@FunctionalInterface
public interface BinaryOperator<T> extends BiFunction<T, T, T> {
    // inherits apply(T t1, T t2) from BiFunction
    // static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator)
    // static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator)
}
```

## Example Usage
```java
import java.util.function.BinaryOperator;
import java.util.Comparator;

BinaryOperator<Integer> add = (a, b) -> a + b;
System.out.println(add.apply(2, 3)); // 5

BinaryOperator<String> longer = BinaryOperator.maxBy(Comparator.comparingInt(String::length));
System.out.println(longer.apply("Java", "Programming")); // Programming
```

## Summary
Use `BinaryOperator` when both input arguments and the result are of the same type, making your code more readable and intention clear compared to using `BiFunction<T, T, T>`. The static methods `minBy` and `maxBy` are helpful for common reduction operations.