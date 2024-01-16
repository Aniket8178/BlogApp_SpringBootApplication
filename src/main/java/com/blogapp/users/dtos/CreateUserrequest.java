package com.blogapp.users.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public class CreateUserrequest {
   
	  @NonNull
	  private String username;
	  @NonNull
	  private String password;
	  @NonNull
	  private String email;
}
