package fun.baozi.boot.helper;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author baozi
 * 2019-07-13
 */
@Component
public class SplitterHelper {

    /**
     * 将字符串通过${splitter}分割成字符串集合
     * @param str 原始字符串
     * @param splitter 分隔符
     * @return 分割后的list
     */
    public List<String> splitToStringList(String str, String splitter) {
        // splitter 可以为 ""
        if (Strings.isEmpty(str) || null == splitter) {
            return new ArrayList<>();
        }
        String[] strings = str.split(splitter);
        // 去除空格
        return Arrays.stream(strings).map(String::trim).filter(Strings::isNotEmpty).collect(Collectors.toList());
    }

    /**
     * 将字符串通过${splitter}分割成Long类型的集合
     * @param str 原始字符串
     * @param splitter 分隔符
     * @return 分割后的list
     */
    public List<Long> splitToLongList(String str, String splitter) {
        // splitter 可以为 ""
        if (Strings.isEmpty(str) || null == splitter) {
            return new ArrayList<>();
        }
        String[] strings = str.split(splitter);
        List<Long> result = new ArrayList<>();
        for (String string : strings) {
            if (isDigit(string)) {
                result.add(Long.valueOf(string));
            }
        }
        return result;
    }

    /**
     * 判断是否是数字
     * @param num 字符串
     * @return true: 是 false 不是
     */
    private boolean isDigit(String num) {
        if (Strings.isEmpty(num)) {
            return false;
        }
        for (int i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
