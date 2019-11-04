package com.jdkhome.blzo.manage.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 应用服务数据源配置
 * 如果你希望链接更多的数据源,直接复制这个类做修改即可
 */
@Configuration
@MapperScan(basePackages = {"com.jdkhome.blzo.common.generator.dao"}, sqlSessionFactoryRef = "CoreSqlSessionFactory")
@ConfigurationProperties(prefix = "spring.datasource.core")
public class CoreDataSource {

    @Bean(name = "CoreDataSource")
    public DataSource CoreDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);             //设置URL
        dataSource.setUsername(username);   //设置用户名
        dataSource.setPassword(password);   //设置密码
        return dataSource;
    }

    @Bean(name = "CoreTransactionManager")
    public DataSourceTransactionManager TransactionManager() {
        return new DataSourceTransactionManager(CoreDataSource());
    }

    @Bean(name = "CoreSqlSessionFactory")
    public SqlSessionFactory CoreServerSqlSessionFactory(@Autowired @Qualifier("CoreDataSource") DataSource CoreServerDataSource) throws Exception {
        final SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(CoreServerDataSource);

        fb.setTypeAliasesPackage("com.jdkhome.blzo.common.generator.model");

        //设置驼峰转换，例如数据库字段user_name可以映射到javaBean的属性userName上
        fb.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        // 当列的数据为空值时，mybatis在返回的map中并不会存在对应的key,此配置可避免没有空值key
        fb.getObject().getConfiguration().setCallSettersOnNulls(true);

        return fb.getObject();
    }


    private String url;

    private String username;

    private String password;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
