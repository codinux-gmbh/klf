package net.codinux.log

import net.codinux.log.appender.AppenderCollection
import kotlin.jvm.JvmOverloads


open class DelegateToAppendersLogger @JvmOverloads constructor(
  name: String,
  protected open val collection: AppenderCollection, // or use ILoggerFactory implementation directly?
  level: LogLevel? = null // do not set to RootLevel to enable late log level determination e.g. due to a changed RootLevel
) : LoggerBase(name, level) {

  override fun log(level: LogLevel, message: String, exception: Throwable?) {
    collection.appendToAppenders(level, name, message, exception)
  }

}