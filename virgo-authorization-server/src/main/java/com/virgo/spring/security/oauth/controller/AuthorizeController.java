package com.virgo.spring.security.oauth.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author HZI.HUI
 * @since 2021/4/20
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizeController {

    private final ClientDetailsService clientDetailsService;


    @GetMapping("/confirm")
    public ModelAndView confirmAccess(HttpServletRequest request,ModelAndView modelAndView){
        Map<String,Object> scopes = (Map<String,Object>)request.getAttribute("scopes");

        HttpSession session = request.getSession();

        AuthorizationRequest authorizationRequest = (AuthorizationRequest) session.getAttribute("authorizationRequest");
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());

        modelAndView.addObject("app", clientDetails.getAdditionalInformation());
        modelAndView.addObject("scopes", scopes.keySet());
        modelAndView.setViewName("authorize");
        return modelAndView;
    }


}
