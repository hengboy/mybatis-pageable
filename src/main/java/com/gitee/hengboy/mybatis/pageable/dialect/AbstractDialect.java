package com.gitee.hengboy.mybatis.pageable.dialect;

import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.PageParameterSortMapping;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.*;

/**
 * 数据库方言抽象实现类
 *
 * @author：于起宇 <p>
 * Created with IDEA.
 * Date：2018/7/29
 * Time：3:17 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * </p>
 */
public abstract class AbstractDialect implements Dialect {
    /**
     * 查询总数sql前缀
     */
    private static final String COUNT_SQL_PREFIX = "SELECT COUNT(0) FROM ( ";
    /**
     * 查询总数sql后缀
     */
    private static final String COUNT_SQL_SUFFIX = " ) TEMP_COUNT";
    /**
     * 预处理占位符
     */
    protected static final String PRE_PLACEHOLDER = "?";
    /**
     * 分隔符
     */
    protected static final String SPLIT = " , ";

    /**
     * 分页关键字：limit
     */
    private static final String PAGE_KEYWORD_LIMIT = " LIMIT ";
    /**
     * 分页关键字：offset
     */
    private static final String PAGE_KEYWORD_OFFSET = " OFFSET ";
    /**
     * 查询关键字：SELECT
     */
    protected static final String PAGE_KEYWORD_SELECT = "SELECT";

    /**
     * 分页参数：当前页码开始位置参数名称
     */
    protected static final String PARAM_PAGE_OFFSET = "pageable_page_offset_";
    /**
     * 分页参数：当前页码结束位置参数名
     */
    protected static final String PARAM_PAGE_END = "pageable_page_end_";
    /**
     * 分页参数：每页条数参数名称
     */
    protected static final String PARAM_PAGE_SIZE = "pageable_page_size_";

    /**
     * 获取查询总条数sql
     *
     * @param boundSql boundSql 对象
     * @return 查询总条数count sql
     */
    @Override
    public String getCountSql(BoundSql boundSql) {
        StringBuffer sql = new StringBuffer();
        sql.append(COUNT_SQL_PREFIX);
        sql.append(boundSql.getSql());
        sql.append(COUNT_SQL_SUFFIX);
        return sql.toString();
    }

    /**
     * 获取分页的参数列表
     *
     * @param parameter 分页查询时请求参数集合
     * @param page      分页响应对象实例
     * @return
     */
    @Override
    public Object getPageParameter(Object parameter, Page page) {
        Map<String, Object> pageParameter = new HashMap<>();
        // 如果参数为map类型
        if (parameter instanceof Map) {
            pageParameter.putAll((Map<? extends String, ?>) parameter);
        }
        // 非map参数类型
        else {
            MetaObject metaObject = SystemMetaObject.forObject(parameter);
            for (String name : metaObject.getGetterNames()) {
                pageParameter.put(name, metaObject.getValue(name));
            }
        }
        // 设置分页信息到参数集合
        pageParameter.put(PARAM_PAGE_SIZE, page.getPageSize());
        pageParameter.put(PARAM_PAGE_OFFSET, page.getOffset());
        pageParameter.put(PARAM_PAGE_END, page.getEndRow());
        return pageParameter;
    }

    /**
     * 添加分页参数映射
     * 注意：参数的顺序直接映射参数的绑定，绑定占位符时从parameterMappings集合list内第一个依次匹配
     * 如：sql : limit ?,?，parameterMapping：{offset = 0,limit = 5}
     * 最后匹配为 limit offset,limit，也就是limit 0,5
     *
     * @param pageBoundSql 分页的BoundSql实例
     * @param statement    MappedStatement对象实例
     * @param page         分页响应对象实例
     */
    @Override
    public void addPageMapping(BoundSql pageBoundSql, MappedStatement statement, Page page) {
        if (pageBoundSql.getParameterMappings() != null) {

            List<ParameterMapping> newParameterMappings = new ArrayList<ParameterMapping>();
            if (pageBoundSql != null && pageBoundSql.getParameterMappings() != null) {
                newParameterMappings.addAll(pageBoundSql.getParameterMappings());
            }

            // 获取不同数据库的排序后的分页参数映射
            List<PageParameterSortMapping> sortParameterMappings = getSortParameterMapping();
            for (PageParameterSortMapping mapping : sortParameterMappings) {
                newParameterMappings.add(new ParameterMapping.Builder(statement.getConfiguration(), mapping.getParameterName(), mapping.getTypeClass()).build());
            }
            // 仅仅更新pageBoundSql的参数映射
            MetaObject metaObject = SystemMetaObject.forObject(pageBoundSql);
            metaObject.setValue("parameterMappings", newParameterMappings);
        }
    }

    /**
     * 默认提供的获取分页的sql
     * 支持：MySQL、Postgres、HSQL数据库
     * 其他数据库重写该方法即可
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
