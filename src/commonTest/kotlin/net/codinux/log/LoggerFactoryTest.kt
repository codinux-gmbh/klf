package net.codinux.log

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class LoggerFactoryTest {

  init {
    // otherwise on JVM slf4j's org.slf4j.helpers.NOPLoggerFactory is used. Loggers then have the name "NOP"
    LoggerFactory.setLoggerFactory(DefaultLoggerFactory())
  }

  // on all other platforms the logger name starts with the package name
  private val isRunningInJs = SystemDefaults.getLoggerName(this::class) == "LoggerFactoryTest"

  @Test
  @JsName("LoggerDeclarationInInstancePropertyLoggerNameIsOfClass")
  fun `Logger declaration in instance property - logger name is of class`() {
    val actualName = ClassDeclaringLoggerAsInstanceProperty().log.name

    if (isRunningInJs) {
      assertEquals("ClassDeclaringLoggerAsInstanceProperty", actualName)
    } else {
      assertEquals("net.codinux.log.ClassDeclaringLoggerAsInstanceProperty", actualName)
    }
  }

  @Test
  @JsName("LoggerDeclarationInCompanionObjectButLoggerNameIsOfEnclosingClass")
  fun `Logger declaration in companion object - logger name is of enclosing class`() {
    val actualName = ClassDeclaringLoggerInCompanionObject.log.name

    if (isRunningInJs) {
      assertEquals("Companion", actualName)
    } else {
      assertEquals("net.codinux.log.ClassDeclaringLoggerInCompanionObject", actualName)
    }
  }
}

class ClassDeclaringLoggerInCompanionObject {
  companion object {
    val log by logger()
  }
}

class ClassDeclaringLoggerAsInstanceProperty {
  val log by logger()
}