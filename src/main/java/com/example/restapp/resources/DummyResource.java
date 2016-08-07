package com.example.restapp.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.restapp.App;
import com.example.restapp.business.DummyBean;
import com.example.restapp.dao.DummyBeanDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("Dummies")
@Path("/dummy")
public class DummyResource {
	
	//Getting a DAO instance from container
	private DummyBeanDAO dummyDAO = App.container().get(DummyBeanDAO.class);
	
	@ApiOperation("Add a new dummy")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DummyBean add(@ApiParam("A dummy to post") DummyBean bean) {
		return dummyDAO.add(bean);
	}
	
	@ApiOperation("List all dummy beans")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DummyBean> listAll() {
		return dummyDAO.listByExample(new DummyBean());
	}

}
