import { Component, OnInit } from '@angular/core';
import {Book} from "../book";
import {BookService} from "../book.service";
import {Router} from "@angular/router";
import {Genre} from "../genre";
import {GenreService} from "../genre.service";

@Component({
  selector: 'app-create-book',
  templateUrl: './create-book.component.html',
  styleUrls: ['./create-book.component.css']
})
export class CreateBookComponent implements OnInit {

  genres: Array<Genre> = [];
  title: string;
  author: string;
  year: number;
  selectedGenre: string;
  genreType: string;
  msg: string = '';

  constructor(private genreService: GenreService, private bookService: BookService, private router: Router) { }

  ngOnInit(): void {
    this.getGenres();
  }


  save() {
    debugger;
    this.bookService.createBook(this.title, this.author, this.year, this.selectedGenre)
      .subscribe(data => console.log(data), error => console.log(error));
  }

  onSubmit() {
    this.save();
    this.gotoList();
  }

  gotoList() {
    this.router.navigate(['books']);
  }

  getGenres(){
    this.genreService.getListGenres()
      .subscribe(
        (genres: Array<Genre>) => {
          console.log(genres);
          this.genres = genres;
        },
        (error: any) => {
          console.log(error);
        }
      );
  }
  onChange(event: any){
    this.selectedGenre = event;
  }

  saveGenre(){
    this.genreService.addNewGenreType(this.genreType).subscribe(
      data => {
        this.msg = data;
      },
      (error: any) => {
        console.log(error);
      }
    );
    this.getGenres();
  }
}