package com.springcrud.CurdOparationcom.swagger;

public class SwaggerConstants {
	
	/// common properties
	
	public static final String SWAGGER_ERROR_CODE_200 = "Successful";
	public static final String SWAGGER_ERROR_CODE_401 = "You are not authorzied to view the source";
	public static final String SWAGGER_ERROR_CODE_403= "Accessing the resource you were trying to reach is forbidden";
	public static final String SWAGGER_ERROR_CODE_1 = "Error responce";
	public static final String SWAGGER_ERROR_CODE_0 = "Successful responce";
	public static final String SWAGGER_ERROR_CODE_404 = "The resource you were trying to reach is not found";

	/// UserController properties
	public static final String USER_CONTROLLER_GET_USERS ="get all Users";//getAllUserDetails
	public static final String USER_CONTROLLER_POST_USER = "add User";//addUser
	public static final String USER_CONTROLLER_PUT_USER ="update";//update
	public static final String USER_CONTROLLER_DELETE_USER = "delete";//delete
	public static final String USER_CONTROLLER_CHANGE_PASSWORD = "change Password";//changePassword
	public static final String USER_CONTROLLER_PUT_PHOTO = "updateImage";//updateImage
	
	
	/// ChartController properties
	public static final String CHART_CONTROLLER_GET_CHART ="get all Bar Chart";//getAllBarChart
	
	/// class names
	public static final String USER_CTRL = "User-Role";
	public static final String USER_CTRL_DESC = "User Services";
	
	public static final String CHART_CTRL = "Chart Access Services";
	public static final String CHART_CTRL_DESC = "Chart Services";
}
