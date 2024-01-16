package com.blogapp.users;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapp.users.dtos.CreateUserrequest;

@Service
public class UserService {

	private UserRepository userRepository;
	private ModelMapper modelmapper;
	private  PasswordEncoder passwordEncoder;
	 public UserService(UserRepository usersRepository, ModelMapper modelMapper ,PasswordEncoder passwordEncoder ) {
	        this.userRepository = usersRepository;
	        this.modelmapper = modelMapper;
	        this.passwordEncoder = passwordEncoder;
	      
	    }

	public UserEntity createUser(CreateUserrequest u) {
		UserEntity newUser = modelmapper.map(u, UserEntity.class);
		newUser.setPassword(passwordEncoder.encode(u.getPassword()));

		return userRepository.save(newUser);
	}

	public UserEntity getUser(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}

	public UserEntity getuser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}

	 public UserEntity loginUser(String username, String password) {
	        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	        var passMatch = passwordEncoder.matches(password, user.getPassword());
	        if (!passMatch) throw new InvalidCredentialsException();
	        return user;
	    }

	public static class UserNotFoundException extends IllegalArgumentException {
		public UserNotFoundException(String username) {
			super("User with username: " + username + " not found");
		}

		public UserNotFoundException(Long userId) {
			super("User with id: " + userId + " not found");
		}
	}

	public static class InvalidCredentialsException extends IllegalArgumentException {
		public InvalidCredentialsException() {
			super("Invalid username or password combination");
		}
	}
}
