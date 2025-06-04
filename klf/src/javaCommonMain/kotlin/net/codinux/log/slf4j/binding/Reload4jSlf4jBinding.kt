package net.codinux.log.slf4j.binding

import net.codinux.log.JvmDefaults
import net.codinux.log.status.StatusManager
import org.slf4j.Logger
import java.lang.reflect.Field

open class Reload4jSlf4jBinding : Log4j1Slf4jBinding(), Slf4jBindingAdapter {

    protected open val loggerField: Field? by lazy { getReload4jLoggerAdapterLoggerField() }


    override fun getLog4j1Logger(logger: Logger): org.apache.log4j.Logger? =
        getLog4j1LoggerFromReload4jAdapter(logger)
            ?: getLog4j1Logger(logger.name)

    protected open fun getLog4j1LoggerFromReload4jAdapter(logger: Logger): org.apache.log4j.Logger? =
        loggerField?.get(logger) as? org.apache.log4j.Logger


    protected open fun getReload4jLoggerAdapterLoggerField(): Field? = try {
        val adapterClass = JvmDefaults.getClassOrNull("org.slf4j.reload4j.Reload4jLoggerAdapter") // slf4j 2
            ?: JvmDefaults.getClassOrNull("org.slf4j.impl.Reload4jLoggerAdapter") // slf4j 1

        if (adapterClass != null) {
            val loggerField = adapterClass.getDeclaredField("logger")
            loggerField.isAccessible = true
            loggerField
        } else {
            null
        }
    } catch (e: Throwable) {
        StatusManager.newError(this, "Could not get logger field of Reload4jLoggerAdapter class, " +
                "therefore cannot get or set Reload4j Loggers' LogLevel", e)

        null
    }

}