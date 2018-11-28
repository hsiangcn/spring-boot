package com.free.dao.model;

import java.io.Serializable;
import java.util.Date;

public class ServerLog implements Serializable {
    private Long id;

    private String userId;

    private String mobileImei;

    private String clientIp;

    private String serverIp;

    private String serverPost;

    private String requestUrl;

    private String requestMethod;

    private Long consumingTime;

    private Date createTime;

    private String requestParam;

    private String responseBody;

    private String executeStatus;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getMobileImei() {
        return mobileImei;
    }

    public void setMobileImei(String mobileImei) {
        this.mobileImei = mobileImei == null ? null : mobileImei.trim();
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public String getServerPost() {
        return serverPost;
    }

    public void setServerPost(String serverPost) {
        this.serverPost = serverPost == null ? null : serverPost.trim();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod == null ? null : requestMethod.trim();
    }

    public Long getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(Long consumingTime) {
        this.consumingTime = consumingTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam == null ? null : requestParam.trim();
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody == null ? null : responseBody.trim();
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus == null ? null : executeStatus.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", mobileImei=").append(mobileImei);
        sb.append(", clientIp=").append(clientIp);
        sb.append(", serverIp=").append(serverIp);
        sb.append(", serverPost=").append(serverPost);
        sb.append(", requestUrl=").append(requestUrl);
        sb.append(", requestMethod=").append(requestMethod);
        sb.append(", consumingTime=").append(consumingTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", requestParam=").append(requestParam);
        sb.append(", responseBody=").append(responseBody);
        sb.append(", executeStatus=").append(executeStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}