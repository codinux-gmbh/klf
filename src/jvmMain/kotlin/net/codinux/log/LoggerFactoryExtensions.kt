package net.codinux.log

fun <T : Any> LoggerFactory.getLogger(forClass: Class<T>): Logger = getLogger(forClass.kotlin)