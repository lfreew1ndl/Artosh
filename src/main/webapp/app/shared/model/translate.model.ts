export interface ITranslate {
  id?: number;
  translate?: string;
  languageName?: string;
  languageId?: number;
  wordWord?: string;
  wordId?: number;
}

export class Translate implements ITranslate {
  constructor(
    public id?: number,
    public translate?: string,
    public languageName?: string,
    public languageId?: number,
    public wordWord?: string,
    public wordId?: number
  ) {}
}
