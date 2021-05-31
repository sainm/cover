package org.rub.cover.cover.entity

class HttpResult<T> {
    var code: Int
    var msg: String? = null
    var data: T? = null

    companion object {
        fun <T> success(): HttpResult<T> {
            return build(ErrorCode.SUCCESS)
        }

        fun <T> success(data: T): HttpResult<T> {
            return build(ErrorCode.SUCCESS.code, ErrorCode.SUCCESS.msg, data)
        }

        fun <T> fail(): HttpResult<T> {
            return build(ErrorCode.FAIL)
        }

        fun <T> build(data: T): HttpResult<T> {
            return build(ErrorCode.SUCCESS, data)
        }

        fun <T> build(code: ErrorCode): HttpResult<T> {
            return build(code.code, code.msg, null)
        }

        fun <T> build(success: Boolean): HttpResult<T> {
            return build(if (success) ErrorCode.SUCCESS else ErrorCode.FAIL)
        }

        fun <T> build(code: ErrorCode, data: T): HttpResult<T> {
            return build(code.code, code.msg, data)
        }

        fun <T> build(success: Boolean, data: T): HttpResult<T> {
            return build(if (success) ErrorCode.SUCCESS else ErrorCode.FAIL, data)
        }

        fun <T> build(code: ErrorCode, msg: String?): HttpResult<T> {
            return build(code.code, msg, null)
        }

        fun <T> build(success: Boolean, msg: String?): HttpResult<T> {
            return build(if (success) ErrorCode.SUCCESS else ErrorCode.FAIL, msg)
        }

        fun <T> build(code: ErrorCode, msg: String?, data: T): HttpResult<T> {
            return build(code.code, msg, data)
        }

        fun <T> build(success: Boolean, msg: String?, data: T): HttpResult<T> {
            return build(if (success) ErrorCode.SUCCESS else ErrorCode.FAIL, msg, data)
        }

        fun <T> build(code: Int, msg: String?): HttpResult<T> {
            return build(code, msg, null)
        }

        fun <T> build(code: Int, msg: String?, data: T?): HttpResult<T> {
            val httpResult: HttpResult<T> = HttpResult<T>()
            httpResult.code = code
            httpResult.msg = msg
            httpResult.data = data
            return httpResult
        }
    }

    init {
        this.code = ErrorCode.SUCCESS.code
    }
}