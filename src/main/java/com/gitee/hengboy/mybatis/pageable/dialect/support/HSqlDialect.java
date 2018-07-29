package com.gitee.hengboy.mybatis.pageable.dialect.support;

import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

/**
 * hsql数据库方言
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：4:27 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class HSqlDialect extends AbstractDialect {
    @Override
    public String getPageSql(BoundSql boundSql) {
        return null;
    }
}
