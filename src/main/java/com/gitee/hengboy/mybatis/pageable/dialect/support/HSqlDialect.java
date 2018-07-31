package com.gitee.hengboy.mybatis.pageable.dialect.support;

import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.PageParameterSortMapping;
import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;

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
    /**
     * 设置hsqldb数据库排序后的分页参数
     * 对应com.gitee.hengboy.mybatis.pageable.dialect.support.HSqlDialect#getPageSql(org.apache.ibatis.mapping.BoundSql, com.gitee.hengboy.mybatis.pageable.Page)
     * 获取的分页sql的占位符索引
     *
     * @return
     */
    @Override
    public List<PageParameterSortMapping> getSortParameterMapping() {
        return new ArrayList() {
            {
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_OFFSET).typeClass(Long.class).build());
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_SIZE).typeClass(Integer.class).build());
            }
        };
    }
    @Override
    public String getPageSql(BoundSql boundSql, Page page) {
        return null;
    }
}
