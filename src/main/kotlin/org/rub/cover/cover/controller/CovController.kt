package org.rub.cover.cover.controller

import org.rub.cover.cover.entity.Greeting
import org.rub.cover.cover.entity.HttpResult
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CovController {

    @RequestMapping("/list")
    fun list(@RequestParam(value = "name", defaultValue = "World") name: String) = HttpResult.success<Any>()


}