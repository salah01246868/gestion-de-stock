import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UtilisateursService } from '../../../../gs-api/src/services/utilisateurs.service';
import { UtilisateurDto } from '../../../../gs-api/src/models/utilisateur-dto';

@Component({
  selector: 'app-page-utilisateur',
  templateUrl: './page-utilisateur.component.html',
  styleUrls: ['./page-utilisateur.component.scss']
})
export class PageUtilisateurComponent implements OnInit {
  
  utilisateurs: Array<UtilisateurDto> = [];
  errorMsg = '';

  constructor(
    private router: Router,
    private utilisateursService: UtilisateursService
  ) { }

  ngOnInit(): void {
    this.findAllUtilisateurs();
  }

  findAllUtilisateurs(): void {
    this.utilisateursService.findAll()
      .subscribe(users => {
        this.utilisateurs = users;
      }, error => {
        this.errorMsg = error.message;
        console.error(error);
      });
  }

  nouvelUtilosateur(): void {
    this.router.navigate(['nouvelutilisateur']);
  }
}
