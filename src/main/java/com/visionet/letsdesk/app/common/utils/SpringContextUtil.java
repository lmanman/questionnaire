package com.visionet.letsdesk.app.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * SpringContent Tools class
 * 注意：该方式获取Dao资源，不会自动关闭DB session ！
 * 
 */
@Service("SpringContextUtil")
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

    private static void setStaticAppContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }
	

	/**
	 * Implement call-back method of ApplicationContextAware interface, and set application context
	 * 
	 * @param applicationContext
	 * @throws org.springframework.beans.BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
        setStaticAppContext(applicationContext);
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Get object
	 * 注意：该方式获取Dao资源，不会自动关闭DB session ！
	 *
	 * @param name
	 * @return Object Bean object
	 * @throws org.springframework.beans.BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * Get object in requiredType,
	 * throw BeanNotOfRequiredTypeException if bean type cannot been converted
	 *
	 * @param name
	 *            bean name
	 * @param requiredType
	 *            Object type
	 * @return Object Return object in requiredType
	 * @throws org.springframework.beans.BeansException
	 */
	public static Object getBean(String name, Class requiredType)
			throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * Return true if there is a matched bean definition with given name in BeanFactory
	 *
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * Check bean definition registered with a given name is a singleton or prototype,
	 * throw NoSuchBeanDefinitionException if no bean defination found
	 *
	 * @param name
	 * @return boolean
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class Registered object type
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static Class getType(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * Return aliases if bean definition contains aliases
	 *
	 * @param name
	 * @return
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
}