# Prerequisite Notice

Before reading about Streams in Java, you should first learn or be familiar with the concepts covered in the other folders within the Java directory:
- Lambda Expressions
- Method Reference
- Predicate
- Function
- Consumer
- Supplier

Understanding these foundational topics will help you grasp Java Streams more effectively.

# Java Streams – Beginner Notes

## What is a Stream in Java?
A Stream in Java is a sequence of elements (like a list of numbers or strings) that supports various operations to process data easily and efficiently. Streams help you work with collections (like List, Set, etc.) in a functional style.

---

## Key Concepts

### 1. Creating Streams
- **From a Collection:**  
  `List<String> names = Arrays.asList("Alice", "Bob");`  
  `Stream<String> nameStream = names.stream();`
- **From an Array:**  
  `String[] fruits = {"Apple", "Banana"};`  
  `Stream<String> fruitStream = Arrays.stream(fruits);`
- **Directly:**  
  `Stream<String> carStream = Stream.of("Toyota", "Honda");`

---

### 2. Stream Operations

#### a. Intermediate Operations (return a new stream)
- **filter:** Selects elements that match a condition.  
  `names.stream().filter(name -> name.startsWith("A"))`
- **map:** Transforms each element.  
  `names.stream().map(String::toUpperCase)`
- **sorted:** Sorts the elements.  
  `names.stream().sorted()`
- **distinct:** Removes duplicates.  
  `names.stream().distinct()`
- **limit:** Limits the number of elements.  
  `names.stream().limit(3)`
- **skip:** Skips the first N elements.  
  `names.stream().skip(2)`
- **peek:** Performs an action for each element (for debugging).  
  `names.stream().peek(System.out::println)`
- **flatMap:** Flattens nested collections.  
  `listOfLists.stream().flatMap(Collection::stream)`

#### b. Terminal Operations (produce a result, end the stream)
- **collect:** Converts the stream to a collection (List, Set, etc.).  
  `names.stream().collect(Collectors.toList())`
- **count:** Counts the elements.  
  `names.stream().count()`
- **forEach:** Performs an action for each element.  
  `names.stream().forEach(System.out::println)`
- **toArray:** Converts the stream to an array.  
  `names.stream().toArray(String[]::new)`
- **findFirst / findAny:** Gets the first/any element.  
  `names.stream().findFirst()`
- **reduce:** Combines elements (e.g., sum, concatenate).  
  `numbers.stream().reduce((a, b) -> a + b)`
- **min / max:** Finds the minimum/maximum element.  
  `names.stream().min(String::compareTo)`

---

### 3. Special Stream Operations

- **anyMatch / allMatch / noneMatch:**  
  Check if any/all/none elements match a condition.
- **groupingBy:**  
  Groups elements by a property.  
  `names.stream().collect(Collectors.groupingBy(String::length))`
- **partitioningBy:**  
  Splits elements into two groups (true/false).  
  `names.stream().collect(Collectors.partitioningBy(name -> name.length() > 3))`
- **joining:**  
  Joins elements into a single string.  
  `names.stream().collect(Collectors.joining(", "))`
- **summarizingInt:**  
  Gets statistics (count, sum, min, max, average) for numbers.  
  `names.stream().collect(Collectors.summarizingInt(String::length))`

---

### 4. Infinite Streams

- **iterate:**  
  `Stream.iterate(0, n -> n + 2).limit(10)`  
  (First 10 even numbers)
- **generate:**  
  `Stream.generate(Math::random).limit(5)`  
  (5 random numbers)

---

### 5. Parallel Streams

- Use `.parallelStream()` to process elements in parallel (faster for large data).
- Example:  
  `names.parallelStream().filter(name -> name.startsWith("A"))`

---

### 6. Lazy Evaluation

- Intermediate operations (like `filter`, `map`) are not executed until a terminal operation (like `collect`, `count`) is called.

---

### 7. Example: Factorial Calculation

- You can use streams to calculate factorials for a list of numbers, and compare the speed of normal vs. parallel streams.

---

## Summary Table

| Operation         | What it does                        | Example                                      |
|-------------------|-------------------------------------|----------------------------------------------|
| filter            | Selects elements by condition       | `filter(n -> n > 2)`                         |
| map               | Transforms each element             | `map(String::toUpperCase)`                   |
| sorted            | Sorts elements                      | `sorted()`                                   |
| distinct          | Removes duplicates                  | `distinct()`                                 |
| limit             | Limits number of elements           | `limit(5)`                                   |
| skip              | Skips first N elements              | `skip(3)`                                    |
| collect           | Collects into List/Set/Map          | `collect(Collectors.toList())`               |
| count             | Counts elements                     | `count()`                                    |
| forEach           | Performs action for each element    | `forEach(System.out::println)`               |
| reduce            | Combines elements                   | `reduce((a, b) -> a + b)`                    |
| groupingBy        | Groups by property                  | `groupingBy(String::length)`                 |
| partitioningBy    | Splits into two groups              | `partitioningBy(name -> name.length() > 3)`  |
| joining           | Joins elements into a string        | `joining(", ")`                              |

---

## Tips for Beginners

- Streams do not change the original collection.
- You can chain multiple operations together.
- Always end a stream with a terminal operation.
- Use parallel streams for large data, but test performance.

---

## Code Examples from MyStreamExample.java

### Creating Streams
```java
// From a Collection
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
Stream<String> nameStream = names.stream();

// From an Array
String[] fruits = {"Apple", "Banana", "Cherry"};
Stream<String> fruitStream = Arrays.stream(fruits);

// Directly from values
Stream<String> carStream = Stream.of("Toyota", "Honda", "Ford");
```

