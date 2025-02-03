package com.springcrud.CurdOparation.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import springfox.documentation.spring.web.json.Json;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcrud.CurdOparation.model.UserTbl;
import com.springcrud.CurdOparation.repository.UserRepository;
import com.springcrud.CurdOparation.utils.ResponseModel;
import com.springcrud.CurdOparationcom.swagger.SwaggerConstants;

import ch.qos.logback.core.db.dialect.DBUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.spring.web.json.Json;

@RestController
@CrossOrigin(origins ="${CROS.ORIGIN}")
@ApiResponses(value = { @ApiResponse(code = 200, message = SwaggerConstants.SWAGGER_ERROR_CODE_200),
		@ApiResponse(code = 401, message = SwaggerConstants.SWAGGER_ERROR_CODE_401),
		@ApiResponse(code = 403, message = SwaggerConstants.SWAGGER_ERROR_CODE_403),
		@ApiResponse(code = 1, message = SwaggerConstants.SWAGGER_ERROR_CODE_1),
		@ApiResponse(code = 0, message = SwaggerConstants.SWAGGER_ERROR_CODE_0),
		@ApiResponse(code = 404, message = SwaggerConstants.SWAGGER_ERROR_CODE_404) })

@Api(value =SwaggerConstants.USER_CTRL, description = SwaggerConstants.USER_CTRL_DESC , tags = {
		SwaggerConstants.USER_CTRL })

