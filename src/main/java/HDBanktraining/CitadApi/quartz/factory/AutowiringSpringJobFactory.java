package HDBanktraining.CitadApi.quartz.factory;

import org.quartz.spi.TriggerFiredBundle;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import org.springframework.scheduling.quartz.SpringBeanJobFactory;




public class AutowiringSpringJobFactory  extends SpringBeanJobFactory {
    private final AutowireCapableBeanFactory beanFactory;

    public AutowiringSpringJobFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }
}