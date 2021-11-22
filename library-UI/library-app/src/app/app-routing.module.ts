import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BooksComponent} from "./books/books.component";
import {CreateBookComponent} from "./create-book/create-book.component";
import{ReadersComponent} from "./readers/readers.component";
import{CreateReaderComponent} from "./create-reader/create-reader.component";


const routes: Routes = [
  { path: '', redirectTo: 'books', pathMatch: 'full' },
  {path: "books", component: BooksComponent},
  {path: "add", component: CreateBookComponent},
  {path:'', redirectTo: 'readers', pathMatch: 'full'},
  {path:"readers",component: ReadersComponent},
  {path:"addreader",component:CreateReaderComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
