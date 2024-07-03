package com.proj.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.proj.demo.client.ApiClient;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ApiClient apiClient;
    private URI baseUrl;

    public void setBaseUrl(String url) {
        baseUrl = URI.create(url);
    }
    public Long getLongValue(String str) {
        if (isAlphanumeric(str)) {
            String value = apiClient.getLongValue(baseUrl, str);
            return Long.parseLong(value);
        }
        else {
            return null;
        }
    }

    public List<String> getStringList(String str) {
        if (isAlphanumeric(str)) {
            return apiClient.getStringList(baseUrl, str);
        }
        else {
            return null;
        }
    }
    private static boolean isAlphanumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false; // Если символ не буква и не цифра, то строка не alphanumeric
            }
        }
        return true; // Если все символы - буквы или цифры, то строка alphanumeric
    }
}

