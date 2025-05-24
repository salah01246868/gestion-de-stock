import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ArticleDto} from '../../../../gs-api/src/models/article-dto';
import {ArticleService} from '../../../services/article/article.service';

@Component({
  selector: 'app-page-article',
  templateUrl: './page-article.component.html',
  styleUrls: ['./page-article.component.scss']
})
export class PageArticleComponent implements OnInit {

  listArticle: Array<ArticleDto> = [];
  errorMsg = '';

  constructor(
    private router: Router,
    private articleService: ArticleService
  ) { }

  ngOnInit(): void {
    this.findListArticle();
  }

  findListArticle(): void {
    this.errorMsg = '';
    const user = JSON.parse(localStorage.getItem('connectedUser') || '{}');
    console.log('Connected user:', user);
    
    if (!user || !user.id) {
      this.errorMsg = 'Utilisateur non connecté. Veuillez vous connecter.';
      console.error('User not logged in or invalid user data');
      return;
    }

    console.log('Fetching articles for user:', user.email);
    
    // Add try-catch around the subscription to prevent any uncaught exceptions
    try {
      this.articleService.findAllArticles()
      .subscribe({
        next: (articles) => {
          try {
            if (!articles) {
              this.listArticle = [];
              console.log('No articles returned, using empty array');
              return;
            }
            
            // Filter out any potentially problematic articles
            this.listArticle = articles.filter(article => {
              return article && article.id && article.codeArticle;
            });
            console.log('Articles loaded:', this.listArticle.length);
          } catch (err) {
            console.error('Error processing articles in component:', err);
            this.listArticle = [];
            this.errorMsg = 'Erreur lors du traitement des articles. Veuillez réessayer.';
          }
        },
        error: (error) => {
          console.error('Error loading articles:', error);
          if (error.status === 403) {
            this.errorMsg = 'Accès refusé. Votre session a peut-être expiré. Veuillez vous reconnecter.';
          } else if (error.status === 500) {
            this.errorMsg = 'Erreur serveur. Veuillez réessayer plus tard ou contacter l\'administrateur.';
            
            // Try to recover from 500 error by setting empty article list
            this.listArticle = [];
          } else {
            this.errorMsg = 'Erreur lors du chargement des articles. Veuillez vérifier votre connexion.';
          }
          console.error('Error details:', error.error);
        }
      });
    } catch (err) {
      console.error('Unexpected error in findListArticle:', err);
      this.errorMsg = 'Erreur inattendue. Veuillez rafraîchir la page.';
      this.listArticle = [];
    }
  }

  nouvelArticle(): void {
    this.router.navigate(['nouvelarticle']);
  }

  handleSuppression(event: any): void {
    if (event === 'success') {
      this.findListArticle();
    } else {
      this.errorMsg = event;
    }
  }
}
