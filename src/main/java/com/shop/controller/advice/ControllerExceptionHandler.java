package com.shop.controller.advice;

import com.shop.controller.admin.CategoryController;
import com.shop.controller.admin.GoodController;
import com.shop.controller.admin.GoodTypeController;
import com.shop.controller.admin.ManufacturerController;
import com.shop.exception.InvalidDataException;
import com.shop.exception.RecordExistsException;
import com.shop.service.request.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(assignableTypes = {CategoryController.class, GoodTypeController.class, GoodController.class, ManufacturerController.class})
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    private UrlService urlService;

    @Autowired
    public void setUrlService(UrlService urlService) {
        this.urlService = urlService;
    }

    @ExceptionHandler({InvalidDataException.class, RecordExistsException.class})
    public ModelAndView handleFillingError(HttpServletRequest request, Exception ex, RedirectAttributes redirectAttributes) {
        logger.info("Handle exception during filling fields. Exception type: {}, message - [{}].", ex.getClass().getTypeName(), ex.getMessage());
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView(new RedirectView(urlService.getUrlPath(request), true));
    }
}