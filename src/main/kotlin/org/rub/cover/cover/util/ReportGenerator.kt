package org.rub.cover.cover.util

import org.jacoco.core.analysis.Analyzer
import org.jacoco.core.analysis.CoverageBuilder
import org.jacoco.core.analysis.IBundleCoverage
import org.jacoco.core.tools.ExecFileLoader
import org.jacoco.report.DirectorySourceFileLocator
import org.jacoco.report.FileMultiReportOutput
import org.jacoco.report.html.HTMLFormatter
import java.io.File
import java.io.IOException


class ReportGenerator {
    private var title: String? = null

    private var executionDataFile: File? = null
    private var classesDirectory: File? = null
    private var sourceDirectory: File? = null
    private var reportDirectory: File? = null

    private var execFileLoader: ExecFileLoader? = null

    /**
     * Create a new generator based for the given project.
     *
     * @param projectDirectory
     */
    constructor(projectDirectory: File) {
        title = projectDirectory.name
        executionDataFile = File(projectDirectory, "jacoco.exec")
        classesDirectory = File(projectDirectory, "bin")
        sourceDirectory = File(projectDirectory, "src")
        reportDirectory = File(projectDirectory, "coveragereport")
    }

    /**
     * Create the report.
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun create() {

        loadExecutionData()

        val bundleCoverage = analyzeStructure()
        createReport(bundleCoverage)
    }

    @Throws(IOException::class)
    private fun createReport(bundleCoverage: IBundleCoverage) {

        val htmlFormatter = HTMLFormatter()
        val visitor = htmlFormatter
            .createVisitor(FileMultiReportOutput(reportDirectory))

        visitor.visitInfo(
            execFileLoader!!.sessionInfoStore.infos,
            execFileLoader!!.executionDataStore.contents
        )

        visitor.visitBundle(
            bundleCoverage,
            DirectorySourceFileLocator(sourceDirectory, "utf-8", 4)
        )
        visitor.visitEnd()
    }

    @Throws(IOException::class)
    private fun loadExecutionData() {
        execFileLoader = ExecFileLoader()
        execFileLoader?.load(executionDataFile)
    }

    @Throws(IOException::class)
    private fun analyzeStructure(): IBundleCoverage {
        val coverageBuilder = CoverageBuilder()
        val analyzer = Analyzer(
            execFileLoader!!.executionDataStore, coverageBuilder
        )
        analyzer.analyzeAll(classesDirectory)
        return coverageBuilder.getBundle(title)
    }
}

//@Throws(IOException::class)
//fun main(args: Array<String>) {
//    for (i in args.indices) {
//        val generator = ReportGenerator(
//            File(args[i])
//        )
//        generator.create()
//    }
//}