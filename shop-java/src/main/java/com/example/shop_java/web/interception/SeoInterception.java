package com.example.shop_java.web.interception;

import com.example.shop_java.entity.Seo;
import com.example.shop_java.service.SeoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class SeoInterception implements HandlerInterceptor {

    private final SeoService seoService;

    public static final String SEO_TITLE = "Set-title";
    public static final String SEO_KEYWORD = "Set-keyword";
    public static final String SEO_DESCRIPTION = "Set-description";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Seo seo = this.seoService.findByUri(request.getRequestURI());

        if (seo != null) {
            response.setHeader(SEO_TITLE, seo.getTitle());
            response.setHeader(SEO_KEYWORD, seo.getKeywords());
            response.setHeader(SEO_DESCRIPTION, seo.getDescription());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
