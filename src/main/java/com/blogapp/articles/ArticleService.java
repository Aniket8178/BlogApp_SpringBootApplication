package com.blogapp.articles;

import org.springframework.stereotype.Service;

import com.blogapp.articles.dtos.CreateArticleRequest;
import com.blogapp.articles.dtos.UpdateArticleRequest;
import com.blogapp.users.UserRepository;
import com.blogapp.users.UserService.UserNotFoundException;

@Service
public class ArticleService {

	private ArticleRepository articleRepositoy;
	private UserRepository usersRepository;

	public ArticleService(ArticleRepository articlesRepository, UserRepository usersRepository) {
		this.articleRepositoy = articlesRepository;
		this.usersRepository = usersRepository;
	}
  
	public Iterable<ArticleEntity> getAllArticles(){
		return articleRepositoy.findAll();
	}
	
	public ArticleEntity getArticleBySlug(String Slug)
	{
		var article = articleRepositoy.findBySlug(Slug);
		if(article == null)
		{
			throw new ArticleNotFoundException(Slug);
		}
		return article;
	}
	
   
	public ArticleEntity createArticle(Long authorId, CreateArticleRequest a) {
	    var author = usersRepository.findById(authorId).orElseThrow(() -> new UserNotFoundException(authorId));

	    return articleRepositoy.save( ArticleEntity.builder()
	            .title(a.getTitle())
	            .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"))
	            .body(a.getBody())
	            .subtitle(a.getSubtitle())
	            .author(author)
	            .build());
	}

	 public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest a) {
	        var article = articleRepositoy.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));

	        if (a.getTitle() != null) {
	            article.setTitle(a.getTitle());
	            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"));
	        }

	        if (a.getBody() != null) {
	            article.setBody(a.getBody());
	        }

	        if (a.getSubtitle() != null) {
	            article.setSubtitle(a.getSubtitle());
	        }

	        return articleRepositoy.save(article);
	    }
	public static class ArticleNotFoundException extends IllegalArgumentException {
		public ArticleNotFoundException(String slug) {
			super("Article " + slug + " not found");
		}

		public ArticleNotFoundException(Long id) {
			super("Article with id: " + id + " not found");
		}
	}
}
