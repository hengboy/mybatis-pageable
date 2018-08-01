package com.gitee.hengboy.mybatis.pageable.dialect.support;

import com.gitee.hengboy.mybatis.pageable.PageParameterSortMapping;
import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;

import java.util.ArrayList;
import java.util.List;

/**
 * Postgres数据库方言
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：4:26 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class PostgresDialect extends AbstractDialect {

    /**
     * 设置postgres数据库排序后的分页参数
     * 对应com.gitee.hengboy.mybatis.pageable.dialect.support.PostgresDialect#getPageSql(org.apache.ibatis.mapping.BoundSql, Page)
     * 获取的分页sql的占位符索引
     *
     * @return 获取排序后的分页参数映射列表
     */
    public List<PageParameterSortMapping> getSortParameterMapping() {
        return new ArrayList(){
            {
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_SIZE).typeClass(Integer.class).build());
                add(PageParameterSortMapping.builder().parameterName(PARAM_PAGE_OFFSET).typeClass(Long.class).build());
            }
        };
    }
}
