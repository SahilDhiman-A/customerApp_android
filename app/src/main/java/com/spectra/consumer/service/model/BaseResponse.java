package com.spectra.consumer.service.model;

public class BaseResponse<T> {
    T data;
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
