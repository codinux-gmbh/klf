package net.codinux.log

import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject


class LoggerFactory {

  companion object {

    fun <T: Any> logger(forClass: KClass<T>): Logger {
      return logger(forClass.java)
    }

    fun <T: Any> logger(forClass: Class<T>): Logger {
      return DelegateToAppenderLogger(unwrapCompanionClass(forClass).name)
    }

    // unwrap companion class to enclosing class given a Java Class
    fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
      return ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
      } ?: ofClass
    }

  }

}