package net.codinux.log


enum class LogLevel(val priority: Int) {

    // as in slf4j we don't support Fatal:
    // "slf4j drops the FATAL logging level (introduced in Log4j) based on the premise that in a logging framework we should not decide when to terminate an application."

    Error(0),

    Warn(1),

    Info(2),

    Debug(3),

    Trace(4),

    Off(5)

}