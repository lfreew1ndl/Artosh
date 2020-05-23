import { element, by, ElementFinder } from 'protractor';

export class TranslateComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-translate div table .btn-danger'));
  title = element.all(by.css('jhi-translate div h2#page-heading span')).first();
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

export class TranslateUpdatePage {
  pageTitle = element(by.id('jhi-translate-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  translateInput = element(by.id('field_translate'));

  languageSelect = element(by.id('field_language'));
  wordSelect = element(by.id('field_word'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTranslateInput(translate: string): Promise<void> {
    await this.translateInput.sendKeys(translate);
  }

  async getTranslateInput(): Promise<string> {
    return await this.translateInput.getAttribute('value');
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

  async wordSelectLastOption(): Promise<void> {
    await this.wordSelect.all(by.tagName('option')).last().click();
  }

  async wordSelectOption(option: string): Promise<void> {
    await this.wordSelect.sendKeys(option);
  }

  getWordSelect(): ElementFinder {
    return this.wordSelect;
  }

  async getWordSelectedOption(): Promise<string> {
    return await this.wordSelect.element(by.css('option:checked')).getText();
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

export class TranslateDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-translate-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-translate'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
