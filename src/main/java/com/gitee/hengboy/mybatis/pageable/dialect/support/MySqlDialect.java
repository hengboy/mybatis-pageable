package com.gitee.hengboy.mybatis.pageable.dialect.support;

import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

/**
 * MySQL数据库方言实现
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：3:37 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class MySqlDialect extends AbstractDialect {
    /**
     * 获取MySQL数据库的分页sql
     *
     * @param boundSql boundSql 对象
     * @return 分页sql
     */
    @Override
    public String getPageSql(BoundSql boundSql) {

        return null;
    }
}
