package com.example.test.config;


import com.zaxxer.hikari.HikariConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")  // DB의 경로
public class dataBaseConfiguration {

    @Autowired
    private ApplicationContext appContext;

    // Bean으로 된 것은 자동으로 올려줌
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    // db와 관련된것 -> datasource
    public DataSource dataSource() throws Exception {
        // DB에 대한 접근
        DataSource dataSource = new HikariDataSource(hikariConfig());
        System.out.println("============>" + dataSource.toString());
        return dataSource;
    }

	@Bean
	public SqlSessionFactory sqlSessoinFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);

		// 매퍼 연결
		sqlSessionFactoryBean.setMapperLocations(
				appContext.getResources("classpath:/mapper/sql-*.xml")
				);

		// 마이바티스에 대한 설정 추가
	sqlSessionFactoryBean.setConfiguration(myBatisConfig());

			return sqlSessionFactoryBean.getObject();
	}

	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration myBatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}

/*	@Bean
	public SqlFunctionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlFunctionTemplate(sqlSessionFactory);
	}*/

    /**
     * <pre>
     * 1. 개요 : JPA 설정
     * 2. 처리내용 : JPA 설정 빈 등록
     * </pre>
     * @Method Name : hibernateConfig
     * @return
     */
   /* @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    public Properties hibernateConfig() {
        return new Properties();
    }*/
}
