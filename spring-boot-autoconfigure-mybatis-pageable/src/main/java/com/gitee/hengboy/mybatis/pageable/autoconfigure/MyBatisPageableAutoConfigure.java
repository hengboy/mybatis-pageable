package com.gitee.hengboy.mybatis.pageable.autoconfigure;

import com.gitee.hengboy.mybatis.pageable.interceptor.MyBatisExecutePageableInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

import static com.gitee.hengboy.mybatis.pageable.autoconfigure.MyBatisPageableProperties.PREFIX_MYBATIS_PAGEABLE;

/**
 * mybatis-pageable自动配置类
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/8/4
 * Time：2:19 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(MyBatisPageableProperties.class)
public class MyBatisPageableAutoConfigure {
    /**
     * 自动化分页配置类
     */
    MyBatisPageableProperties myBatisPageableProperties;

    public MyBatisPageableAutoConfigure(MyBatisPageableProperties myBatisPageableProperties) {
        this.myBatisPageableProperties = myBatisPageableProperties;
    }

    /**
     * 实例化自动化分页拦截器
     * 仅在配置hengboy.pageable.enable=true时才会实例化该拦截器
     *
     * @return 自动化插件拦截器
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX_MYBATIS_PAGEABLE, name = "enable", havingValue = "true")
    Interceptor pageableInterceptor() {
        Interceptor interceptor = new MyBatisExecutePageableInterceptor();
        Properties properties = new Properties();
        properties.setProperty("dialect", myBatisPageableProperties.getDialect());
        interceptor.setProperties(properties);
        return interceptor;
    }
}
