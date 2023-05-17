package net.codinux.log.appender

actual class SystemDefaultAppender {

    actual companion object {

        actual fun getDefaultAppender(): Appender = ConsoleAppender.Default

    }

}