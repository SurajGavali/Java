import java.util.*;
import java.util.stream.*;

public class MyStreamExample {
    public static void main(String[] args) {
        // Example usage of Stream API in Java
        // source, intermediate, and terminal operations
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");


        // Using Stream to filter and print names that start with 'A'
                
        System.out.println("Names starting with 'D': " + 
                           names.stream()
                                .filter(name -> name.startsWith("D"))
                                .collect(Collectors.toList()));

        // Creating Stream from Collections

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        java.util.stream.Stream<Integer> numberStream = numbers.stream();

        System.out.println("Numbers greater than 2: " + 
                           numberStream.filter(n -> n > 2)
                                       .collect(Collectors.toList()));

        // Creating Stream from Arrays
        String[] fruits = {"Apple", "Banana", "Cherry"};
        java.util.stream.Stream<String> fruitStream = Arrays.stream(fruits);
        
        System.out.println("Fruits that contain 'a': " + 
                           fruitStream.filter(fruit -> fruit.contains("a"))
                                      .collect(Collectors.toList()));

        // Using Stream to perform operations
        java.util.stream.Stream<String> carStream = Stream.of("Toyota", "Honda", "Ford");
        System.out.println("Cars that start with 'T': " + 
                           carStream.filter(car -> car.startsWith("T"))
                                    .collect(Collectors.toList()));

        // Infinite Stream example with iterate

        java.util.stream.Stream<Integer> infiniteStream = Stream.iterate(0, n -> n + 2)
                                                               .limit(10); // Limit to 10 elements

        System.out.println("First 10 even numbers: " + infiniteStream.collect(Collectors.toList()));

        // Infinite Stream example with generate
        java.util.stream.Stream<Double> randomStream = Stream.generate(Math::random)
                                                             .limit(5); // Limit to 5 elements

        System.out.println("5 random numbers: " +
                           randomStream.collect(Collectors.toList()));

        // Intermediate operations example
        // 1. Filter operation
        List<String> mixedNames = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        Long size = mixedNames.stream().filter(name -> name.length() > 3).count();

        // Intermdediate operation runs only when terminal operation is called so in above case unitl and unless we called count() method, filter operation will not be executed.
        System.out.println("Count of names longer than 3 characters: " + size);

        // 2. Map operation
        List<String> upperCaseNames = mixedNames.stream()
                                                .map(String::toUpperCase)
                                                .collect(Collectors.toList());
        System.out.println("Names in uppercase: " + upperCaseNames);

        // 3. Sorted operation
        List<String> sortedNames = mixedNames.stream()
                                             .sorted()
                                             .collect(Collectors.toList());

        List<String> sortedNamesByLength = mixedNames.stream().sorted((s1,s2) -> s1.length() - s2.length())
                                             .collect(Collectors.toList());
        System.out.println("Sorted names: " + sortedNames);
        System.out.println("Sorted names by length: " + sortedNamesByLength);

        // 4. Distinct operation
        List<String> duplicateNames = Arrays.asList("Alice", "Bob", "Alice", "Charlie", "Bob");
        List<String> distinctNames = duplicateNames.stream()
                                                   .distinct()
                                                   .collect(Collectors.toList());
        System.out.println("Distinct names: " + distinctNames);
        // 5. Limit operation
        List<Integer> numbersList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> limitedNumbers = numbersList.stream()
                                                  .limit(5)
                                                  .collect(Collectors.toList());
        System.out.println("First 5 numbers: " + limitedNumbers);
        // 6. Skip operation
        List<Integer> skippedNumbers = numbersList.stream()
                                                  .skip(5)
                                                  .collect(Collectors.toList());
        System.out.println("Numbers after skipping first 5: " + skippedNumbers);
        // 7. Peek operation
        List<String> peekedNames = mixedNames.stream()
                                             .peek(name -> System.out.println("Processing: " + name))
                                             .collect(Collectors.toList());
        System.out.println("Peeked names: " + peekedNames);
        // 8. FlatMap operation
        List<List<String>> listOfNames = Arrays.asList(
            Arrays.asList("Alice", "Bob"),
            Arrays.asList("Charlie", "David"),
            Arrays.asList("Eve")
        );
        List<String> flatMappedNames = listOfNames.stream()
                                                  .flatMap(Collection::stream)
                                                  .collect(Collectors.toList());
        System.out.println("FlatMapped names: " + flatMappedNames);

        List<String> sentences = Arrays.asList(
            "Java Streams are powerful",
            "Streams can be used for parallel processing",
            "Stream API is flexible and efficient"
        );
        List<String> words = sentences.stream()
                                      .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                                      .collect(Collectors.toList());
        System.out.println("Words from sentences: " + words);
        // 9. AnyMatch, AllMatch, NoneMatch operations
        boolean anyMatch = mixedNames.stream().anyMatch(name -> name.startsWith("A"));
        boolean allMatch = mixedNames.stream().allMatch(name -> name.length() > 2);
        boolean noneMatch = mixedNames.stream().noneMatch(name -> name.startsWith("Z"));
        System.out.println("Any name starts with 'A': " + anyMatch);
        System.out.println("All names longer than 2 characters: " + allMatch);
        System.out.println("No name starts with 'Z': " + noneMatch);
        // 10. FindFirst and FindAny operations
        Optional<String> firstName = mixedNames.stream().findFirst();
        Optional<String> anyName = mixedNames.stream().findAny();
        System.out.println("First name: " + firstName.orElse("No names found"));
        System.out.println("Any name: " + anyName.orElse("No names found"));
        // 11. Reduce operation
        List<Integer> numbersToSum = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> sum = numbersToSum.stream()
                                            .reduce((a, b) -> a + b);
        System.out.println("Sum of numbers: " + sum.orElse(0));
        // 12. Collect operation
        List<String> collectedNames = mixedNames.stream()
                                                .collect(Collectors.toList());
        System.out.println("Collected names: " + collectedNames);
        // 13. GroupingBy operation
        Map<Integer, List<String>> groupedByLength = mixedNames.stream()
                                                               .collect(Collectors.groupingBy(String::length));
        System.out.println("Names grouped by length: " + groupedByLength);
        // 14. PartitioningBy operation
        Map<Boolean, List<String>> partitionedByLength = mixedNames.stream()
                                                                   .collect(Collectors.partitioningBy(name -> name.length() > 3));
        System.out.println("Names partitioned by length > 3: " + partitionedByLength);
        // 15. Joining operation
        String joinedNames = mixedNames.stream()
                                       .collect(Collectors.joining(", ", "Names: ", "."));
        System.out.println(joinedNames);
        // 16. Mapping operation
        List<String> mappedNames = mixedNames.stream()
                                             .map(name -> "Name: " + name)
                                             .collect(Collectors.toList());
        System.out.println("Mapped names: " + mappedNames);
        // 17. SummarizingInt operation
        IntSummaryStatistics stats = mixedNames.stream()
                                               .collect(Collectors.summarizingInt(String::length));
        System.out.println("Summary of name lengths: " + stats);
        // 18. CollectingAndThen operation
        List<String> collectedAndSortedNames = mixedNames.stream()
                                                         .collect(Collectors.collectingAndThen(
                                                             Collectors.toList(),
                                                             list -> {
                                                                 Collections.sort(list);
                                                                 return list;
                                                             }
                                                         ));
        System.out.println("Collected and sorted names: " + collectedAndSortedNames);
        // 19. ToMap operation
        Map<String, Integer> nameLengthMap = mixedNames.stream()
                                                       .collect(Collectors.toMap(name -> name, String::length));
        System.out.println("Name to length map: " + nameLengthMap);
        // 20. ToSet operation
        Set<String> nameSet = mixedNames.stream()
                                        .collect(Collectors.toSet());
        System.out.println("Set of names: " + nameSet);
        // 21. ToCollection operation
        List<String> nameList = mixedNames.stream()
                                          .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("List of names: " + nameList);
        // 22. ToConcurrentMap operation
        Map<String, Integer> concurrentNameLengthMap = mixedNames.parallelStream()
                                                                 .collect(Collectors.toConcurrentMap(name -> name, String::length));
        System.out.println("Concurrent name to length map: " + concurrentNameLengthMap);
        // 23. ToConcurrentSet operation
        Set<String> concurrentNameSet = mixedNames.parallelStream()
                                                  .collect(Collectors.toConcurrentMap(name -> name, String::length))
                                                  .keySet();
        System.out.println("Concurrent set of names: " + concurrentNameSet);
        // 24. ToUnmodifiableList operation
        List<String> unmodifiableNames = mixedNames.stream()
                                                   .collect(Collectors.toUnmodifiableList());
        System.out.println("Unmodifiable list of names: " + unmodifiableNames);
        // 25. ToUnmodifiableSet operation
        Set<String> unmodifiableNameSet = mixedNames.stream()
                                                    .collect(Collectors.toUnmodifiableSet());
        System.out.println("Unmodifiable set of names: " + unmodifiableNameSet);
        // 26. ToUnmodifiableMap operation
        Map<String, Integer> unmodifiableNameLengthMap = mixedNames.stream()
                                                                   .collect(Collectors.toUnmodifiableMap(name -> name, String::length));
        System.out.println("Unmodifiable name to length map: " + unmodifiableNameLengthMap);
        // 27. ToImmutableList operation
        List<String> immutableNames = mixedNames.stream()
                                                .collect(Collectors.collectingAndThen(
                                                    Collectors.toList(),
                                                    Collections::unmodifiableList
                                                ));
        System.out.println("Immutable list of names: " + immutableNames);
        // 28. ToImmutableSet operation
        Set<String> immutableNameSet = mixedNames.stream()
                                                 .collect(Collectors.collectingAndThen(
                                                     Collectors.toSet(),
                                                     Collections::unmodifiableSet
                                                 ));
        System.out.println("Immutable set of names: " + immutableNameSet);
        // 29. ToImmutableMap operation
        Map<String, Integer> immutableNameLengthMap = mixedNames.stream()
                                                                .collect(Collectors.collectingAndThen(
                                                                    Collectors.toMap(name -> name, String::length),
                                                                    Collections::unmodifiableMap
                                                                ));
        System.out.println("Immutable name to length map: " + immutableNameLengthMap);
        // 30. ToConcurrentImmutableList operation
        List<String> concurrentImmutableNames = mixedNames.parallelStream()
                                                          .collect(Collectors.collectingAndThen(
                                                              Collectors.toList(),
                                                              Collections::unmodifiableList
                                                          ));
        System.out.println("Concurrent immutable list of names: " + concurrentImmutableNames);
        // 31. ToConcurrentImmutableSet operation
        Set<String> concurrentImmutableNameSet = mixedNames.parallelStream()
                                                           .collect(Collectors.collectingAndThen(
                                                               Collectors.toSet(),
                                                               Collections::unmodifiableSet
                                                           ));
        System.out.println("Concurrent immutable set of names: " + concurrentImmutableNameSet);
        // 32. ToConcurrentImmutableMap operation
        Map<String, Integer> concurrentImmutableNameLengthMap = mixedNames.parallelStream()
                                                                           .collect(Collectors.collectingAndThen(
                                                                               Collectors.toMap(name -> name, String::length),
                                                                               Collections::unmodifiableMap
                                                                           ));
        System.out.println("Concurrent immutable name to length map: " + concurrentImmutableNameLengthMap);
        // 33. ToConcurrentImmutableList operation
        List<String> concurrentImmutableList = mixedNames.parallelStream()
                                                        .collect(Collectors.collectingAndThen(
                                                            Collectors.toList(),
                                                            Collections::unmodifiableList
                                                        ));
        System.out.println("Concurrent immutable list of names: " + concurrentImmutableList);
        // 34. ToConcurrentImmutableSet operation
        Set<String> concurrentImmutableSet = mixedNames.parallelStream()
                                                      .collect(Collectors.collectingAndThen(
                                                          Collectors.toSet(),
                                                          Collections::unmodifiableSet
                                                      ));
        System.out.println("Concurrent immutable set of names: " + concurrentImmutableSet);
        // 35. ToConcurrentImmutableMap operation
        Map<String, Integer> concurrentImmutableMap = mixedNames.parallelStream()
                                                                .collect(Collectors.collectingAndThen(
                                                                    Collectors.toMap(name -> name, String::length),
                                                                    Collections::unmodifiableMap
                                                                ));



        // Terminal operations
        
        // 1. Collect operation
        List<String> collectedNamesList = mixedNames.stream()
                                                    .collect(Collectors.toList());
        System.out.println("Collected names list: " + collectedNamesList);
        // 2. Count operation
        long count = mixedNames.stream().count();
        System.out.println("Count of names: " + count);
        // 3. ForEach operation
        System.out.print("Names: ");
        mixedNames.stream().forEach(name -> System.out.print(name + " "));
        System.out.println();

        // forEachOrdered operation
        // forEachOrdered is used to maintain the order of elements in the stream when processing them in parallel.
        // 4. ToArray operation
        String[] namesArray = mixedNames.stream()
                                        .toArray(String[]::new);
        System.out.println("Names array: " + Arrays.toString(namesArray));
        // 5. FindFirst operation
        Optional<String> firstNameOptional = mixedNames.stream().findFirst();
        System.out.println("First name: " + firstNameOptional.orElse("No names found"));

        // 6. reduce operation
        Optional<String> concatenatedNames = mixedNames.stream()
                                                       .reduce((name1, name2) -> name1 + ", " + name2);
        System.out.println("Concatenated names: " + concatenatedNames.orElse("No names found"));
        // 7. Min operation
        Optional<String> minName = mixedNames.stream()
                                             .min(String::compareTo);
        System.out.println("Minimum name: " + minName.orElse("No names found"));
        // 8. Max operation
        Optional<String> maxName = mixedNames.stream()
                                             .max(String::compareTo);
        System.out.println("Maximum name: " + maxName.orElse("No names found"));

        String sentence = "Java Streams are powerful and flexible";

        System.out.println("Occurence of l in sentence :: " + sentence.chars().filter(s -> s == 'l').count());

        // Lazy evaluation example

        List<Integer> lazyNumbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> lazyFilteredNumbers = lazyNumbers.stream()
                                                       .filter(n -> {
                                                           System.out.println("Filtering: " + n);
                                                           return n > 2;
                                                       })
                                                       .collect(Collectors.toList());
        System.out.println("Lazy filtered numbers: " + lazyFilteredNumbers);
        // Parallel Stream example
        List<String> parallelNames = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        List<String> parallelFilteredNames = parallelNames.parallelStream()
                                                          .filter(name -> {
                                                              System.out.println("Processing in parallel: " + name);
                                                              return name.startsWith("A");
                                                          })
                                                          .collect(Collectors.toList());
        System.out.println("Parallel filtered names: " + parallelFilteredNames);

        // Factorial calculation using Stream API

        List<Integer> factorialNumbers = Stream.iterate(1, n -> n + 1)
                                                .limit(2000) // Calculate factorial for numbers 1 to 10
                                                .collect(Collectors.toList());

        long startTime = System.currentTimeMillis();
        List<Long> factorialList = factorialNumbers.stream().map(MyStreamExample::calculateFactorial)
                                                   .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to calculate factorials using normal stream : " + (endTime - startTime) + " ms");

        // Parallel Stream for factorial calculation
        // Parallelel streams are most effective when the task is CPU-intensive and can be divided into smaller tasks that can be executed concurrently.
        // they may add overhead for tasks that are not CPU-intensive or that involve a lot of I/O operations.

        startTime = System.currentTimeMillis();
        List<Long> parallelFactorialList = factorialNumbers.parallelStream()
                                                            .map(MyStreamExample::calculateFactorial)
                                                            .collect(Collectors.toList());
        endTime = System.currentTimeMillis();

        System.out.println("Time taken to calculate factorials using parallel stream : " + (endTime - startTime) + " ms");

        // Parallel streams can not be run when task is not independent of each other, for example, if we want to calculate factorial of a number using previous factorials, then we can not use parallel stream.

        // But if we want to do some sequential operation after calculating factorial using parallel stream we can convert the parallel stream to sequential stream using sequential() method.

        List<Long> sequentialFactorialList = parallelFactorialList.parallelStream()
                                                                  .sequential()
                                                                  .collect(Collectors.toList());
        // System.out.println("Sequential factorial list: " + sequentialFactorialList);

        // Collectors

        // 1. toList
        List<String> collectedList = mixedNames.stream()
                                               .collect(Collectors.toList());
        System.out.println("Collected list: " + collectedList);
        // 2. toSet
        Set<String> collectedSet = mixedNames.stream()
                                             .collect(Collectors.toSet());
        System.out.println("Collected set: " + collectedSet);
        // 3. Collecting to a specific collection type
        List<String> collectedArrayList = mixedNames.stream()
                                                    .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Collected ArrayList: " + collectedArrayList);
        List<String> collectedLinkedList = mixedNames.stream()
                                                      .collect(Collectors.toCollection(LinkedList::new));
        System.out.println("Collected LinkedList: " + collectedLinkedList);
        List<String> collectedVector = mixedNames.stream()
                                                  .collect(Collectors.toCollection(Vector::new));

        List<String> collectedStack = mixedNames.stream()
                                                  .collect(Collectors.toCollection(Stack::new));
        System.out.println("Collected Vector: " + collectedVector);
        System.out.println("Collected Stack: " + collectedStack);

        ArrayDeque<String> collectedQueue = mixedNames.stream()
                                                  .collect(Collectors.toCollection(ArrayDeque::new));


        // 4. Joining Strings
        String joinedNamesString = mixedNames.stream()
                                             .collect(Collectors.joining(", ", "Names: ", "."));
        System.out.println("Joined names: " + joinedNamesString);

        // 5. Summarizing statistics
        List<Integer> numbersStastics = Arrays.asList(1, 2, 3, 4, 5);
        IntSummaryStatistics statistics = numbersStastics.stream()
                                                        .collect(Collectors.summarizingInt(Integer::intValue));
        
        System.out.println("Count: " + statistics.getCount());
        System.out.println("Sum: " + statistics.getSum());
        System.out.println("Min: " + statistics.getMin());
        System.out.println("Max: " + statistics.getMax());
        System.out.println("Average: " + statistics.getAverage());
                                                          
        // 6. Grouping by a property
        Map<Integer, List<String>> groupedByLengthMap = mixedNames.stream()
                                                                  .collect(Collectors.groupingBy(String::length));
        System.out.println("Grouped by length: " + groupedByLengthMap);

        // 7. Downstream operations
        Map<Integer, Long> lengthCountMap = mixedNames.stream()
                                                      .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println("Length count map: " + lengthCountMap);

        // 8. Partitioning by a condition
        Map<Boolean, List<String>> partitionedByLengthMap = mixedNames.stream()
                                                                      .collect(Collectors.partitioningBy(name -> name.length() > 3));
        System.out.println("Partitioned by length > 3: " + partitionedByLengthMap);

        // 9. Mapping and Collecting
        List<String> mappedNamesList = mixedNames.stream().collect(Collectors.mapping(
            name -> "Name: " + name,
            Collectors.toList()
        ));
        System.out.println("Mapped names: " + mappedNamesList);

        // Example 1 : Collecting names by their lengths
        mixedNames.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.toList()))
                .forEach((length, namesList) -> 
                    System.out.println("Length: " + length + ", Names: " + namesList));
        
        // Example 2 : Counting word occurrences in String

        String text = "Java Streams are powerful. Streams can be used for parallel processing. Stream API is flexible and efficient.";

        Arrays.stream(text.split(" ")).collect(Collectors.groupingBy(word -> word, Collectors.counting()))
                .forEach((word, wordcount) -> System.out.println("Word: " + word + ", Count: " + wordcount));
        
        // example 3 : Partitionaning number into even and odd numbers
        List<Integer> numbersList2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println(numbersList2.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0)));

        // Example 4 : Summing Values in Map
        Map<String, Integer> itemPrices = new HashMap<>();
        itemPrices.put("Apple", 100);
        itemPrices.put("Banana", 50);
        itemPrices.put("Cherry", 75);
        System.out.println(itemPrices.values().stream().collect(Collectors.summingInt(x -> x)));

        // Example 5 : Creating a Map from a List
        List<String> items = Arrays.asList("Apple", "Banana", "Cherry");
        System.out.println(items.stream().collect(Collectors.toMap(x -> x, x -> x.toString().length())));

        // Exxample 6 : Finding number of occurences in a list

        List<String> wordsList = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");
        System.out.println(wordsList.stream().collect(Collectors.toMap(k -> k, v -> 1, (x,y) -> x + y)));

        // Premitive Streams Example
        // Used for premitive data structures like int, long, double etc.

        IntStream intStream = IntStream.range(1, 11); // Creates a stream of integers from 1 to 10
        intStream.boxed() // Converts IntStream to Stream<Integer>
                .forEach(System.out::println); // Prints each integer in the stream
        
        IntStream.of( 1, 2, 3, 4, 5);

        new Random().ints(10, 1, 100) // Generates a stream of 10 random integers between 1 and 100
                .forEach(System.out::println); // Prints each random integer

    }

    private static Long calculateFactorial(int number) {

        Long factorial = 1L;
        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }
        return factorial;
    }
}