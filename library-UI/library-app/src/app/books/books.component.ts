import { Component, OnInit } from '@angular/core';
import {BookService} from "../book.service";
import { Router } from '@angular/router';
import {Book} from "../book";
import {Message} from "@angular/compiler/src/i18n/i18n_ast";

const pageSize = 5;

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {

  books: Array<Book> = [];
  currentSelectedPage = 0;
  totalPages = 0;
  pageIndexes: Array<number> = [];

  // genre list
  genres: Array<string> = [];
  selectedGenre = '';

  // sorting
  yearsorting = false;
  desc = false;

  constructor(private bookService: BookService, private router: Router) {
  }

  ngOnInit(): void {
    this.reloadData();
  }

  reloadData(){
    this.getPage(1, '', false, false);
    this.getGenres();
    console.log('Някакъв текст');
  }

  // getAllBooks(){
  //   this.bookService.getBookList().subscribe(books =>
  //   this.books = books)
  // }

  onYearSortingChange(value: any){
    this.yearsorting= !this.yearsorting;
    if (!this.yearsorting){
      this.desc = false;
    }
  }

  sortNow(){
    if (this.desc && !this.yearsorting){
      alert('Please select \'yearsorting\' option before selecting \'desc\' option!');
      return;
    }
    // load again from backend for sorting with year field
    this.getPage(1, this.selectedGenre, this.yearsorting, this.desc);
  }

  getPage(page: number, selectedGenre: string, yearsorting: boolean, desc: boolean){
    this.bookService.getPageableBooks(page, pageSize, selectedGenre,
      yearsorting, desc)
      .subscribe(
        data => {
          console.log(data);
          this.books = data.books;
          this.totalPages = data.totalPages;
          this.pageIndexes = Array(this.totalPages).fill(0).map((x, i) => i);
          this.currentSelectedPage = data.pageNumber;
        },
        (error) => {
          console.log(error);
        }
      );
  }

  getPaginationWithIndex(index: number) {
    this.getPage(index+1, this.selectedGenre, this.yearsorting, this.desc);
  }

  getBooksPagesWithGenreFiltering(optionValue: any) {
    if (optionValue !== 'Всички'){
      this.selectedGenre = optionValue;
    } else {
      this.selectedGenre = '';
    }

    this.getPage(1, this.selectedGenre, this.yearsorting, this.desc);
  }

  getGenres(){
    this.bookService.getListGenres()
      .subscribe(
        (genres: Array<string>) => {
          console.log(genres);
          this.genres = genres;
        },
          (error: any) => {
          console.log(error);
        }
      );
  }

  active(index: number) {
    if (this.currentSelectedPage === index ){
      return {
        active: true
      };
    } else {
      return  {
        active: false
      }
    }
  }

  nextClick(){
    if (this.currentSelectedPage < this.totalPages - 1){
      this.getPage(++this.currentSelectedPage + 1 ,
        this.selectedGenre, this.yearsorting, this.desc);
    }
  }

  previousClick(){
    if (this.currentSelectedPage > 0){
      this.getPage(--this.currentSelectedPage + 1,
        this.selectedGenre, this.yearsorting, this.desc);
    }
  }

  deleteBook(id: number){
    this.bookService.deleteBookByID(id).subscribe(
      () => {
        this.reloadData();
      },
      error => console.log(error));
  }

  goToNewBook(){
    this.router.navigate(['add']);
  }

}
