package com.gitee.hengboy.mybatis.pageable.config;

import org.apache.ibatis.plugin.Interceptor;

import java.util.List;

/**
 * mybatis-pageable配置接口
 * 实现该接口可以完成部分的内置配置
 *
 * @author：于起宇 <p>
 * ===============================
 * Created with IDEA.
 * Date：2018/8/8
 * Time：1:42 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 * </p>
 */
public interface PageableConfigurer {
    /**
     * 获取mybatis-pageable执行前执行的Plugin插件列表
     *
     * @return 插件列表
     */
    List<Interceptor> prePlugins();

    /**
     * 获取mybatis-pageable执行后执行的Plugin插件列表
     *
     * @return 插件列表
     */
    List<Interceptor> postPlugins();
}
