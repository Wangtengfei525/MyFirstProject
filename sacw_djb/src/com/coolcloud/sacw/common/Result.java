package com.coolcloud.sacw.common;

import java.util.HashMap;
import java.util.List;

/**
 * 请求返回结构
 * 
 * @author xyz
 *
 * @date 2018年4月7日 下午12:56:56
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = -2952819610608583742L;

    private static final String DEFAULT_KEY_ROWS = "rows";

    private static final String DEFAULT_KEY_TOTAL = "total";

    private Result(String message, boolean success) {
        this.put("message", message);
        this.put("success", success);
    }

    public static Result success() {
        return new Result("请求成功", true);
    }

    public static Result success(String message) {
        return new Result(message, true);
    }

    public static Result failed() {
        return new Result("请求失败", false);
    }

    public static Result failed(String message) {
        return new Result(message, false);
    }

    public Result add(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public Result rows(List<?> rows) {
        this.put(DEFAULT_KEY_ROWS, rows);
        return this;
    }

    public Result total(long total) {
        this.put(DEFAULT_KEY_TOTAL, total);
        return this;
    }

}
