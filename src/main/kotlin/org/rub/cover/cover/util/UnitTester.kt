//package org.rub.cover.cover.util
//
//import org.rub.cover.cover.entity.CoverageReportEntity
//import java.util.concurrent.TimeoutException
//
//class UnitTester {
//    private val UNITTEST_TIMEOUT = 3600000L
//
//    fun executeUnitTest(coverageReport: CoverageReportEntity): Unit {
//        val startTime = System.currentTimeMillis()
//        val unittestCmd = "cd " + coverageReport.nowLocalPath + "&&mvn clean"
//
//        val logFile: String = System.getProperty("user.home") + "/report/logs/jacoco.log"
////            coverageReport.logFile.replace(LocalIpUtils.getTomcatBaseUrl().toString() + "logs/", LOG_PATH)
//        val cmd = arrayOf(
//            unittestCmd + " -Dmaven.test.skip=false org.jacoco:jacoco-maven-plugin:1.0.2-SNAPSHOT:prepare-agent "
//                    + "compile test-compile org.apache.maven.plugins:maven-surefire-plugin:2.22.1:test "
//                    + "org.apache.maven.plugins:maven-jar-plugin:2.4:jar org.jacoco:jacoco-maven-plugin:0.8.7:report -Dmaven.test.failure.ignore=true -Dfile.encoding=UTF-8 "
//                    + ">" + logFile
//        )
//        // 超时时间设置为一小时,
//        val exitCode: Int
//        try {
//            exitCode = CmdExecutor.executeCmd(cmd, UNITTEST_TIMEOUT)
////            log.info("单元测试执行结果exitCode={} uuid={}", exitCode, coverageReport.getUuid())
//            if (exitCode == 0) {
////                log.info("执行单元测试成功...")
//                //coverageReport.requestStatus =(Constants.JobStatus.UNITTEST_DONE.`val`())
//            } else {
////                coverageReport.setRequestStatus(Constants.JobStatus.UNITTEST_FAIL.`val`())
////                coverageReport.setErrMsg("执行单元测试报错")
//            }
//        } catch (e: TimeoutException) {
////            coverageReport.setRequestStatus(Constants.JobStatus.TIMEOUT.`val`())
////            coverageReport.setErrMsg("执行单元测试超时")
//        } catch (e: Exception) {
////            log.error("执行单元测试异常", e)
////            coverageReport.setErrMsg("执行单元测试异常:" + e.message)
////            coverageReport.setRequestStatus(Constants.JobStatus.UNITTEST_FAIL.`val`())
//        } finally {
////            log.info("uuid={} 执行单元测试耗时{}ms", coverageReport.getUuid(), System.currentTimeMillis() - startTime)
//        }
//    }
//}