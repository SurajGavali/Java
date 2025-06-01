# Predicate

A **Predicate** in Java is a functional interface that represents a single argument function that returns a **boolean value**. It is part of the `java.util.function` package and is commonly used for filtering or matching operations.

## Key Points
- **Functional Interface**: Predicate has a single abstract method `test(T t)`.
- **Usage**: Often used in lambda expressions and streams for filtering data.
- **Method Signature**:
  ```java
  @FunctionalInterface
  public interface Predicate<T> {
      boolean test(T t);
  }
  ```
- **Default Methods**: Includes methods like `and()`, `or()`, and `negate()` for combining predicates.

## Example Usage
```java
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4)); // true
System.out.println(isEven.test(5)); // false
```

## Common Use Cases
- Filtering collections using streams:
  ```java
  List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
  List<Integer> evenNumbers = numbers.stream()
      .filter(n -> n % 2 == 0)
      .collect(Collectors.toList());
  ```
- Validating input data.
- Conditional logic in functional programming.

## Summary
A Predicate is a powerful tool for expressing boolean conditions in a concise and reusable way, especially when working with Java streams and lambda expressions.
