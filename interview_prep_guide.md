# Java Backend Interview Preparation Guide
**For 3-4 Years Experience | Spring Boot & Microservices Focus**

---

## 📋 Table of Contents
1. [Core Java Fundamentals](#core-java-fundamentals)
2. [Java 11 → 17 Migration & New Features](#java-11--17-migration--new-features)
3. [Spring Boot Deep Dive](#spring-boot-deep-dive)
4. [Microservices Architecture](#microservices-architecture)
5. [Kafka & Event-Driven Architecture](#kafka--event-driven-architecture)
6. [Security & Authorization](#security--authorization)
7. [Database & Transaction Management](#database--transaction-management)
8. [Docker & Kubernetes](#docker--kubernetes)
9. [System Design for Microservices](#system-design-for-microservices)
10. [Tricky Interview Questions](#tricky-interview-questions)

---

## Core Java Fundamentals

### Method Overloading vs Overriding

| Aspect | Overloading | Overriding |
|--------|-------------|-----------|
| **Definition** | Multiple methods with same name, different parameters | Subclass redefines parent method with same signature |
| **Binding** | Compile-time (Early/Static) | Runtime (Late/Dynamic) |
| **Scope** | Within same class or inherited | Inheritance hierarchy required |
| **Return Type** | Can be different | Must be same (or covariant) |
| **Access Modifier** | Can be different | Cannot be more restrictive |
| **Example Use** | `print(int)`, `print(String)` | Parent `calculate()` → Child `calculate()` |

**Key Interview Point:** 
- Overloading = compile-time polymorphism
- Overriding = runtime polymorphism

### Static Keyword

```java
public class Counter {
    static int count = 0;  // Class-level variable, single copy shared
    int instance = 0;      // Instance variable, one per object
    
    static void increment() {  // Static method - can't access instance members
        count++;
        // instance++;  ❌ Compilation error
    }
    
    void incrementInstance() {
        instance++;
        count++;  // ✅ Instance method can access static members
    }
}
```

**Critical Points:**
- Static members belong to class, not object
- Static methods can't use `this` or `super`
- Static blocks execute once when class is loaded
- Cannot override static methods (method hiding occurs instead)

### Garbage Collection (GC)

**How GC Works:**
1. **Mark Phase:** Identifies live objects starting from GC roots
2. **Sweep Phase:** Reclaims memory from dead objects
3. **Compact Phase:** Defragments memory (if needed)

**GC Algorithms:**

| Algorithm | Best For | Key Characteristics |
|-----------|----------|-------------------|
| **G1 GC** | Balanced throughput/latency | Default in Java 11+, region-based, predictable pauses |
| **ZGC** | Ultra-low latency (<10ms) | Concurrent, scalable, minimal pause times |
| **Parallel GC** | High throughput | Multiple threads for GC, longer pauses |
| **Serial GC** | Small applications | Single-threaded, simple |

**For High-Traffic Microservices:**
```bash
# G1 GC Configuration
-XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:InitiatingHeapOccupancyPercent=45

# ZGC Configuration (Java 17+)
-XX:+UseZGC -XX:ZCollectionInterval=120
```

### Metaspace vs PermGen

| Aspect | PermGen (Java 7) | Metaspace (Java 8+) |
|--------|------------------|---------------------|
| **Storage Location** | Heap | Native memory |
| **Size** | Fixed, often causes `OutOfMemoryError` | Auto-expands, limited by OS |
| **Stores** | Class metadata, interned strings | Class metadata only |
| **GC** | Full GC required | More efficient collection |

**Configuration:**
```bash
# PermGen (deprecated)
-XX:PermSize=256m -XX:MaxPermSize=512m

# Metaspace
-XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m
```

### Thread Pool & ExecutorService

```java
// Creating Thread Pools
ExecutorService fixedPool = Executors.newFixedThreadPool(10);
ExecutorService cachedPool = Executors.newCachedThreadPool();
ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(5);

// Custom ThreadPoolExecutor
ThreadPoolExecutor customPool = new ThreadPoolExecutor(
    5,                      // corePoolSize
    10,                     // maximumPoolSize
    60L,                    // keepAliveTime
    TimeUnit.SECONDS,
    new LinkedBlockingQueue<>(100),
    new ThreadPoolExecutor.CallerRunsPolicy()  // Rejection policy
);

// Submitting tasks
Future<String> future = fixedPool.submit(() -> {
    // Long-running task
    return "Result";
});

// Always shutdown
fixedPool.shutdown();
```

**Rejection Policies:**
- `AbortPolicy`: Throws `RejectedExecutionException` (default)
- `CallerRunsPolicy`: Caller thread executes the task
- `DiscardPolicy`: Silently discards the task
- `DiscardOldestPolicy`: Discards oldest unhandled request

### HashMap Internals

**Structure:**
```
HashMap = Array of Buckets (Nodes)
Each Bucket = LinkedList → Red-Black Tree (when size > 8)
```

**Key Operations:**

```java
// Internal flow of put()
1. Calculate hash: hash(key) = key.hashCode() ^ (key.hashCode() >>> 16)
2. Find bucket: index = (n - 1) & hash  // n = array length
3. Handle collision:
   - If bucket empty → insert directly
   - If key exists → update value
   - LinkedList size > 8 → convert to Red-Black Tree
4. Resize if load factor > 0.75
```

**Performance:**
- Average case: O(1) for get/put
- Worst case (before Java 8): O(n) - all keys in one bucket
- Worst case (Java 8+): O(log n) - tree structure in bucket

**Critical Interview Points:**
- Default capacity: 16
- Default load factor: 0.75
- Threshold for treeification: 8
- Threshold for converting back to list: 6
- Not thread-safe → use `ConcurrentHashMap`

---

## Java 11 → 17 Migration & New Features

### Records (Java 14+)

```java
// Before: Boilerplate POJO
public class UserDTO {
    private final String name;
    private final String email;
    
    public UserDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters, equals(), hashCode(), toString()...
}

// After: Record
public record UserDTO(String name, String email) {
    // Compact constructor for validation
    public UserDTO {
        if (name == null || email == null) {
            throw new IllegalArgumentException("Fields cannot be null");
        }
    }
}
```

**Benefits:**
- Immutable by default
- Auto-generates: constructor, getters, `equals()`, `hashCode()`, `toString()`
- Perfect for DTOs and value objects

### Pattern Matching for Switch (Java 17)

```java
// Old way
public String getDescription(Object obj) {
    if (obj instanceof String) {
        String s = (String) obj;
        return "String: " + s;
    } else if (obj instanceof Integer) {
        Integer i = (Integer) obj;
        return "Integer: " + i;
    }
    return "Unknown";
}

// New way - Pattern Matching Switch
public String getDescription(Object obj) {
    return switch (obj) {
        case String s -> "String: " + s;
        case Integer i -> "Integer: " + i;
        case null -> "Null value";
        default -> "Unknown";
    };
}

// With guards
public String categorize(Object obj) {
    return switch (obj) {
        case Integer i && i > 0 -> "Positive";
        case Integer i && i < 0 -> "Negative";
        case Integer i -> "Zero";
        default -> "Not an integer";
    };
}
```

### Text Blocks (Java 15)

```java
// Before
String json = "{\n" +
              "  \"name\": \"John\",\n" +
              "  \"age\": 30\n" +
              "}";

// After
String json = """
    {
      "name": "John",
      "age": 30
    }
    """;
```

### Sealed Classes (Java 17)

```java
public sealed interface Payment permits CreditCard, DebitCard, UPI {
    void process();
}

public final class CreditCard implements Payment {
    public void process() { /* ... */ }
}

public final class DebitCard implements Payment {
    public void process() { /* ... */ }
}

public final class UPI implements Payment {
    public void process() { /* ... */ }
}
```

**Use Case:** Restrict inheritance hierarchy for domain models

### Stream API Enhancements

```java
// takeWhile / dropWhile (Java 9)
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
numbers.stream()
       .takeWhile(n -> n < 4)  // [1, 2, 3]
       .collect(Collectors.toList());

// toList() (Java 16) - returns unmodifiable list
List<String> result = stream.toList();  // Shorter than .collect(Collectors.toList())
```

---

## Spring Boot Deep Dive

### @RestController vs @Controller

```java
// @RestController = @Controller + @ResponseBody
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        // Returns JSON automatically
        return ResponseEntity.ok(userService.findById(id));
    }
}

// @Controller - for view rendering (Thymeleaf, JSP)
@Controller
public class WebController {
    
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello");
        return "home";  // Returns view name
    }
}
```

### PUT vs PATCH

| Aspect | PUT | PATCH |
|--------|-----|-------|
| **Operation** | Full resource replacement | Partial update |
| **Idempotent** | Yes | Not always |
| **Request Body** | Complete resource | Only changed fields |
| **Missing Fields** | Set to null/default | Keep existing values |

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // PUT - Full replacement
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
        @PathVariable Long id,
        @RequestBody @Valid UserDTO userDTO  // All fields required
    ) {
        User updated = userService.replaceUser(id, userDTO);
        return ResponseEntity.ok(updated);
    }
    
    // PATCH - Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(
        @PathVariable Long id,
        @RequestBody Map<String, Object> updates  // Only changed fields
    ) {
        User patched = userService.patchUser(id, updates);
        return ResponseEntity.ok(patched);
    }
}
```

### Centralized Exception Handling

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
        MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        // Log error, don't expose internal details
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

### Validation

```java
// DTO with validation annotations
public record CreateLoanRequest(
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    BigDecimal amount,
    
    @NotBlank(message = "PAN is required")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    String pan,
    
    @Email(message = "Invalid email format")
    String email,
    
    @Size(min = 6, max = 6, message = "Pincode must be 6 digits")
    String pincode
) {}

// Controller
@PostMapping("/loans")
public ResponseEntity<LoanDTO> createLoan(@RequestBody @Valid CreateLoanRequest request) {
    // If validation fails, MethodArgumentNotValidException is thrown
    return ResponseEntity.ok(loanService.create(request));
}
```

### Custom Validators

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PincodeValidator.class)
public @interface ValidPincode {
    String message() default "Invalid pincode";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class PincodeValidator implements ConstraintValidator<ValidPincode, String> {
    
    @Override
    public boolean isValid(String pincode, ConstraintValidatorContext context) {
        if (pincode == null) return false;
        return pincode.matches("^[1-9][0-9]{5}$") && 
               servicingAreaService.isPincodeServiced(pincode);
    }
}
```

---

## Microservices Architecture

### Service Communication Patterns

#### 1. Synchronous - Feign Client

```java
@FeignClient(name = "credit-service", url = "${credit.service.url}")
public interface CreditServiceClient {
    
    @GetMapping("/api/credit/check")
    CreditReportDTO getCreditReport(
        @RequestParam String pan,
        @RequestHeader("Authorization") String token
    );
}

// Usage with circuit breaker
@Service
public class LoanService {
    
    @Autowired
    private CreditServiceClient creditClient;
    
    @CircuitBreaker(name = "creditService", fallbackMethod = "getCreditFallback")
    public CreditReportDTO checkCredit(String pan, String token) {
        return creditClient.getCreditReport(pan, token);
    }
    
    private CreditReportDTO getCreditFallback(String pan, String token, Exception ex) {
        log.error("Credit service unavailable, using fallback", ex);
        return CreditReportDTO.builder()
            .score(0)
            .status("UNAVAILABLE")
            .build();
    }
}
```

#### 2. Asynchronous - Kafka

```java
// Producer Configuration
@Configuration
public class KafkaProducerConfig {
    
    @Bean
    public ProducerFactory<String, LoanEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.RETRIES_CONFIG, 3);
        return new DefaultKafkaProducerFactory<>(config);
    }
}

// Producer Service
@Service
public class LoanEventProducer {
    
    @Autowired
    private KafkaTemplate<String, LoanEvent> kafkaTemplate;
    
    public CompletableFuture<SendResult<String, LoanEvent>> publishLoanCreated(LoanEvent event) {
        return kafkaTemplate.send("loan-created-topic", event.getLoanId(), event);
    }
}

// Consumer
@Service
public class LoanEventConsumer {
    
    @KafkaListener(
        topics = "loan-created-topic",
        groupId = "disbursement-service",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleLoanCreated(
        @Payload LoanEvent event,
        @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {
        log.info("Processing loan: {}", event.getLoanId());
        disbursementService.processDisbursement(event);
    }
}
```

### Circuit Breaker Pattern (Resilience4j)

```java
// Configuration
resilience4j:
  circuitbreaker:
    instances:
      creditService:
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        permittedNumberOfCallsInHalfOpenState: 3

// Implementation
@Service
public class ExternalAPIService {
    
    @CircuitBreaker(name = "creditService", fallbackMethod = "fallback")
    @Retry(name = "creditService", fallbackMethod = "fallback")
    @RateLimiter(name = "creditService")
    public CreditReport fetchCreditReport(String pan) {
        return externalClient.getCreditReport(pan);
    }
    
    private CreditReport fallback(String pan, Exception ex) {
        log.error("Circuit open or max retries reached", ex);
        return CreditReport.unavailable();
    }
}
```

### API Gateway Pattern

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        
        String token = extractToken(request);
        
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            List<String> roles = jwtUtil.extractRoles(token);
            
            // Set authentication in context
            UsernamePasswordAuthenticationToken auth = 
                new UsernamePasswordAuthenticationToken(username, null, 
                    roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
            
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        chain.doFilter(request, response);
    }
}
```

---

## Kafka & Event-Driven Architecture

### Kafka Core Concepts

```
Topic: loan-application-events
├── Partition 0: [msg1, msg2, msg3] → Consumer A
├── Partition 1: [msg4, msg5, msg6] → Consumer B
└── Partition 2: [msg7, msg8, msg9] → Consumer C

Consumer Group: disbursement-service
- Each partition consumed by exactly one consumer in the group
- Enables parallel processing
```

### Producer Best Practices

```java
@Service
public class OptimizedKafkaProducer {
    
    @Autowired
    private KafkaTemplate<String, LoanEvent> kafkaTemplate;
    
    // Async send with callback
    public void sendAsync(LoanEvent event) {
        kafkaTemplate.send("loan-topic", event.getLoanId(), event)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent successfully: offset={}", 
                        result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to send", ex);
                    // Handle retry or dead letter queue
                }
            });
    }
    
    // Transactional send
    @Transactional("kafkaTransactionManager")
    public void sendTransactional(List<LoanEvent> events) {
        events.forEach(event -> 
            kafkaTemplate.send("loan-topic", event.getLoanId(), event)
        );
    }
}
```

### Consumer Patterns

```java
// Idempotent consumer (handle duplicates)
@Service
public class IdempotentConsumer {
    
    @Autowired
    private ProcessedEventRepository processedEventRepo;
    
    @KafkaListener(topics = "loan-topic")
    @Transactional
    public void consume(LoanEvent event) {
        String eventId = event.getEventId();
        
        // Check if already processed
        if (processedEventRepo.existsById(eventId)) {
            log.info("Event {} already processed, skipping", eventId);
            return;
        }
        
        // Process event
        loanService.processLoan(event);
        
        // Mark as processed
        processedEventRepo.save(new ProcessedEvent(eventId, LocalDateTime.now()));
    }
}

// Batch consumer for high throughput
@KafkaListener(
    topics = "high-volume-topic",
    containerFactory = "batchListenerFactory"
)
public void consumeBatch(List<LoanEvent> events) {
    loanService.processBatch(events);
}
```

### Error Handling & DLQ

```java
@Configuration
public class KafkaErrorConfig {
    
    @Bean
    public DefaultErrorHandler errorHandler(
        KafkaTemplate<String, LoanEvent> template
    ) {
        // Dead Letter Queue
        DeadLetterPublishingRecoverer recoverer = 
            new DeadLetterPublishingRecoverer(template,
                (record, ex) -> new TopicPartition("loan-dlq", record.partition()));
        
        // Retry with exponential backoff
        FixedBackOff backOff = new FixedBackOff(1000L, 3L);
        
        return new DefaultErrorHandler(recoverer, backOff);
    }
}
```

---

## Security & Authorization

### JWT Authentication Flow

```
1. User Login
   ↓
2. API Gateway validates credentials
   ↓
3. Generate JWT with claims (user, roles, exp)
   ↓
4. Sign token with secret from Azure Key Vault
   ↓
5. Return token to client
   ↓
6. Client includes token in Authorization: Bearer {token}
   ↓
7. Gateway validates JWT (signature, expiry, claims)
   ↓
8. Extract user context, forward to downstream services
```

### JWT Implementation

```java
@Service
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        claims.put("email", userDetails.getUsername());
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            return false;
        }
    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("roles");
    }
    
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
```

### RBAC (Role-Based Access Control)

```java
// Role hierarchy
public enum Role {
    ADMIN,
    MANAGER,
    SALES_USER,
    CUSTOMER
}

// Permission-based authorization
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig {
}

@Service
public class LoanService {
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void approveLoan(Long loanId) {
        // Only admins and managers can approve
    }
    
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public UserDTO getUserDetails(Long userId) {
        // Users can view their own details, admins can view any
    }
    
    @PostAuthorize("returnObject.userId == authentication.principal.id")
    public LoanDTO getLoanDetails(Long loanId) {
        // Post-check: ensure returned loan belongs to requesting user
    }
}

// Custom permission evaluator
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    
    @Override
    public boolean hasPermission(
        Authentication auth,
        Object targetDomainObject,
        Object permission
    ) {
        if (auth == null || targetDomainObject == null || !(permission instanceof String)) {
            return false;
        }
        
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
        return hasPrivilege(auth, targetType, permission.toString());
    }
    
    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        // Check database or cache for role-permission mapping
        return permissionService.hasPermission(
            auth.getName(),
            targetType,
            permission
        );
    }
}
```

### Endpoint Security

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/loans/**").hasAnyRole("MANAGER", "SALES_USER")
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

// IP Whitelisting for internal services
@Component
public class IpWhitelistFilter extends OncePerRequestFilter {
    
    @Value("${security.ip.whitelist}")
    private List<String> whitelistedIps;
    
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        
        if (request.getRequestURI().startsWith("/internal/")) {
            String clientIp = getClientIp(request);
            if (!whitelistedIps.contains(clientIp)) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
}
```

