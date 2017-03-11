package cn.moo.trainingcollege.utils;

import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/2/28.
 */
public class MapUtil {
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        if(obj!=null) {
            try {
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
                for (int i = 0; i < descriptors.length; i++) {
                    String name = descriptors[i].getName();
                    if (!"class".equals(name)) {
                        Object attr = propertyUtilsBean.getNestedProperty(obj, name);

                        // 转换Timestamp
                        if(attr instanceof Timestamp) {
                            params.put(name, TimeUtil.timestampToDateString((Timestamp) attr));
                        } else {
                            params.put(name, attr);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }

    public static List<Map> beanListToMap(List list) {
        List<Map> mapList = new ArrayList<>();

        if(list!=null) {
            for (Object o : list) {
                mapList.add(beanToMap(o));
            }
        }
        return mapList;
    }
}
