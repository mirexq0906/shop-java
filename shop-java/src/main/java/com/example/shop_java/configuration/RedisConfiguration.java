package com.example.shop_java.configuration;

import com.example.shop_java.entity.user.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.Duration;
import java.util.Collections;

@Configuration
@EnableRedisRepositories(
        keyspaceConfiguration = RedisConfiguration.myKeyspaceConfiguration.class,
        enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP
)
public class RedisConfiguration {

    @Value("${app.jwt.refresh}")
    private Duration refreshTokenExpired;

    @Bean
    public RedisConnectionFactory lettuceConnectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(
                        redisProperties.getHost(), redisProperties.getPort()
                )
        );
    }

    public class myKeyspaceConfiguration extends KeyspaceConfiguration {

        public static final String REFRESH_TOKEN = "refresh_token";

        @Override
        protected Iterable<KeyspaceSettings> initialConfiguration() {
            KeyspaceSettings keyspaceSettings = new KeyspaceSettings(RefreshToken.class, REFRESH_TOKEN);
            keyspaceSettings.setTimeToLive(refreshTokenExpired.getSeconds());
            return Collections.singleton(keyspaceSettings);
        }
    }

}
