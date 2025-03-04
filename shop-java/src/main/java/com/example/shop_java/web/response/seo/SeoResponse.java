package com.example.shop_java.web.response.seo;

import lombok.Data;

@Data
public class SeoResponse {

    private Long id;

    private String uri;

    private String title;

    private String description;

    private String keywords;

}
