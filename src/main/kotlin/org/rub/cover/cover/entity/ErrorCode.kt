package org.rub.cover.cover.entity

import org.apache.commons.lang3.StringUtils

enum class ErrorCode(val code: Int, val msg: String) {
    SUCCESS(200, "success"), FAIL(-1, "fail");

    fun getMsg(msg: String): String {
        return if (StringUtils.isBlank(msg)) this.msg else this.msg + ": " + msg
    }
}