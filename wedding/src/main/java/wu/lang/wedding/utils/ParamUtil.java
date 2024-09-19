package wu.lang.wedding.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

public class ParamUtil<T> {

    public static String md5(Object... param) {
        StringBuilder sb = new StringBuilder();
        for (Object o : param) {
            sb.append(o);
        }
        return Md5Util.md5(sb.toString());
    }

    /**
     * 数据类型转换集合
     *
     * @param objList 需要转换的数据
     * @param tClass  转换的类型
     * @return
     */
    public static <T> List<T> conversionDataList(List<Object> objList, Class<T> tClass) {
        if (CollectionUtils.isEmpty(objList)) {
            return Collections.EMPTY_LIST;
        }
        return JSONObject.parseArray(objList.toString(), tClass);
    }

    /**
     * 数据类型转换单个
     *
     * @param obj    需要转换的数据
     * @param tClass 转换的类型
     */
    public static <T> T conversionData(Object obj, Class<T> tClass) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(obj), tClass);
    }
}
