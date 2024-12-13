package com.test.t1.domain.model.dto;

import lombok.Data;

@Data
public class VersionDTO {
    private String app_code;
    private String app_name;
    private String version;
    private String versionDescription;

    public VersionDTO(String appCode, String appName, String version, String versionDescription) {
        this.app_code = appCode;
        this.app_name = appName;
        this.version = version;
        this.versionDescription = versionDescription;
    } 
}