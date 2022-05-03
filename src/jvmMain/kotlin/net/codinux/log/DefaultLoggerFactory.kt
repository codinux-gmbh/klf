package net.codinux.log

import net.codinux.log.slf4j.Slf4jLoggerFactory


actual class DefaultLoggerFactory {

    actual fun createDefaultLoggerFactory(): ILoggerFactory {
        if (isClassAvailable("org.slf4j.Logger")) {
            return Slf4jLoggerFactory()
        }

        return ConsoleLoggerFactory()
    }

    private fun isClassAvailable(qualifiedClassName: String): Boolean {
        try {
            Class.forName(qualifiedClassName)

            return true
        } catch (ignored: Exception) { }

        return false
    }

}