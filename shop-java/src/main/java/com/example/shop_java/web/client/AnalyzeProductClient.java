package com.example.shop_java.web.client;

import com.example.shop_java.entity.Analyze;
import com.example.shop_java.web.dto.AnalyzeDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AnalyzeProductClient {

    private final OkHttpClient client;

    @Value("${app.traffic-service}")
    private String serviceUrl;

    private final ObjectMapper objectMapper;

    public AnalyzeDto getAnalyzeProduct() throws IOException {
        Request request = new Request.Builder()
                .url(serviceUrl + "/api/v1/traffic")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return objectMapper.readValue(response.body().string(), AnalyzeDto.class);
        }
    }

}
