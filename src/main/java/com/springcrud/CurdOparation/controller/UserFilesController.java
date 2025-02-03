package com.springcrud.CurdOparation.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;


import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcrud.CurdOparation.model.UserFilesTbl;
import com.springcrud.CurdOparation.model.UserTbl;
import com.springcrud.CurdOparation.repository.UserFilesRepository;
import com.springcrud.CurdOparation.utils.ResponseModel;

import springfox.documentation.spring.web.json.Json;
import io.swagger.models.Operation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class UserFilesController {
	@Autowired
	private UserFilesRepository userFilesController;
	
	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();
	StringBuilder respString = new StringBuilder();
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	EntityManager em;
	@PostMapping(path= "/uploadFilesInFolders",headers=("content-type=multipart/*"))
	public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,@RequestHeader String Authorization,@RequestHeader int fileId) {
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		
		try {
			Optional<UserFilesTbl> userFileTbl = userFilesController.findByFileId(fileId);
			
			UserFilesTbl userFileTblObj = em.find(UserFilesTbl.class, fileId);
			
			if(!userFileTbl.isPresent()) {
				logger.error("User details not found");
				responseModel.setMessage("User Not Found");
				responseModel = responseModel.setRecNotFoundResponse("User Details", requestId, "UPDATE");
			}else {
				if(!file.isEmpty()) {
					
					System.out.println(file.getContentType());
					if(file.getContentType().equals("text/csv")) {
						System.out.println(file.getContentType());
					 String UPLOAD_DIR = userFileTblObj.getUserFile().toString();
					 System.out.println(file.getName());
						System.out.println(file.getContentType());
						if(!file.isEmpty()) {
//							if(Files.exists(UPLOAD_DIR))
							InputStream is = file.getInputStream();
							byte data[] = new byte[is.available()];
							is.read(data);
							FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+"//"+file.getOriginalFilename());
							
							fos.write(data);
							fos.close();
						responseModel.setMessage("File is Uploaded Succesfully");
						}
						}
					else{
						responseModel.setMessage("File is not a csv type");
						}
					}
				
				else {
					responseModel.setMessage("No File to Upload ");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			 responseModel.setMessage("File not found");
		}
		return new ResponseEntity(responseModel, HttpStatus.OK);
	} 
	
	@PostMapping(path= "/uploadFilesInBulkFolders",headers=("content-type=multipart/*"))
	public ResponseEntity handleBulkFileUpload(@RequestParam("file") MultipartFile file,@RequestHeader String Authorization) {
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		
		try {
			List<UserTbl> utblList = new ArrayList<>();
			for (UserTbl userTbl : utblList) {
//			UserTbl userTbl = new UserTbl();
				int fileId = userTbl.getFileId();
//				int fileId = 2;
				Optional<UserFilesTbl> userFileTbl = userFilesController.findByFileId(fileId);
				
				UserFilesTbl userFileTblObj = em.find(UserFilesTbl.class, fileId);
				
				if(!userFileTbl.isPresent()) {
					logger.error("User details not found");
					responseModel.setMessage("User Not Found");
					responseModel = responseModel.setRecNotFoundResponse("User Details", requestId, "UPDATE");
				}else {
					if(!file.isEmpty()) {
						
						System.out.println(file.getContentType());
						if(file.getContentType().equals("text/csv")) {
							System.out.println(file.getContentType());
						 String UPLOAD_DIR = userFileTblObj.getUserFile().toString();
						 System.out.println(file.getName());
							System.out.println(file.getContentType());
							if(!file.isEmpty()) {
//								if(Files.exists(UPLOAD_DIR))
								InputStream is = file.getInputStream();
								byte data[] = new byte[is.available()];
								is.read(data);
								FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+"//"+file.getOriginalFilename());
								
								fos.write(data);
								fos.close();
							
							}
							}
						else{
							responseModel.setMessage("File is not a csv type");
							}
						}
					
					else {
						responseModel.setMessage("No File to Upload ");
					}
				}
			}
			responseModel.setMessage("File is Uploaded Succesfully");
			
		}catch (Exception e) {
			e.printStackTrace();
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			 responseModel.setMessage("File not found");
		}
		return new ResponseEntity(responseModel, HttpStatus.OK);
	} 
	
	
	@GetMapping("/downloadCSV")
