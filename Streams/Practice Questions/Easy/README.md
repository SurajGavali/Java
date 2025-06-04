📌 Java Stream API Interview Questions (Easy Level)

📖 Basics & Definitions
	1.	What is the Java Stream API?
	2.	How does the Stream API differ from a Collection?
	3.	Can you explain what a stream pipeline is?
	4.	What is the difference between intermediate and terminal operations in streams?
	5.	What is the difference between a sequential stream and a parallel stream?

📖 Stream Creation
	6.	How do you create a stream from a collection?
	7.	How do you create a stream from an array?
	8.	How can you create an empty stream?
	9.	How can you create a stream using the Stream.of() method?
	10.	How can you create a stream of primitive types (IntStream, LongStream, DoubleStream)?

📖 Intermediate Operations
	11.	What does the filter() operation do in a stream?
	12.	How does the map() operation work in a stream?
	13.	What’s the difference between map() and flatMap()?
	14.	How does distinct() work on a stream?
	15.	How does sorted() work on a stream? How can you sort custom objects?
	16.	How does the limit() operation work?
	17.	What does the skip() operation do?
	18.	How can you remove duplicates from a stream?

📖 Terminal Operations
	19.	What does the collect() operation do?
	20.	How can you convert a stream to a list or set using collect()?
	21.	How does the forEach() terminal operation work?
	22.	What’s the difference between forEach() and forEachOrdered()?
	23.	How can you use count() on a stream?
	24.	How can you get the minimum or maximum value from a stream?
	25.	How does the reduce() method work? Can you give a simple example?
	26.	How does anyMatch(), allMatch(), and noneMatch() work?
	27.	How does findFirst() and findAny() work?

📖 Collectors
	28.	How do you use Collectors.toList()?
	29.	How do you use Collectors.toSet()?
	30.	How do you use Collectors.toMap()?
	31.	How can you use Collectors.joining() to concatenate strings?
	32.	What is the purpose of Collectors.groupingBy()? Give a simple example.

📖 Advanced (but still easy-level) Topics
	33.	What is a short-circuiting operation? Give examples.
	34.	How can you parallelize a stream? What’s the benefit?
	35.	Is a stream reusable? Why or why not?
	36.	How can you handle exceptions inside stream operations?
	37.	How can you debug a stream pipeline?
	38.	What’s the difference between a stream and an iterator?
	39.	How does lazy evaluation work in streams?
	40.	How does a stream close? Can you close a stream explicitly?
	41.	Can you call a terminal operation twice on the same stream?

---

## Sample Answers and Code for Each Question

### 📖 Basics & Definitions

1. **What is the Java Stream API?**
   - The Java Stream API is a feature introduced in Java 8 that allows functional-style operations on collections of elements, such as filtering, mapping, and reducing.
   - *Sample code:*
     ```java
     List<Integer> numbers = Arrays.asList(1, 2, 3);
     numbers.stream().forEach(System.out::println);
     ```

2. **How does the Stream API differ from a Collection?**
   - Collections store and manage data; Streams process data. Streams do not store elements and are not reusable.
   - *Sample code:*
     ```java
     List<String> list = Arrays.asList("a", "b");
     Stream<String> stream = list.stream();
     ```

3. **Can you explain what a stream pipeline is?**
   - A stream pipeline is a sequence of stream operations (source, intermediate, terminal) chained together.
   - *Sample code:*
     ```java
     List<String> names = Arrays.asList("Alice", "Bob");
     names.stream().filter(n -> n.startsWith("A")).forEach(System.out::println);
     ```

4. **What is the difference between intermediate and terminal operations in streams?**
   - Intermediate operations return a new stream and are lazy (e.g., filter, map). Terminal operations produce a result and end the stream (e.g., collect, forEach).
   - *Sample code:*
     ```java
     // filter is intermediate, collect is terminal
     List<Integer> evens = numbers.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
     ```

5. **What is the difference between a sequential stream and a parallel stream?**
   - Sequential streams process elements one after another; parallel streams process elements concurrently using multiple threads.
   - *Sample code:*
     ```java
     list.stream().forEach(System.out::println); // sequential
     list.parallelStream().forEach(System.out::println); // parallel
     ```

### 📖 Stream Creation

6. **How do you create a stream from a collection?**
   - Use the `.stream()` method on a collection.
   - *Sample code:*
     ```java
     List<String> list = Arrays.asList("a", "b");
     Stream<String> stream = list.stream();
     ```

