/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * purpose of the class
 *
 * @author Truong
 */
@Component
public class CDCApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        Environment environment = applicationContext.getEnvironment();
        String className = environment.getProperty(tClass.getSimpleName());
        T bean = null;
        try {
            bean = (T) Class.forName(className).getDeclaredConstructor().newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (bean == null){
            bean = applicationContext.getBean(tClass);
        }
        return bean;
    }
}
