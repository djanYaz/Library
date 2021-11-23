import { Component, OnInit } from '@angular/core';
import {Genre} from "../genre";
import {Book} from "../book";
import {Reader} from "../reader";
import {BookService} from "../book.service";
import {Router} from "@angular/router";
import {ReaderService} from "../reader.service";
import {BorrowBookService} from "../borrow-book.service";

@Component({
  selector: 'app-borrow-book',
  templateUrl: './borrow-book.component.html',
  styleUrls: ['./borrow-book.component.css']
})
export class BorrowBookComponent implements OnInit {

  books: Array<Book> = [];
  readers: Array<Reader> = [];
  selectedBook: number;
  selectedReader: number;
  msg: string;

  constructor(private bookService: BookService, private readerService: ReaderService,
              private borrowBookService: BorrowBookService, private router: Router) { }

  ngOnInit(): void {
    this.getBooks();
    this.getReaders();
  }

  getBooks() {
    this.bookService.getBookList()
      .subscribe(
        (books: Array<Book>) => {
          console.log(books);
          this.books = books;
        },
        (error: any) => {
          console.log(error);
        }
      );
  }

  getReaders() {
    this.readerService.getReaderList()
      .subscribe(
        (readers: Array<Reader>) => {
          console.log(readers);
          this.readers = readers;
        },
        (error: any) => {
          console.log(error);
        }
      );
  }

  onChangeReader(event: any){
    this.selectedReader = event;
  }

  onChangeBook(event: any){
    this.selectedBook = event;
  }

  onSubmit() {
    this.borrowBook();
  }

  borrowBook() {
    debugger;
    this.borrowBookService.borrowBook(this.selectedReader, this.selectedBook)
      .subscribe(
        data => {
          this.msg = data;
        },
        (error: any) => {
          console.log(error);
        }
      );
  }
}
