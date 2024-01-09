package com.brude.springboot.calendar.interceptor.springboothorario.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("calendar")
public class calendarInterceptor implements HandlerInterceptor{

    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println(hour);
        if(hour >= open && hour < close) {
            StringBuilder message = new StringBuilder("Bienvenidos al horario de atencion");
            message.append("atendemos desde "); 
            message.append(open);
            message.append(" hasta"); 
            message.append(close); 
            request.setAttribute("message", message.toString());
            return true;
        }else{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<String, Object>();
            StringBuilder message = new StringBuilder("fuera del horario");
            message.append("por favor visitar desde las ");
            message.append(open);
            data.put("message", message.toString());
            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().write(mapper.writeValueAsString(data));
         
            return false;
        }
    }

    
}
