package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import net.codinux.log.slf4j.Slf4jLoggerFactory


actual class SystemDefaults {

    actual companion object {
        actual fun createDefaultLoggerFactory(): ILoggerFactory {
            if (isClassAvailable("org.slf4j.Logger")) {
                // TODO: should we not use slf4j if it's LoggerFactory is org.slf4j.helpers.NOPLoggerFactory = no binding is available for slf4j?
                return Slf4jLoggerFactory()
            }

            return DefaultLoggerFactory()
        }

        actual fun getDefaultAppender(): Appender = ConsoleAppender.Default


        private fun isClassAvailable(qualifiedClassName: String): Boolean {
            try {
                Class.forName(qualifiedClassName)

                return true
            } catch (ignored: Exception) { }

            return false
        }

    }

}