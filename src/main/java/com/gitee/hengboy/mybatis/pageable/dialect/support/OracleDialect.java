package com.gitee.hengboy.mybatis.pageable.dialect.support;

import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.PageParameterSortMapping;
import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;

/**
 * Oracle数据库方言
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：4:24 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class OracleDialect extends AbstractDialect {

    /**
     * 设置mysql数据库排序后的分页参数
     * 对应com.gitee.hengboy.mybatis.pageable.dialect.support.MySqlDialect#getPageSql(org.apache.ibatis.mapping.BoundSql, com.gitee.hengboy.mybatis.pageable.Page)
     * 获取的分页sql的占位符索引
     *
     * @return
     */
    @Override
    public List<PageParameterSortMapping> getSortParameterMapping() {
        return new ArrayList() {
            {
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_END).typeClass(Long.class).build());
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_OFFSET).typeClass(Long.class).build());
            }
        };
    }

    /**
     * 获取oracle数据库分页sql
     *
     * @param boundSql boundSql 对象
     * @param page     分页响应对象实例
     * @return
     */
    @Override
    public String getPageSql(BoundSql boundSql, Page page) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ( ");
        sql.append("SELECT PAGEABLE_.*,ROWNUM ROW_ID FROM ( ");
        sql.append(boundSql.getSql());
        sql.append(" ) PAGEABLE_ WHERE ROWNUM <= ? ");
        sql.append(" ) WHERE ROW_ID > ? ");
        return sql.toString();
    }
}