7. **How do you create a stream from an array?**
   - Use `Arrays.stream(array)` or `Stream.of(array)`.
   - *Sample code:*
     ```java
     String[] arr = {"a", "b"};
     Stream<String> stream = Arrays.stream(arr);
     ```

8. **How can you create an empty stream?**
   - Use `Stream.empty()`.
   - *Sample code:*
     ```java
     Stream<String> empty = Stream.empty();
     ```

9. **How can you create a stream using the Stream.of() method?**
   - Use `Stream.of(elements...)`.
   - *Sample code:*
     ```java
     Stream<Integer> stream = Stream.of(1, 2, 3);
     ```

10. **How can you create a stream of primitive types (IntStream, LongStream, DoubleStream)?**
    - Use `IntStream`, `LongStream`, or `DoubleStream`.
    - *Sample code:*
      ```java
      IntStream intStream = IntStream.range(1, 5); // 1,2,3,4
      ```

### 📖 Intermediate Operations

11. **What does the filter() operation do in a stream?**
    - It filters elements based on a condition (predicate).
    - *Sample code:*
      ```java
      List<Integer> evens = numbers.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
      ```

12. **How does the map() operation work in a stream?**
    - It transforms each element using a function.
    - *Sample code:*
      ```java
      List<String> upper = names.stream().map(String::toUpperCase).collect(Collectors.toList());
      ```

13. **What’s the difference between map() and flatMap()?**
    - `map()` transforms each element; `flatMap()` flattens nested streams.
    - *Sample code:*
      ```java
      // map
      List<String> upper = names.stream().map(String::toUpperCase).collect(Collectors.toList());
      // flatMap
      List<String> words = sentences.stream().flatMap(s -> Arrays.stream(s.split(" "))).collect(Collectors.toList());
      ```

14. **How does distinct() work on a stream?**
    - It removes duplicate elements.
    - *Sample code:*
      ```java
      List<Integer> unique = Arrays.asList(1,2,2,3).stream().distinct().collect(Collectors.toList());
      ```

15. **How does sorted() work on a stream? How can you sort custom objects?**
    - It sorts elements. For custom objects, provide a comparator.
    - *Sample code:*
      ```java
      List<String> sorted = names.stream().sorted().collect(Collectors.toList());
      // Custom sort
      list.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList());
      ```

16. **How does the limit() operation work?**
    - It limits the stream to a given number of elements.
    - *Sample code:*
      ```java
      List<Integer> firstTwo = numbers.stream().limit(2).collect(Collectors.toList());
      ```

17. **What does the skip() operation do?**
    - It skips the first N elements.
    - *Sample code:*
      ```java
      List<Integer> skipped = numbers.stream().skip(2).collect(Collectors.toList());
      ```

18. **How can you remove duplicates from a stream?**
    - Use `distinct()`.
    - *Sample code:*
      ```java
      List<Integer> unique = numbers.stream().distinct().collect(Collectors.toList());
      ```

### 📖 Terminal Operations

19. **What does the collect() operation do?**
    - It gathers the elements of a stream into a collection or other result.
    - *Sample code:*
      ```java
      List<String> list = names.stream().collect(Collectors.toList());
      ```

20. **How can you convert a stream to a list or set using collect()?**
    - Use `Collectors.toList()` or `Collectors.toSet()`.
    - *Sample code:*
      ```java
      List<String> list = stream.collect(Collectors.toList());
      Set<String> set = stream.collect(Collectors.toSet());
      ```

21. **How does the forEach() terminal operation work?**
    - It performs an action for each element in the stream.
    - *Sample code:*
      ```java
      names.stream().forEach(System.out::println);
      ```

22. **What’s the difference between forEach() and forEachOrdered()?**
    - `forEach()` may not preserve order in parallel streams; `forEachOrdered()` always does.
    - *Sample code:*
      ```java
      stream.parallel().forEachOrdered(System.out::println);
      ```

23. **How can you use count() on a stream?**
    - It returns the number of elements in the stream.
    - *Sample code:*
      ```java
      long count = names.stream().count();
      ```

24. **How can you get the minimum or maximum value from a stream?**
    - Use `min()` or `max()` with a comparator.
    - *Sample code:*
      ```java
      Optional<String> min = names.stream().min(String::compareTo);
      Optional<String> max = names.stream().max(String::compareTo);
      ```

25. **How does the reduce() method work? Can you give a simple example?**
    - It combines elements using an operation (e.g., sum, concatenate).
    - *Sample code:*
      ```java
      int sum = numbers.stream().reduce(0, Integer::sum);
      ```

