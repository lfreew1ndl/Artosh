export interface IUserExtra {
  id?: number;
  gramarScore?: number;
  vocabularyScore?: number;
  lisningScore?: number;
  userLogin?: string;
  userId?: number;
  languageName?: string;
  languageId?: number;
}

export class UserExtra implements IUserExtra {
  constructor(
    public id?: number,
    public gramarScore?: number,
    public vocabularyScore?: number,
    public lisningScore?: number,
    public userLogin?: string,
    public userId?: number,
    public languageName?: string,
    public languageId?: number
  ) {}
}
