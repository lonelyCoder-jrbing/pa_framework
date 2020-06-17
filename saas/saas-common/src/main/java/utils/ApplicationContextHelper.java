package utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:  spring context 辅助工具
 **/
@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) throws Exception {
        T instance = null;
        try {
            instance = applicationContext.getBean(beanClass);
        } catch (Exception e) {

        }
        if (Objects.isNull(instance)) {
            String simpleName = beanClass.getSimpleName();
            simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            instance = (T) applicationContext.getBean(simpleName);
        }
        if (Objects.isNull(instance)) {
            throw new Exception("bean  " + beanClass + " can not be found,check bean in spring context");
        }
        return instance;
    }
}
