package com.springcrud.CurdOparation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CurdOparationApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(CurdOparationApplication.class, args);
	}
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				  .select()                                  
		          .apis(RequestHandlerSelectors.any())              
		          .paths(PathSelectors.any())                          
		          .build()
		          .pathMapping("/");
	}
	
	 @Bean
	    public WebMvcConfigurer webMvcConfigurer()
	    {
	        return new WebMvcConfigurer()
	        {
	            @Override
	            public void addResourceHandlers( ResourceHandlerRegistry registry )
	            {
	                registry.addResourceHandler( "swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
	                registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
	            }
	        };
	    }
//	 @Bean
//	    public CorsFilter corsFilter() {
//	        org.springframework.web.cors.CorsConfiguration config = new CorsConfiguration();
//	        config.setAllowCredentials(true);
//	        config.addAllowedOrigin("*");
//	        config.addAllowedHeader("*");
//	        config.addAllowedMethod("*");
//	        
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//	        // Allow all origins, methods, and headers
//	       
//
//	        source.registerCorsConfiguration("/**", config);
//
//	        return new CorsFilter(source);
//	 }
}
