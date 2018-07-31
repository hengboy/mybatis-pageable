package com.gitee.hengboy.mybatis.pageable.dialect.support;

import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.PageParameterSortMapping;
import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;

/**
 * Postgres数据库方言
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：4:26 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class PostgresDialect extends AbstractDialect {

    /**
     * 分页关键字：limit
     */
    private static final String PAGE_KEYWORD_LIMIT = " LIMIT ";
    /**
     * 分页关键字：offset
     */
    private static final String PAGE_KEYWORD_OFFSET = " OFFSET ";

    /**
     * 设置postgres数据库排序后的分页参数
     * 对应com.gitee.hengboy.mybatis.pageable.dialect.support.PostgresDialect#getPageSql(org.apache.ibatis.mapping.BoundSql, com.gitee.hengboy.mybatis.pageable.Page)
     * 获取的分页sql的占位符索引
     *
     * @return
     */
    @Override
    public List<PageParameterSortMapping> getSortParameterMapping() {
        return new ArrayList(){
            {
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_SIZE).typeClass(Integer.class).build());
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_OFFSET).typeClass(Long.class).build());
            }
        };
    }

    /**
     * 获取postgres数据库分页sql
     *
     * @param boundSql boundSql 对象
     * @param page     分页响应对象实例
     * @return
     */
    @Override
    public String getPageSql(BoundSql boundSql, Page page) {
        StringBuilder sql = new StringBuilder();
        sql.append(boundSql.getSql());
        sql.append(PAGE_KEYWORD_LIMIT);
        sql.append(PRE_PLACEHOLDER);
        sql.append(PAGE_KEYWORD_OFFSET);
        sql.append(PRE_PLACEHOLDER);
        return sql.toString();
    }
}
