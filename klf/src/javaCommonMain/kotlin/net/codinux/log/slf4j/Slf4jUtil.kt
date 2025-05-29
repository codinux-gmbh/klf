package net.codinux.log.slf4j

import net.codinux.log.JvmDefaults
import org.slf4j.ILoggerFactory
import org.slf4j.LoggerFactory
import org.slf4j.helpers.NOPLoggerFactory

object Slf4jUtil {

    val isSlf4jOnClasspath: Boolean by lazy { JvmDefaults.isClassAvailable("org.slf4j.Logger") }

    val slf4jLoggerFactory: ILoggerFactory by lazy { LoggerFactory.getILoggerFactory() }

    val boundLoggingFramework: Slf4jBinding by lazy { determineSlf4jBinding() }

    val useSlf4j: Boolean by lazy { isSlf4jOnClasspath && boundLoggingFramework != Slf4jBinding.NOP }

    val boundLoggingFrameworkRootLoggerName: String? by lazy {
        getLoggingFrameworkRootLoggerName(boundLoggingFramework)
    }


    fun getLoggingFrameworkRootLoggerName(loggingFramework: Slf4jBinding): String? = when (loggingFramework) {
        Slf4jBinding.Logback -> "ROOT"
        Slf4jBinding.Log4j2 -> ""
        Slf4jBinding.Log4j1, Slf4jBinding.Reload4j -> "root"
        Slf4jBinding.JUL, Slf4jBinding.JBossLogging -> ""
        // slf4j Simple and slf4j Android don't have a root logger (TODO: what about JCL?)
        else -> null
    }

    private fun determineSlf4jBinding(): Slf4jBinding {
        return try {
            val loggerFactory = slf4jLoggerFactory
            if (loggerFactory is NOPLoggerFactory) {
                return Slf4jBinding.NOP
            }

            val loggerFactoryClass = loggerFactory::class
            when (loggerFactoryClass.qualifiedName) {
                "ch.qos.logback.classic.LoggerContext" -> Slf4jBinding.Logback
                "org.apache.logging.slf4j.Log4jLoggerFactory" -> Slf4jBinding.Log4j2
                "org.slf4j.impl.Log4jLoggerFactory" -> Slf4jBinding.Log4j1
                "org.slf4j.impl.Reload4jLoggerFactory",
                "org.slf4j.reload4j.Reload4jLoggerFactory" -> Slf4jBinding.Reload4j
                "org.slf4j.impl.JDK14LoggerFactory" -> Slf4jBinding.JUL
                "org.slf4j.impl.Slf4jLoggerFactory" -> Slf4jBinding.JBossLogging // defined in org.jboss.slf4j:slf4j-jboss-logmanager
                "org.slf4j.impl.SimpleLoggerFactory" -> Slf4jBinding.Slf4jSimple
                "org.slf4j.impl.AndroidLoggerFactory" -> Slf4jBinding.Android // removed in slf4j 2.x
                "org.slf4j.impl.JCLLoggerFactory" -> Slf4jBinding.JCL // removed in slf4j 2.x
                "org.slf4j.helpers.SubstituteLoggerFactory" -> Slf4jBinding.SubstituteLogger // SubstituteLogger in most cases binds to NOP, but we cannot know to which logger it really binds
                else -> Slf4jBinding.Unknown
            }
        } catch (e: Exception) {
            // TODO: add an ErrorHandler
            println("Could not determine logging framework that slf4j binds to: $e")

            Slf4jBinding.Unknown
        }
    }

}