package com.AlTaraf.Booking.response.keys;

import java.util.List;

public class ErrorResponsePayload {
    private String key;
    private String arabicMessage;
    private String englishMessage;
    private String fixAr;
    private String fixEn;

    public ErrorResponsePayload(String key, String arabicMessage, String englishMessage, String fixAr, String fixEn) {
        this.key = key;
        this.arabicMessage = arabicMessage;
        this.englishMessage = englishMessage;
        this.fixAr = fixAr;
        this.fixEn = fixEn;
    }

    public ErrorResponsePayload(String key, String arabicMessage, String englishMessage) {
        this.key = key;
        this.arabicMessage = arabicMessage;
        this.englishMessage = englishMessage;
    }

    public ErrorResponsePayload() {
    }

    public ErrorResponsePayload(List<String> errorCodes) {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getArabicMessage() {
        return arabicMessage;
    }

    public void setArabicMessage(String arabicMessage) {
        this.arabicMessage = arabicMessage;
    }

    public String getEnglishMessage() {
        return englishMessage;
    }

    public void setEnglishMessage(String englishMessage) {
        this.englishMessage = englishMessage;
    }

    public String getFixAr() {
        return fixAr;
    }

    public void setFixAr(String fixAr) {
        this.fixAr = fixAr;
    }

    public String getFixEn() {
        return fixEn;
    }

    public void setFixEn(String fixEn) {
        this.fixEn = fixEn;
    }
}
