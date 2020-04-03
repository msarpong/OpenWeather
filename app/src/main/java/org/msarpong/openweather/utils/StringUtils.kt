package org.msarpong.openweather.utils

fun capitalize(str: String): String? {

    val c = str.replace("\\s+".toRegex(), " ")
    val s = c.trim { it <= ' ' }
    var l = ""
    var i = 0
    while (i < s.length) {
        if (i == 0) {
            l += s.toUpperCase()[i]
            i++
        }
        l += s[i]
        if (s[i]
                .toInt() == 32
        ) {
            l += s.toUpperCase()[i + 1]
            i++
        }
        i++
    }
    return l
}