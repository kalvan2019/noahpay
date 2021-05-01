package com.kalvan.pay.sdk.wxpay.api.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * IP工具类
 */
public final class IpUtils {

    /**
     * 获得系统属性集
     */
    private static final Properties props = System.getProperties();


    /**
     * 根据系统的类型获取本服务器的ip地址
     * InetAddress inet = InetAddress.getLocalHost();<br>
     * 但是上述代码在Linux下返回127.0.0.1。<br>
     * 主要是在linux下返回的是/etc/hosts中配置的localhost的ip地址，<br>
     * 而不是网卡的绑定地址。后来改用网卡的绑定地址，可以取到本机的ip地址
     *
     * @throws UnknownHostException
     */
    public static InetAddress getSystemLocalIp() throws UnknownHostException {
        InetAddress inet = null;
        String osname = getSystemOSName();
        try {
            //针对window系统
            if (osname.equalsIgnoreCase("Windows XP")) {
                inet = getWinLocalIp();
                //针对linux系统
            } else if (osname.equalsIgnoreCase("Linux")) {
                inet = getUnixLocalIp();
            }
            if (inet == null) {
                throw new UnknownHostException("主机的ip地址未知");
            }
        } catch (SocketException e) {
            throw new UnknownHostException("获取本机ip错误" + e.getMessage());
        }
        return inet;
    }

    /**
     * 获取操作系统名称
     *
     * @return
     */
    public static String getSystemOSName() {
        //操作系统名称
        String osname = props.getProperty("os.name");
        return osname;
    }

    /**
     * 获取属性的值
     *
     * @param propertyName
     * @return
     */
    public static String getPropertery(String propertyName) {
        return props.getProperty(propertyName);
    }

    /**
     * 获取window本地ip地址
     *
     * @return 本地ip地址
     * @throws UnknownHostException
     */
    public static InetAddress getWinLocalIp() throws UnknownHostException {
        InetAddress inet = InetAddress.getLocalHost();
        return inet;
    }

    /**
     * 可能很多个ip地址只获取一个ip地址
     * 获取Linux 本地IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private static InetAddress getUnixLocalIp() throws SocketException {
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            ip = ni.getInetAddresses().nextElement();
            if (!ip.isSiteLocalAddress()
                    && !ip.isLoopbackAddress()
                    && ip.getHostAddress().indexOf(":") == -1) {
                break;
            } else {
                ip = null;
            }
        }
        return ip;
    }

    /**
     * 获取当前运行程序的内存信息
     *
     * @return
     */
    public static final String getRAMInfo() {
        Runtime rt = Runtime.getRuntime();
        return "RAM: " + rt.totalMemory() + " bytes total, " + rt.freeMemory() + " bytes free.";
    }

    /**
     * 获取客户端IP地址
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。<br>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (!StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");

        }
        if (!StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (!StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
