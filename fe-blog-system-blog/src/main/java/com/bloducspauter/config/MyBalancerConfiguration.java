package com.bloducspauter.config;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MyBalancerConfiguration {

    @Bean
    public ReactorServiceInstanceLoadBalancer myOnlyOneReactorServiceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory){
        String name=environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new OnlyOneLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }

    @Bean
    public MapperScannerConfigurer mapperScanConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.bloducspauter.mapper");
        return mapperScannerConfigurer;
    }
}
