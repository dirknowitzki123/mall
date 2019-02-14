/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.kingyon.chengxin.framework.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.github.pagehelper.PageInterceptor;
import com.kingyon.chengxin.framework.util.Converter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * MyBatis基础配置
 *
 */
@Configuration
//@EnableTransactionManagement
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnMissingClass
public class DruidConfig implements ApplicationContextAware,EnvironmentAware, BeanDefinitionRegistryPostProcessor, TransactionManagementConfigurer {
    //作用域对象.
    private static ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    //bean名称生成器.
    private static BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
    private static BeanDefinitionRegistry registry;

    private static final Set<DataSource> allDataSource = new HashSet<>();

//    @Autowired
//    AppConfig appConfig;

//    private static RelaxedPropertyResolver dataSourceResolver;
//    private static RelaxedPropertyResolver myBatisResolver;

    private  static Environment dataSourceResolver;

    private  static Environment myBatisResolver;



    @Override
    public void setEnvironment(Environment environment) {
        this.dataSourceResolver =environment;
        this.myBatisResolver=environment;

    }


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }



    /**
     * Transaction 相关配置
     * 因为有两个数据源，所有使用ChainedTransactionManager把两个DataSourceTransactionManager包括在一起。
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        log.info("----------设置多数据库源TransactionManager----------");
        DataSourceTransactionManager[] dtm = new DataSourceTransactionManager[allDataSource.size()];
        java.util.Iterator<DataSource> all = allDataSource.iterator();
        int i = 0;
        while(all.hasNext()) {
            dtm[i++] = new DataSourceTransactionManager( all.next() );
        }
        ChainedTransactionManager ctm = new ChainedTransactionManager(dtm);
        return ctm;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanRegistry) throws BeansException {
        registry = beanRegistry;

        log.info("------------------postRegistry---------------------");

        try {
            configMultiDataSource();
        }catch (Exception e) {
            throw new BeanCreationException("---初始化多连接池错误", e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

//    /**
//     * 默认的数据库连接池
//     * @return
//     * @throws SQLException
//     */
//    @Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
//    @Primary
//    @Order(1)
//    public static DataSource dataSource() throws Exception {
//        DruidDataSource dataSource = configDruidDataSource(new DruidDataSource(), "");
//        configSqlSessionFactoryBean("", dataSource);
//        configMapperScannerConfigurer("");
//        return dataSource;
//    }

    private static String getProperty(String name, String index) {
        index = index == null ? "" : index;

        String val = dataSourceResolver.getProperty("ds" + index + "." + name);

        if (val == null) {
            val = dataSourceResolver.getProperty("spring.datasource."+name );
        }
        return val;
    }

    /**
     * DruidDataSource
     * @return
     * @throws SQLException
     */
    public static DruidDataSource configDruidDataSource( String index) throws SQLException {
        BeanDefinition bean = registerBean(registry, "dataSource" + index, DruidDataSource.class);
        if ("".equals(index)) bean.setPrimary(true);
        DruidDataSource dataSource = (DruidDataSource) applicationContext.getBean("dataSource" + index);

        String val = getProperty("url", index);
        if (val == null)
            return null;
//        DruidDataSource dataSource = new DruidDataSource();

        log.info("注入 druid！！！index=[{}],url={}", index, val);
        if (val != null)
            dataSource.setUrl(val);
        val = getProperty("username", index);
        if (val != null)
            dataSource.setUsername(val);//用户名
        val = getProperty("password", index);
        if (val != null) {
            dataSource.setPassword(val);//密码
        }
        val = getProperty("driver-class-name", index);
        if (val != null)
            dataSource.setDriverClassName(val);
        val = getProperty("initialSize", index);
        if (val != null)
            dataSource.setInitialSize(Converter.stringToInteger(val));
        val = getProperty("minIdle", index);
        if (val != null)
            dataSource.setMinIdle(Converter.stringToInteger(val));

        val = getProperty("maxActive", index);
        if (val != null)
            dataSource.setMaxActive(Converter.stringToInteger(val));
        val = getProperty("maxWait", index);
        if (val != null)
            dataSource.setMaxWait(Converter.stringToInteger(val));
        val = getProperty("timeBetweenEvictionRunsMillis", index);
        if (val != null)
            dataSource.setTimeBetweenEvictionRunsMillis(Converter.stringToInteger(val));
        val = getProperty("minEvictableIdleTimeMillis", index);
        if (val != null)
            dataSource.setMinEvictableIdleTimeMillis(Converter.stringToInteger(val));
        val = getProperty("validationQuery", index);
        if (val != null)
            dataSource.setValidationQuery(val);
        val = getProperty("testOnBorrow", index);
        if (val != null)
            dataSource.setTestOnBorrow(Boolean.getBoolean(val));
        val = getProperty("testWhileIdle", index);
        if (val != null)
            dataSource.setTestWhileIdle(Boolean.getBoolean(val));
        val = getProperty("testOnReturn", index);
        if (val != null)
            dataSource.setTestOnReturn(Boolean.getBoolean(val));
        val = getProperty("poolPreparedStatements", index);
        if (val != null)
            dataSource.setPoolPreparedStatements(Boolean.getBoolean(val));
        val = getProperty("maxPoolPreparedStatementPerConnectionSize", index);
        if (val != null)
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(Converter.stringToInteger(val));
        //配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
        val = getProperty("filters", index);
        if (val != null)
            dataSource.setFilters(val);

        val = getProperty("connectionProperties", index);
        if (val != null) {
            String[] ps = val.split(";");
            Properties props = new Properties();
            for (String line : ps) {
                String[] ones = line.split(":");
                props.put(ones[0].trim(), ones[1].trim());
            }
            dataSource.setConnectProperties(props);
        }

        val = getProperty("useGlobalDataSourceStat", index);
        if (val != null) {
            dataSource.setUseGlobalDataSourceStat(Boolean.getBoolean(val));
        }
        dataSource.init();

        return dataSource;
    }


    /**
     *
     * 根据配置，注入DataSource、SqlSessionFactory、MapperScannerConfigurer等。取代默认的配置（以便支持多数据库源）
     * @return
     * @throws Exception
     */
    public static Boolean configMultiDataSource() throws Exception {
        configMultiDataSource("");

        int index = 1;
        String val = dataSourceResolver.getProperty("ds" + index + ".url");
        while (val != null)
        {
//            log.info("注入 druid！！！index={},url={}", index, val);

            configMultiDataSource("" + index);

            index++;
            val = dataSourceResolver.getProperty("ds" + index + ".url");
        }
        return true;
    }

    private static void configMultiDataSource(String index) throws Exception {
        /**DataSource*/
        DataSource dataSource = configDruidDataSource(index);
        allDataSource.add(dataSource );

        /**sqlSessionFactory*/
        configSqlSessionFactoryBean(index, dataSource);

        /**MapperScannerConfigurer*/
        configMapperScannerConfigurer(index);
    }

    private static void configSqlSessionFactoryBean(String index, DataSource dataSource) {
        MutablePropertyValues mpv;
        /**DataSource*/
        BeanDefinition bean = registerBean(registry, "sqlSessionFactory" + index, SqlSessionFactoryBean.class );
        mpv = bean.getPropertyValues();
        mpv.addPropertyValue("dataSource", dataSource);

        //分页插件
//        log.info("注入 PageHelper ！！！");

//        Properties properties = new Properties();
////        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql");
//
////        //4.x的方法
//////        PageHelper pageHelper = new PageHelper();
//////        pageHelper.setProperties(properties);
//
//        PageInterceptor pageInterceptor = new PageInterceptor();
//        pageInterceptor.setProperties(properties);

//        //添加插件
//        mpv.addPropertyValue("plugins", new Interceptor[]{pageInterceptor});

        //添加XML目录
        String val = myBatisResolver.getProperty("mybatis.mapperLocations");
//        log.info("注入 SqlSessionFactory ！ mapperLocations: " + val);
        try {
            if (val != null)
                mpv.addPropertyValue("mapperLocations", new PathMatchingResourcePatternResolver().getResources(val) );
//                bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(val));

        } catch (Exception e) {
            log.error("mapperLocations: " + e.getMessage());
        }
        /**myBatis config*/
        val = myBatisResolver.getProperty("mybatis.configLocation");
        try {
            if (val != null)
                mpv.addPropertyValue("configLocation", new PathMatchingResourcePatternResolver().getResource(val) );
//                bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(val));
        } catch (Exception e) {
            log.error("configLocation: " + e.getMessage());
        }
        log.info("注入 SqlSessionFactory [{}]", index);
    }

    private static void configMapperScannerConfigurer(String index) throws Exception {
        BeanDefinition bean = registerBean(registry, "mapperScannerConfigurer" + index, MapperScannerConfigurer.class );
        MutablePropertyValues mpv = bean.getPropertyValues();
        mpv.addPropertyValue("basePackage", "com.kingyon.chengxin.*");
        mpv.addPropertyValue("sqlSessionFactoryBeanName", "sqlSessionFactory" + index);

        String val = dataSourceResolver.getProperty("ds" + index + ".markerInterface") ;
        if (val == null ) {
            if ("".equals(index))
                val = "com.kingyon.chengxin.framework.mapper.BaseMapper";
            else
                throw new NullPointerException("markerInterface" + index + " 属性无效");
        }
        mpv.addPropertyValue("markerInterface", Class.forName(val));
//        Properties properties = new Properties();
//        properties.setProperty("mappers", "com.kingyon.chengxin.base.mapper.MyMapper");
//        properties.setProperty("notEmpty", "false");
//        properties.setProperty("IDENTITY", "MYSQL");
//
//        mpv.addPropertyValue("Properties",properties);



//        val = dataSourceResolver.getProperty("ds" + index + ".markerInterface") ;
//        if (val == null ) {
//            if ("".equals(index))
//                val = "com.kingyon.chengxin.base.mapper.MyMapper";
//            else
//                throw new NullPointerException("markerInterface" + index + " 属性无效");
//        }
//        mpv.addPropertyValue("markerInterface", Class.forName(val));

        log.info("注入 MapperScannerConfigurer [{}] Marker[{}]", index, val);

//        MapperScannerConfigurer configurer = (MapperScannerConfigurer)applicationContext.getBean("mapperScannerConfigurer" + index );
//        configurer.postProcessBeanDefinitionRegistry(registry);
    }

    /**
     * 注册Bean到Spring
     *
     * @param registry
     * @param name
     * @param beanClass
     */
    private static BeanDefinition registerBean(BeanDefinitionRegistry registry, String name, Class<?> beanClass) {
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);

        //单例还是原型等等...作用域对象.
        ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(abd);
        abd.setScope(scopeMetadata.getScopeName());
        // 可以自动生成name
        String beanName = (name != null ? name : beanNameGenerator.generateBeanName(abd, registry));

        AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);

        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
//        abd.setAutowireCandidate(true);

        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
        return definitionHolder.getBeanDefinition();
    }

    /**
     * 阿里DruidDataSrouce监控、统计配置: 注册一个StatViewServlet
     *
     * @return
     */
    @Bean
    @Order(5)
    public ServletRegistrationBean registStatView() {
        log.info("注入 Druid StatViewServlet ！！！");
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

//        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        configurer.setBasePackage("com.llktop.dao");
//        configurer.setAnnotationClass();
        //添加初始化参数：initParams

        //白名单：
//        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
//        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
//        servletRegistrationBean.addInitParameter("deny","192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin2");
        servletRegistrationBean.addInitParameter("loginPassword", "admin22016");
//        //是否能够重置数据.
//        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    /**
     * 阿里DruidDataSrouce监控: 统计配置 注册一个：filterRegistrationBean
     *
     * @return:
     */
    @Bean
    @Order(6)
    public FilterRegistrationBean registStatFilter() {
        log.info("注入 Druid WebStatFilter ！！！");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("/error", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }


}
