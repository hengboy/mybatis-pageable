package com.gitee.hengboy.mybatis.pageable.common;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * mybatis MappedStatement对象处理工具类
 *
 * @author：于起宇 <p>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/30
 * Time：4:19 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 * </p>
 */
public final class MappedStatementHelper {
    /**
     * 查询数量类型mappedStatement的后缀名称
     */
    private static final String COUNT_STATEMENT_SUFFIX = "_PAGEABLE_COUNT";
    /**
     * 缓存查询总数的mappedStatement
     * key => statement Id
     * value => mappedStatement
     */
    private static final Hashtable<String, MappedStatement> COUNT_STATEMENT_CACHE = new Hashtable();

    /**
     * 初始化或者从缓存内获取Count MappedStatement对象实例
     *
     * @param statement 原始mappedStatement对象实例
     * @return 查询数量mappedStatement对象
     */
    public static MappedStatement initOrGetCountStatement(MappedStatement statement) {
        String statementId = statement.getId() + COUNT_STATEMENT_SUFFIX;
        MappedStatement countStatement = COUNT_STATEMENT_CACHE.get(statementId);
        if (countStatement != null) {
            return countStatement;
        }
        // 缓存中并未获取到,重新初始化
        countStatement = initCountStatement(statement);
        // 写入缓存
        COUNT_STATEMENT_CACHE.put(countStatement.getId(), countStatement);
        return countStatement;
    }

    /**
     * 初始化查询总数量
     *
     * @param statement 未处理的查询MappedStatement对象实例
     * @return 查询数量mappedStatement对象实例
     */
    public static MappedStatement initCountStatement(MappedStatement statement) {
        // mappedStatement builder 构造函数初始化builder对象
        MappedStatement.Builder builder = new MappedStatement
                .Builder(statement.getConfiguration(), statement.getId() + COUNT_STATEMENT_SUFFIX, statement.getSqlSource(), statement.getSqlCommandType());
        /*
         * 以下的配置使用未处理查询的MappedStatement配置信息
         */
        builder.parameterMap(statement.getParameterMap());
        builder.statementType(statement.getStatementType());
        builder.resource(statement.getResource());
        builder.resultSetType(statement.getResultSetType());
        builder.flushCacheRequired(statement.isFlushCacheRequired());
        builder.useCache(statement.isUseCache());

        // 设置返回映射类型为Long类型
        final ResultMap resultMap = new ResultMap.Builder(statement.getConfiguration(), statement.getId(), Long.class, new ArrayList()).build();
        builder.resultMaps(new ArrayList() {
            {
                add(resultMap);
            }
        });
        return builder.build();
    }
}
