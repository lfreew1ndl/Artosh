import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  WordComponentsPage,
  /* WordDeleteDialog, */
  WordUpdatePage,
} from './word.page-object';

const expect = chai.expect;

describe('Word e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let wordComponentsPage: WordComponentsPage;
  let wordUpdatePage: WordUpdatePage;
  /* let wordDeleteDialog: WordDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Words', async () => {
    await navBarPage.goToEntity('word');
    wordComponentsPage = new WordComponentsPage();
    await browser.wait(ec.visibilityOf(wordComponentsPage.title), 5000);
    expect(await wordComponentsPage.getTitle()).to.eq('artoshApp.word.home.title');
    await browser.wait(ec.or(ec.visibilityOf(wordComponentsPage.entities), ec.visibilityOf(wordComponentsPage.noResult)), 1000);
  });

  it('should load create Word page', async () => {
    await wordComponentsPage.clickOnCreateButton();
    wordUpdatePage = new WordUpdatePage();
    expect(await wordUpdatePage.getPageTitle()).to.eq('artoshApp.word.home.createOrEditLabel');
    await wordUpdatePage.cancel();
  });

  /* it('should create and save Words', async () => {
        const nbButtonsBeforeCreate = await wordComponentsPage.countDeleteButtons();

        await wordComponentsPage.clickOnCreateButton();

        await promise.all([
            wordUpdatePage.setWordInput('word'),
            wordUpdatePage.setDescriptionInput('description'),
            wordUpdatePage.setImageUrlInput('imageUrl'),
            wordUpdatePage.categorySelectLastOption(),
        ]);

        expect(await wordUpdatePage.getWordInput()).to.eq('word', 'Expected Word value to be equals to word');
        expect(await wordUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
        expect(await wordUpdatePage.getImageUrlInput()).to.eq('imageUrl', 'Expected ImageUrl value to be equals to imageUrl');

        await wordUpdatePage.save();
        expect(await wordUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await wordComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Word', async () => {
        const nbButtonsBeforeDelete = await wordComponentsPage.countDeleteButtons();
        await wordComponentsPage.clickOnLastDeleteButton();

        wordDeleteDialog = new WordDeleteDialog();
        expect(await wordDeleteDialog.getDialogTitle())
            .to.eq('artoshApp.word.delete.question');
        await wordDeleteDialog.clickOnConfirmButton();

        expect(await wordComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
