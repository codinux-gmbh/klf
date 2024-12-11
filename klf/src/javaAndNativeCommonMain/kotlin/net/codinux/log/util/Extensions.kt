package net.codinux.log.util

fun CharSequence.lastIndexOfOrNull(char: Char, startIndex: Int = lastIndex, ignoreCase: Boolean = false): Int? =
    this.lastIndexOf(char, startIndex, ignoreCase).takeIf { it != -1 }

fun CharSequence.lastIndexOfOrNull(string: String, startIndex: Int = lastIndex, ignoreCase: Boolean = false): Int? =
    this.lastIndexOf(string, startIndex, ignoreCase).takeIf { it != -1 }