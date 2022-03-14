package com.base.myFramework.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *   
 *  * <p> MyBatis-Plus 配置 </p>
 *   
 *  * @author: zangyi（zangyi@yunxi.tv）
 *  * @date: 20191118140831481  
 *
 * @since V1.0
 *  
 */
@Configuration
@MapperScan(basePackages = {
        "com.yololiv.dao",
        "com.yololiv.statistics.dao"
})
public class MybatisPlusConfig {

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     *   
     *  * <p> 乐观锁插件配置 </p>
     *  
     *  * @return
     *  
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
