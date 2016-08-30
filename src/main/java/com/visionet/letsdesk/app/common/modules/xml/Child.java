package com.visionet.letsdesk.app.common.modules.xml;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAttribute;

public class Child {
    private Long id;
    private String name;

    public Child() {
    }

    public Child(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
