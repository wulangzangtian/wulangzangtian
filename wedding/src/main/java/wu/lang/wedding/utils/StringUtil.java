package wu.lang.wedding.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class StringUtil {

    /**
     * ip正则表达式
     */
    private static final String IP_REGEX = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\."
            + "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    static char[] CHARS = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'};

    public static boolean isIp(String address) {
        Pattern pat = Pattern.compile(IP_REGEX);
        Matcher mat = pat.matcher(address);
        return mat.find();
    }


    /**
     * ip转小端字节序
     *
     * @param ipAddress
     * @return
     */
    public static long getIpConvertLittleEndian(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);
        long ipNum = d * 256 * 256 * 256 + c * 256 * 256 + b * 256 + a;
        return ipNum;
    }

    /**
     * ip转大端字节序
     *
     * @param ipAddress
     * @return
     */
    public static long getIpConvertBigEndian(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);
        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
        return ipNum;
    }

    public static String str2URLEncoder(String src) {
        String result = "";
        if (null == src) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(src, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String URLDecoder2Str(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String genCode(long id, int codeLength) {
        int CHARS_LENGTH = 34;
        long SLAT = 1234561L;
        int PRIME1 = 3;
        int PRIME2 = 11;
        // 补位
        id = id * PRIME1 + SLAT;
        long[] b = new long[codeLength];
        b[0] = id;
        for (int i = 0; i < codeLength - 1; i++) {
            b[i + 1] = b[i] / CHARS_LENGTH;
            b[i] = (b[i] + i * b[0]) % CHARS_LENGTH;
        }
        long tmp = 0;
        for (int i = 0; i < b.length - 2; i++) {
            tmp += b[i];
        }
        b[b.length - 1] = tmp * PRIME1 % CHARS_LENGTH;
        // 进行混淆
        long[] codeIndexArray = new long[codeLength];
        for (int i = 0; i < codeLength; i++) {
            codeIndexArray[i] = b[i * PRIME2 % codeLength];
        }
        StringBuilder buffer = new StringBuilder();
        Arrays.stream(codeIndexArray).boxed().map(Long::intValue).map(t -> CHARS[t]).forEach(buffer::append);
        return buffer.toString();
    }


    public static void main(String[] args) {
        Map<String, Integer> temp = new HashMap<>();
        int now = TimeUtil.getNowOfSeconds();
        long time = now - 725328000L;
        long day100 = TimeUtil.timeParse("2100-09-30 16:22:51",TimeUtil.DEFAULT_FORMAT);
        String timeFormat = TimeUtil.timeFormat(time * 1000L, TimeUtil.DEFAULT_FORMAT);
        System.out.println("timeFormat=" + timeFormat + ",time=" + time + ",now=" + now+",day100="+day100+",long="+Long.MAX_VALUE);
        for (int i = 0; i < 10000; i++) {
            long tempIndex = time * 10000L + i;
            String code = genCode(tempIndex, 6);
            System.out.println("tempIndex=" + tempIndex + ",code=" + code);
            int count = temp.getOrDefault(code, 0);
            count++;
            if (count > 1) {
                System.out.println("+++++++++++++++重复" + code + "++++++++++");
            }
            temp.put(code, count);
        }
        System.out.println("=========success,size=" + temp.size() + "==============");
    }
}