---

## Database & Transaction Management

### @Transactional Deep Dive

```java
@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepo;
    
    @Autowired
    private AuditRepository auditRepo;
    
    @Autowired
    private KafkaTemplate<String, LoanEvent> kafkaTemplate;
    
    // Default: Propagation.REQUIRED, Isolation.DEFAULT, rollback on RuntimeException
    @Transactional
    public Loan createLoan(LoanDTO dto) {
        Loan loan = loanRepo.save(dto.toEntity());
        auditRepo.save(new AuditLog("LOAN_CREATED", loan.getId()));
        
        // If this throws exception, both saves are rolled back
        if (loan.getAmount().compareTo(MAX_AMOUNT) > 0) {
            throw new BusinessException("Loan amount exceeds limit");
        }
        
        return loan;
    }
    
    // Rollback on checked exceptions too
    @Transactional(rollbackFor = Exception.class)
    public void processLoan(Long loanId) throws Exception {
        // Will rollback even if checked exception is thrown
    }
    
    // Read-only optimization
    @Transactional(readOnly = true)
    public List<Loan> getAllLoans() {
        return loanRepo.findAll();
    }
    
    // Requires new transaction - independent of caller
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAudit(String action, Long entityId) {
        // This commits independently, even if parent transaction rolls back
        auditRepo.save(new AuditLog(action, entityId));
    }
    
    // Nested transaction scenario
    @Transactional
    public void outerTransaction() {
        loanRepo.save(new Loan());
        
        try {
            innerService.requiresNewTransaction();  // Commits independently
        } catch (Exception e) {
            // Parent transaction can still commit
        }
    }
}
```

