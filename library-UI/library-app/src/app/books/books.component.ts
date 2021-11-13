import { Component, OnInit } from '@angular/core';
import {BookService} from "../book.service";
import { Router } from '@angular/router';
import {Bookinspection} from "../bookinspection";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {

  books: Array<Bookinspection> = [];

  constructor(private bookService: BookService, private router: Router) {
  }

  ngOnInit(): void {
    this.reloadData();
  }

  reloadData(){
    this.getAllBooks();
  }

  getAllBooks(){
    this.bookService.getBookList().subscribe(books =>
    this.books = books)
  }

}