//	public ResponseEntity<FileSystemResource> downloadFile(@RequestHeader String Authorization, @RequestHeader int userId, @RequestHeader String fileName) {
	public ResponseEntity downloadFile(@RequestHeader String Authorization, @RequestHeader int fileId, @RequestHeader String fileName) {
	String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		
		try {
			Optional<UserFilesTbl> userFileTbl = userFilesController.findByFileId(fileId);
			
			UserFilesTbl userFileTblObj = em.find(UserFilesTbl.class, fileId);
			
			if(!userFileTbl.isPresent()) {
				logger.error("User details not found");
				responseModel.setMessage("User Not Found");
				responseModel = responseModel.setRecNotFoundResponse("User Details", requestId, "UPDATE");
			}else {
				
				String filePath = userFileTblObj.getUserFile().toString();
				 Path path = Paths.get(filePath,fileName);
				 String fileExtension =  fileName;
				 int index = fileExtension.lastIndexOf("."); 
				 String extension = fileExtension.substring(index + 1);
				 System.out.println(extension);
				 System.out.println("file name is"+ fileName);
				 System.out.println("path is "+path);
				 if(extension.equals("csv")) {
				byte[] file = Files.readAllBytes(path);
				FileSystemResource filesysResource = new FileSystemResource(path);
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileName + "\"");
				 String contentType = Files.probeContentType(path);
				
//				responseModel.setStatus(0);
//				responseModel.setResponseCode(0);
//				responseModel.setMessage("User File");		
				    
			        headers.setContentType(MediaType.parseMediaType(contentType));
			        headers.setContentDispositionFormData("attachment", fileName );
			        headers.setContentLength(file.length);
				 return new ResponseEntity<>(file, headers, HttpStatus.OK);
//			        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(contentType)).body(filesysResource);
				}else {
					responseModel.setMessage("Not CSV file file type was " + extension);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			 responseModel.setMessage("File not found");
		}
		
		return new ResponseEntity(responseModel, HttpStatus.OK);
		
	}
	
@GetMapping("/downloadExcel")
public ResponseEntity downloadExcelFile(@RequestHeader String Authorization, @RequestHeader int fileId, @RequestHeader String fileName) {
	String requestId = UUID.randomUUID().toString();
	ResponseModel responseModel = new ResponseModel();
	responseModel.setRequestId(requestId);
	try {
		Optional<UserFilesTbl> userFileTbl = userFilesController.findByFileId(fileId);
		
		UserFilesTbl userFileTblObj = em.find(UserFilesTbl.class, fileId);
		
		if(!userFileTbl.isPresent()) {
			logger.error("User details not found");
			responseModel.setMessage("User Not Found");
			responseModel = responseModel.setRecNotFoundResponse("User Details", requestId, "UPDATE");
		}else {
			
			String filePath = userFileTblObj.getUserFile().toString();
			 Path path = Paths.get(filePath,fileName);
			 String fileExtension =  fileName;
			 int index = fileExtension.lastIndexOf("."); 
			 String extension = fileExtension.substring(index + 1);
			 System.out.println(extension);
			 System.out.println("file name is"+ fileName);
			 System.out.println("path is "+path);
			 if(extension.equals("xlsx")) {
			byte[] file = Files.readAllBytes(path);
			FileSystemResource filesysResource = new FileSystemResource(path);
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileName + "\"");
			 String contentType = Files.probeContentType(path);
			
//			responseModel.setStatus(0);
//			responseModel.setResponseCode(0);
//			responseModel.setMessage("User File");		
			    
		        headers.setContentType(MediaType.parseMediaType(contentType));
		        headers.setContentDispositionFormData("attachment", fileName );
		        headers.setContentLength(file.length);
			 return new ResponseEntity<>(file, headers, HttpStatus.OK);
//		        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(contentType)).body(filesysResource);
			}else {
				responseModel.setMessage("Not xlsx file file type was " + extension);
			}
			
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		responseModel.setLocalizedMessage(e.getLocalizedMessage());
		 responseModel.setMessage("File not found");	}
	
	
	
	return new ResponseEntity(responseModel, HttpStatus.OK);


}
	
}
