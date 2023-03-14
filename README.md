# kLogger

Idiomatic Kotlin Multiplatform logging facade with simple logger creation:  
`val log by logger()`

and creating the message string only if log level is enabled:  
`log.info { "Message with ${heavyCalculation()}" } // heavyCalculation() gets only called if INFO level is enabled`

## Instantiation

### Idiomatic via delegate.

```kotlin
import net.codinux.log.LoggerFactory.logger

class OrderService {
  private val log by logger() // automatically uses declaring class / OrderService::class.name as logger name
}
```

The logger name then automatically gets derived from declaring class.  
And the logger instance gets instantiated lazily on its first usage (or never if it never gets called).

If the logger is declared in a companion object automatically the enclosing class gets used as logger name
(like in the following example `OrderService` instead of `OrderService.Companion`).  
Except on JavaScript, there the logger name will always be `"Companion"`.

```kotlin
import net.codinux.log.LoggerFactory.logger

class OrderService {
  companion object {
    private val log by logger() // automatically uses enclosing class / OrderService::class.name as logger name (except on JavaScript)
  }
}
``` 

### Classically via LoggerFactory:

```kotlin
private val log = LoggerFactory.getLogger(OrderService::class)
```

or:

```kotlin
private val log = LoggerFactory.getLogger("OrderService")
```

## Logging

#### Via lazy evaluated closure that is only called if log level is enabled:
```kotlin
log.info { "An info message: $detailedMessage" } // gets only concatenated if info level is enabled

log.error(e) { "An error occurred" } // e is a throwable
```

#### Classically:

```kotlin
log.info("An info message: $detailedMessage") // string get directly concatenated whether info level is enabled or not

log.error("An error occurred", e) // e is a throwable
```

You might ask why is there no overload with format arguments like `fun info(message: String, exception: Throwable? = null, vararg arguments: Any)`.  
This is due to the restrictions of Kotlin multiplatform as there's no `String.format()` available (also the format specifier differ, e.g. `%s` on the JVM and `%@` on iOS and macOS).

## Logger bindings

Depending on the platform by default these loggers are used:

### JVM

If slf4j is on the classpath: slf4j.

Otherwise: Console (`println()` / `System.err.println()`)

### iOS

NSLog

### JavaScript

`console.log()` / `console.error()` / ...

### Native

Console (`println()` / `System.err.println()`)

### Custom

You can also use you custom logger binding.
Implement ILoggerFactory and call
```kotlin
LoggerFactory.setLoggerFactory(MyCustomLoggerFactory())
```