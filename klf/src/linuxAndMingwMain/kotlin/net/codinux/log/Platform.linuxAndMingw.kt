package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import net.codinux.log.util.LoggerNameResolver
import kotlin.native.concurrent.Worker
import kotlin.reflect.KClass

internal actual object Platform {

  actual val type = PlatformType.LinuxOrMingw

  actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

  actual val systemDefaultAppender: Appender = ConsoleAppender.Default

  actual fun <T : Any> getLoggerName(forClass: KClass<T>) =
    LoggerNameResolver.getLoggerNameForKClassesWithQualifiedName(forClass)

  actual fun getLoggerNameFromCallingMethod(): String? = null

  actual fun getCurrentThreadName(): String? =
    Worker.current.name // TODO: use native C implementation

  actual fun lineSeparator(): String =
    LinuxAndMingwPlatform.lineSeparator()

  @kotlin.experimental.ExperimentalNativeApi
  actual val isRunningInDebugMode: Boolean =
    NativeDefaults.isRunningInDebugMode

  actual val appName: String? = null

}