package com.gitee.hengboy.mybatis.pageable.common.enums;

import com.gitee.hengboy.mybatis.pageable.dialect.Dialect;
import com.gitee.hengboy.mybatis.pageable.dialect.support.*;
import lombok.Getter;

/**
 * 数据库方言枚举
 * 该枚举用具DialectDynamicFactory工厂使用
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：3:40 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Getter
public enum DialectEnum {
    MYSQL(MySqlDialect.class),
    DB2(Db2Dialect.class),
    HSQL(HSqlDialect.class),
    ORACLE(OracleDialect.class),
    POSTGRES(PostgresDialect.class),
    SQLSERVER2000(SqlServer2000Dialect.class),
    SQLSERVER(SqlServerDialect.class),
    INfORMIX(InforMixDialect.class);

    DialectEnum(Class<? extends Dialect> value) {
        this.value = value;
    }

    private Class<? extends Dialect> value;
}
