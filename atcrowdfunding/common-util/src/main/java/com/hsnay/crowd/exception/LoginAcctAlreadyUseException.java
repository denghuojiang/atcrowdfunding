package com.hsnay.crowd.exception;

/**
 * 保存或者更新Admin acct时候如果监测到acct重复所抛的异常
 */
public class LoginAcctAlreadyUseException extends RuntimeException{
    static final long serialVersionUID = 1L;

    public LoginAcctAlreadyUseException() {
    }

    public LoginAcctAlreadyUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
