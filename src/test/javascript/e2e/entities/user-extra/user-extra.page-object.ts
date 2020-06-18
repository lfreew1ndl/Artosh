import { element, by, ElementFinder } from 'protractor';

export class UserExtraComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-user-extra div table .btn-danger'));
  title = element.all(by.css('jhi-user-extra div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class UserExtraUpdatePage {
  pageTitle = element(by.id('jhi-user-extra-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  gramarScoreInput = element(by.id('field_gramarScore'));
  vocabularyScoreInput = element(by.id('field_vocabularyScore'));
  lisningScoreInput = element(by.id('field_lisningScore'));

  userSelect = element(by.id('field_user'));
  languageSelect = element(by.id('field_language'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setGramarScoreInput(gramarScore: string): Promise<void> {
    await this.gramarScoreInput.sendKeys(gramarScore);
  }

  async getGramarScoreInput(): Promise<string> {
    return await this.gramarScoreInput.getAttribute('value');
  }

  async setVocabularyScoreInput(vocabularyScore: string): Promise<void> {
    await this.vocabularyScoreInput.sendKeys(vocabularyScore);
  }

  async getVocabularyScoreInput(): Promise<string> {
    return await this.vocabularyScoreInput.getAttribute('value');
  }

  async setLisningScoreInput(lisningScore: string): Promise<void> {
    await this.lisningScoreInput.sendKeys(lisningScore);
  }

  async getLisningScoreInput(): Promise<string> {
    return await this.lisningScoreInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async languageSelectLastOption(): Promise<void> {
    await this.languageSelect.all(by.tagName('option')).last().click();
  }

  async languageSelectOption(option: string): Promise<void> {
    await this.languageSelect.sendKeys(option);
  }

  getLanguageSelect(): ElementFinder {
    return this.languageSelect;
  }

  async getLanguageSelectedOption(): Promise<string> {
    return await this.languageSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class UserExtraDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userExtra-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userExtra'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
