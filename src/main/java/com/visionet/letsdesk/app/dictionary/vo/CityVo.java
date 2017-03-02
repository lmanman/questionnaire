package com.visionet.letsdesk.app.dictionary.vo;

public class CityVo {

    private Long id;
    private String cityName;
    private String telephoneAreaCode;
    private Long provinceId;
    private String provinceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTelephoneAreaCode() {
        return telephoneAreaCode;
    }

    public void setTelephoneAreaCode(String telephoneAreaCode) {
        this.telephoneAreaCode = telephoneAreaCode;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
