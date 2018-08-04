package com.gitee.hengboy.mybatis.pageable.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.gitee.hengboy.mybatis.pageable.autoconfigure.MyBatisPageableProperties.PREFIX_MYBATIS_PAGEABLE;

/**
 * mybatis-pageable自动化配置属性
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/8/4
 * Time：2:22 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PREFIX_MYBATIS_PAGEABLE)
public class MyBatisPageableProperties {
    /**
     * 自动化配置的前缀
     */
    public static final String PREFIX_MYBATIS_PAGEABLE = "hengboy.pageable";
    /**
     * 是否启用自动化分页
     */
    private boolean enable;
    /**
     * 数据库方言
     * 默认使用mysql数据库方言
     */
    private String dialect = "com.gitee.hengboy.mybatis.pageable.dialect.support.MySqlDialect";
}
