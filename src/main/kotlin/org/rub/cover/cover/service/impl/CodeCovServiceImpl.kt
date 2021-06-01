package org.rub.cover.cover.service.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jacoco.core.tools.ExecDumpClient
import org.jacoco.core.tools.ExecFileLoader
import org.rub.cover.cover.entity.Demo
import org.rub.cover.cover.service.CodeCovService
import org.slf4j.LoggerFactory
import java.io.File


class CodeCovServiceImpl : CodeCovService {

    val log = LoggerFactory.getLogger(this.javaClass)


    /**
     * TODO
     * will be
     */
    fun dump(ip: String, port: Int = 6300) {
        val client = ExecDumpClient()
        client.setDump(true)
        var file: ExecFileLoader = client.dump(ip, port)

        file.save(File("/test/jacoco.exec"), false)

    }

    /**
     *
     */
    private fun mergeExec(ExecFiles: List<String>, NewFileName: String) {
        val mapper = jacksonObjectMapper()
        val json =""
        val movieList: List<Demo> = mapper.readValue(json)
        val execFileLoader = ExecFileLoader()
        try {
            for (ExecFile in ExecFiles) {
                execFileLoader.load(File(ExecFile))
            }
        } catch (e: Exception) {
            log.error("ExecFiles 合并失败 errorMessege is {}", e.fillInStackTrace())
        }
        try {
            execFileLoader.save(File(NewFileName), false)
        } catch (e: Exception) {
            log.error("ExecFiles 保存失败 errorMessege is {}", e.fillInStackTrace())
        }
    }
}