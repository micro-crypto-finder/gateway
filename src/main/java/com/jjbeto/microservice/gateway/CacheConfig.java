package com.jjbeto.microservice.gateway;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.util.Map;

import static com.jjbeto.microservice.gateway.service.CryptoLocatorService.CACHE_CRYPTO_LOCATOR;
import static com.jjbeto.microservice.gateway.service.IpLocatorService.CACHE_IP_LOCATOR;
import static java.time.Duration.ofSeconds;
import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration;
import static org.ehcache.config.builders.ResourcePoolsBuilder.newResourcePoolsBuilder;
import static org.ehcache.config.units.MemoryUnit.MB;

@Configuration
@EnableCaching
public class CacheConfig {

    private final Boolean cacheOnHeap;

    private final Integer cacheSize;

    private final Integer ipLocatorTtlInSec;

    private final Integer cryptoLocatorTtlInSec;

    public CacheConfig(
            @Value("${cache.config.pool.heap:true}") final Boolean cacheOnHeap,
            @Value("${cache.config.pool.size:1000}") final Integer cacheSize,
            @Value("${feign.client.ip-locator.cache.ttl-in-sec}") final Integer ipLocatorTtlInSec,
            @Value("${feign.client.crypto-locator.cache.ttl-in-sec}") final Integer cryptoLocatorTtlInSec
    ) {
        this.cacheOnHeap = cacheOnHeap;
        this.cacheSize = cacheSize;
        this.ipLocatorTtlInSec = ipLocatorTtlInSec;
        this.cryptoLocatorTtlInSec = cryptoLocatorTtlInSec;
    }

    @Bean
    public JCacheCacheManager jCacheCacheManager() {
        return new JCacheCacheManager(ehCacheManager());
    }

    @Bean(destroyMethod = "close")
    public CacheManager ehCacheManager() {
        final ResourcePoolsBuilder poolBuilder;
        if (cacheOnHeap) {
            poolBuilder = ResourcePoolsBuilder.heap(cacheSize);
        } else {
            poolBuilder = newResourcePoolsBuilder().offheap(cacheSize, MB);
        }
        var resourcePools = poolBuilder.build();

        var cacheIpLocator = newCacheConfigurationBuilder(String.class, String.class, resourcePools)
                .withExpiry(timeToLiveExpiration(ofSeconds(ipLocatorTtlInSec))).build();

        var cacheCryptoLocator = newCacheConfigurationBuilder(String.class, String.class, resourcePools)
                .withExpiry(timeToLiveExpiration(ofSeconds(cryptoLocatorTtlInSec))).build();

        final Map<String, CacheConfiguration<?, ?>> caches = Map.of(
                CACHE_IP_LOCATOR, cacheIpLocator,
                CACHE_CRYPTO_LOCATOR, cacheCryptoLocator
        );

        var provider = (EhcacheCachingProvider) Caching.getCachingProvider();
        var configuration = new DefaultConfiguration(caches, provider.getDefaultClassLoader());
        return provider.getCacheManager(provider.getDefaultURI(), configuration);
    }

}
