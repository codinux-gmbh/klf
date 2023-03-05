# kLogger

Kotlin Multiplatform logging facade with pragmatic Kotlin style interface.

## Instantiation

#### Classic way via LoggerFactory:

```kotlin
private val log = LoggerFactory.getLogger(OrderService::class)
```

or:

```kotlin
private val log = LoggerFactory.getLogger("OrderService")
```

#### More Kotlin idiomatic via delegate.  
The logger name then automatically gets derived from declaring class.  
And the logger instance gets instantiated lazily on its first usage (or never if it never gets called).

```kotlin
import net.codinux.log.LoggerFactory.logger

class OrderService {
  private val log by logger() // automatically uses declaring class / OrderService::class.name as logger name
}
```

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

## Logging

#### Via lazy evaluated closure that is only called if message really gets logged:
```kotlin
log.info { "An info message: $detailedMessage" } // gets only concatenated if info level is enabled

log.error(e) { "An error occurred" }
```

#### Classically:

```kotlin
log.info("An info message: $detailedMessage") // string get directly concatenated whether info level is enabled or not

log.error("An error occurred", e) // e is a throwable
```

You might ask why is there no overload with format arguments like `fun info(message: String, exception: Throwable? = null, vararg arguments: Any)`.
This is due to the restrictions of Kotlin multiplatform as there's no `String.format()` available (also the format specifier differ, e.g. `%s` on the JVM and `%@` on iOS and macOS).

#### Static / Android style (TODO: do you really want to implement this?)
```kotlin
Log.i(TAG, "An info message: $detailedMessage")
Log.i(TAG) { "An info message: $detailedMessage" }

Log.e(TAG, "An error occurred", e)
Log.e(TAG, e) { "An error occurred" }
```

## Logger bindings

Depending on the platform by default these loggers are used:

### JVM

If slf4j is on the classpath: slf4j.

Otherwise: Console (`println()` / `System.err.println()`)

### Android (TODO)

If slf4j is on the classpath: slf4j.

Otherwise: Logcat.

### iOS (TODO: also on macOS?)

NSLog

### JavaScript

`console.log()` // TODO: other console log level?

### Native

Console (`println()` / `System.err.println()`)

### Custom

You can also use you custom logger binding.
Implement ILoggerFactory and call
```kotlin
LoggerFactory.setLoggerFactory(MyCustomLoggerFactory())
```