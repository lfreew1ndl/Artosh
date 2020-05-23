import { element, by, ElementFinder } from 'protractor';

export class WordComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-word div table .btn-danger'));
  title = element.all(by.css('jhi-word div h2#page-heading span')).first();
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

export class WordUpdatePage {
  pageTitle = element(by.id('jhi-word-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  wordInput = element(by.id('field_word'));
  descriptionInput = element(by.id('field_description'));
  imageUrlInput = element(by.id('field_imageUrl'));

  categorySelect = element(by.id('field_category'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setWordInput(word: string): Promise<void> {
    await this.wordInput.sendKeys(word);
  }

  async getWordInput(): Promise<string> {
    return await this.wordInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setImageUrlInput(imageUrl: string): Promise<void> {
    await this.imageUrlInput.sendKeys(imageUrl);
  }

  async getImageUrlInput(): Promise<string> {
    return await this.imageUrlInput.getAttribute('value');
  }

  async categorySelectLastOption(): Promise<void> {
    await this.categorySelect.all(by.tagName('option')).last().click();
  }

  async categorySelectOption(option: string): Promise<void> {
    await this.categorySelect.sendKeys(option);
  }

  getCategorySelect(): ElementFinder {
    return this.categorySelect;
  }

  async getCategorySelectedOption(): Promise<string> {
    return await this.categorySelect.element(by.css('option:checked')).getText();
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

export class WordDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-word-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-word'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
