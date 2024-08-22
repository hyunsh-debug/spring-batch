package kr.co.batch.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@MapperScan(annotationClass = Mapper.class, sqlSessionFactoryRef = "sqlSessionFactory")
public class SessionFactoryConfig {

    @Value("${spring.mybatis.common.config}")
    private String mybatisConfig;

    @Value("${spring.mybatis.common.mapper}")
    private String mybatisMapper;

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setConfigLocation(applicationContext.getResource(mybatisConfig));
        sessionFactory.setMapperLocations(applicationContext.getResources(mybatisMapper));
                
        return sessionFactory.getObject();
    }
}
