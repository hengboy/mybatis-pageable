package com.gitee.hengboy.mybatis.pageable.config;

import org.apache.ibatis.plugin.Interceptor;

import java.util.List;

/**
 * 自动分页配置抽象类
 * 由于支持jdk1.8以下的版本，所以这里不能采用default新特性
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/8/8
 * Time：3:18 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class AbstractPageableConfigurer implements PageableConfigurer {
    @Override
    public List<Interceptor> prePlugins() {
        return null;
    }

    @Override
    public List<Interceptor> postPlugins() {
        return null;
    }
}
