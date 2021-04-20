package com.virgo.spring.security.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author HZI.HUI
 * @version 1.0
 * @since 2021/4/18
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthTokenController {


    private final RestTemplate restTemplate;


    @GetMapping("/callback")
    public String callback(@RequestParam String code, String state){

        log.info(" callback state is {}", state);

        HttpHeaders httpHeaders  = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth("baidu","baidu");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("code",code);
        params.add("grant_type","authorization_code");
        params.add("redirect_uri","http://localhost:3000/oauth/callback");

        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(params,httpHeaders);


        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:1000/oauth/token", HttpMethod.POST, httpEntity, String.class);
        String body = exchange.getBody();
        log.info("授权结果信息：{}" ,body);
        return body;

    }
}
