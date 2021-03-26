package com.fuhx;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author fuhongxing
 */
@Slf4j
@MapperScan("com.fuhx.dao")
@EnableDubbo(scanBasePackages = "com.fuhx.dubbo")
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication
{
    public static void main( String[] args )
    {
        ConfigurableApplicationContext config = SpringApplication.run(OrderApplication.class, args);
        //获取容器中的组件
        String[] beanDefinitionNames = config.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println("args = " + beanDefinitionName);
        }
        log.info("===============Order START SUCCESS==============");
    }

    /**
     * https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
     * @return
     * @throws SQLException
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        //加入Druid监控
        druidDataSource.setFilters("stat, wall");
        return druidDataSource;
    }

    /**
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean stateViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        Map<String,String> initParams = new HashMap<>();

        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        //默认就是允许所有访问
        initParams.put("allow","");
        //表示只有本机可以访问
        //initParams.put("allow", "localhost")：
        initParams.put("deny","");
        //设置初始化参数
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * WebStatFilter：用于配置Web和Druid数据源之间的管理关联监控统计
     * @return
     */
    @Bean
    public FilterRegistrationBean webstatFilter(){
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        //exclusions：设置哪些请求进行过滤排除掉，从而不进行统计
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/**");
        return filterRegistrationBean;
    }
}
