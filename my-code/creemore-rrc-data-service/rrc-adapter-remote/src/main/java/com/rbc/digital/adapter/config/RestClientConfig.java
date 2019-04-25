package com.home.digital.adapter.config;


import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {
    private static Logger logger = LoggerFactory.getLogger(RestClientConfig.class);

    @Value("${rest.client.connectionTimeoutMillis}")
    private int connectionTimeoutMillis;

    @Value("${rest.client.maxConnectionPerHost}")
    private int maxConnectionPerHost;

    @Value("${rest.client.maxTotalConnections}")
    private int maxTotalConnections;

    @Value("${rest.client.readTimeoutMillis}")
    private int readTimeoutMillis;

    
    @Bean
    public CloseableHttpClient getHttpClient() throws Exception {
        logger.info("[Initialization of Apache PoolingHttpClientConnectionManager...]");
        logger.info("Connection timeout[{}]", connectionTimeoutMillis);
        logger.info("Max connection per host [{}]", maxConnectionPerHost);
        logger.info("Max total connections [{}]", maxTotalConnections);
        logger.info("Reading timeout [{}]", readTimeoutMillis);

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        HttpRoute route = new HttpRoute(new HttpHost("https://wsii0.devfg.home.com"));

        connManager.setDefaultMaxPerRoute(maxConnectionPerHost);
        connManager.setMaxTotal(maxTotalConnections);
        connManager.setSocketConfig(route.getTargetHost(),SocketConfig.custom().setSoTimeout(25000).setSoLinger(20).build());        

        RequestConfig config = RequestConfig.custom().build();

        CloseableHttpClient bean = HttpClientBuilder.create()
                                  .setConnectionManager(connManager)
                                  .setDefaultRequestConfig(config)
                                  .setRetryHandler(new DefaultHttpRequestRetryHandler(5, true)).build();

        logger.info("[CloseableHttpClient created [{}]");

        return bean;
    }

    public ClientHttpRequestFactory getClientHttpRequestFactory() throws Exception {
        logger.info("[Initialization of Apache ClientHttpRequestFactory...]");

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(getHttpClient());
        factory.setConnectTimeout(connectionTimeoutMillis);
        factory.setReadTimeout(readTimeoutMillis);
        factory.setConnectionRequestTimeout(connectionTimeoutMillis);
        logger.info("[HttpComponentsClientHttpRequestFactory created [{}]");
        return factory;
    }

    /**
     * 
     * @return restTempalte spring bean with GSON converter to call MLS
     */
    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplate() throws Exception  {
        logger.info("[Initialization of Spring restTemplate...]");
        // There is a network issue preventing the pools working properly. connection gets opened but when the response is back from DP
        // the same connection gets restarted and we get an exception. needs to be investigated
//      RestTemplate bean = new RestTemplate(getClientHttpRequestFactory());
        RestTemplate bean = new RestTemplate();            
        bean.getMessageConverters().add(getGsonHttpMessageConverter());
        for (HttpMessageConverter<?> item : bean.getMessageConverters()) {
            if (item instanceof MappingJackson2HttpMessageConverter) {
                bean.getMessageConverters().remove(item);
            }
        }
        
        return bean;
    }

    @Bean(name = "gsonHttpMessageConverter")
    public GsonHttpMessageConverter getGsonHttpMessageConverter() {

        GsonHttpMessageConverter bean = new GsonHttpMessageConverter();

        return bean;
    }
}
