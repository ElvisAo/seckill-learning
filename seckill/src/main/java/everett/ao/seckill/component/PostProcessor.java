package everett.ao.seckill.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class PostProcessor implements Ordered, BeanPostProcessor, BeanNameAware, BeanFactoryAware, DisposableBean, InitializingBean {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("bean factory: " + beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("bean name: " + name);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("bean destroy...");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before " + beanName + " initializing: " + bean.toString());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after " + beanName + " initializing: " + bean.toString());
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("properties set");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
