package com.gitee.hengboy.mybatis.pageable.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 注解工具类
 *
 * @author：于起宇 <p>
 * ================================
 * Created with IDEA.
 * Date：2018/8/20
 * Time：4:07 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 * </p>
 */
public class AnnotationHelper {
    /**
     * 获取枚举字段的值
     *
     * @param object          需要提取注解对应字段的对象实例
     * @param annotationClass 注解类型
     * @return 枚举字段的值
     */
    public static Object getValue(Object object, Class<? extends Annotation> annotationClass) {
        try {
            List<Field> fields = ReflectionHelper.getAllFields(object.getClass());
            if (null != fields) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(annotationClass)) {
                        field.setAccessible(true);
                        return field.get(object);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取Int类型枚举字段的值
     *
     * @param object          需要提取注解对应字段的对象实例
     * @param annotationClass 注解类型
     * @return 枚举字段的值
     */
    public static Integer getIntValue(Object object, Class<? extends Annotation> annotationClass) {
        Object value = getValue(object, annotationClass);
        return null == value ? 0 : Integer.valueOf(String.valueOf(value));
    }
}
