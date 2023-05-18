package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import net.codinux.log.slf4j.Slf4jLoggerFactory
import kotlin.reflect.KClass


actual class Platform {

    actual companion object {

        actual fun createDefaultLoggerFactory(): ILoggerFactory {
            if (isClassAvailable("org.slf4j.Logger")) {
                // TODO: should we not use slf4j if it's LoggerFactory is org.slf4j.helpers.NOPLoggerFactory = no binding is available for slf4j?
                return Slf4jLoggerFactory()
            }

            return DefaultLoggerFactory()
        }

        actual val systemDefaultAppender: Appender = ConsoleAppender.Default


        actual fun <T : Any> getLoggerName(forClass: KClass<T>) = getLoggerName(forClass.java)

        fun <T : Any> getLoggerName(forClass: Class<T>): String = unwrapCompanionClass(forClass).name

        // unwrap companion class to enclosing class given a Java Class
        private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
            return ofClass.enclosingClass?.takeIf {
                ofClass.enclosingClass.kotlin.isCompanion == false
            } ?: ofClass
        }


        private fun isClassAvailable(qualifiedClassName: String): Boolean {
            try {
                Class.forName(qualifiedClassName)

                return true
            } catch (ignored: Exception) { }

            return false
        }

    }

}