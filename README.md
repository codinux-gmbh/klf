# Kotlin Logging Facade (klf)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.codinux.log/klf/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.codinux.log/klf)

Kotlin (Multiplatform) logging facade for idiomatic logging in Kotlin with appenders for all supported KMP platforms.

## Setup

### Gradle

```
implementation("net.codinux.log:klf:1.6.2")
```

### Native Images (e.g. Quarkus native)

For native images use

```
implementation("net.codinux.log:klf-graal:1.6.2")
```

which substitutes calls to reflection. (As otherwise each class for which a Logger gets created would need to be annotated with `@RegisterForReflection`.)

### Compatibility note

With version 1.6.0 the project has been renamed from `kmp-log` to `klf` and the Kotlin version changed from 1.8 to 1.9.
Projects targeting Kotlin 1.8 therefore should use `net.codinux.log:kmp-log:1.5.1`.

## Usage

### Kotlin style

```kotlin
    package com.example.service
    
    import net.codinux.log.logger
    
    class OrderService {
        private val log by logger() // automatically uses declaring class / OrderService::class.name as logger name
      
        fun showUsage() {
            log.info { "Message with ${heavyCalculation()}" } // lambda and therefor heavyCalculation() gets only called if INFO level is enabled
    
            log.error(e) { "An error occurred" } // e is a throwable
        }
    }
```

The log lambdas like `info { }`, `warn { }`, ... are only called if the according log level is enabled.  
Therefor there's no need for String formats like `log.info("Message with %s", heavyCalculation())` as `"Message with ${heavyCalculation()}"` of example above gets only called if INFO level is enabled.

With `by logger()` the logger name gets automatically derived from declaring class. In above example it would by `"com.example.service.OrderService"`.  
And the logger instance gets instantiated lazily on its first usage (or never if it never gets called).

If the logger is declared in a companion object automatically the enclosing class gets used as logger name
(like in the following example `OrderService` instead of `OrderService.Companion`).  
Except on JavaScript, there the logger name will always be `"Companion"`.

```kotlin
    import net.codinux.log.logger
    
    class OrderService {
      companion object {
        private val log by logger() // automatically uses enclosing class = OrderService::class.name as logger name (except on JavaScript)
      }
    }
``` 

### Classically:

```kotlin
package com.example.service

import net.codinux.log.LoggerFactory

class OrderService {
    private val log = LoggerFactory.getLogger(OrderService::class)
    // or by setting logger name (tag) via a string
    private val logByName = LoggerFactory.getLogger("OrderService")
  
    fun showUsage() {
        log.info("An info message: $detailedMessage") // string get directly concatenated whether INFO level is enabled or not

        logByName.error("An error occurred", e) // e is a throwable
    }
}
```

You might ask why is there no overload with format arguments like `fun info(message: String, exception: Throwable? = null, vararg arguments: Any)`.  
This is due to the restrictions of Kotlin multiplatform as there's no `String.format()` available (also the format specifier differ, e.g. `%s` on the JVM and `%@` on iOS and macOS).  
So if you like to format the log message, please use lambda variants.

## Static Loggers

Since version 1.5.0 klf also supports static Loggers:

```kotlin
package com.example.service

import net.codinux.log.LoggerFactory

class OrderService {
  
    fun showUsage() {
        Log.info<OrderService> { "Message with ${heavyCalculation()}" }

        Log.error<OrderService>(e) { "An error occurred" } // e is a throwable

        // or set the logger tag via String:
        Log.info(loggerName = "OrderService") { "Message with ${heavyCalculation()}" }
        
        // The logger tag can also be omitted; in this case, the default logger name resolution will apply, as described below.
        Log.info { "Message with ${heavyCalculation()}" }
    }
}
```

## Logger name resolution if no logger tag is provided

If no logger tag is provided, e.g. with `Log.info { "Info" }`, the following logger name resolution will be applied:

1. If `LoggerFactory.config.useCallerMethodIfLoggerNameNotSet` or in debug mode  `LoggerFactory.debugConfig.useMethodNameIfLogTagIsNotSet` is set to true, then the method that executed the log statement will be used as logger name (which is quite handy e.g. for @Composable functions).       
It's a bit resource intensive, see below.   
Currently only works on `JVM` and `Android`.

2. Otherwise if set, the value of `LoggerFactory.defaultLoggerName` will be used.

3. If `defaultLoggerName` is not set, we try to determine the app name (e.g. Main Bundle name on iOS, app's package name and name on Android (but see Configuration -> [Android](#android)), jar name on JVM and URL's last path name on JavaScript).

4. If this does not work, `"net.codinux.log.klf"` will be used as logger name.


## Configuration

### Android

For some features to work on Android, like logging the app name or determining if running in debug mode, Android 
Context is required. Set it at app start, e.g. in MainActivity:

```kotlin
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        net.codinux.log.android.AndroidContext.applicationContext = this.applicationContext

        // ...
    }
}
```

### Options

#### useCallerMethodIfLoggerNameNotSet

If no logger tag is passed to log statement, e.g. with `Log.info { ".." }`, 
and `LoggerFactory.config.useCallerMethodIfLoggerNameNotSet` or in debug mode 
`LoggerFactory.debugConfig.useMethodNameIfLogTagIsNotSet` is set to true, 
then the method that executed the log statement will be used as logger name, 
in the format `<class name>.<method name>`.

This comes in handy in Compose as in composable functions there's (usually) no class to reference with `by logger()` or `Log.info<ClassName> { }`. 

It's also trying to remove auto generated class name parts like they are applied to Coroutine functions
(like "org.example.AppKt$App$2$1$2.invokeSuspend" -> "org.example.App").
This may not work reliably under all circumstances as we have to extract the class name and method name from strings and we may are not aware of all possible formats yet.

