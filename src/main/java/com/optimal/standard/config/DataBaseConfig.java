package com.optimal.standard.config;

import static com.optimal.standard.config.DataBaseConfig.PRIMARY_BASE_PACKAGE;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = PRIMARY_BASE_PACKAGE)
public class DataBaseConfig {

  static final String PRIMARY_BASE_PACKAGE = "com.optimal.standard.persistence";

  @Bean
  @Primary
  @ConfigurationProperties("app.datasource.primary")
  public HikariConfig primaryDataSourceProperties() {
    return new HikariConfig();
  }

  @Bean
  @Primary
  public DataSource primaryDataSource() {
    return new HikariDataSource(primaryDataSourceProperties());
  }

}
