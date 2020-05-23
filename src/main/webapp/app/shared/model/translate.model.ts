import { ILanguage } from 'app/shared/model/language.model';
import { IWord } from 'app/shared/model/word.model';

export interface ITranslate {
  id?: number;
  translate?: string;
  language?: ILanguage;
  word?: IWord;
}

export class Translate implements ITranslate {
  constructor(public id?: number, public translate?: string, public language?: ILanguage, public word?: IWord) {}
}
