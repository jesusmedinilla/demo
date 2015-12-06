package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.EntityDAO;
import com.example.model.Entity;


@RestController
public class HelloController {
    
	@Autowired
	EntityDAO dao;

	@RequestMapping(value="/", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Entity> findAll() {
		System.out.println("findAll");
		return dao.findAll();
	}

	@RequestMapping(value="/search/{query}",method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Entity> findByName(@PathVariable("query") String query) {
		System.out.println("findByName: " + query);
		return dao.findByName(query);
	}

	@RequestMapping(value="/{id}",method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Entity findById(@PathVariable("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(Integer.parseInt(id));
	}

	
	@RequestMapping(method=RequestMethod.POST)//, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Entity create(@RequestBody Entity wine) {
		return dao.create(wine);
	}

	@RequestMapping(method=RequestMethod.PUT)//, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Entity update(@RequestBody Entity wine) {
		System.out.println("Updating wine: " + wine.getName());
		dao.update(wine);
		return wine;
	}
	
	@RequestMapping( 	value="/{id}",
						method=RequestMethod.DELETE )
	public void remove(@PathVariable("id") int id) {
		System.out.println("Deleting wine: " + id);
		dao.remove(id);
	}
    
}
