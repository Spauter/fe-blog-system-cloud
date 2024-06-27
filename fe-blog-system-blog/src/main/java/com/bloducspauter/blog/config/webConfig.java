package com.bloducspauter.blog.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
@LoadBalancerClients(value = {
        @LoadBalancerClient(name = "fe-blog",configuration ={MyBalancerConfiguration.class} ),
        @LoadBalancerClient(name = "fe-category",configuration ={MyBalancerConfiguration.class}),
        @LoadBalancerClient(name = "fe-ornament",configuration ={MyBalancerConfiguration.class})
},defaultConfiguration = LoadBalancerClientConfiguration.class)
public class webConfig {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    配置自定义配置策略
    @Bean
//     spring容器中没有  ReactorServiceInstanceLoadBalancer  这个Ｂｅａｎ
    public ReactorServiceInstanceLoadBalancer myOnlyOneReactorServiceInstanceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        //随机RandomLoadBalancer；轮询RoundRobinLoadBalancer
        return new OnlyOneLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }

}
