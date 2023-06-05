package net.codinux.log


enum class LogLevel(val priority: Int) {

    // as in slf4j we don't support log level Fatal:
    // "slf4j drops the FATAL logging level (introduced in Log4j) based on the premise that in a logging framework we should not decide when to terminate an application."

    Off(Int.MAX_VALUE),

    Error(900),

    Warn(800),

    Info(700),

    Debug(600),

    Trace(500)

}