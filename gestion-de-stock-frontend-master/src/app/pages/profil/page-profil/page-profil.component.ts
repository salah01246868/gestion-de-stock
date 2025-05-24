import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user/user.service';
import { UtilisateurDto } from '../../../../gs-api/src/models/utilisateur-dto';

@Component({
  selector: 'app-page-profil',
  templateUrl: './page-profil.component.html',
  styleUrls: ['./page-profil.component.scss']
})
export class PageProfilComponent implements OnInit {
  utilisateur: UtilisateurDto = {};
  errorMsg = '';

  constructor(
    private router: Router,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadUserData();
  }

  loadUserData(): void {
    // Get the connected user from local storage
    this.utilisateur = this.userService.getConnectedUser();
    
    // If user data is incomplete, try to refresh it from the API
    if (!this.utilisateur || !this.utilisateur.id) {
      const email = this.utilisateur?.email;
      if (email) {
        this.userService.getUserByEmail(email)
          .subscribe(user => {
            this.utilisateur = user;
            this.userService.setConnectedUser(user);
          }, error => {
            this.errorMsg = 'Erreur lors du chargement des données utilisateur';
            console.error(error);
          });
      }
    }
  }

  modifierMotDePasse(): void {
    this.router.navigate(['changermotdepasse']);
  }
  
  modifierProfil(): void {
    // You can implement this functionality later
    console.log('Modify profile clicked');
  }
  
  // Helper method to get full name
  getFullName(): string {
    if (this.utilisateur) {
      return `${this.utilisateur.nom || ''} ${this.utilisateur.prenom || ''}`;
    }
    return 'N/A';
  }
  
  // Helper method to get address
  getFullAddress(): string {
    if (this.utilisateur && this.utilisateur.adresse) {
      const adresse = this.utilisateur.adresse;
      return `${adresse.adresse1 || ''} ${adresse.adresse2 || ''}, ${adresse.ville || ''}, ${adresse.codePostale || ''}`;
    }
    return 'N/A';
  }
}
