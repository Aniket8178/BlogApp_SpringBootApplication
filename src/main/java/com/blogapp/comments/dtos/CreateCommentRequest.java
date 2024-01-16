package com.blogapp.comments.dtos;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.lang.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

	 @Nullable
	 private  String title;
	 //@NonNull
	 private  String commentbody;
}
