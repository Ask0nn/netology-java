package com.ask0n.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class Operation {
    @NotNull
    private String operationId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = 4)
    private String code;

    public Operation() {}
    public Operation(String operationId) {
        this.operationId = operationId;
    }
    public Operation(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }
    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
