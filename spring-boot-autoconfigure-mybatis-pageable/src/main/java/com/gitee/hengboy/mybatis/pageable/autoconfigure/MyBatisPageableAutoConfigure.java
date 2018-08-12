package com.gitee.hengboy.mybatis.pageable.autoconfigure;

import com.gitee.hengboy.mybatis.pageable.config.PageableConfigurer;
import com.gitee.hengboy.mybatis.pageable.interceptor.MyBatisExecutePageableInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * mybatis-pageable自动配置类
 *
 * @author：于起宇 <p>
 * ===============================
 * Created with IDEA.
 * Date：2018/8/4
 * Time：2:19 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 * </p>
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(MyBatisPageableProperties.class)
@ConditionalOnClass(DataSourceAutoConfiguration.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MyBatisPageableAutoConfigure {
    /**
     * 自动化分页配置类
     */
    private MyBatisPageableProperties myBatisPageableProperties;
    /**
     * mybatis内的sqlSessionFactory注入的实例列表
     */
    private List<SqlSessionFactory> sqlSessionFactoryList;
    /**
     * 自动化分页配置信息
     */
    private PageableConfigurer pageableConfigurer;

    public MyBatisPageableAutoConfigure(MyBatisPageableProperties myBatisPageableProperties,
                                        ObjectProvider<List<SqlSessionFactory>> interceptorsProvider,
                                        ObjectProvider<PageableConfigurer> pageableConfigurerObjectProvider) {
        this.myBatisPageableProperties = myBatisPageableProperties;
        this.sqlSessionFactoryList = interceptorsProvider.getIfAvailable();
        this.pageableConfigurer = pageableConfigurerObjectProvider.getIfAvailable();
    }

    /**
     * 实例化自动化分页拦截器
     */
    @PostConstruct
    void addInterceptors() {
        Interceptor interceptor = new MyBatisExecutePageableInterceptor();
        // 设置配置信息
        interceptor.setProperties(myBatisPageableProperties.getProperties());

        // 遍历sqlSessionFactory内的配置信息并添加自动化分页插件到拦截器内
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            // 添加pageable执行前的插件
            addPreInterceptors(sqlSessionFactory);
            // 添加pageable执行插件
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
            // 添加pageable执行后的插件
            addPostInterceptors(sqlSessionFactory);
        }
    }

    /**
     * 添加分页插件执行前的插件列表
     *
     * @param sqlSessionFactory sqlSessionFactory对象实例
     */
    void addPreInterceptors(SqlSessionFactory sqlSessionFactory) {
        if (allowPageableConfigurer() && pageableConfigurer.prePlugins() != null) {
            loopAddInterceptor(pageableConfigurer.prePlugins(), sqlSessionFactory);
        }
    }

    /**
     * 添加分页插件执行后的插件列表
     *
     * @param sqlSessionFactory sqlSessionFactory对象实例
     */
    void addPostInterceptors(SqlSessionFactory sqlSessionFactory) {
        if (allowPageableConfigurer() && pageableConfigurer.postPlugins() != null) {
            loopAddInterceptor(pageableConfigurer.postPlugins(), sqlSessionFactory);
        }
    }

    /**
     * 循环添加插件到mybatis
     *
     * @param interceptors      插件列表
     * @param sqlSessionFactory sqlSessionFactory对象实例
     */
    void loopAddInterceptor(List<Interceptor> interceptors, SqlSessionFactory sqlSessionFactory) {
        for (Interceptor interceptor : interceptors) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }

    /**
     * 判断是否存在自动化分页的配置实例
     *
     * @return
     */
    boolean allowPageableConfigurer() {
        return pageableConfigurer != null;
    }
}
