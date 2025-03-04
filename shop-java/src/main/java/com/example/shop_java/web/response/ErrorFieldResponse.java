package com.example.shop_java.web.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ErrorFieldResponse {

    private Map<String, String> errors;

}
