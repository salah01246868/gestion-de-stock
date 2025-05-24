import { Injectable } from '@angular/core';
import {UserService} from '../user/user.service';
import {ArticlesService} from '../../../gs-api/src/services/articles.service';
import {ArticleDto} from '../../../gs-api/src/models/article-dto';
import {Observable, of} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(
    private userService: UserService,
    private articleService: ArticlesService
  ) { }


  enregistrerArticle(articleDto: ArticleDto): Observable<ArticleDto> {
    articleDto.idEntreprise = this.userService.getConnectedUser().entreprise?.id;
    return this.articleService.save(articleDto);
  }

  findAllArticles(): Observable<ArticleDto[]> {
    const idEntreprise = this.userService.getConnectedUser()?.entreprise?.id;
    if (!idEntreprise) {
      console.error('No enterprise ID found for the connected user');
      return of([]);
    }
    console.log('Fetching articles for enterprise ID:', idEntreprise);
    
    // Add error handling and retry logic
    return this.articleService.findAll().pipe(
      map((articles: ArticleDto[]) => {
        // Filter out any potentially malformed articles 
        return articles.filter(article => {
          // Basic validation to ensure the article has required fields
          return article && article.id && article.codeArticle;
        });
      }),
      // Handle errors by returning an empty array instead of propagating the error
      // This way the UI won't crash
      map(articles => articles || []),
    );
  }

  findArticleById(idArticle?: number): Observable<ArticleDto> {
    if (idArticle) {
      return this.articleService.findById(idArticle);
    }
    return of();
  }

  deleteArticle(idArticle: number): Observable<any> {
    if (idArticle) {
      return this.articleService.delete(idArticle);
    }
    return of();
  }

  findArticleByCode(codeArticle: string): Observable<ArticleDto> {
    return this.articleService.findByCodeArticle(codeArticle);
  }
}
