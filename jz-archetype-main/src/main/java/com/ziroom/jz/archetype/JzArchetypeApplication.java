package com.ziroom.jz.archetype;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScans;

/**
 * 应用程序入口
 * 开启SpringBoot和Dubbo方式
 * 本应用既可以作为Dubbo的服务提供者，也可以作为Dubbo的服务消费者 @See <code>TestController</code>
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = {"com.ziroom.jz.archetype.service","com.ziroom.jz.archetype.api"})
public class JzArchetypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JzArchetypeApplication.class, args);
	}

	/**
	 * micrometer 注册中心，收集本应用的所有监控指标
	 * @param applicationName
	 * @return
	 */
	@Bean
	MeterRegistryCustomizer<MeterRegistry> micrometerRegistryConfigure(
			@Value("${spring.application.name}") String applicationName) {
		return (registry) -> registry.config().commonTags("application", applicationName);
	}
}
