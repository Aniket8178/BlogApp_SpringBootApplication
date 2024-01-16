package com.blogapp.comments.dtos;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.blogapp.articles.dtos.CreateArticleRequest;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
@Getter
public class UpdateCommentRequest {

	 @Nullable
	    private String title;

	 @Nullable
	    private String body;
}
