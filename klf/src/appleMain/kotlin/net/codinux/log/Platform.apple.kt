@file:OptIn(ExperimentalNativeApi::class, ExperimentalForeignApi::class)

package net.codinux.log

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.useContents
import net.codinux.log.appender.Appender
import net.codinux.log.appender.NSLogAppender
import net.codinux.log.appender.OSLogAppender
import net.codinux.log.util.LoggerNameResolver
import platform.CoreFoundation.kCFBundleNameKey
import platform.Foundation.NSBundle
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSThread
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform
import kotlin.reflect.KClass

internal actual object Platform {

  private val supportsOsLog = isOsLogSupported()

  actual val type = PlatformType.Apple

  actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

  actual val systemDefaultAppender: Appender = if (supportsOsLog) OSLogAppender() else NSLogAppender()

  actual fun <T : Any> getLoggerName(forClass: KClass<T>) =
    LoggerNameResolver.getLoggerNameForKClassesWithQualifiedName(forClass)

  // TODO: may use Thread.callStackSymbols
  actual fun getLoggerNameFromCallingMethod(): String? = null

  actual fun getCurrentThreadName(): String? {
    val currentThread = NSThread.currentThread

    return currentThread.name?.takeIf { it.isNotBlank() }
      ?: currentThread.description
      ?: NSOperationQueue.currentQueue?.underlyingQueue?.description
  }

  fun printStackTrace() {
    NSThread.callStackSymbols
      .drop(1) // skip invocation of Exception constructor and this method
      .forEach { callStackSymbol ->
        println(callStackSymbol)
      }
  }

  actual fun lineSeparator(): String =
    "\n" // TODO: check if this works reliably; may use NSParagraphSeparatorCharacter or NSLineSeparatorCharacter

  actual val isRunningInDebugMode: Boolean =
    NativeDefaults.isRunningInDebugMode

  actual val appName: String? by lazy {
//      val info = NSBundle.mainBundle.infoDictionary!!
//      println("appName: ${info["CFBundleName"]}, displayName: ${info["CFBundleDisplayName"]}, identifier: ${info["CFBundleIdentifier"]}, appBuild: ${info["CFBundleVersion"]}, appVersion: ${info["CFBundleShortVersionString"]}")
    NSBundle.mainBundle.infoDictionary?.get(kCFBundleNameKey) as? String
  }


  @OptIn(UnsafeNumber::class)
  private fun isOsLogSupported(): Boolean {
    val osVersion = NSProcessInfo.processInfo.operatingSystemVersion

    return osVersion.useContents {
      val majorVersion = this.majorVersion.toInt()

      // os_log is available for: iOS 10.0+, iPadOS 10.0+, macOS 10.12+, Mac Catalyst 13.1+, tvOS 10.0+, watchOS 3.0+ (https://developer.apple.com/documentation/os/1643744-os_log_create)
      when (Platform.osFamily) {
        OsFamily.MACOSX -> majorVersion > 10 || (majorVersion == 10 && this.minorVersion >= 12)
        OsFamily.IOS, OsFamily.TVOS -> majorVersion >= 10
        OsFamily.WATCHOS -> majorVersion >= 3
        else -> false
      }
    }
  }

}