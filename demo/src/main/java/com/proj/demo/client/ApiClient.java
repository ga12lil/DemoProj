package com.proj.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;

@FeignClient(value = "api", url = "api.com")
public interface ApiClient {

    @GetMapping("/long-value")
    String getLongValue(URI baseUrl, @RequestParam("alphanumericParam") String alphanumericParam);

    @GetMapping("/string-list")
    List<String> getStringList(URI baseUrl, @RequestHeader("alphanumericHeader") String alphanumericHeader);
}
