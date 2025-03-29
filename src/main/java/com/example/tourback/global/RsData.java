package com.example.tourback.global;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsData<T> {

    private String resultCode;
    private String message;
    private T data;

    public static <T> RsData<T> of(String resultCode, String message, T data) {
        return new RsData<>(resultCode, message, data);
    }

    public static <T> RsData<T> of(String resultCode, String message) {
        return RsData.of(resultCode, message, null);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }
}
