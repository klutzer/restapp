package com.restapp.resources;

public class BeanResponse {
	
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