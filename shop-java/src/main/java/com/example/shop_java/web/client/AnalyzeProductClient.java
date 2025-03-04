package com.example.shop_java.web.client;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AnalyzeProductClient {

    private final OkHttpClient client;

    @Value("${app.traffic-service}")
    private String serviceUrl;

    public String getAnalyzeProduct() throws IOException {
        Request request = new Request.Builder()
                .url(serviceUrl + "/api/v1/traffic")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
