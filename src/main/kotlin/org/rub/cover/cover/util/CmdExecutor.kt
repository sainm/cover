//package org.rub.cover.cover.util
//
//import org.slf4j.LoggerFactory
//import java.io.BufferedReader
//import java.io.IOException
//import java.io.InputStream
//import java.io.InputStreamReader
//import java.util.concurrent.ArrayBlockingQueue
//import java.util.concurrent.ThreadPoolExecutor
//import java.util.concurrent.TimeUnit
//import java.util.concurrent.TimeoutException
//import java.util.concurrent.atomic.AtomicInteger
//
//object CmdExecutor {
//    private val LOG = LoggerFactory.getLogger(CmdExecutor::class.java)
//    private val counter = AtomicInteger(0)
//    private const val maxThread = 64
//    private val executor = ThreadPoolExecutor(
//        20, maxThread, 5 * 60, TimeUnit.SECONDS,
//        ArrayBlockingQueue(1)
//    ) { r: Runnable? -> Thread(r, "CmdThread-" + counter.getAndIncrement()) }
//
//    @Throws(Exception::class)
//    fun executeCmd(commands: Array<String>, timeout: Long?): Int {
//        val ret = StringBuffer()
//        require(!(commands == null || commands.isEmpty()))
//        var process: Process? = null
//        return try {
//            val e = StringBuilder()
//            for (builder in commands.indices) {
//                e.append(commands[builder])
//                if (builder < commands.size - 1) {
//                    e.append(" && ")
//                }
//            }
//            LOG.info("CmdThreadPool:{}", executor)
//            if (executor.poolSize >= maxThread) {
//                LOG.warn("CmdThreadPoolBusy")
//            }
//            LOG.info("executeCmd : bash -c $e")
//            val var12 = ProcessBuilder(*arrayOf("bash", "-c", e.toString()))
//            var12.redirectErrorStream(true)
//            process = var12.start()
//            val readLine = ReadLine(process.inputStream, ret, true)
//            val readLineFuture = executor.submit(readLine)
//            val begin = System.currentTimeMillis()
//            if (process.waitFor(timeout!!, TimeUnit.MILLISECONDS)) {
//                LOG.info("readLine.stop();")
//                readLine.setFlag(false)
//                LOG.info("progressBar.stop();")
//                LOG.info("executeCmd done !!!!!!")
//                LOG.info("worker done !!!!!! times = " + (System.currentTimeMillis() - begin) / 1000L + "s")
//                process.exitValue()
//            } else {
//                throw TimeoutException()
//            }
//        } catch (var10: IOException) {
//            LOG.error("executeCmd builder.start(); IOException : ", var10)
//            throw var10
//        } catch (var10: InterruptedException) {
//            LOG.error("executeCmd builder.start(); IOException : ", var10)
//            throw var10
//        } finally {
//            process?.destroy()
//        }
//    }
//
//    private class ReadLine  // this.sb = sb;
//    private constructor(
//        private val `is`: InputStream, sb: StringBuffer, // private final StringBuffer sb;
//        @field:Volatile private var flag: Boolean
//    ) :
//        Runnable {
//        fun setFlag(flag: Boolean) {
//            this.flag = flag
//        }
//
//        override fun run() {
//            val reader = BufferedReader(InputStreamReader(`is`))
//            try {
//                var line: String
//                try {
//                    while (flag && reader.readLine().also { line = it } != null) {
//                        val e = line.trim { it <= ' ' }
//                        if (e.length != 0) {
//                            LOGGER.info(e)
//                            // 这个sb的太大了，另外有啥用
//                            //this.sb.append(e + System.getProperty("line.separator"));
//                        }
//                    }
//                } catch (var12: IOException) {
//                    LOGGER.error("@@@@@@@@@@@@@@ ReadLine Thread, read IOException : ", var12)
//                }
//            } finally {
//                try {
//                    reader.close()
//                } catch (var11: IOException) {
//                    LOGGER.error("@@@@@@@@@@@@@@ ReadLine Thread, close IOException : ", var11)
//                }
//            }
//        }
//
//        companion object {
//            private val LOGGER = LoggerFactory.getLogger("commandOutputLogger")
//        }
//    }
//}