### Intermediate Operations
```java
// filter: names starting with 'D'
System.out.println("Names starting with 'D': " + 
    names.stream()
         .filter(name -> name.startsWith("D"))
         .collect(Collectors.toList()));

// map: convert names to uppercase
List<String> upperCaseNames = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
System.out.println("Names in uppercase: " + upperCaseNames);

// sorted: sort names alphabetically
List<String> sortedNames = names.stream()
    .sorted()
    .collect(Collectors.toList());
System.out.println("Sorted names: " + sortedNames);

// distinct: remove duplicate names
List<String> duplicateNames = Arrays.asList("Alice", "Bob", "Alice", "Charlie", "Bob");
List<String> distinctNames = duplicateNames.stream()
    .distinct()
    .collect(Collectors.toList());
System.out.println("Distinct names: " + distinctNames);

// limit: get first 5 numbers
List<Integer> numbersList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
List<Integer> limitedNumbers = numbersList.stream()
    .limit(5)
    .collect(Collectors.toList());
System.out.println("First 5 numbers: " + limitedNumbers);

// skip: skip first 5 numbers
List<Integer> skippedNumbers = numbersList.stream()
    .skip(5)
    .collect(Collectors.toList());
System.out.println("Numbers after skipping first 5: " + skippedNumbers);

// peek: print each name as it's processed
List<String> peekedNames = names.stream()
    .peek(name -> System.out.println("Processing: " + name))
    .collect(Collectors.toList());
System.out.println("Peeked names: " + peekedNames);

// flatMap: flatten a list of lists
List<List<String>> listOfNames = Arrays.asList(
    Arrays.asList("Alice", "Bob"),
    Arrays.asList("Charlie", "David"),
    Arrays.asList("Eve")
);
List<String> flatMappedNames = listOfNames.stream()
    .flatMap(Collection::stream)
    .collect(Collectors.toList());
System.out.println("FlatMapped names: " + flatMappedNames);
```

### Terminal Operations
```java
// collect: collect names into a list
List<String> collectedNames = names.stream()
    .collect(Collectors.toList());
System.out.println("Collected names: " + collectedNames);

// count: count names longer than 3 characters
long size = names.stream().filter(name -> name.length() > 3).count();
System.out.println("Count of names longer than 3 characters: " + size);

// forEach: print each name
names.stream().forEach(name -> System.out.print(name + " "));
System.out.println();

// toArray: convert stream to array
String[] namesArray = names.stream().toArray(String[]::new);
System.out.println("Names array: " + Arrays.toString(namesArray));

// findFirst: get the first name
Optional<String> firstName = names.stream().findFirst();
System.out.println("First name: " + firstName.orElse("No names found"));

// reduce: concatenate all names
Optional<String> concatenatedNames = names.stream()
    .reduce((name1, name2) -> name1 + ", " + name2);
System.out.println("Concatenated names: " + concatenatedNames.orElse("No names found"));

// min/max: find min and max name alphabetically
Optional<String> minName = names.stream().min(String::compareTo);
Optional<String> maxName = names.stream().max(String::compareTo);
System.out.println("Minimum name: " + minName.orElse("No names found"));
System.out.println("Maximum name: " + maxName.orElse("No names found"));
```

### Special Operations
```java
// groupingBy: group names by length
Map<Integer, List<String>> groupedByLength = names.stream()
    .collect(Collectors.groupingBy(String::length));
System.out.println("Names grouped by length: " + groupedByLength);

// partitioningBy: partition names by length > 3
Map<Boolean, List<String>> partitionedByLength = names.stream()
    .collect(Collectors.partitioningBy(name -> name.length() > 3));
System.out.println("Names partitioned by length > 3: " + partitionedByLength);

// joining: join names into a single string
String joinedNames = names.stream()
    .collect(Collectors.joining(", ", "Names: ", "."));
System.out.println(joinedNames);

// summarizingInt: get statistics about name lengths
IntSummaryStatistics stats = names.stream()
    .collect(Collectors.summarizingInt(String::length));
System.out.println("Summary of name lengths: " + stats);
```

### Infinite Streams
```java
// First 10 even numbers using iterate
Stream<Integer> infiniteStream = Stream.iterate(0, n -> n + 2).limit(10);
System.out.println("First 10 even numbers: " + infiniteStream.collect(Collectors.toList()));

// 5 random numbers using generate
Stream<Double> randomStream = Stream.generate(Math::random).limit(5);
System.out.println("5 random numbers: " + randomStream.collect(Collectors.toList()));
```

### Parallel Streams
```java
// Filter names in parallel that start with 'A'
List<String> parallelNames = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
List<String> parallelFilteredNames = parallelNames.parallelStream()
    .filter(name -> name.startsWith("A"))
    .collect(Collectors.toList());
System.out.println("Parallel filtered names: " + parallelFilteredNames);
```

### Lazy Evaluation Example
```java
List<Integer> lazyNumbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> lazyFilteredNumbers = lazyNumbers.stream()
    .filter(n -> {
        System.out.println("Filtering: " + n);
        return n > 2;
    })
    .collect(Collectors.toList());
System.out.println("Lazy filtered numbers: " + lazyFilteredNumbers);
```

### Factorial Calculation with Streams
```java
List<Integer> factorialNumbers = Stream.iterate(1, n -> n + 1)
    .limit(10)
    .collect(Collectors.toList());
List<Long> factorialList = factorialNumbers.stream()
    .map(MyStreamExample::calculateFactorial)
    .collect(Collectors.toList());
System.out.println("Factorials: " + factorialList);

// Factorial function
private static Long calculateFactorial(int number) {
    Long factorial = 1L;
    for (int i = 1; i <= number; i++) {
        factorial *= i;
    }
    return factorial;
}
```

---