package com.free.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * IP地址 Utils
 *
 * @author hsiangcn@sina.com
 *
 */
public class IpAddrUtils {

	/**
	 *  获取客户端真实IP地址
     *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
	      String ip = request.getHeader("x-forwarded-for");

	      if(StringUtils.isEmpty(ip )|| "unknown".equalsIgnoreCase(ip)) {
	         ip = request.getHeader("Proxy-Client-IP");
	     }
	      if(StringUtils.isEmpty(ip )|| "unknown".equalsIgnoreCase(ip)) {
	         ip = request.getHeader("WL-Proxy-Client-IP");
	      }
	     if(StringUtils.isEmpty(ip )|| "unknown".equalsIgnoreCase(ip)) {
	          ip = request.getRemoteAddr();
	     }
	     return ip;
	}

    /**
     * 获取服务器IP地址
     * @return
     */
    public static String  getServerIp(){
        String SERVER_IP = null;
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                ip = (InetAddress) ni.getInetAddresses().nextElement();
                SERVER_IP = ip.getHostAddress();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {
                    SERVER_IP = ip.getHostAddress();
                    break;
                } else {
                    ip = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SERVER_IP;
    }

    /**
     * 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
     * @param strIp String类型的IP地址
     * @return
     */
    public static long ipToLong(String strIp){
        long[] ip = new long[4];
        //先找到IP地址字符串中.的位置
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        //将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1+1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2+1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3+1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
    
    /**
     * 将十进制整数形式转换成127.0.0.1形式的ip地址
     * @param longIp 长整形的IP地址
     * @return
     */
    public static String longToIP(long longIp){
        StringBuffer sb = new StringBuffer("");
        //直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        //将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        //将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        //将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }
    
    public static void main(String[] args){
        String ipStr = "172.16.3.249";
        long longIp = IpAddrUtils.ipToLong(ipStr);
        System.out.println("IP type long ：" + longIp);
        System.out.println("IP type long" + longIp + "转化成 IP type String："
                + IpAddrUtils.longToIP(longIp));
        //ip地址转化成二进制形式输出
        System.out.println("二进制形式为：" + Long.toBinaryString(longIp));
        String a = "110115041500000002130706433";
        System.out.println(a.length());
    }
}
