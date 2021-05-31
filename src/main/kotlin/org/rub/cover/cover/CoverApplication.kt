package org.rub.cover.cover

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoverApplication

fun main(args: Array<String>) {
    runApplication<CoverApplication>(*args)
}
