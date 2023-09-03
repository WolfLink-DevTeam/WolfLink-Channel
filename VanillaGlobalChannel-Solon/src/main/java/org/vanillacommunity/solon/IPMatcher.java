package org.vanillacommunity.solon;

public class IPMatcher {
    public static boolean isIpMatch(String ip1,String ip2) {
        String[] ip1Segments = ip1.split("\\.");
        String[] ip2Segments = ip2.split("\\.");
        if(ip1Segments.length != ip2Segments.length) return false;
        for (int i = 0; i < ip1Segments.length; i++) {
            String arg1 = ip1Segments[i];
            String arg2 = ip2Segments[i];
            if(arg1.equals("*"))continue;
            if(arg2.equals("*"))continue;
            if(!arg1.equals(arg2)) return false;
        }
        return true;
    }
}
