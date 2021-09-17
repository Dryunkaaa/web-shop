package com.shop.service.request;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Service
public class UrlService {

    public String getUrlPath(HttpServletRequest request) {
        boolean isFirstParameter = true;

        StringBuilder redirectUrl = new StringBuilder(request.getRequestURL().toString());

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameter = parameterNames.nextElement();

            if (isFirstParameter) {
                redirectUrl.append("?");
                isFirstParameter = false;
            } else {
                redirectUrl.append("&");
            }

            redirectUrl.append(parameter).append("=").append(request.getParameter(parameter));
        }

        return redirectUrl.toString();
    }
}
