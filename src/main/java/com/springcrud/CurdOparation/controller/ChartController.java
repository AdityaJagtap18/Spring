package com.springcrud.CurdOparation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcrud.CurdOparation.model.ChartReportTbl;
import com.springcrud.CurdOparation.repository.ChartRepository;
import com.springcrud.CurdOparation.utils.ResponseModel;
import com.springcrud.CurdOparationcom.swagger.SwaggerConstants;

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

@Api(value =SwaggerConstants.CHART_CTRL, description = SwaggerConstants.CHART_CTRL_DESC , tags = {
		SwaggerConstants.CHART_CTRL })

public class ChartController {
	
	
	
	@Autowired
	private ChartRepository chartRepository;
	
	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();
	StringBuilder respString = new StringBuilder();
	
	@GetMapping("getAllBarChart")
	public ResponseEntity<ResponseModel> getAllBarChart(@RequestHeader String Authorization, @RequestHeader Optional<String> chartType ){
		String requestId = UUID.randomUUID().toString();
		ResponseModel responseModel = new ResponseModel();
		responseModel.setRequestId(requestId);
		try {
			List<ChartReportTbl> ChartReport_tbllist = new ArrayList<ChartReportTbl>();
			if (chartType.isPresent()) {
				
				ChartReport_tbllist = chartRepository.findByType(chartType.get());
			} else {
				ChartReport_tbllist = chartRepository.findAll();
			}
			
//			ChartReport_tbllist = chartRepository.findAll();
			responseModel.setStatus(0);
			responseModel.setResponseCode(0);
			responseModel.setMessage("Bar Chart Details");
			System.out.println("ChartReport_tbllist   : "+ChartReport_tbllist.size());
			responseModel.setData(new Json(new ObjectMapper().writeValueAsString(ChartReport_tbllist)));
		}catch (Exception e) {
			responseModel.setStatus(1);
			responseModel.setResponseCode(1);
			responseModel.setMessage("Unable to retrive Bar Chart Details");
			responseModel.setLocalizedMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.ACCEPTED);
	}
}
