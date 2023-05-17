package net.codinux.log.appender

actual open class SystemDefaultAppender {

    actual companion object {

        private val instance = NSLogAppender()

        actual fun getDefaultAppender(): Appender = instance

    }

}