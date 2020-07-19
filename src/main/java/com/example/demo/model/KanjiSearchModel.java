package com.example.demo.model;

import javax.validation.constraints.NotBlank;

public class KanjiSearchModel {
    @NotBlank
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
