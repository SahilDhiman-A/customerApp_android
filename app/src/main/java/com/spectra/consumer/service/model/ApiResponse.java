package com.spectra.consumer.service.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import static com.spectra.consumer.service.model.Status.ERROR;
import static com.spectra.consumer.service.model.Status.LOADING;
import static com.spectra.consumer.service.model.Status.SUCCESS;


public class ApiResponse {
    public final Status status;
    @Nullable
    public final Object data;
    @Nullable
    public final Throwable error;
    public final String code;
    private ApiResponse(Status status, @Nullable Object data, @Nullable Throwable error, String code) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.code=code;
    }
    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null,null);
    }

    public static ApiResponse success(@NonNull Object data, String code) {
        return new ApiResponse(SUCCESS, data, null,code);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error,null);
    }

}