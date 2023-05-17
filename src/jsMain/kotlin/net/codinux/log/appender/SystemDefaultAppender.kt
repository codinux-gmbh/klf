package net.codinux.log.appender

actual class SystemDefaultAppender {

    actual companion object {

        private val instance = JsConsoleAppender()

        actual fun getDefaultAppender(): Appender = instance

    }

}