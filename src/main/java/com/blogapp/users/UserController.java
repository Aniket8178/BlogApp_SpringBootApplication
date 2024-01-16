package com.blogapp.users;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.common.error.ErrorResponse;
import com.blogapp.security.JWTService;
import com.blogapp.users.dtos.CreateUserrequest;
import com.blogapp.users.dtos.LoginUserRequest;
import com.blogapp.users.dtos.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

	
	    private  ModelMapper modelMapper;
		private UserService UserService;
	    private JWTService jwtservice;

	    public UserController(UserService usersService, ModelMapper modelMapper , JWTService jwtservice) {
	        this.UserService = usersService;
	        this.modelMapper = modelMapper;
	        this.jwtservice = jwtservice;
	  
	    }

	    @PostMapping("")
	    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserrequest request) {
	        UserEntity savedUser =  UserService.createUser(request);
	        URI savedUserUri = URI.create("/users/" + savedUser.getId());
	        var userResponse = modelMapper.map(savedUser, UserResponse.class);
	        userResponse.setToken(
	        		jwtservice.createJwt(savedUser.getId())
	        );

	        return ResponseEntity.created(savedUserUri)
	                .body(userResponse);
	        
	       // return ResponseEntity.created(savedUserUri).body(modelMapper.map(savedUser, UserResponse.class));
	    }
	    
	    @PostMapping("/login")
	    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request) {
	        UserEntity savedUser =  UserService.loginUser(request.getUsername(), request.getPassword());
	        var userResponse = modelMapper.map(savedUser, UserResponse.class);
	        userResponse.setToken(
	        		jwtservice.createJwt(savedUser.getId())
	        );

	        return ResponseEntity.ok(userResponse);
	      //  return ResponseEntity.ok(modelMapper.map(savedUser, UserResponse.class));
	    }
	    
	    @ExceptionHandler({
	    	UserService.UserNotFoundException.class,
	    	UserService.InvalidCredentialsException.class
    })
    ResponseEntity<ErrorResponse> handleUserExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if (ex instanceof UserService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UserService.InvalidCredentialsException) {
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        } else {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
