package org.rub.cover.cover.util

import java.io.*
import java.util.regex.Pattern


object ReportHelper {
    /**
     * @param projectPath
     * @return
     * @description:为多模块的工程添加一个子模块jacocomodule，dependency其他工程的原有子模块
     */
    fun addcoverageModule(projectPath: String): Boolean {
        val projectDir = File(projectPath)
        var writer: FileWriter? = null
        var cwriter: FileWriter? = null
        if (!projectDir.exists()) {
            return false
        }
        val parentFile = File(projectDir.absolutePath + "/pom.xml")
        if (!parentFile.exists()) {
            return false
        }
        val files = projectDir.listFiles()
        var childFile: File? = null

        //查找是否存在pom文件
        var num = 0
        for (file: File in files) {
            if (childFile != null) {
                break
            }
            if (file.isDirectory) {
                val subfiles = file.listFiles()
                for (subfile: File in subfiles) {
                    if (subfile.name == "pom.xml") {
                        childFile = subfile.absoluteFile
                        num = 1
                        break
                    }
                }
            }
        }
        if (num == 0) {
            return false
        }
        try {
            val parentbReader = BufferedReader(FileReader(parentFile))
            val sb = StringBuilder()
            val sb1 = StringBuilder()
            var s = ""
            while (parentbReader.readLine().also { s = it } != null) {
                sb.append(s.trim { it <= ' ' })
                sb1.append(s.trim { it <= ' ' } + "\n")
            }
            val pomstr = sb.toString()
            //  留作使用pomstrfinal
            var pomstrfinal = sb1.toString()
            //获取groupid和version和artifactId，这里需要pom中的<groupid><version><artifactId>放在前面，否则会出错
            val childReader = BufferedReader(FileReader(childFile))
            val childsb = StringBuilder()
            var childs = ""
            while (childReader.readLine().also { childs = it } != null) {
                childsb.append(childs.trim { it <= ' ' })
            }
            val childpomstr = childsb.toString()

            //重childpom中获取parent
            val childRegex = "<parent>.*?</parent>"
            val childPattern = Pattern.compile(childRegex)
            val childM = childPattern.matcher(childpomstr)
            var childParentStr: String? = ""
            if (!childM.find()) {
                return false
            } else {
                childParentStr = childM.group()
            }


            //获取groupid
            val groupidregex = "<groupId>.*?</groupId>"
            val groupidpattern = Pattern.compile(groupidregex)
            val groupidM = groupidpattern.matcher(childParentStr)
            var groupid = ""
            if (groupidM.find()) {
                groupid = groupidM.group().replace("</?groupId>".toRegex(), "")
            } else {
                return false
            }
            //获取version
            val versionregex = "<version>.*?</version>"
            val versionpattern = Pattern.compile(versionregex)
            val versionM = versionpattern.matcher(childParentStr)
            var version = ""
            if (versionM.find()) {
                //version=versionM.group().replaceAll("</?version>","");
                version = versionM.group()
            } else {
                return false
            }

            //创建新模块和新模块的pom文件
            val projectlabel = "<project\n" +
                    "        xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\"\n" +
                    "        xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
            val modelbabel =
                "<modelVersion>4.0.0</modelVersion>\n<artifactId>jacocomodule</artifactId>\n<name>jacocomodule</name>\n$version\n<dependencies>\n"
            val coveragestr = StringBuilder("")
            coveragestr.append(projectlabel)
            coveragestr.append(childParentStr)
            coveragestr.append(modelbabel)


            // 获取工程的所有module
            val moduleregex = "<modules>.*?</modules>"
            val modulepattern = Pattern.compile(moduleregex)
            val moduleM = modulepattern.matcher(pomstr)
            var modules = ""
            if (moduleM.find()) {
                modules = moduleM.group()
                modules = modules.replace("</?modules?>".toRegex(), ",")
                val module = modules.split(",").toTypedArray()
                for (i in module.indices) {
                    if (module.get(i) != "") {
                        coveragestr.append(
                            "<dependency>\n<groupId>" +
                                    groupid + "</groupId>\n<artifactId>" + module[i] + "</artifactId>\n</dependency>\n"
                        )
                    }
                }
            } else {
                return false
            }
            coveragestr.append("</dependencies>\n")
            coveragestr.append(
                ("<build>\n" +
                        "        <plugins>\n" +
                        "            <plugin>\n" +
                        "                <groupId>org.jacoco</groupId>\n" +
                        "                <artifactId>jacoco-maven-plugin</artifactId>\n" +
                        "                <version>1.0.1-SNAPSHOT</version>\n" +
                        "                <executions>\n" +
                        "                    <execution>\n" +
                        "                        <id>report-aggregate</id>\n" +
                        "                        <phase>compile</phase>\n" +
                        "                        <goals>\n" +
                        "                            <goal>report-aggregate</goal>\n" +
                        "                        </goals>\n" +
                        "                    </execution>\n" +
                        "                </executions>\n" +
                        "            </plugin>\n" +
                        "        </plugins>\n" +
                        "    </build>\n")
            )
            coveragestr.append("</project>")

            //加入<module>jacocomodule</module>到parentpom文件
            pomstrfinal = pomstrfinal.replace("<modules>", "<modules>\n<module>jacocomodule</module>")
            writer = FileWriter(parentFile)
            writer.write(pomstrfinal)
            writer.flush()
            //end
            val coveragemodule = File("$projectPath/jacocomodule")
            if (!coveragemodule.exists()) {
                coveragemodule.mkdir()
                val coveragepomFile = File("$projectPath/jacocomodule/pom.xml")
                coveragepomFile.setWritable(true, false)
                if (!coveragepomFile.exists()) {
                    coveragepomFile.createNewFile()
                    cwriter = FileWriter(coveragepomFile)
                    cwriter.write(coveragestr.toString())
                    cwriter.flush()
                    return true
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                writer?.close()
                cwriter?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }

    fun deleteFile(file: File): Boolean {
        if (!file.exists()) {
            return false
        }
        if (file.isFile) {
            return file.delete()
        } else {
            val files = file.listFiles()
            for (f: File in files) {
                deleteFile(f)
            }
            return file.delete()
        }
    }
}