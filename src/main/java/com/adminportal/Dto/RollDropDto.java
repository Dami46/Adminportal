package com.adminportal.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class RollDropDto {

    private Integer dropId;

    public Integer getDropId() {
        return dropId;
    }

    public void setDropId(Integer dropId) {
        this.dropId = dropId;
    }
}
