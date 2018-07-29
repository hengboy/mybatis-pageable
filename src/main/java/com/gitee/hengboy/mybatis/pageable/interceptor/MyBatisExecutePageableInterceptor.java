package com.gitee.hengboy.mybatis.pageable.interceptor;

import com.gitee.hengboy.mybatis.pageable.DefaultPage;
import com.gitee.hengboy.mybatis.pageable.common.PageableRequestHelper;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/25
 * Time：7:55 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class MyBatisExecutePageableInterceptor implements Interceptor {

    /**
     * MyBaits内部Xml节点解析的语言驱动
     */
    private static final XMLLanguageDriver languageDriver = new XMLLanguageDriver();
    private Field additionalParametersField;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        //System.out.println("参数：" + parameter);

        RowBounds rowBounds = (RowBounds) args[2];
        // 默认行绑定不执行分页
        if (PageableRequestHelper.PAGEABLE_THREAD_LOCAL.get() == null) {
            System.out.println("默认不执行分页");
            return invocation.proceed();
        }

        ResultHandler resultHandler = (ResultHandler) args[3];

        BoundSql boundSql = ms.getBoundSql(parameter);

        Configuration configuration = ms.getConfiguration();

        Executor executor = (Executor) invocation.getTarget();
        DefaultPage pageable = (DefaultPage) PageableRequestHelper.PAGEABLE_THREAD_LOCAL.get();


        CacheKey dataKey = executor.createCacheKey(ms, parameter, RowBounds.DEFAULT, boundSql);

        Map<String, Object> paramMap = new HashMap<>();

        if (parameter instanceof Map) {
            paramMap.putAll((Map<? extends String, ?>) parameter);
        } else {

        }
        // 设置分页参数
        paramMap.put("pageIndex", pageable.getOffset());
        paramMap.put("pageSize", pageable.getPageSize());

        Iterator<String> iterator = paramMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println("参数：" + key + "，值：" + paramMap.get(key));
        }

        // 处理分页参数
        BoundSql dataBoundSql = new BoundSql(configuration, boundSql.getSql() + " limit ?,?", boundSql.getParameterMappings(), paramMap);
        if (boundSql.getParameterMappings() != null) {

            System.out.println("存在参数映射列表");

            List<ParameterMapping> newParameterMappings = new ArrayList<ParameterMapping>();
            if (boundSql != null && boundSql.getParameterMappings() != null) {
                newParameterMappings.addAll(boundSql.getParameterMappings());
            }
            if (pageable.getOffset() == 0) {
                newParameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), "pageSize", Integer.class).build());
            } else {
                newParameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), "pageIndex", Long.class).build());
                newParameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), "pageSize", Integer.class).build());
            }

            // 仅仅更新dataBoundSql的参数映射
            MetaObject metaObject = SystemMetaObject.forObject(dataBoundSql);
            metaObject.setValue("parameterMappings", newParameterMappings);
        }
        System.out.println("data->>>" + dataBoundSql.getSql());
        List data = executor.query(ms, paramMap, rowBounds, resultHandler, dataKey, dataBoundSql);
        pageable.setData(data);



        // 查询总数量
        MappedStatement countStatement = createStatement(ms);
        CacheKey countKey = executor.createCacheKey(countStatement, parameter, RowBounds.DEFAULT, boundSql);
        BoundSql countBoundSql = new BoundSql(configuration, "select count(0) from (" + boundSql.getSql() + ") temp_", boundSql.getParameterMappings(), parameter);
        System.out.println(countBoundSql.getSql());
        Object countResultList = executor.query(countStatement, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        long count = ((Number) ((List) countResultList).get(0)).longValue();
        System.out.println(countStatement.getId() + "，查询总条数" + count);

        pageable.setTotalElements(count);
        System.out.println("分页开始位置：" + pageable.getOffset() + "，当前页码：" + pageable.getPageIndex() + " ，每页条数" + pageable.getPageSize());


        // 返回，继续执行
        return data;

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }


    public MappedStatement createStatement(MappedStatement statement) {
        MappedStatement.Builder builder = new MappedStatement.Builder(statement.getConfiguration(), statement.getId() + "_count", statement.getSqlSource(), statement.getSqlCommandType());
        // 参数集合
        builder.parameterMap(statement.getParameterMap());
        builder.statementType(statement.getStatementType());
        builder.resource(statement.getResource());
        builder.resultSetType(statement.getResultSetType());
        builder.flushCacheRequired(statement.isFlushCacheRequired());
        builder.useCache(statement.isUseCache());
        //count查询返回值int
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap resultMap = new ResultMap.Builder(statement.getConfiguration(), statement.getId(), Long.class, new ArrayList<ResultMapping>()).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        return builder.build();
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("配置信息" + properties);
        try {
            //反射获取 BoundSql 中的 additionalParameters 属性
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}