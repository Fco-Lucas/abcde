package com.lcsz.abcde;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String baseImagesUrl;
    private String baseImagesPath;
    private String apiPythonUrl;

    // getters e setters
    public String getBaseImagesUrl() {
        return baseImagesUrl;
    }
    public void setBaseImagesUrl(String baseImagesUrl) {
        this.baseImagesUrl = baseImagesUrl;
    }

    public String getBaseImagesPath() {
        return baseImagesPath;
    }
    public void setBaseImagesPath(String baseImagesPath) {
        this.baseImagesPath = baseImagesPath;
    }

    public String getApiPythonUrl() {
        return apiPythonUrl;
    }
    public void setApiPythonUrl(String apiPythonUrl) {
        this.apiPythonUrl = apiPythonUrl;
    }
}