public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();
	StringBuilder respString = new StringBuilder();
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	@GetMapping("/getAllUserDetails")
	public ResponseEntity<ResponseModel> getAllUserDetails(@RequestHeader String Authorization,@RequestHeader(defaultValue = "0") Integer pageNo, @RequestHeader(defaultValue = "10") Integer pageSize,@RequestHeader Optional<String> search){
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		
		Pageable pageableObj = PageRequest.of(pageNo, pageSize);

		
		try {
			
			List<UserTbl> User_tblList = new ArrayList<UserTbl>();
			if(!search.isPresent()) {
			User_tblList= userRepository.findAll(pageableObj).getContent();
			}else {
				User_tblList = userRepository.searchByUsername(search);
			}
			responseModel.setStatus(0);
			responseModel.setResponseCode(0);
			responseModel.setMessage("User Details");
			System.out.println("User_tblList   : "+User_tblList.size());
			responseModel.setData(new Json(new ObjectMapper().writeValueAsString(User_tblList)));
		
		} catch (Exception e) {
			responseModel.setStatus(1);
			responseModel.setResponseCode(1);
			responseModel.setMessage("Unable to retrive user Details");
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getUserByRole")
	public ResponseEntity<ResponseModel> getUserByRole(@RequestHeader String Authorization,@RequestHeader String userRole){
		
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		
		try {
			List<UserTbl> User_tblList = new ArrayList<UserTbl>();
			
			User_tblList= userRepository.findAllByUserRole(userRole);
			responseModel.setStatus(0);
			responseModel.setResponseCode(0);
			responseModel.setMessage("User Details");
			System.out.println("User_tblList   : "+User_tblList.size());
			responseModel.setData(new Json(new ObjectMapper().writeValueAsString(User_tblList)));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return null;
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<ResponseModel> addUser(@RequestHeader String Authorization,
			@RequestBody UserTbl userTbl) throws SQLException{
		
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		
		try {
			Date cDate = new Date();
			userTbl.setCreationDate(cDate);	
			userTbl.setLastUpdateDate(cDate);	
			userTbl = userRepository.save(userTbl);
			responseModel.setStatus(0);
			responseModel.setResponseCode(0);
			responseModel.setMessage("User Details Saved Successfully");
			responseModel.setLocalizedMessage("User Details Saved Successfully");
			responseModel.setData(new Json(new ObjectMapper().writeValueAsString(userTbl)));
//			userRepository.addStatusLog(1, responseModel, "/add", new ObjectMapper().writeValueAsString(UserTbl), requestId);
		} catch (Exception e) {
			
			responseModel.setStatus(1);
			responseModel.setResponseCode(1);
			responseModel.setMessage("CANNOT ADD USER");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.ACCEPTED);
		
	}
	
	
	@PutMapping("update")
	public ResponseEntity<ResponseModel> updateUser(@RequestHeader String Authorization,@RequestBody UserTbl userTbl, @RequestHeader int userId){
		
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		try {
			Optional<UserTbl> existingUser = userRepository.findById(userId);
			if(existingUser.isPresent()) {
				userTbl.setLastUpdatedBy(userId +"");
				userTbl.setLastUpdateDate(new Timestamp(new Date().getTime()));
//				userTbl.setCreatedBy(existingUser.get().getCreatedBy());
				userTbl.setCreatedBy(userTbl.getCreatedBy());

				userTbl.setCreationDate(existingUser.get().getCreationDate());
				
				userTbl = userRepository.save(userTbl);
				responseModel.setResponseCode(0);
				responseModel.setStatus(0);
				responseModel.setMessage("User Details Updated Successfully");
				responseModel.setLocalizedMessage("User Details Updated Successfully");
				responseModel.setData(new Json(new ObjectMapper().writeValueAsString(userTbl)));
			
			}else {
				responseModel.setMessage("User Details Not Found");
				responseModel.setLocalizedMessage("User Details Not Found");
			}
			responseModel.setStatus(0);
			responseModel.setResponseCode(0);
		} catch (Exception e) {
			responseModel.setStatus(1);
			responseModel.setResponseCode(1);
			responseModel.setMessage("Unable to update user");
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("remove")
	public ResponseEntity<ResponseModel> deleteUser(@RequestHeader String Authorization,@RequestHeader int userId){
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		try {
			if(userRepository.findById(userId).isPresent()) {
				userRepository.delete(userRepository.findById(userId).get());
				responseModel.setMessage("User Details Deleted Successfully");
				responseModel.setLocalizedMessage("User Details Deleted Successfully");
			}else
			{
				responseModel.setMessage("User Details Not Found");
				responseModel.setLocalizedMessage("User Details Not Found");
			}
			responseModel.setStatus(0);
			responseModel.setResponseCode(0);
		} catch (Exception e) {
			responseModel.setStatus(1);
			responseModel.setResponseCode(1);
			responseModel.setMessage("Unable to delete user");
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.ACCEPTED);

		
	}
	
	@PutMapping("changePassword")
	public ResponseEntity<ResponseModel> resetPassword(@RequestHeader String newPassword , @RequestHeader int userId ,@RequestHeader String Authorization) throws JsonProcessingException{
//	public ResponseEntity<ResponseModel> resetPassword(@RequestHeader String newPassword , @RequestHeader int userId ) throws JsonProcessingException{
	
		System.out.println(userId);
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		if(userId < -1) {
			return new ResponseEntity<ResponseModel>(responseModel,HttpStatus.UNAUTHORIZED);
		}
		try {
			Optional<UserTbl> userTbl = userRepository.findById(userId);
			if(userTbl.isPresent())
			{
				userTbl.get().setPassword(new BCryptPasswordEncoder().encode(newPassword));
				userRepository.save(userTbl.get());
				responseModel.setMessage("Password Reset Was Succesfull");
				responseModel.setLocalizedMessage("Password modified for user: "+userTbl.get().getUsername());
			}else {
				responseModel.setMessage("Invalid User");
				responseModel.setLocalizedMessage("User ID does not exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setMessage("Error while reset user password");
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
		}
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
		
	}
	
	@Autowired
	EntityManager em;
	@PostMapping(path="/updateImage",headers=("content-type=multipart/*"))
	public ResponseEntity updateImage(@RequestHeader String Authorization,@RequestHeader int userId,@RequestParam("photoIcon") MultipartFile photo ) {
		
		logger.debug("Inside Update Image");
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		
		try {
			logger.debug("Retriving Image");
			
			Optional<UserTbl> userTbl = userRepository.findById(userId);
			
			UserTbl userTblObj = em.find(UserTbl.class, userId);
			
			if(!userTbl.isPresent()) {
				logger.error("User details not found");
				responseModel.setMessage("User Not Found");

				responseModel = responseModel.setRecNotFoundResponse("User Details", requestId, "UPDATE");
			}else {
				if(!photo.isEmpty()) {
				userTblObj.setPhoto(photo.getBytes());
				
				userTblObj = prepareUserTbl(userTblObj,"UPDATE",userId);
				System.out.println("ppp**** "+ userTblObj.toString());
				userRepository.save(userTblObj);
				responseModel.setMessage("Photo Uploaded successfully");
				}
				else {
					responseModel.setMessage("No Photo to Uploaded ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			 responseModel.setMessage("Data not found");
		}finally {
			em.close();
		}
		
		return new ResponseEntity(responseModel, HttpStatus.OK);
		
	}
	
	

	private @Validated UserTbl prepareUserTbl(@Validated UserTbl userTbl,String serviceName, int userId) {
		
		userTbl.setCreationDate(new Date());
		userTbl.setLastUpdateDate(new Date());
		userTbl.setLastUpdatedBy(userId+"");
		userTbl.setCreatedBy(userId+"");
		
		return userTbl;
		
	}
	
	@PostMapping(path = "/uploadFile",headers=("content-type=multipart/*"))
	public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,@RequestHeader String Authorization,@RequestHeader int userId) {
		
		
		logger.debug("Inside Upload File");
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		final String UPLOAD_DIR = "D:\\Code\\imgUpload";
		try {
			System.out.println(file.getName());
			System.out.println(file.getContentType());
			if(!file.isEmpty()) {

				InputStream is = file.getInputStream();
				byte data[] = new byte[is.available()];
				is.read(data);
				FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+"//"+file.getOriginalFilename());
				fos.write(data);
				fos.close();
				responseModel.setMessage("File is Uploaded Succesfully");
			}else {
				responseModel.setMessage("No File to Upload ");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			 responseModel.setMessage("Data not found");
		}
		return new ResponseEntity(responseModel, HttpStatus.OK);
		
	}
	
	@SuppressWarnings("null")
	@PostMapping("/addBulkUser")
	public ResponseEntity<ResponseModel> addManyUser(@RequestHeader String Authorization,@RequestBody List<UserTbl> userTbl) throws SQLException{
		
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		List<UserTbl> additionalUsers = new ArrayList<>();
		try {
			for (UserTbl userTbl2 : userTbl) {
				Date cDate = new Date();
				userTbl2.setCreationDate(cDate);
				userTbl2.setLastUpdateDate(cDate);	
				additionalUsers.add(userTbl2);
			}
			userTbl.addAll(additionalUsers);
			userTbl = userRepository.saveAll(userTbl);
			responseModel.setStatus(0);
			responseModel.setResponseCode(0);
			responseModel.setMessage("User Details Saved Successfully");
			responseModel.setLocalizedMessage("User Details Saved Successfully");
			responseModel.setData(new Json(new ObjectMapper().writeValueAsString(userTbl)));
//			userRepository.addStatusLog(1, responseModel, "/add", new ObjectMapper().writeValueAsString(UserTbl), requestId);
		} catch (Exception e) {
			
			responseModel.setStatus(1);
			responseModel.setResponseCode(1);
			responseModel.setMessage("CANNOT ADD USER");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.ACCEPTED);
		
	}
	
	
}


















































