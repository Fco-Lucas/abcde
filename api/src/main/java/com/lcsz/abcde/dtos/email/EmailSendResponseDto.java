package com.lcsz.abcde.dtos.email;

import java.util.List;

public class EmailSendResponseDto {
    private String cnpj;
    private List<Long> successIds;
    private List<Long> failedIds;
    private Integer total;
    private Integer sentCount;

    public EmailSendResponseDto() {
    }

    public EmailSendResponseDto(String cnpj, List<Long> successIds, List<Long> failedIds, Integer total, Integer sentCount) {
        this.cnpj = cnpj;
        this.successIds = successIds;
        this.failedIds = failedIds;
        this.total = total;
        this.sentCount = sentCount;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Long> getSuccessIds() {
        return successIds;
    }

    public void setSuccessIds(List<Long> successIds) {
        this.successIds = successIds;
    }

    public List<Long> getFailedIds() {
        return failedIds;
    }

    public void setFailedIds(List<Long> failedIds) {
        this.failedIds = failedIds;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSentCount() {
        return sentCount;
    }

    public void setSentCount(Integer sentCount) {
        this.sentCount = sentCount;
    }

    @Override
    public String toString() {
        return "EmailSendResponseDto{" +
                "cnpj=" + cnpj +
                ", successIds=" + successIds +
                ", failedIds=" + failedIds +
                ", total=" + total +
                ", sentCount=" + sentCount +
                '}';
    }
}
