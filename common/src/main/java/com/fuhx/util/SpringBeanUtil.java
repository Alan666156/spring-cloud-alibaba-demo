package com.fuhx.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 * 直接通过Spring 上下文获取SpringBean,用于多线程环境
 * @author fuhongxing
 */
@Component("springUtils")
@Lazy(false)
public class SpringBeanUtil implements DisposableBean, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * 在Bean生命周期结束前调用destory()方法做一些收尾工作
     */
    @Override
    public void destroy() {
        applicationContext = null;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        Assert.hasText(name, "must not be null");
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public static <T> T getBean(String name, Class<T> type) {
        Assert.hasText(name, "bean must not be null");
        Assert.notNull(type, "Bean type must not be null");
        return applicationContext.getBean(name, type);
    }

    public static String getMessage(String code, Object... args) {
        LocaleResolver resolver = (LocaleResolver) getBean("localeResolver", LocaleResolver.class);
        Locale locale = resolver.resolveLocale(null);
        return applicationContext.getMessage(code, args, locale);
    }

}