But be aware that this is a bit resource intensive as it walks up the call 
stack for each log call to find the calling method, so preferably only enable it in debug mode.     
For UI applications this should be fine anyway, just don't enable it on high load servers.

This currently works only on `JVM` and `Android`.

#### defaultLoggerName

If no logger tag is passed to log statement, e.g. with `Log.info { ".." }`,
and [useCallerMethodIfLoggerNameNotSet](useCallerMethodIfLoggerNameNotSet)
is set to false, then the value configured with `defaultLoggerName` will be used.

If also `defaultLoggerName` is not set, we are trying to determine the app name
and use that as logger name.        
If this does not work, `"net.codinux.log.klf"` will be used as logger name.

### Debug mode vs. normal / release mode

For all options in `LoggerFactory.config` there's a same named option in `LoggerFactory.debugConfig`.

Values from `debugConfig` will only be applied when running in debug mode / a debugger is attached.     
So you can set values that only get applied during development / when running in debug mode
and don't touch your normal user's configuration like:

```kotlin
LoggerFactory.debugConfig.useCallerMethodIfLoggerNameNotSet = true
```

This does currently not work on `JS` and `WASM` (hints how to detect attached debugger in JS are welcome).        
For `Android` it means that the app is compiled in `debug build variant`.


## Log appenders

klf by ships with a lot of log appenders. Depending on the platform by default these appenders are used:

### JVM

If slf4j is on the classpath: **slf4j**.

Otherwise: **Console** (`println()`)

### Android

**Logcat**

### Apple system (macOS, iOS, iPadOS, watchOS, tvOS)

If available (iOS 10.0+, iPadOS 10.0+, macOS 10.12+, Mac Catalyst 13.1+, tvOS 10.0+, watchOS 3.0+): **OSLog**

Otherwise: **NSLog**

### JavaScript

**JavaScript Console** (`console.log()` / `console.error()` / ...)

### Native

**Console** (`println()`)

### Getting notified about log events

If you want to get notified about each log event, add a `NotifyAboutLogEventsAppender`:

```kotlin
LoggerFactory.addAppender(NotifyAboutLogEventsAppender(includeThreadName = true, includeException = true) { event ->
    // handle received log event
})
```

For `includeException` `true` is the default value, for `includeThreadName` `false`.

### Loki

If you want to apply centralized logging, you can push all logs collected with klf directly to [Loki](https://github.com/grafana/loki) with `klf-loki-appender`:

Add to Gradle:
```
implementation("net.codinux.log:klf-loki-appender:1.8.0")
```

And on app start add LokiAppender:

```kotlin
// configure Loki url and credentials:
val config = LokiLogAppenderConfig(writer = WriterConfig("http://192.168.0.27:3100", username = null, password = null))

// then add LokiAppender to klf LoggerFactory
LoggerFactory.addAppender(LokiAppender(config))
```

If using http (e.g. for tests in local network) on Android add `android:usesCleartextTraffic="true"` to AndroidManifest.xml -> <application> tag.

### Custom

You can also use add your custom log appender by implementing Appender interface:

```kotlin
  LoggerFactory.addAppender(CustomAppender()) // CustomAppender implements Appender interface
```

If you dislike above default appenders and want to control logger creation entirely implement ILoggerFactory and call:
```kotlin
  LoggerFactory.setLoggerFactory(MyCustomLoggerFactory())
```


## WebAssembly (experimental)

Version 1.1.3 added experimental support for WASM Browser. While it should work in most circumstances, there is explicitly
currently no support for multithreaded applications due to lack of synchronization mechanisms in Kotlin WASM.


## MDC (Java only)

klf has some convenience functions if values should get added to MDC (Mapped Diagnostic Context) only for the next log message and should then automatically get cleared again:

```kotlin
  log.withMdc("key1" to "value1", "key2" to "value2").info { "Info" }
```

Or
```kotlin
  runWithMdc("key1" to "value1") {
    log.info { "Info" }
  }
```

## Terminology

The terminology is mostly identical with slf4j / logback.

### LoggerFactory

Primary facade to create a [Logger](#logger), e.g.  
`private val log = LoggerFactory.getLogger(OrderService::class)`.

klf ships with two LoggerFactories:
- [Slf4jLoggerFactory](klf/src/javaCommonMain/kotlin/net/codinux/log/slf4j/Slf4jLoggerFactory.kt) that delegates logging to slf4j, so that on JVM any logging framework that implements slf4j can be used. 
Gets automatically used if slf4j is on the classpath and its ILoggerFactory implementation is any other than [NOPLoggerFactory](https://www.slf4j.org/apidocs/org/slf4j/helpers/NOPLoggerFactory.html).
- [DefaultLoggerFactory](klf/src/commonMain/kotlin/net.codinux.log/DefaultLoggerFactory.kt) that delegates logging to system's default log appender (see [Log appenders](#log-appenders)).

### Logger

Interface to log messages at different levels (Info, Error, ...) , e.g.  
`log.error(e) { "Calculating Fibonacci numbers for $number failed" }`.

### Appender

- If a messages gets accepted by a Logger, Appender do the actual work of 'writing' log messages.
- Multiple appender can be defined so that one logged messages e.g. gets written to console, to a file (only available via a slf4j compatible logging framework), to [Elasticsearch](https://github.com/codinux-gmbh/ElasticsearchLogger), [Loki](https://github.com/codinux-gmbh/LokiLogAppender), ...
- All Loggers use the same appenders. (It's not possible to set an appender for a specific Logger like as logback.)
- Except ConsoleAppender klf doesn't implement any Appender itself but delegates on to native loggers like Logcat, OSLog, JavaScript Console, slf4j, ...


# License

    Copyright 2023 codinux GmbH & Co. KG

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.