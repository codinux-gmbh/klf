package net.codinux.log


enum class LogLevel(val priority: Int) {

    Fatal(0),

    Error(1),

    Warn(2),

    Info(3),

    Debug(4),

    Trace(5),

    Off(6)

}