26. **How does anyMatch(), allMatch(), and noneMatch() work?**
    - They check if any, all, or none of the elements match a condition.
    - *Sample code:*
      ```java
      boolean any = names.stream().anyMatch(n -> n.startsWith("A"));
      boolean all = names.stream().allMatch(n -> n.length() > 2);
      boolean none = names.stream().noneMatch(n -> n.startsWith("Z"));
      ```

27. **How does findFirst() and findAny() work?**
    - They return the first or any element from the stream as an Optional.
    - *Sample code:*
      ```java
      Optional<String> first = names.stream().findFirst();
      Optional<String> any = names.stream().findAny();
      ```

### 📖 Collectors

28. **How do you use Collectors.toList()?**
    - It collects stream elements into a List.
    - *Sample code:*
      ```java
      List<String> list = stream.collect(Collectors.toList());
      ```

29. **How do you use Collectors.toSet()?**
    - It collects stream elements into a Set.
    - *Sample code:*
      ```java
      Set<String> set = stream.collect(Collectors.toSet());
      ```

30. **How do you use Collectors.toMap()?**
    - It collects elements into a Map using key and value mapping functions.
    - *Sample code:*
      ```java
      Map<String, Integer> map = names.stream().collect(Collectors.toMap(n -> n, String::length));
      ```

31. **How can you use Collectors.joining() to concatenate strings?**
    - It joins stream elements into a single string with a delimiter.
    - *Sample code:*
      ```java
      String joined = names.stream().collect(Collectors.joining(", "));
      ```

32. **What is the purpose of Collectors.groupingBy()? Give a simple example.**
    - It groups elements by a classifier function.
    - *Sample code:*
      ```java
      Map<Integer, List<String>> grouped = names.stream().collect(Collectors.groupingBy(String::length));
      ```

### 📖 Advanced (but still easy-level) Topics

33. **What is a short-circuiting operation? Give examples.**
    - An operation that may not process all elements (e.g., `findFirst()`, `anyMatch()`, `limit()`).
    - *Sample code:*
      ```java
      names.stream().anyMatch(n -> n.startsWith("A"));
      numbers.stream().limit(2).forEach(System.out::println);
      ```

34. **How can you parallelize a stream? What’s the benefit?**
    - Use `.parallelStream()` or `.parallel()`. It can speed up processing for large data sets.
    - *Sample code:*
      ```java
      list.parallelStream().forEach(System.out::println);
      ```

35. **Is a stream reusable? Why or why not?**
    - No, streams cannot be reused. Once a terminal operation is called, the stream is closed.
    - *Sample code:*
      ```java
      Stream<String> s = names.stream();
      s.forEach(System.out::println);
      // s.forEach(System.out::println); // Error: stream has already been operated upon or closed
      ```

36. **How can you handle exceptions inside stream operations?**
    - Use try-catch inside lambda expressions.
    - *Sample code:*
      ```java
      names.stream().forEach(n -> {
          try {
              // risky code
          } catch (Exception e) {
              // handle
          }
      });
      ```

37. **How can you debug a stream pipeline?**
    - Use the `peek()` method to inspect elements as they flow through the pipeline.
    - *Sample code:*
      ```java
      names.stream().peek(System.out::println).collect(Collectors.toList());
      ```

38. **What’s the difference between a stream and an iterator?**
    - Streams support functional-style operations, can be parallelized, and are lazy; iterators are imperative and not parallelizable.
    - *Sample code:*
      ```java
      Iterator<String> it = names.iterator();
      while(it.hasNext()) System.out.println(it.next());
      names.stream().forEach(System.out::println);
      ```

39. **How does lazy evaluation work in streams?**
    - Intermediate operations are not executed until a terminal operation is invoked.
    - *Sample code:*
      ```java
      List<Integer> result = numbers.stream().filter(n -> {
          System.out.println("Filtering: " + n);
          return n > 2;
      }).collect(Collectors.toList());
      ```

40. **How does a stream close? Can you close a stream explicitly?**
    - Streams close automatically after a terminal operation. You can close a stream explicitly if it implements `AutoCloseable` (e.g., IO streams).
    - *Sample code:*
      ```java
      try (Stream<String> s = Files.lines(Paths.get("file.txt"))) {
          s.forEach(System.out::println);
      }
      ```

41. **Can you call a terminal operation twice on the same stream?**
    - No, a stream can only have one terminal operation. After that, it is closed.
    - *Sample code:*
      ```java
      Stream<String> s = names.stream();
      s.forEach(System.out::println);
      // s.collect(Collectors.toList()); // Error
      ```

---