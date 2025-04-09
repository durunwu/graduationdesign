package com.liujian.gymxmjpa.util;

/**
 * @author durunwu
 * @date 2025-04-09-22:08
 */
public class CustomException extends RuntimeException {
    private int statusCode;

    public CustomException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
