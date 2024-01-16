package com.blogapp.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.articles.ArticleEntity;
import com.blogapp.articles.ArticleRepository;
import com.blogapp.articles.ArticleService;
import com.blogapp.articles.ArticleService.ArticleNotFoundException;
import com.blogapp.comments.dtos.CreateCommentRequest;
import com.blogapp.comments.dtos.UpdateCommentRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
 
	  private  CommentService commentService;
	  private  ArticleService articleService;

	    @Autowired
	    public CommentController(CommentService commentService , ArticleService articleService) {
	        this.commentService = commentService;
	        this.articleService = articleService;
	    }
	    

	    // Get all comments
	    @GetMapping("")
	    public ResponseEntity<List<CommentEntity>> getAllComments() {
	        List<CommentEntity> comments = commentService.getAllComments();
	        return new ResponseEntity<>(comments, HttpStatus.OK);
	    }

	    // Create a new comment
	    @PostMapping("/{slug}")
	    public ResponseEntity<CommentEntity> saveComment(@PathVariable String slug, @RequestBody CreateCommentRequest createCommentRequest) {
	        CommentEntity newComment = commentService.saveComment(slug, createCommentRequest);
	        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
	    }

	    // Update an existing comment
	    @PutMapping("/{commentId}")
	    public ResponseEntity<CommentEntity> updateComment(@PathVariable Long commentId, @RequestBody UpdateCommentRequest updateCommentRequest) {
	        CommentEntity updatedComment = commentService.updateComment(commentId, updateCommentRequest);
	        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	    }

	    // Delete a comment by its ID
	    @DeleteMapping("/{commentId}")
	    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
	        commentService.deleteComment(commentId);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

}
