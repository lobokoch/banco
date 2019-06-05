/**********************************************************************************************
Code generated with MKL Plug-in version: 3.6.2
Code generated at time stamp: 2019-06-05T06:37:56.738
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

// Angular
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

// Kerubin - BEGIN
import { ConfirmAccountComponent } from './account/confirmaccount/confirmaccount.component';
import { NewAccountComponent } from './account/newaccount/newaccount.component';
import { ConfigNewAccountComponent } from './account/confignewaccount/confignewaccount.component';
import { LoginComponent } from './security/login/login.component';
// Kerubin - END

const routes: Routes = [
  // ENTITY CHILD ROUTES
  
  
  // BEGIN ENTITIES FOR SERVICE: cadastros.banco
  { path: 'banco', loadChildren: './modules/cadastros/banco/banco/banco.module#BancoModule' },
  { path: 'agenciabancaria', loadChildren: './modules/cadastros/banco/agenciabancaria/agenciabancaria.module#AgenciaBancariaModule' },
  { path: 'bandeiracartao', loadChildren: './modules/cadastros/banco/bandeiracartao/bandeiracartao.module#BandeiraCartaoModule' },
  { path: 'contabancaria', loadChildren: './modules/cadastros/banco/contabancaria/contabancaria.module#ContaBancariaModule' },
  { path: 'cartaocredito', loadChildren: './modules/cadastros/banco/cartaocredito/cartaocredito.module#CartaoCreditoModule' },
  // END ENTITIES FOR SERVICE: cadastros.banco
  
  
  // *****
  { path: 'mainmenu', loadChildren: './modules/cadastros/fornecedor/fornecedor/fornecedor.module#FornecedorModule' },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  
  { path: 'login', component: LoginComponent },
  { path: 'confignewaccount', component: ConfigNewAccountComponent },
  { path: 'newaccount', component: NewAccountComponent },
  { path: 'confirmaccount', component: ConfirmAccountComponent }
];


@NgModule({

  imports: [
    RouterModule.forRoot(routes)
  ],

  exports: [
    RouterModule
  ]

})

export class AppRoutingModule { }

