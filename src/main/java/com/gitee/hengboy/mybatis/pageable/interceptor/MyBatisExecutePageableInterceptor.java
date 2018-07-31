package com.gitee.hengboy.mybatis.pageable.interceptor;

import com.gitee.hengboy.mybatis.pageable.DefaultPage;
import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.common.ExecutorHelper;
import com.gitee.hengboy.mybatis.pageable.common.MappedStatementHelper;
import com.gitee.hengboy.mybatis.pageable.common.PageableRequestHelper;
import com.gitee.hengboy.mybatis.pageable.dialect.Dialect;
import com.gitee.hengboy.mybatis.pageable.dialect.DialectDynamicFactory;
import com.gitee.hengboy.mybatis.pageable.executor.ExecutorQueryRequest;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 封装mybatis的自动化分页拦截器插件
 * 拦截org.apache.ibatis.executor.Executor接口的两个query方法来完成自动化分页
 *
 * @author：于起宇 <p>
 * Created with IDEA.
 * Date：2018/7/25
 * Time：7:55 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * </p>
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class MyBatisExecutePageableInterceptor implements Interceptor {
    /**
     * 项目配置数据库方言的类名全限定名
     */
    private String dialect;

    /**
     * 拦截器方法
     *
     * @param invocation 拦截器拦截方法封装对象
     * @return 拦截的目标方法执行后的返回值
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 默认行绑定不执行分页
        if (PageableRequestHelper.getPageLocal() == null) {
            return invocation.proceed();
        }

        // 获取Executor对象query方法的参数列表
        final Object[] args = invocation.getArgs();
        // MappedStatement对象实例
        MappedStatement statement = (MappedStatement) args[0];

        // 本次拦截的执行器对象
        Executor executor = (Executor) invocation.getTarget();

        // 获取threadLocal内保存的分页响应对象
        DefaultPage pageable = (DefaultPage) PageableRequestHelper.getPageLocal();

        /*
         * 数据库方言
         * 根据不同的数据库方言生成分页sql、查询总数量sql、参数映射、参数重装等操作
         */
        Dialect dialect = DialectDynamicFactory.newInstance(statement, this.dialect);

        // 构建请求对象
        ExecutorQueryRequest queryRequest = buildExecutorQueryRequest(args);

        // 执行分页查询 & 设置到分页响应对象
        pageable.setData(executeQuery(executor, queryRequest, pageable, dialect));

        // 查询总数量 & 设置到分页响应对象
        pageable.setTotalElements(executeCount(executor, queryRequest, dialect));

        // 分页查询后的列表数据
        return pageable.getData();

    }

    /**
     * 构建执行器查询时的请求对象
     *
     * @param args 本次拦截Executor.query方法的参数数组
     * @return 执行器查询请求对象
     */
    private ExecutorQueryRequest buildExecutorQueryRequest(Object[] args) {
        // MappedStatement对象实例
        MappedStatement statement = (MappedStatement) args[0];
        // 执行查询时携带的参数实体或者参数Map集合
        Object parameter = args[1];
        // 行绑定信息
        RowBounds rowBounds = RowBounds.DEFAULT;
        // 返回结果处理对象
        ResultHandler resultHandler = (ResultHandler) args[3];
        // boundSql
        BoundSql boundSql = statement.getBoundSql(parameter);

        return ExecutorQueryRequest.builder()
                .statement(statement)
                .parameter(parameter)
                .rowBounds(rowBounds)
                .resultHandler(resultHandler)
                .boundSql(boundSql)
                .build();
    }

    /**
     * 获取总数量
     *
     * @param executor 执行器
     * @param request  执行器所需的请求对象
     * @param dialect
     * @return
     * @throws SQLException
     */
    private Long executeCount(Executor executor, ExecutorQueryRequest request, Dialect dialect) throws SQLException {
        /*
         *  获取查询总数量的MappedStatement实例
         *  会先从缓存内获取，缓存不存在时创建后写入缓存并返回
         */
        MappedStatement statement = MappedStatementHelper.initOrGetCountStatement(request.getStatement());
        // 替换分页查询数据的mappedStatement
        request.setStatement(statement);

        // 原始查询的boundSql
        BoundSql boundSql = request.getBoundSql();

        // 根据不同数据库方言获取统计sql
        String countSql = dialect.getCountSql(boundSql);
        Object parameter = request.getParameter();

        // 创建查询总数量的boundSql对象实例
        BoundSql countBoundSql = new BoundSql(statement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
        // 替换分页查询数据的BoundSql
        request.setBoundSql(countBoundSql);

        // 创建缓存key
        CacheKey countKey = executor.createCacheKey(statement, parameter, RowBounds.DEFAULT, countBoundSql);
        // 替换分页查询数据的缓存key
        request.setCacheKey(countKey);

        // 执行查询count
        Object countResultList = ExecutorHelper.query(executor, request);
        return ((Number) ((List) countResultList).get(0)).longValue();
    }

    /**
     * 获取分页后的数据
     *
     * @param executor 执行器
     * @param request  执行器所需的请求对象
     * @param pageable 分页相应对象
     * @param dialect  数据库方言
     * @return 数据列表
     * @throws SQLException
     */
    private List executeQuery(Executor executor, ExecutorQueryRequest request, Page pageable, Dialect dialect) throws SQLException {
        /*
         * 获取执行分页查询时的参数
         */
        MappedStatement statement = request.getStatement();
        BoundSql boundSql = request.getBoundSql();

        // 根据不同的数据库方言获取分页sql
        String dataSql = dialect.getPageSql(request.getBoundSql(), pageable);
        /*
         * 根据不同数据库方言获取分页所需的参数集合
         * 处理后的参数包含未处理之前的参数列表
         */
        Object pageParameter = dialect.getPageParameter(request.getParameter(), pageable);
        // 替换请求参数，原parameter为未添加分页信息的数据列表
        request.setParameter(pageParameter);

        // 实例化分页的绑定sql对象
        BoundSql pageBoundSql = new BoundSql(statement.getConfiguration(), dataSql, boundSql.getParameterMappings(), pageParameter);

        /*
         * 根据不同的数据库方言设置不同的参数映射
         * 处理后包含未处理之前的参数映射列表
         */
        dialect.addPageMapping(pageBoundSql, statement, pageable);

        // 替换原boundSql对象
        request.setBoundSql(pageBoundSql);
        /*
         * 创建缓存key
         * 并且将缓存key设置到本次执行对象内
         */
        CacheKey dataKey = executor.createCacheKey(statement, pageParameter, request.getRowBounds(), pageBoundSql);
        request.setCacheKey(dataKey);

        // 执行查询
        return ExecutorHelper.query(executor, request);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 设置传递的数据库方言
        this.dialect = properties.getProperty("dialect");
    }
}