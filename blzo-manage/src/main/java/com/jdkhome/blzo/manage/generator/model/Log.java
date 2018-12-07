package com.jdkhome.blzo.manage.generator.model;

import java.util.Date;

public class Log {
    private Integer id;

    private Integer adminId;

    private String adminName;

    private String authjUri;

    private String authjName;

    private String ip;

    private Date createTime;

    private String paramers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getAuthjUri() {
        return authjUri;
    }

    public void setAuthjUri(String authjUri) {
        this.authjUri = authjUri == null ? null : authjUri.trim();
    }

    public String getAuthjName() {
        return authjName;
    }

    public void setAuthjName(String authjName) {
        this.authjName = authjName == null ? null : authjName.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParamers() {
        return paramers;
    }

    public void setParamers(String paramers) {
        this.paramers = paramers == null ? null : paramers.trim();
    }
}