package com.gitee.hengboy.mybatis.pageable.dialect;

import com.gitee.hengboy.mybatis.pageable.Page;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 分页参数：当前页码参数名称
     */
    protected static final String PARAM_PAGE_OFFSET = "pageable_page_offset_";
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
        pageParameter.put(PARAM_PAGE_OFFSET, page.getOffset());
        pageParameter.put(PARAM_PAGE_SIZE, page.getPageSize());
        return pageParameter;
    }

    /**
     * 添加分页参数映射
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

            newParameterMappings.add(new ParameterMapping.Builder(statement.getConfiguration(), PARAM_PAGE_OFFSET, Long.class).build());
            newParameterMappings.add(new ParameterMapping.Builder(statement.getConfiguration(), PARAM_PAGE_SIZE, Integer.class).build());

            // 仅仅更新dataBoundSql的参数映射
            MetaObject metaObject = SystemMetaObject.forObject(pageBoundSql);
            metaObject.setValue("parameterMappings", newParameterMappings);
        }
    }
}
