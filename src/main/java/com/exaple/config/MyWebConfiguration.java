package com.exaple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.dao.EntityDAO;

public class MyWebConfiguration extends WebMvcConfigurerAdapter 
{
	@Bean
	public EntityDAO getDao()
	{
		return new EntityDAO();
	}

}
