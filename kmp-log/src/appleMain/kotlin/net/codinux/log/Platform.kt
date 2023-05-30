package net.codinux.log

import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.useContents
import net.codinux.log.appender.Appender
import net.codinux.log.appender.NSLogAppender
import net.codinux.log.appender.OSLogAppender
import platform.Foundation.NSProcessInfo
import kotlin.native.Platform
import kotlin.reflect.KClass

actual class Platform {

  actual companion object {

    private val supportsOsLog = isOsLogSupported()

    actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

    actual val systemDefaultAppender: Appender = if (supportsOsLog) OSLogAppender() else NSLogAppender()

    actual fun <T : Any> getLoggerName(forClass: KClass<T>) =
      forClass.qualifiedName ?: forClass.simpleName ?: forClass.toString().replace("class ", "")


    @OptIn(UnsafeNumber::class)
    private fun isOsLogSupported(): Boolean {
      val osVersion = NSProcessInfo.processInfo.operatingSystemVersion

      return osVersion.useContents {
        val majorVersion = this.majorVersion

        // os_log is available for: iOS 10.0+, iPadOS 10.0+, macOS 10.12+, Mac Catalyst 13.1+, tvOS 10.0+, watchOS 3.0+ (https://developer.apple.com/documentation/os/1643744-os_log_create)
        when (Platform.osFamily) {
          OsFamily.IOS, OsFamily.MACOSX, OsFamily.TVOS -> majorVersion >= 10
          OsFamily.WATCHOS -> majorVersion >= 3
          else -> false
        }
      }
    }

  }

}