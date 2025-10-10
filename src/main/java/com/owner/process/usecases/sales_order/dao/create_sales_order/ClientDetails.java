
package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientDetails {

    @JsonProperty("browser_ip")
    private String browserIp;
    @JsonProperty("accept_language")
    private String acceptLanguage;
    @JsonProperty("user_agent")
    private String userAgent;
    @JsonProperty("session_hash")
    private String sessionHash;
    @JsonProperty("browser_width")
    private String browserWidth;
    @JsonProperty("browser_height")
    private String browserHeight;

    public String getBrowserIp() {
        return browserIp;
    }

    public void setBrowserIp(String browserIp) {
        this.browserIp = browserIp;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSessionHash() {
        return sessionHash;
    }

    public void setSessionHash(String sessionHash) {
        this.sessionHash = sessionHash;
    }

    public String getBrowserWidth() {
        return browserWidth;
    }

    public void setBrowserWidth(String browserWidth) {
        this.browserWidth = browserWidth;
    }

    public String getBrowserHeight() {
        return browserHeight;
    }

    public void setBrowserHeight(String browserHeight) {
        this.browserHeight = browserHeight;
    }

}