package net.codinux.log

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.codinux.kotlin.platform.Platform
import net.codinux.kotlin.platform.isJavaScript
import kotlin.js.JsName
import kotlin.test.Test

class LoggerFactoryTest {

  init {
    // otherwise on JVM slf4j's org.slf4j.helpers.NOPLoggerFactory is used. Loggers then have the name "NOP"
    LoggerFactory.setLoggerFactory(DefaultLoggerFactory())
  }

  @Test
  @JsName("LoggerDeclarationInInstancePropertyLoggerNameIsOfClass")
  fun `Logger declaration in instance property - logger name is of class`() {
    val actualName = ClassDeclaringLoggerAsInstanceProperty().log.name

    if (Platform.isJavaScript) {
      assertThat(actualName).isEqualTo("ClassDeclaringLoggerAsInstanceProperty")
    } else {
      assertThat(actualName).isEqualTo("net.codinux.log.ClassDeclaringLoggerAsInstanceProperty")
    }
  }

  @Test
  @JsName("LoggerDeclarationInCompanionObjectButLoggerNameIsOfEnclosingClass")
  fun `Logger declaration in companion object - logger name is of enclosing class`() {
    val actualName = ClassDeclaringLoggerInCompanionObject.log.name

    if (Platform.isJavaScript) {
      assertThat(actualName).isEqualTo("Companion_1")
    } else {
      assertThat(actualName).isEqualTo("net.codinux.log.ClassDeclaringLoggerInCompanionObject")
    }
  }

  @Test
  @JsName("LoggerDeclarationInObjectLoggerNameIsOfObject")
  fun `Logger declaration in object - logger name is of object`() {
    val actualName = ObjectDeclaringLoggerAsInstanceProperty.log.name

    if (Platform.isJavaScript) {
      assertThat(actualName).isEqualTo("ObjectDeclaringLoggerAsInstanceProperty")
    } else {
      assertThat(actualName).isEqualTo("net.codinux.log.ObjectDeclaringLoggerAsInstanceProperty")
    }
  }

  @Test
  @JsName("LoggerDeclarationInInnerClassLoggerNameIsOfInnerClass")
  fun `Logger declaration in inner class - logger name is of inner class`() {
    val actualName = OuterClass.InnerClass().log.name

    if (Platform.isJavaScript) {
      assertThat(actualName).isEqualTo("InnerClass")
    } else {
      assertThat(actualName).isEqualTo("net.codinux.log.OuterClass.InnerClass")
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