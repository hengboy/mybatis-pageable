package com.gitee.hengboy.mybatis.pageable;

import com.gitee.hengboy.mybatis.pageable.dialect.AbstractDialect;
import lombok.Builder;
import lombok.Data;

/**
 * 分页参数排序映射实体
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/31
 * Time：8:21 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 * @see AbstractDialect#addPageMapping(org.apache.ibatis.mapping.BoundSql, org.apache.ibatis.mapping.MappedStatement, com.gitee.hengboy.mybatis.pageable.Page)
 */
@Data
@Builder
public class PageParameterSortMapping {
    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * 参数类型
     */
    private Class typeClass;
}
