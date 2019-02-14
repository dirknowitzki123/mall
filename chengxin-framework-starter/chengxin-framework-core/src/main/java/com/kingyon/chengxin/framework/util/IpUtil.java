package com.kingyon.chengxin.framework.util;


import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * ip 分析，IP匹配许可或限制
 * Created by Administrator on 16-11-17.
 */
public class IpUtil {

    class IpSub {
        /** -1 代表*号 */
        int from = -1;
        /** 当>=0时为ip段范围，-1 无范围 */
        int to = -1;
    }

    class IpPattern {
        ArrayList<IpSub> ip = new ArrayList<>();
        void addIpSub(IpSub sub) {
            ip.add(sub);
        }
    }

    private Set<IpPattern> allows = new HashSet<>();
    private Set<IpPattern> denys = new HashSet<>();

    public void addAllow(String ip) {
        String[] ips = ip.split(",");
        for (String cur : ips) {
            allows.add(parseIp(cur));
        }
    }

    public void addDeny(String ip) {
        String[] ips = ip.split(",");
        for (String cur : ips) {
            denys.add(parseIp(cur));
        }
    }

    private IpPattern parseIp(String ip) {
        int idx = ip.indexOf(":");
        if (idx > 0)
            ip = ip.substring(0, idx);
        String[] ipsubs = ip.split( "\\.");

        IpPattern ipsub = new IpPattern();
        String tmp;
        for(String cur : ipsubs) {
            IpSub sub = new IpSub();
            if (cur.contains("-")) {
                String[] from_to = cur.split("-");
                int length = from_to.length;
                if (length < 2)
                    throw new IllegalArgumentException("无效ip段");
                tmp = from_to[0].trim();
                if (StringUtils.isEmpty( tmp ) )
                    sub.from = 0;
                else
                    sub.from = Integer.parseInt(tmp);
                tmp = from_to[1].trim();
                if (StringUtils.isEmpty(tmp ) )
                    sub.to = 255;
                else
                    sub.to = Integer.parseInt(tmp);
            } else {
                tmp = cur.trim();
                if (!StringUtils.isEmpty( tmp ) && !"*".equals(tmp )) {
                    sub.from = Integer.parseInt(tmp);
                }
            }
            ipsub.addIpSub(sub );
        }
        return ipsub;
    }

    public boolean isAllowed(String ip) {
        return checkIn(ip, this.allows);
    }

    public boolean isDenied(String ip) {
        return checkIn(ip, this.denys);
    }

    private boolean checkIn(String ip, Set<IpPattern> pattern) {
        IpPattern cur_ip = parseIp(ip);
        for (IpPattern cur : pattern) {
            if (checkIn(cur_ip, cur)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIn(IpPattern cur_ip, IpPattern pattern) {
        IpSub curSub, curPattern;

        for (int i = 0; i < cur_ip.ip.size() && i < pattern.ip.size(); i++) {
            curSub = cur_ip.ip.get(i);
            curPattern = pattern.ip.get(i);
            if (curPattern.from == -1) { //  *号
                continue;
            } else {
                if (curPattern.to != -1) {
                    if (curSub.from < curPattern.from || curSub.from > curPattern.to) {
                        return false;
                    }
                } else {
                    if (curSub.from != curPattern.from ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

//    /**
//     * 校验所给IP是否满足给定规则
//     *
//     * @author li_hongzhe @nhn.com
//     * @date 2014年10月23日 下午1:38:37
//     * @param ipStr
//     *            待校验IP
//     * @param ipPattern
//     *            IP匹配规则。支持 * 匹配所有和 - 匹配范围。用分号分隔 &lt;br&gt;
//     *            例如：10.34.163.*,10.34.162.1 -128
//     * @return
//     */
//    public static boolean validateIP(String ipStr, String ipPattern) {
//        if ( ipStr == null || ipPattern == null) {
//            return false;
//        }
//        String[] patternList = ipPattern.split( ",");
//        for (String pattern : patternList) {
//            if ( passValidate(ipStr, pattern)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static boolean passValidate(String ipStr, String pattern) {
//        String[] ipStrArr = ipStr.split( "\\.");
//        String[] patternArr = pattern.split( "\\.");
////        if ( ipStrArr. length != 4 || patternArr. length != 4) {
////            return false;
////        }
//        int end = ipStrArr. length;
//        int patternLength = patternArr. length;
//        if (patternLength == 4 && patternArr[3].contains( "-")) {
//            end = 3;
//            String[] rangeArr = patternArr[3].split( "-");
//            int from = Integer.valueOf(rangeArr[0]).intValue();
//            int to = Integer.valueOf(rangeArr[1]).intValue();
//            int value = Integer.valueOf(ipStrArr[3]).intValue();
//            if ( value < from || value > to) {
//                return false;
//            }
//        }
//        for ( int i = 0; i < end; i++) {
//            if ( patternArr[ i].equals( "*")) {
//                continue;
//            }
//            if (!patternArr[ i].equalsIgnoreCase( ipStrArr[ i])) {
//                return false;
//            }
//        }
//        return true;
//    }
}
