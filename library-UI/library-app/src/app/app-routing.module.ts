import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BooksComponent} from "./books/books.component";
import {CreateBookComponent} from "./create-book/create-book.component";
import{ReadersComponent} from "./readers/readers.component";
import {BorrowBookComponent} from "./borrow-book/borrow-book.component";
import{CreateReaderComponent} from "./create-reader/create-reader.component";
import{UpdateReaderComponent} from "./update-reader/update-reader.component";


const routes: Routes = [
  { path: '', redirectTo: 'books', pathMatch: 'full' },
  {path: "books", component: BooksComponent},
  {path: "add", component: CreateBookComponent},
  {path:'', redirectTo: 'readers', pathMatch: 'full'},
  {path:"readers",component: ReadersComponent},
  {path:"borrow",component: BorrowBookComponent}
  {path:"addreader",component:CreateReaderComponent},
  {path:'updatereader/:id',component:UpdateReaderComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
