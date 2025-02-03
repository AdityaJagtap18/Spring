package com.springcrud.CurdOparation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class ResponseModel {

	private int responseCode;
	private String localizedMessage;
	private String message;
	private int status;
	private Object data;
	private String requestId;
	private Object respData;
	private Long totalCount;
	private int orderId;
	private int count;
	private String htmlFormat; 
	private String orderNumber;
	private Object orderDetails;
	private String orderStatus ;
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getLocalizedMessage() {
		return localizedMessage;
	}
	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Object getRespData() {
		return respData;
	}
	public void setRespData(Object respData) {
		this.respData = respData;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getHtmlFormat() {
		return htmlFormat;
	}
	public void setHtmlFormat(String htmlFormat) {
		this.htmlFormat = htmlFormat;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Object getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(Object orderDetails) {
		this.orderDetails = orderDetails;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getProperty(String keyString) {
		try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			String property = prop.getProperty(keyString);
			System.out.println("String from properties file =>  " + keyString + " : " + property);
			return property;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

	}
	
	public ResponseModel setRecNotFoundResponse(String owner, String requestId, String type) {
		ResponseModel responseModel = new ResponseModel();
		responseModel.setStatus(Integer.parseInt(getProperty("FAIL_ERR_CODE").trim()));
		responseModel.setResponseCode(Integer.parseInt(getProperty("FAIL_ERR_CODE").trim()));
		responseModel.setRequestId(requestId);
		responseModel.setLocalizedMessage(owner + getProperty("REC_NOT_EXIST_ERR_MSG"));
		responseModel.setMessage(getProperty(owner + "REC_NOT_EXIST_ERR_MSG"));
		return responseModel;
	}
	
	
}
