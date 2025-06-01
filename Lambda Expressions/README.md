# 📌 Lambda Expressions

Lambda expressions provide a clear and concise way to implement **functional interfaces** (interfaces with only one abstract method). They allow writing code that’s more readable and concise, especially in scenarios like using threads or passing behavior as parameters.

---

## 📝 What is a Lambda Expression?

A **lambda expression** in Java is an **anonymous function** (no name, no return type, and no access modifier) that can be used to represent a single method interface (functional interface). 

**Syntax:**
```java
(parameters) -> { body }
```
- parameters: The parameters required by the method.
- ->: The lambda operator.
- body: The implementation of the method.

## 💡 Quick Trick to Write Lambda Expressions:
- Remove the return type.
- Remove the function name.
- Remove the access modifiers.
- Add -> between the parameter list and the body.

## 🎯 Why Use Lambda Expressions?

- ✅ Reduce boilerplate code
- ✅ Make code more readable and maintainable
- ✅ Enable passing behavior as arguments to methods (functional programming)
---
## 🚀 Example Usage

### Using Lambda with Threads

Instead of implementing the Runnable interface using an anonymous class, we can use a lambda:

```Java
Thread thread = new Thread(() -> {
    System.out.println("Printing through thread");
});
thread.start();
```

**This is equivalent to:**

```Java
Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Printing through thread");
    }
});
thread.start();
```

## 🧩 Lambda Expression with Functional Interfaces

### Example: MathOperation Interface

```Java
interface MathOperation {
    int operate(int a, int b);
}
```

**Using Lambda:**

```Java
MathOperation sum = (a, b) -> a + b;
System.out.println(sum.operate(5, 3)); // Output: 8
```

**Without Lambda:**

```Java
class SumOperation implements MathOperation {
    @Override
    public int operate(int a, int b) {
        return a + b;
    }
}
```

## 📚 Full Example

```Java
package org.suraj;

public class Main {
    public static void main(String[] args) {
        // Lambda expression to create a thread
        Thread thread = new Thread(() -> {
            System.out.println("Printing through thread");
        });
        thread.start();

        // Lambda expression to implement MathOperation
        MathOperation sum = (a, b) -> a + b;
        System.out.println(sum.operate(1, 2)); // Output: 3
    }
}

interface MathOperation {
    int operate(int a, int b);
}
```

## 🔍 Notes

- ✅ Lambda expressions do not have a name (they’re anonymous).
- ✅ They do not declare return types explicitly — the compiler infers it.
- ✅ They can capture variables from the enclosing scope, known as effectively final variables.

---

## 📝 Conclusion

Lambda expressions simplify the code by:
- Reducing boilerplate.
- Making code concise and expressive.
- Enabling functional programming paradigms.

**Use them wherever a functional interface is expected (like Runnable, Callable, Comparator, and custom functional interfaces).**