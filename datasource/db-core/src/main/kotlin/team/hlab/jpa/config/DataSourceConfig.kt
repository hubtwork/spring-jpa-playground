package team.hlab.jpa.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "storage.datasource.jpa")
    fun jpaHikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    fun jpaDataSource(
        @Qualifier("jpaHikariConfig") config: HikariConfig,
    ): HikariDataSource {
        return HikariDataSource(config)
    }
}