# Method Reference in Java

Method reference is a shorthand notation of a lambda expression to call a method directly. Introduced in Java 8, it provides a concise and readable way to refer to methods or constructors without executing them. Method references are often used with functional interfaces and streams.

## Syntax
Method references use the `::` operator. There are four main types:

1. **Reference to a static method**
   ```java
   ClassName::staticMethodName
   // Example:
   Function<String, Integer> parseInt = Integer::parseInt;
   ```
2. **Reference to an instance method of a particular object**
   ```java
   instance::instanceMethodName
   // Example:
   Consumer<String> printer = System.out::println;
   ```
3. **Reference to an instance method of an arbitrary object of a particular type**
   ```java
   ClassName::instanceMethodName
   // Example:
   Predicate<String> isEmpty = String::isEmpty;
   ```
4. **Reference to a constructor**
   ```java
   ClassName::new
   // Example:
   Supplier<List<String>> listSupplier = ArrayList::new;
   ```

## Example Usage
```java
import java.util.*;
import java.util.function.*;

List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
// Using lambda
names.forEach(name -> System.out.println(name));
// Using method reference
names.forEach(System.out::println);

// Static method reference
Function<String, Integer> toInt = Integer::parseInt;
System.out.println(toInt.apply("123")); // 123

// Constructor reference
Supplier<List<String>> listCreator = ArrayList::new;
List<String> newList = listCreator.get();
```

## When to Use
- When a lambda expression simply calls an existing method.
- To improve code readability and conciseness.

## Summary
Method references are a powerful feature in Java for writing cleaner and more expressive code, especially when working with streams and functional interfaces.
