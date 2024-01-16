package com.blogapp.comments;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogapp.articles.ArticleEntity;
import com.blogapp.articles.ArticleRepository;
import com.blogapp.articles.dtos.CreateArticleRequest;
import com.blogapp.comments.dtos.CreateCommentRequest;
import com.blogapp.comments.dtos.UpdateCommentRequest;
import com.blogapp.users.UserRepository;
import com.blogapp.users.UserService.UserNotFoundException;

@Service
public class CommentService {

	 private CommentsRepository commentsRepository;
	 private ArticleRepository articleRepository;
	 private UserRepository userRepository;
	 
	public CommentService(CommentsRepository commentsRepository, ArticleRepository articleRepository,UserRepository userRepository) {
		this.commentsRepository = commentsRepository;
		this.articleRepository = articleRepository;
		this.userRepository = userRepository;
	}
	
	// Get all comments
    public List<CommentEntity> getAllComments() {
        return commentsRepository.findAll();
    }

    // Save a new comment using CreateCommentRequest
    public CommentEntity saveComment(String slug, CreateCommentRequest createCommentRequest) {
    	 ArticleEntity article = articleRepository.findBySlug(slug);
    	           // .orElseThrow(() -> new ArticleNotFoundException(slug));

    	    // Create a new comment entity
    	    CommentEntity comment = CommentEntity.builder()
    	            .title(createCommentRequest.getTitle())
    	            .commentbody(createCommentRequest.getCommentbody())
    	            .article(article)
    	            .build();

    	    // Save the comment using the commentsRepository
    	    return commentsRepository.save(comment);
        	
    }

    // Update an existing comment
    public CommentEntity updateComment(Long commentId, UpdateCommentRequest updateCommentRequest) {
        CommentEntity existingComment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        // Update fields if needed
        if (updateCommentRequest.getTitle() != null) {
            existingComment.setTitle(updateCommentRequest.getTitle());
        }

        if (updateCommentRequest.getBody() != null) {
            existingComment.setCommentbody(updateCommentRequest.getBody());
        }

        // Save the updated comment
        return commentsRepository.save(existingComment);
    }

    // Delete a comment by its ID
    public void deleteComment(Long commentId) {
        commentsRepository.deleteById(commentId);
    }

    // Exception classes for comment and article not found
    static class CommentNotFoundException extends RuntimeException {
        public CommentNotFoundException(Long commentId) {
            super("Comment with ID " + commentId + " not found");
        }
    }

    static class ArticleNotFoundException extends RuntimeException {
        public ArticleNotFoundException(String slug) {
            super("Article with slug " + slug + " not found");
        }
    }

	
}
