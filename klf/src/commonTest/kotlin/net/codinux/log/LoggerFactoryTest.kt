package net.codinux.log

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class LoggerFactoryTest {

  init {
    // otherwise on JVM slf4j's org.slf4j.helpers.NOPLoggerFactory is used. Loggers then have the name "NOP"
    LoggerFactory.setLoggerFactory(DefaultLoggerFactory())
  }

  @Test
  @JsName("LoggerDeclarationInInstancePropertyLoggerNameIsOfClass")
  fun `Logger declaration in instance property - logger name is of class`() {
    val actualName = ClassDeclaringLoggerAsInstanceProperty().log.name

    if (Platform.type.isJsOrWasm) {
      assertEquals("ClassDeclaringLoggerAsInstanceProperty", actualName)
    } else {
      assertEquals("net.codinux.log.ClassDeclaringLoggerAsInstanceProperty", actualName)
    }
  }

  @Test
  @JsName("LoggerDeclarationInCompanionObjectButLoggerNameIsOfEnclosingClass")
  fun `Logger declaration in companion object - logger name is of enclosing class`() {
    val actualName = ClassDeclaringLoggerInCompanionObject.log.name

    if (Platform.type.isJsOrWasm) {
      assertEquals("Companion", actualName)
    } else {
      assertEquals("net.codinux.log.ClassDeclaringLoggerInCompanionObject", actualName)
    }
  }

  @Test
  @JsName("LoggerDeclarationInObjectLoggerNameIsOfObject")
  fun `Logger declaration in object - logger name is of object`() {
    val actualName = ObjectDeclaringLoggerAsInstanceProperty.log.name

    if (Platform.type.isJsOrWasm) {
      assertEquals("ObjectDeclaringLoggerAsInstanceProperty", actualName)
    } else {
      assertEquals("net.codinux.log.ObjectDeclaringLoggerAsInstanceProperty", actualName)
    }
  }

  @Test
  @JsName("LoggerDeclarationInInnerClassLoggerNameIsOfInnerClass")
  fun `Logger declaration in inner class - logger name is of inner class`() {
    val actualName = OuterClass.InnerClass().log.name

    if (Platform.type.isJsOrWasm) {
      assertEquals("InnerClass", actualName)
    } else {
      assertEquals("net.codinux.log.OuterClass.InnerClass", actualName)
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

object ObjectDeclaringLoggerAsInstanceProperty {
  val log by logger()
}

class OuterClass {
  class InnerClass {
    val log by logger()
  }
}