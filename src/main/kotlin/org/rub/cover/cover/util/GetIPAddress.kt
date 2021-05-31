package org.rub.cover.cover.util

import java.net.NetworkInterface
import java.net.SocketException

object GetIPAddress {
    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    @get:Throws(SocketException::class)
    val linuxLocalIp: String
        get() {
            var ip = ""
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val name = intf.name
                    if (name.contains("docker") || name.contains("lo")) continue
                    val enumIpAddr = intf.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (inetAddress.isLoopbackAddress) continue
                        val ipaddress = inetAddress.hostAddress.toString()
                        if (ipaddress.contains("::") || ipaddress.contains("0:0:") || ipaddress.contains("fe80")) continue
                        ip = ipaddress
                    }
                }
            } catch (ex: SocketException) {
                ex.printStackTrace()
            }
            println("IP:$ip")
            return ip
        }


}