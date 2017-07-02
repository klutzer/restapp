package com.restapp.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.restapp.RestApp;
import com.restapp.dao.DummyBeanDAO;
import com.restapp.entity.DummyBean;

@Api("Dummies")
@Path("/dummy")
public class DummyResource {
	
	//Getting a DAO instance from container
	private DummyBeanDAO dummyDAO = RestApp.get(DummyBeanDAO.class);
	
	@ApiOperation("Insert a new dummy")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DummyBean add(@ApiParam("The new dummy to add") DummyBean bean) {
		return dummyDAO.add(bean);
	}
	
	@ApiOperation("Add or update a dummy")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DummyBean save(@ApiParam("A dummy to save (upsert)") DummyBean bean) {
		return dummyDAO.save(bean);
	}
	
	@ApiOperation("Update a dummy")
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DummyBean update(@PathParam("id") long id, @ApiParam("A dummy to update") DummyBean bean) {
		bean.setId(id);
		return dummyDAO.update(bean);
	}
	
	@ApiOperation("List all dummy beans")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DummyBean> listAll() {
		return dummyDAO.listByExample(new DummyBean());
	}
	
	@ApiOperation("Get a specific dummy by id")
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DummyBean getById(@PathParam("id") long id) {
		return dummyDAO.getById(new DummyBean(id));
	}
	
	@ApiOperation("Delete dummy bean by id")
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BeanResponse delete(@PathParam("id") long id) {
		return new BeanResponse().setMsg(
				dummyDAO.delete(new DummyBean(id)) ? "Bean deleted" : "Nothing was deleted");
	}

}
