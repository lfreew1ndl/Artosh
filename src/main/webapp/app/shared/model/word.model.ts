export interface IWord {
  id?: number;
  word?: string;
  description?: string;
  imageUrl?: string;
  categoryName?: string;
  categoryId?: number;
}

export class Word implements IWord {
  constructor(
    public id?: number,
    public word?: string,
    public description?: string,
    public imageUrl?: string,
    public categoryName?: string,
    public categoryId?: number
  ) {}
}
