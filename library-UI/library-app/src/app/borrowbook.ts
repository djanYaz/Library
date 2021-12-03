import {Reader} from "./reader";
import {Book} from "./book";

export class BorrowedBook {
  id: number;
  book: Book;
  reader: Reader;
  borrowDate: Date;
  returnDate: Date;
}
