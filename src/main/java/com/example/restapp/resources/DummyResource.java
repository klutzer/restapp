package com.example.restapp.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@ApiOperation("Add or update a dummy")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DummyBean save(@ApiParam("The new dummy to save") DummyBean bean) {
		return dummyDAO.save(bean);
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
		return dummyDAO.get(new DummyBean(id));
	}
	
	@ApiOperation("Delete dummy bean by id")
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BeanResponse delete(@PathParam("id") long id) {
		return new BeanResponse().setMsg(
				dummyDAO.delete(new DummyBean(id)) ? "Bean deleted" : "Nothing was deleted");
	}
	
	public static class BeanResponse {
		
		private Boolean success;
		private String msg;
		
		public Boolean isSuccess() {
			return success;
		}
		public BeanResponse setSuccess(Boolean success) {
			this.success = success;
			return this;
		}
		public String getMsg() {
			return msg;
		}
		public BeanResponse setMsg(String msg) {
			this.msg = msg;
			return this;
		}
	}

}
