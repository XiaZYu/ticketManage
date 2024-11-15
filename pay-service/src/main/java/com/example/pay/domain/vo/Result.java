package com.example.pay.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Result<T> {
    private Integer code;
    private String message;
    private Object data;
    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T>Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }
    public static <T> Result<T> error(String message){
        return new Result<>(400, message, null);
    }
    public Result() {
    }

}
