package net.codinux.log.slf4j

import net.codinux.log.JvmDefaults
import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.*
import net.codinux.log.status.StatusManager
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.NOPLoggerFactory
import java.util.concurrent.ConcurrentHashMap

object Slf4jUtil {

    private val bindingMap = ConcurrentHashMap<Slf4jBinding, Slf4jBindingImplementation?>()


    val isSlf4jOnClasspath: Boolean by lazy { JvmDefaults.isClassAvailable("org.slf4j.Logger") }

    private val slf4jLoggerFactory: ILoggerFactory by lazy { LoggerFactory.getILoggerFactory() }

    val boundLoggingFramework: Slf4jBinding by lazy { determineSlf4jBinding() }

    private val boundLoggingFrameworkImplementation: Slf4jBindingImplementation? by lazy { getBindingImplementation(boundLoggingFramework) }

    val useSlf4j: Boolean by lazy { isSlf4jOnClasspath && boundLoggingFramework != Slf4jBinding.NOP }

    val boundLoggingFrameworkRootLoggerName: String? by lazy {
        getLoggingFrameworkRootLoggerName(boundLoggingFramework)
    }


    fun getLevel(slf4jLogger: Logger): LogLevel? =
        boundLoggingFrameworkImplementation?.getLevel(slf4jLogger)

    fun setLevel(slf4jLogger: Logger, level: LogLevel?): Boolean =
        boundLoggingFrameworkImplementation?.setLevel(slf4jLogger, level)
            ?: false

    fun getLoggingFrameworkRootLoggerName(loggingFramework: Slf4jBinding): String? =
        getBindingImplementation(loggingFramework)?.rootLoggerName

    private fun getBindingImplementation(binding: Slf4jBinding): Slf4jBindingImplementation? =
        // ConcurrentHashMap throws a NullPointerException if value is null, so add NoopSlf4jBinding ...
        bindingMap.getOrPut(binding) { createBindingImplementation(binding) ?: NopSlf4jBinding }
            .takeUnless { it is NopSlf4jBinding } // ... and filter it out on retrieval

    private fun createBindingImplementation(binding: Slf4jBinding): Slf4jBindingImplementation? = when (binding) {
        Slf4jBinding.Logback -> LogbackSlf4jBinding()
        Slf4jBinding.Log4j2 -> Log4j2Slf4jBinding()
        Slf4jBinding.Log4j1 -> Log4j1Slf4jBinding()
        Slf4jBinding.Reload4j -> Reload4jSlf4jBinding()
        Slf4jBinding.JBossLogging -> JBossLoggingSlf4jBinding()
        Slf4jBinding.JavaUtilLog -> JavaUtilLogSlf4jBinding()
        Slf4jBinding.Slf4jSimple -> Slf4jSimpleSlf4jBinding()
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
                "org.slf4j.jul.JDK14LoggerFactory",
                "org.slf4j.impl.JDK14LoggerFactory" -> Slf4jBinding.JavaUtilLog
                "org.jboss.slf4j.JBossLoggerFactory", // slf4j 1
                "org.slf4j.impl.Slf4jLoggerFactory" -> Slf4jBinding.JBossLogging // defined in org.jboss.slf4j:slf4j-jboss-logmanager
                "org.slf4j.simple.SimpleLoggerFactory",
                "org.slf4j.impl.SimpleLoggerFactory" -> Slf4jBinding.Slf4jSimple
                "org.slf4j.impl.AndroidLoggerFactory" -> Slf4jBinding.Android // removed in slf4j 2.x
                "org.slf4j.impl.JCLLoggerFactory" -> Slf4jBinding.JCL // removed in slf4j 2.x
                "org.slf4j.helpers.SubstituteLoggerFactory" -> Slf4jBinding.SubstituteLogger // SubstituteLogger in most cases binds to NOP, but we cannot know to which logger it really binds
                else -> {
                    println("Unknown slf4j binding detected: $loggerFactoryClass")
                    Slf4jBinding.Unknown
                }
            }
        } catch (e: Exception) {
            StatusManager.newError(this, "Could not determine logging framework that slf4j binds to", e)

            Slf4jBinding.Unknown
        }
    }

}