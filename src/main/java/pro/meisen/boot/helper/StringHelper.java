package pro.meisen.boot.helper;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.meisen.boot.core.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author meisen
 * 2019-07-14
 */
@Component
public class StringHelper {

    @Autowired
    private SplitterHelper splitterHelper;

    /**
     * 去除逗号分隔的字符串中的指定字符
     * @param origin 原始字符串
     * @param str 指定字符串
     * @return 处理后的字符串
     */
    public String wipeSpecifyStr(String origin, String str) {
        if (Strings.isEmpty(origin) || Strings.isEmpty(str)) {
            return origin;
        }
        List<String> originList = splitterHelper.splitToStringList(origin, AppConstants.COMMON_SPLIT);
        List<String> resultList = new ArrayList<>();
        for (String string : originList) {
            if (!str.equals(string)) {
                resultList.add(string);

            }
        }
        return String.join(AppConstants.COMMON_SPLIT, resultList);
    }

    /**
     * 判断字符串是不是数字
     * @param str 字符串
     * @return true 是  false 不是
     */
    public boolean isNumeric(String str) {
        if (Strings.isEmpty(str)) {
            return false;
        }
        char[] chr = str.toCharArray();
        for (char ch : chr) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }
}
