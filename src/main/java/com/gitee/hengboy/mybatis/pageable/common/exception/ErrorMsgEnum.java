package com.gitee.hengboy.mybatis.pageable.common.exception;

import lombok.Getter;

/**
 * 错误信息枚举
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：11:03 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Getter
public enum ErrorMsgEnum {
    PAGE_INDEX_FAILED("分页参数：当前页码参数传递错误，请传递大于0的正整数."),
    PAGE_SIZE_FAILED("分页参数：每页条数参数传递错误，请传递大于0的正整数."),
    DIALECT_NOT_FOUND("数据库方言暂未支持.");

    ErrorMsgEnum(String message) {
        this.message = message;
    }

    private String message;
}
