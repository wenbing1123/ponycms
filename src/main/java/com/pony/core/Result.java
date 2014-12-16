package com.pony.core;

import com.pony.core.util.SpringUtils;

//操作结果
public class Result {

	private Boolean success;
	private String	message; //返回消息
	private Object	object; //绑定对象
	
	public Result(){}
	
	public Result(Boolean success, String message){
		this.success = success;
		this.message = message;
	}
	
	public Result(Boolean success, Object object){
		this.success = success;
		this.object = object;
	}

	public Result(Boolean success, String message, Object object){
		this.success = success;
		this.message = message;
		this.object = object;
	}
	
	public static Result success(){
		return new Result(true, SpringUtils.getMessage("Result.SuccessMessage"));
	}
	
	public static Result success(String message){
		return new Result(true, message);
	}
	
	public static Result success(String message, Object object){
		return new Result(true, message, object);
	}
	
	public static Result error(){
		return new Result(false, SpringUtils.getMessage("Result.ErrorMessage"));
	}
	
	public static Result error(String message){
		return new Result(false, message);
	}
	
	public static Result error(String message, Object object){
		return new Result(true, message, object);
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
