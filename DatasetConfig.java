package com.home.dataset.config;

/** taskExecutor:
        corePoolSize: 10
        maxPoolSize: 20
        queueCapicity: 50
        prefixName: dataset-service-ThreadPoolTask-
**/
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hoome.dataset.handler.DatasetHandlerInterceptorAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.hoome.blue" })
@EntityScan("com.hoome.persistent.common.model")
@EnableJpaRepositories("com.hoome.persistent.common.repository")
@EnableTransactionManagement
@EnableConfigurationProperties
public class DatasetConfig extends WebMvcConfigurerAdapter {

    @Value("${dataset-service.taskExecutor.corePoolSize}")
    private int taskExecutorCorePoolSize;

    @Value("${dataset-service.taskExecutor.maxPoolSize}")
    private int taskExecutorMaxPoolSize;

    @Value("${dataset-service.taskExecutor.queueCapicity}")
    private int taskExecutorQueueCapicity;

    @Value("${dataset-service.taskExecutor.prefixName}")
    private String taskExecutorPrefixName;

    @Bean
    public DatasetHandlerInterceptorAdapter datasetInterceptorHandler() {
        return new DatasetHandlerInterceptorAdapter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(datasetInterceptorHandler());
    }

    @Bean(name = "datasetThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor datsetThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(taskExecutorCorePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(taskExecutorMaxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(taskExecutorQueueCapicity);
        threadPoolTaskExecutor.setThreadNamePrefix(taskExecutorPrefixName);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }
}
