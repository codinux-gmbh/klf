package net.codinux.log

import platform.Foundation.NSLog


open class NSLogLogger(name: String) : LoggerBase(name) {

    override fun log(level: LogLevel, message: String, exception: Throwable?) {
        NSLog("[$level] $message${exception ?: ""}")
    }

}