package com.blogapp.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.blogapp.articles.ArticleEntity;


@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {
     
  Optional<CommentEntity> findById(Long id);
	//List<CommentEntity> findByArticle(ArticleEntity article);
}
