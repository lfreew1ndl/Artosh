import { ICategory } from 'app/shared/model/category.model';

export interface IWord {
  id?: number;
  word?: string;
  description?: string;
  imageUrl?: string;
  category?: ICategory;
}

export class Word implements IWord {
  constructor(
    public id?: number,
    public word?: string,
    public description?: string,
    public imageUrl?: string,
    public category?: ICategory
  ) {}
}