### Transaction Isolation Levels

| Level | Dirty Read | Non-Repeatable Read | Phantom Read | Performance |
|-------|------------|---------------------|--------------|-------------|
| **READ_UNCOMMITTED** | ✅ | ✅ | ✅ | Fastest |
| **READ_COMMITTED** | ❌ | ✅ | ✅ | Fast (default in most DB) |
| **REPEATABLE_READ** | ❌ | ❌ | ✅ | Moderate |
| **SERIALIZABLE** | ❌ | ❌ | ❌ | Slowest |

```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
public BigDecimal calculateBalance(Long accountId) {
    // Same SELECT will return same result within this transaction
    Account account = accountRepo.findById(accountId);
    // ... calculations
}
```

### Database Connection Pooling (HikariCP)

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
      leak-detection-threshold: 60000
```

**Best Practices:**
- Pool size ≈ (core_count * 2) + effective_spindle_count
- For microservices: 10-20 connections per instance
- Monitor: active connections, wait time, timeout errors

---

## Docker & Kubernetes

### Dockerfile Best Practices

```dockerfile
# Multi-stage build for Spring Boot
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/target/*.jar app.jar

# JVM tuning
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75.0 -XX:+HeapDumpOnOutOfMemoryError"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

### Kubernetes Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: loan-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: loan-service
  template:
    metadata:
      labels:
        app: loan-service
    spec:
      containers:
      - name: loan-service
        image: loan-service:1.0.0
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: JAVA_OPTS
          value: "-Xms512m -Xmx1024m"
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 5
---
apiVersion: v1
kind: Service
metadata:
  name: loan-service
spec:
  selector:
    app: loan-service
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
  type: LoadBalancer
---
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: loan-service-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: loan-service
  minReplicas: 3
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
```

---

## System Design for Microservices

### Latency Optimization Strategies

**From ~3s to ~100ms (Friend's Achievement):**

1. **In-Memory Caching**
```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        return new CaffeineCacheManager("pincodes", "products", "rates");
    }
    
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .recordStats();
    }
}

@Service
public class PincodeService {
    
    @Cacheable(value = "pincodes", key = "#pincode")
    public ServicingArea getServicingArea(String pincode) {
        // Cached for 10 minutes, avoids DB lookup
        return servicingAreaRepo.findByPincode(pincode);
    }
}
```

2. **Kafka Async Orchestration**
```
Synchronous (Before):
Client → Gateway → Service A → Service B → Service C → DB
                     ↓           ↓           ↓
                 Wait 1s     Wait 1s     Wait 1s
Total: ~3s

Asynchronous (After):
Client → Gateway → Kafka Topic
         ↓
      Return immediately (100ms)
         
Background:
Kafka → Service A → Cosmos DB
     → Service B → Cosmos DB (parallel)
     → Service C → Cosmos DB (parallel)
```

3. **Database Optimization**
```java
// Batch inserts
@Transactional
public void saveBatch(List<Loan> loans) {
    int batchSize = 50;
    for (int i = 0; i < loans.size(); i++) {
        loanRepo.save(loans.get(i));
        if (i % batchSize == 0 && i > 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }
}

// Cosmos DB partition key design
{
  "id": "LOAN123",
  "partitionKey": "2024-01",  // Year-Month for time-series data
  "userId": "USER456",
  // ...
}
```

### Handling 16M Requests/Day

**Scaling Strategy:**

```
16M requests/day ÷ 86400 seconds = ~185 req/s average
Peak traffic (3x): ~555 req/s

Kubernetes Scaling:
- Min replicas: 3
- Max replicas: 10
- Each pod: 250m CPU, 512Mi memory
- Can handle ~60 req/s per pod
- Total capacity: 10 × 60 = 600 req/s
```

**High Availability:**
- Multi-AZ deployment
- Circuit breakers + fallbacks
- Health checks + auto-restart
- Rate limiting: 1000 req/min per user

---

## Tricky Interview Questions

### Q1: What happens if you call a @Transactional method from another method in the same class?

**Answer:**
```java
@Service
public class UserService {
    
    public void outerMethod() {
        innerMethod();  // ❌ @Transactional doesn't work!
    }
    
    @Transactional
    public void innerMethod() {
        // No transaction created
    }
}
```

**Why?** Spring uses AOP proxies. When calling from same class, proxy is bypassed.

**Solution:**
```java
@Service
public class UserService {
    
    @Autowired
    private ApplicationContext context;
    
    public void outerMethod() {
        UserService proxy = context.getBean(UserService.class);
        proxy.innerMethod();  // ✅ Works
    }
}
```

### Q2: How would you design a system to prevent duplicate loan disbursals?

**Answer:**
```java
@Service
public class DisbursalService {
    
    @Transactional
    public void disburse(String loanId) {
        // Idempotency key
        String idempotencyKey = loanId + "-" + LocalDate.now();
        
        // Try to insert with unique constraint
        try {
            disbursalRepo.insertIdempotencyRecord(idempotencyKey);
        } catch (DuplicateKeyException e) {
            log.warn("Duplicate disbursal attempt for loan: {}", loanId);
            return;  // Already processed today
        }
        
        // Proceed with disbursal
        processDisbursal(loanId);
    }
}

// Database
CREATE UNIQUE INDEX idx_idempotency ON disbursals(idempotency_key);
```

### Q3: Explain the difference between @Async and Kafka for asynchronous processing

**Answer:**

| Aspect | @Async | Kafka |
|--------|--------|-------|
| **Scope** | Within single application | Across microservices |
| **Persistence** | No (lost if app crashes) | Yes (durable storage) |
| **Ordering** | Not guaranteed | Per-partition ordering |
| **Scalability** | Limited to threads | Horizontal scaling |
| **Use Case** | Send email, log audit | Event-driven architecture |

```java
// @Async - good for
@Async
public void sendEmailNotification(User user) {
    emailService.send(user.getEmail(), "Welcome");
}

// Kafka - good for
kafkaTemplate.send("loan-approved", loanEvent);  // Processed by multiple consumers
```

### Q4: How do you handle cascading failures in microservices?

**Answer:**

**1. Circuit Breaker**
```java
@CircuitBreaker(name = "payment", fallbackMethod = "paymentFallback")
public PaymentResponse processPayment(PaymentRequest request) {
    return paymentGateway.process(request);
}

private PaymentResponse paymentFallback(PaymentRequest request, Exception ex) {
    // Queue for retry, return cached approval, or return error gracefully
    return PaymentResponse.queuedForRetry(request.getId());
}
```

**2. Bulkhead Pattern**
```yaml
resilience4j:
  bulkhead:
    instances:
      paymentService:
        maxConcurrentCalls: 10
        maxWaitDuration: 100ms
```

**3. Timeout**
```java
@TimeLimiter(name = "external-api", fallbackMethod = "timeoutFallback")
public CompletableFuture<Response> callExternalAPI() {
    return CompletableFuture.supplyAsync(() -> 
        externalClient.getData()
    );
}
```

### Q5: What is the N+1 query problem and how do you solve it?

**Answer:**

**Problem:**
```java
// Fetches 1 user
User user = userRepo.findById(1L);

// Fetches N addresses (1 query per address)
user.getAddresses().forEach(address -> 
    System.out.println(address.getCity())
);

// Total: 1 + N queries
```

**Solution 1: Fetch Join**
```java
@Query("SELECT u FROM User u LEFT JOIN FETCH u.addresses WHERE u.id = :id")
User findByIdWithAddresses(@Param("id") Long id);
```

**Solution 2: Entity Graph**
```java
@EntityGraph(attributePaths = {"addresses", "orders"})
@Query("SELECT u FROM User u WHERE u.id = :id")
User findByIdWithDetails(@Param("id") Long id);
```

**Solution 3: Batch Fetching**
```java
@Entity
public class User {
    @OneToMany
    @BatchSize(size = 10)
    private List<Address> addresses;
}
```

### Q6: How would you migrate a monolith to microservices with zero downtime?

**Answer:**

**Strangler Fig Pattern:**

```
Phase 1: API Gateway routes all traffic to monolith
   ↓
Phase 2: Extract "User Service" microservice
         Gateway routes /users/* to microservice
         Other routes still go to monolith
   ↓
Phase 3: Extract "Loan Service"
         Gateway routes /loans/* to loan-service
   ↓
Phase 4: Continue until monolith is empty
   ↓
Phase 5: Decommission monolith
```

**Implementation:**
```yaml
# API Gateway routing
routes:
  - id: user-service
    uri: http://user-service:8080
    predicates:
      - Path=/api/users/**
      
  - id: loan-service
    uri: http://loan-service:8080
    predicates:
      - Path=/api/loans/**
      
  - id: monolith-fallback
    uri: http://monolith:8080
    predicates:
      - Path=/**
```

### Q7: Explain how ConcurrentHashMap achieves thread-safety without locking the entire map

**Answer:**

**Java 7: Segment Locking**
```
ConcurrentHashMap = 16 segments (by default)
Each segment = independent lock

Thread A writes to segment 3 → locks segment 3 only
Thread B writes to segment 7 → locks segment 7 only
Both can proceed concurrently
```

**Java 8+: CAS + Synchronized Buckets**
```java
// Simplified internals
public V put(K key, V value) {
    int hash = hash(key);
    Node<K,V>[] tab = table;
    int index = (tab.length - 1) & hash;
    
    Node<K,V> first = tabAt(tab, index);
    
    if (first == null) {
        // CAS: atomic compareAndSet, no lock
        if (casTabAt(tab, index, null, newNode)) {
            return null;
        }
    } else {
        // Lock only this bucket
        synchronized (first) {
            // Insert into bucket
        }
    }
}
```

**Benefits:**
- Fine-grained locking
- Better concurrency than Hashtable (whole-map lock)
- Lock-free reads in most cases

### Q8: How do you ensure data consistency in distributed transactions?

**Answer:**

**Saga Pattern (Event-Driven):**

```java
// Orchestration-based Saga
@Service
public class LoanSagaOrchestrator {
    
    public void createLoan(LoanRequest request) {
        String sagaId = UUID.randomUUID().toString();
        
        try {
            // Step 1: Create loan
            Loan loan = loanService.createLoan(request, sagaId);
            
            // Step 2: Reserve credit limit
            creditService.reserveLimit(loan.getUserId(), loan.getAmount(), sagaId);
            
            // Step 3: Create account
            accountService.createLoanAccount(loan.getId(), sagaId);
            
            // Step 4: Disburse funds
            disbursalService.disburse(loan.getId(), sagaId);
            
        } catch (Exception e) {
            // Compensating transactions in reverse order
            compensate(sagaId);
        }
    }
    
    private void compensate(String sagaId) {
        disbursalService.reverseDisbursal(sagaId);
        accountService.deleteLoanAccount(sagaId);
        creditService.releaseCreditLimit(sagaId);
        loanService.cancelLoan(sagaId);
    }
}
```

**Choreography-based Saga (Kafka):**
```
loan-created event
  → credit-service reserves limit
    → credit-reserved event
      → account-service creates account
        → account-created event
          → disbursal-service disburses
            → loan-disbursed event

If any fails → publishes compensation event
```

### Q9: What's the difference between stateless and stateful authentication?

**Answer:**

| Aspect | Stateless (JWT) | Stateful (Session) |
|--------|----------------|-------------------|
| **Storage** | Token in client | Session in server |
| **Scalability** | Excellent (no server state) | Requires sticky sessions or shared store |
| **Revocation** | Hard (need blacklist) | Easy (delete session) |
| **Size** | Large (all claims in token) | Small (session ID only) |
| **Use Case** | Microservices, API Gateway | Monoliths, WebSockets |

**Hybrid Approach:**
```java
// Store JWT in Redis for quick revocation
@Service
public class TokenService {
    
    @Autowired
    private RedisTemplate<String, String> redis;
    
    public boolean isTokenValid(String token) {
        if (!jwtUtil.validateSignature(token)) return false;
        
        String tokenId = jwtUtil.extractTokenId(token);
        
        // Check blacklist
        return !redis.hasKey("blacklist:" + tokenId);
    }
    
    public void revokeToken(String token) {
        String tokenId = jwtUtil.extractTokenId(token);
        long ttl = jwtUtil.getExpiration(token).getTime() - System.currentTimeMillis();
        
        redis.opsForValue().set("blacklist:" + tokenId, "1", ttl, TimeUnit.MILLISECONDS);
    }
}
```

### Q10: How would you debug a memory leak in production?

**Answer:**

**1. Enable Heap Dump on OOM**
```bash
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/dumps/
```

**2. Take Thread Dumps**
```bash
jstack <pid> > thread_dump.txt
# Look for blocked threads, deadlocks
```

**3. Use JVM Metrics**
```java
@RestController
public class MemoryController {
    
    @GetMapping("/memory")
    public Map<String, Object> getMemory() {
        Runtime runtime = Runtime.getRuntime();
        return Map.of(
            "totalMemory", runtime.totalMemory() / 1024 / 1024 + " MB",
            "freeMemory", runtime.freeMemory() / 1024 / 1024 + " MB",
            "usedMemory", (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + " MB",
            "maxMemory", runtime.maxMemory() / 1024 / 1024 + " MB"
        );
    }
}
```

**4. Enable GC Logging**
```bash
-Xlog:gc*:file=/logs/gc.log:time,uptime,level,tags
```

**5. Common Leak Sources**
- Unclosed resources (DB connections, streams)
- Static collections growing unbounded
- Circular references preventing GC
- ThreadLocal not cleaned up

---

## Quick Reference Cards

### Spring Boot Annotations Cheat Sheet

| Annotation | Purpose |
|------------|---------|
| `@RestController` | = `@Controller` + `@ResponseBody` |
| `@Service` | Service layer component |
| `@Repository` | DAO layer, provides DB exception translation |
| `@Configuration` | Defines @Bean methods |
| `@Transactional` | Declarative transaction management |
| `@Async` | Async method execution |
| `@Cacheable` | Cache method result |
| `@Scheduled` | Cron/fixed-rate scheduling |
| `@EventListener` | Listen to application events |

### HTTP Status Codes

| Code | Meaning | When to Use |
|------|---------|-------------|
| 200 OK | Success | GET, PUT, PATCH success |
| 201 Created | Resource created | POST success |
| 204 No Content | Success, no body | DELETE success |
| 400 Bad Request | Client error | Validation failure |
| 401 Unauthorized | Auth required | Missing/invalid token |
| 403 Forbidden | Auth insufficient | Insufficient permissions |
| 404 Not Found | Resource not found | GET/PUT/DELETE non-existent |
| 409 Conflict | State conflict | Duplicate resource |
| 500 Internal Server Error | Server error | Unexpected exception |
| 503 Service Unavailable | Temp unavailable | Circuit open, maintenance |

### Recommended Study Plan

**Day Before Interview:**
1. ✅ Review your own projects (LAP module, CIBIL integration)
2. ✅ Practice explaining architecture decisions
3. ✅ Memorize key metrics (16M req/day, 100ms latency, ₹50Cr disbursals)
4. ✅ Practice coding on whiteboard/screen share
5. ✅ Prepare questions for interviewers

**During Interview:**
1. **Clarify requirements** before coding
2. **Think aloud** - explain your approach
3. **Start with brute force**, then optimize
4. **Consider edge cases** (null, empty, large inputs)
5. **Discuss trade-offs** (time vs space, consistency vs availability)

---

## Final Tips

> [!IMPORTANT]
> **For 3-4 Years Experience:**
> - Focus on **real project experience** over theoretical knowledge
> - Be ready to discuss **challenges faced** and **how you solved them**
> - Demonstrate **ownership** - "I designed", "I optimized", not "We did"
> - Show **impact** - mention metrics, business value, user benefit

> [!TIP]
> **Common Pitfalls to Avoid:**
> - Don't say "I don't know" - say "I haven't worked with X, but here's how I'd approach it"
> - Don't bad-mouth previous employers or colleagues
> - Don't claim to know everything about technologies on your resume
> - Don't jump into coding without clarifying requirements

> [!WARNING]
> **Red Flags Interviewers Look For:**
> - Unable to explain projects in depth
> - Blaming others for failures
> - No understanding of why certain tech choices were made
> - Copy-paste coder mentality (no foundational understanding)

**Good Luck! 🎯**
