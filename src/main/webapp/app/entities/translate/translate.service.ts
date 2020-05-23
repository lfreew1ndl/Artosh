import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITranslate } from 'app/shared/model/translate.model';

type EntityResponseType = HttpResponse<ITranslate>;
type EntityArrayResponseType = HttpResponse<ITranslate[]>;

@Injectable({ providedIn: 'root' })
export class TranslateService {
  public resourceUrl = SERVER_API_URL + 'api/translates';

  constructor(protected http: HttpClient) {}

  create(translate: ITranslate): Observable<EntityResponseType> {
    return this.http.post<ITranslate>(this.resourceUrl, translate, { observe: 'response' });
  }

  update(translate: ITranslate): Observable<EntityResponseType> {
    return this.http.put<ITranslate>(this.resourceUrl, translate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITranslate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITranslate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
