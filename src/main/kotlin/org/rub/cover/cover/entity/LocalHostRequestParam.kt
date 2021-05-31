package org.rub.cover.cover.entity

class LocalHostRequestParam {
    /**
     * dump jacoco.exec文件目标地址
     */
     var address: String? = null

    /**
     * dump jacoco.exec文件端口
     */
     var port: Int? = null

    /**
     * 子项目目录名称
     */
     var subModule: String? = null

    /**
     * class文件路径
     */
     var classFilePath: String? = null

    /**
     * base代码存储路径
     */
     var basePath: String? = null

    /**
     * 目标代码存储路径
     */
     var nowPath: String? = null
}