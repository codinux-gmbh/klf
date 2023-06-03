package net.codinux.log.slf4j

enum class Slf4jBinding {

    /**
     * slf4j no operation Logger implementation, that is used if no binding / service provider that implements slf4j is found.
     */
    NOP,

    /**
     * slf4j's simple logging implementation.
     */
    Slf4jSimple,

    Logback,

    /**
     * log4j 2.x
     */
    Log4j2,

    /**
     * log4j 1.2.x
     */
    Log4j1,

    /**
     * [reload4j](https://reload4j.qos.ch/)
     */
    Reload4j,

    /**
     * Java Util Log (also referenced as JDK 1.4 logging)
     */
    JUL,

    Android,

    /**
     * Bridge to Apache Commons Logging (also known as Java or Jakarta Commons Logging).
     */
    JCL,

    /**
     * A logger implementation which logs via a delegate logger. By default, the delegate is a
     * NOPLogger. However, a different delegate can be set at any time.
     */
    SubstituteLogger,

    /**
     * An unknown slf4j logger implementation, but not NOP.
     */
    Unknown

}