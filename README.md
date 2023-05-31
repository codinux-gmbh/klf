# KMP Log
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.codinux.log/kmp-log/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.codinux.log/kmp-log)

Idiomatic Kotlin Multiplatform logging facade with appenders for all supported KMP platforms.

## Setup

### Gradle

```
implementation("net.codinux.log:kmp-log:1.1.0")
```

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
    // or:
    private val logByName = LoggerFactory.getLogger("OrderService")
  
    fun showUsage() {
        log.info("An info message: $detailedMessage") // string get directly concatenated whether INFO level is enabled or not

        log.error("An error occurred", e) // e is a throwable
    }
}
```

You might ask why is there no overload with format arguments like `fun info(message: String, exception: Throwable? = null, vararg arguments: Any)`.  
This is due to the restrictions of Kotlin multiplatform as there's no `String.format()` available (also the format specifier differ, e.g. `%s` on the JVM and `%@` on iOS and macOS).  
So if you like to format the log message, please use lambda variants.

## Log appenders

KMP-Log by ships with a lot of log appenders. Depending on the platform by default these appenders are used:

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

### Custom

You can also use add your custom log appender by implementing Appender interface:

```kotlin
LoggerFactory.addAppender(CustomAppender()) // CustomAppender implements Appender interface
```

If you dislike above default appenders and want to control logger creation entirely implement ILoggerFactory and call:
```kotlin
LoggerFactory.setLoggerFactory(MyCustomLoggerFactory())
```


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