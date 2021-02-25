package com.hsnay.crowd.exception;

/**
 * 保存或者更新Admin acct时候如果监测到acct重复所抛的异常
 */
public class LoginAcctAlreadyUseForUpdateException extends RuntimeException{
    static final long serialVersionUID = 1L;

    public LoginAcctAlreadyUseForUpdateException() {
    }

    public LoginAcctAlreadyUseForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyUseForUpdateException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
