package com.springcrud.CurdOparation.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import com.springcrud.CurdOparation.model.UserTbl;
import com.springcrud.CurdOparation.repository.UserRepository;
import com.springcrud.CurdOparation.utils.JwtTokenUtil;


@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value ="/login",method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
		System.out.println("before auth");	
	authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		System.out.println("After auth");
	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	System.out.println(userDetails);
	System.out.println("before auth 1 ---------------");
	final String token = jwtTokenUtil.generateToken(userDetails);
	System.out.println("before auth 2 --------------");
	UserTbl daoUser = userRepository.findByUsername(authenticationRequest.getUsername());
	
	LoginUserResponse loginUserResponse = new LoginUserResponse();
	
	loginUserResponse.setUserDetails(daoUser);
	
	loginUserResponse.setToken(token);
	
	return ResponseEntity.ok(loginUserResponse);
		
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			System.out.println("before "+username);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			System.out.println(" after "+ username);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
