package com.gitee.hengboy.mybatis.pageable.autoconfigure;

import com.gitee.hengboy.mybatis.pageable.common.enums.DialectEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.Properties;

import static com.gitee.hengboy.mybatis.pageable.autoconfigure.MyBatisPageableProperties.PREFIX_MYBATIS_PAGEABLE;

/**
 * mybatis-pageable自动化配置属性
 *
 * @author：于起宇 ===============================
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
     * 数据库方言
     * 默认使用mysql数据库方言
     */
    private DialectEnum dialect = DialectEnum.MYSQL;

    /**
     * 获取属性配置
     *
     * @return 配置文件对象
     */
    public Properties getProperties() {

        // 返回的配置对象
        Properties properties = new Properties();
        /*
         * 获取本类内创建的field列表
         * 添加到配置对象集合内
         */
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                // 数据库方言
                if ("dialect".equals(field.getName())) {
                    properties.setProperty(field.getName(), dialect.getValue().getName());
                } else {
                    properties.setProperty(field.getName(), String.valueOf(field.get(this)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return properties;
    }
}
