package com.gitee.hengboy.mybatis.pageable.dialect.support;

import com.gitee.hengboy.mybatis.pageable.PageParameterSortMapping;
import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;

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
     * 设置mysql数据库排序后的分页参数
     * 对应com.gitee.hengboy.mybatis.pageable.dialect.support.MySqlDialect#getPageSql(org.apache.ibatis.mapping.BoundSql, Page)
     * 获取的分页sql的占位符索引
     *
     * @return 获取排序后的参数映射列表
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
