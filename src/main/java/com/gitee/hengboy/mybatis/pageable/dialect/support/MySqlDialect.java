package com.gitee.hengboy.mybatis.pageable.dialect.support;

import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.PageParameterSortMapping;
import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;

/**
 * MySQL数据库方言实现
 *
 * @author：于起宇 <p>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：3:37 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 * </p>
 */
public class MySqlDialect extends AbstractDialect {
    /**
     * 分页关键字
     */
    private static final String PAGE_KEYWORD = " LIMIT ";

    /**
     * 设置mysql数据库排序后的分页参数
     * 对应com.gitee.hengboy.mybatis.pageable.dialect.support.MySqlDialect#getPageSql(org.apache.ibatis.mapping.BoundSql, com.gitee.hengboy.mybatis.pageable.Page)
     * 获取的分页sql的占位符索引
     *
     * @return
     */
    @Override
    public List<PageParameterSortMapping> getSortParameterMapping() {
        return new ArrayList(){
            {
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_OFFSET).typeClass(Long.class).build());
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_SIZE).typeClass(Long.class).build());
            }
        };
    }

    /**
     * 获取MySQL数据库的分页sql
     *
     * @param boundSql boundSql 对象
     * @return 分页sql
     */
    @Override
    public String getPageSql(BoundSql boundSql, Page page) {
        StringBuffer sql = new StringBuffer();
        sql.append(boundSql.getSql());
        sql.append(PAGE_KEYWORD);
        sql.append(PRE_PLACEHOLDER);
        sql.append(SPLIT);
        sql.append(PRE_PLACEHOLDER);
        return sql.toString();
    }
}
