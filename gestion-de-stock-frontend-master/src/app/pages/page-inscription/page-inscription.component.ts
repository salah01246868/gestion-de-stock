import { Component, OnInit } from '@angular/core';
import {EntrepriseDto} from '../../../gs-api/src/models/entreprise-dto';
import {EntrepriseService} from '../../services/entreprise/entreprise.service';
import {AdresseDto} from '../../../gs-api/src/models/adresse-dto';
import {UserService} from '../../services/user/user.service';
import {AuthenticationRequest} from '../../../gs-api/src/models/authentication-request';
import {Router} from '@angular/router';

@Component({
  selector: 'app-page-inscription',
  templateUrl: './page-inscription.component.html',
  styleUrls: ['./page-inscription.component.scss']
})
export class PageInscriptionComponent implements OnInit {

  entrepriseDto: EntrepriseDto = {};
  adresse: AdresseDto = {};
  errorsMsg: Array<string> = [];

  constructor(
    private entrepriseService: EntrepriseService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  inscrire(): void {
    // Reset error messages
    this.errorsMsg = [];
    
    // Validate required fields on frontend
    if (!this.entrepriseDto.description) {
      this.errorsMsg.push('Veuillez reseigner la description de l\'entreprise');
      return;
    }
    
    // Prepare data and submit
    this.entrepriseDto.adresse = this.adresse;
    console.log('Registering enterprise:', this.entrepriseDto.nom);
    
    this.entrepriseService.sinscrire(this.entrepriseDto)
    .subscribe(entrepriseDto => {
      console.log('Registration successful, waiting 1 second before login attempt');
      // Add a small delay before attempting to log in
      setTimeout(() => {
        this.connectEntreprise();
      }, 1000); // 1 second delay
    }, error => {
      console.error('Registration error:', error);
      
      // Handle different types of errors
      if (error.error && error.error.errors) {
        this.errorsMsg = error.error.errors;
      } else if (error.error && error.error.message) {
        this.errorsMsg = [error.error.message];
      } else {
        this.errorsMsg = ['Une erreur est survenue lors de l\'inscription. Veuillez réessayer.'];
      }
    });
  }

  connectEntreprise(): void {
    const authenticationRequest: AuthenticationRequest = {
      login: this.entrepriseDto.email,
      password: 'som3R@nd0mP@$$word'
    };
    // Log the exact password being used for debugging
    console.log('Using password for login:', authenticationRequest.password);
    console.log('Attempting to login with:', authenticationRequest.login);
    this.userService.login(authenticationRequest)
    .subscribe(response => {
      console.log('Login successful, setting token');
      this.userService.setAccessToken(response);
      this.getUserByEmail(authenticationRequest.login);
      localStorage.setItem('origin', 'inscription');
      this.router.navigate(['changermotdepasse']);
    }, error => {
      console.error('Login failed after registration:', error);
      // Show error message to user
      this.errorsMsg = ['Échec de la connexion automatique. Veuillez vous connecter manuellement.'];
      // Redirect to login page after 3 seconds
      setTimeout(() => this.router.navigate(['login']), 3000);
    });
  }

  getUserByEmail(email?: string): void {
    this.userService.getUserByEmail(email)
    .subscribe(user => {
      this.userService.setConnectedUser(user);
    });
  }
}
