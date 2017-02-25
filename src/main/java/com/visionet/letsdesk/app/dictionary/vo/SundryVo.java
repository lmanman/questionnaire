package com.visionet.letsdesk.app.dictionary.vo;

import java.io.Serializable;
import java.util.List;

public class SundryVo {

    public SundryVo(){
    }
    public SundryVo(Long id,String name){
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String code;
    private String name;
    private String type;
    private List<ChildData> childDataList;

    public static class ChildData implements Serializable {
        private Long id;
        private String name;
        private List<GrandchildData> grandchildDataList;

        public static class GrandchildData implements Serializable {
            private Long id;
            private String name;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<GrandchildData> getGrandchildDataList() {
            return grandchildDataList;
        }

        public void setGrandchildDataList(List<GrandchildData> grandchildDataList) {
            this.grandchildDataList = grandchildDataList;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ChildData> getChildDataList() {
        return childDataList;
    }

    public void setChildDataList(List<ChildData> childDataList) {
        this.childDataList = childDataList;
    }
}
