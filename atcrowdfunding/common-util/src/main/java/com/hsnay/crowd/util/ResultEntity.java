package com.hsnay.crowd.util;

/**
 * 统一整个项目的ajax返回结果 （未来也可以分布式架构各个模块之间调用返回统一类型）
 *
 * @param <T>
 */
public class ResultEntity<T> {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";
    //结果成功还是失败
    private String result;
    //失败时的错误消息
    private String massage;
    //要返回的数据
    private T data;

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", massage='" + massage + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultEntity(String result, String massage, T data) {
        this.result = result;
        this.massage = massage;
        this.data = data;
    }

    public ResultEntity() {
    }

    public static <Type> ResultEntity<Type> successWithoutData() {
        return new ResultEntity<Type>(SUCCESS, null, null);
    }

    public static <Type> ResultEntity<Type> successWithData(Type data) {
        return new ResultEntity<Type>(SUCCESS, null, data);
    }

    public static <Type> ResultEntity<Type> failed(String massage) {
        return new ResultEntity<Type>(FAILED, massage, null);
    }

}
