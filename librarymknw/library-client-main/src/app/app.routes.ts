import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { BooksViewComponent } from './components/books-view/books-view.component';
import { BookFormComponent } from './components/book-form/book-form.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { RedirectGuard } from './guards/redirect.guard';

export const routes: Routes = [
    { path: '', component: HomeComponent },  // Non fare redirect qui
    { path: 'home', component: HomeComponent},  // Proteggi la home
    { path: 'login', component: LoginComponent, canActivate: [RedirectGuard] },  // RedirectGuard qui
    { path: 'books-view', component: BooksViewComponent },
    { path: 'books-view/:bookId', component: BooksViewComponent},
    { path: 'book-form/:mode', component: BookFormComponent, canActivate: [AuthGuard] },
    { path: 'book-form/:bookId/:mode', component: BookFormComponent, canActivate: [AuthGuard] },
];

