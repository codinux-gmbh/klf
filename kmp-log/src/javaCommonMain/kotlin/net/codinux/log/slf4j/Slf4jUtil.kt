package net.codinux.log.slf4j

import org.slf4j.ILoggerFactory
import org.slf4j.LoggerFactory
import org.slf4j.helpers.NOPLoggerFactory

object Slf4jUtil {

    val isSlf4jOnClasspath = isClassAvailable("org.slf4j.Logger")

    val slf4jLoggerFactory: ILoggerFactory = LoggerFactory.getILoggerFactory()

    val useSlf4j = isSlf4jOnClasspath && slf4jLoggerFactory !is NOPLoggerFactory


    private fun isClassAvailable(qualifiedClassName: String): Boolean {
        try {
            Class.forName(qualifiedClassName)

            return true
        } catch (ignored: Exception) { }

        return false
    }

}