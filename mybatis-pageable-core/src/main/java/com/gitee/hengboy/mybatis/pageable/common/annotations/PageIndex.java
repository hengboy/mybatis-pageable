package com.gitee.hengboy.mybatis.pageable.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页参数：当前页码
 *
 * @author：于起宇 <p>
 * ================================
 * Created with IDEA.
 * Date：2018/8/20
 * Time：4:04 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 * </p>
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageIndex {
}
