package net.codinux.log.appender

import net.codinux.log.classname.ClassNameAbbreviationStrategy
import net.codinux.log.classname.ClassNameAbbreviator
import net.codinux.log.classname.ClassNameAbbreviatorOptions

open class LoggerNameAbbreviator {

    protected open val classNameAbbreviator = ClassNameAbbreviator(ClassNameAbbreviatorOptions(ClassNameAbbreviationStrategy.EllipsisStart, classNameAbbreviationEllipsis = "*")) // for now we keep the current behaviour


    open fun getLoggerTagOfMaxLength(loggerName: String, maxLength: Int): String =
        classNameAbbreviator.abbreviate(loggerName, maxLength)

}