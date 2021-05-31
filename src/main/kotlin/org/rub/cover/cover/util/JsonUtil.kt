package org.rub.cover.cover.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.rub.cover.cover.entity.LocalHostRequestParam


object JsonUtil {
    fun toJSon(obj: Any?): String? {
        val objectMapper = ObjectMapper()
        try {
            return objectMapper.writeValueAsString(obj)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}

//fun main(args: Array<String>) {
//    var a = LocalHostRequestParam()
//    a.address= "11"
//    a.basePath="1"
//    a.classFilePath="2"
//    a.port=633
//    a.subModule= ""
//    var list = listOf(a,a)
//
//    println(JsonUtil.toJSon(list))
//}