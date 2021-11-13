import {Genre} from "./genre";

export class Book {
  id: number;
  title: string;
  author: string;
  year_published: number;
  genre: Genre;
}
