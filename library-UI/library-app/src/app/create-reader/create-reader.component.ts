import { Component, OnInit } from '@angular/core';
import {Reader} from "../reader";
import {ReaderService} from "../reader.service";
import {BookService} from "../book.service";
import {Router} from "@angular/router";
@Component({
  selector: 'app-create-reader',
  templateUrl: './create-reader.component.html',
  styleUrls: ['./create-reader.component.css']
})
export class CreateReaderComponent implements OnInit {

  first_name:string;
  last_name:string;
  city:string;
  phone:number;
  email:string;
  borrow_number:number;
  msg: string = '';

  constructor(private readerService: ReaderService, private router: Router) { }

  ngOnInit(): void {
  }

  save() {
    this.readerService.createReader(this.first_name, this.last_name, this.city, this.phone,this.email,this.borrow_number)
      .subscribe(data => console.log(data), error => console.log(error));
  }
}
