package com.visionet.letsdesk.app.dictionary.vo;

import com.visionet.letsdesk.app.base.vo.BaseVo;
import com.visionet.letsdesk.app.dictionary.entity.Category;
import com.visionet.letsdesk.app.dictionary.entity.Manufacturer;

public class BrandVo extends BaseVo{

    private String name;
    private Manufacturer manufacturer;            //厂商
    private Integer type;                   //BRAND_TYPE_*
    private Long parentId;                  //上级品牌
    private Category categoryMain;			//品类（一级）
    private Category categoryFunction;		//品类（二级）功能
    private Category categoryMaterial;		//品类（二级）材质
    private Category categoryStyle;			//品类（二级）风格
    private Category categoryImport;		//品类（二级）是否进口

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Category getCategoryMain() {
        return categoryMain;
    }

    public void setCategoryMain(Category categoryMain) {
        this.categoryMain = categoryMain;
    }

    public Category getCategoryFunction() {
        return categoryFunction;
    }

    public void setCategoryFunction(Category categoryFunction) {
        this.categoryFunction = categoryFunction;
    }

    public Category getCategoryMaterial() {
        return categoryMaterial;
    }

    public void setCategoryMaterial(Category categoryMaterial) {
        this.categoryMaterial = categoryMaterial;
    }

    public Category getCategoryStyle() {
        return categoryStyle;
    }

    public void setCategoryStyle(Category categoryStyle) {
        this.categoryStyle = categoryStyle;
    }

    public Category getCategoryImport() {
        return categoryImport;
    }

    public void setCategoryImport(Category categoryImport) {
        this.categoryImport = categoryImport;
    }
}
