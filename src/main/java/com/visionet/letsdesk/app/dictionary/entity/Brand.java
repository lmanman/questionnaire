package com.visionet.letsdesk.app.dictionary.entity;

import com.visionet.letsdesk.app.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 品牌
 *
 * @author xt
 */
@Entity
@Table(name = "d_brand")
public class Brand extends IdEntity{

    private String name;
    private Long manufacturerId;            //厂商
    private Integer type;                   //BRAND_TYPE_*
    private Long parentId;                  //上级品牌
    private Integer categoryMainId;			//品类（一级）
    private Integer categoryFunctionId;		//品类（二级）功能
    private Integer categoryMaterialId;		//品类（二级）材质
    private Integer categoryStyleId;		//品类（二级）风格
    private Integer categoryImportId;		//品类（二级）是否进口

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
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

    public Integer getCategoryMainId() {
        return categoryMainId;
    }

    public void setCategoryMainId(Integer categoryMainId) {
        this.categoryMainId = categoryMainId;
    }

    public Integer getCategoryFunctionId() {
        return categoryFunctionId;
    }

    public void setCategoryFunctionId(Integer categoryFunctionId) {
        this.categoryFunctionId = categoryFunctionId;
    }

    public Integer getCategoryMaterialId() {
        return categoryMaterialId;
    }

    public void setCategoryMaterialId(Integer categoryMaterialId) {
        this.categoryMaterialId = categoryMaterialId;
    }

    public Integer getCategoryStyleId() {
        return categoryStyleId;
    }

    public void setCategoryStyleId(Integer categoryStyleId) {
        this.categoryStyleId = categoryStyleId;
    }

    public Integer getCategoryImportId() {
        return categoryImportId;
    }

    public void setCategoryImportId(Integer categoryImportId) {
        this.categoryImportId = categoryImportId;
    }
}
