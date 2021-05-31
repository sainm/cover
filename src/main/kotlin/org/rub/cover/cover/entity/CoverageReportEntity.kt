package org.rub.cover.cover.entity

import java.util.*

class CoverageReportEntity {
    var id: Int? = null
    var uuid: String? = null
    var gitUrl: String? = null
    var baseVersion: String? = null
    var nowVersion: String? = null


    var type: Int? = null
    var requestStatus: Int? = null
    var diffMethod = ""
    var errMsg = ""
    var reportUrl = ""
    var lineCoverage = (-1).toDouble()
    var branchCoverage = (-1).toDouble()
    var createTime: Date? = null
    var updateTime: Date? = null
    var nowLocalPath = ""
    var baseLocalPath = ""
    var subModule = ""
    var codePath: String? = null
    var envType = ""
    var reportFile: String? = null
    var from: Int? = null
    var logFile = ""
}