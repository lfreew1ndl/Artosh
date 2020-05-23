import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TranslateComponentsPage,
  /* TranslateDeleteDialog, */
  TranslateUpdatePage,
} from './translate.page-object';

const expect = chai.expect;

describe('Translate e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let translateComponentsPage: TranslateComponentsPage;
  let translateUpdatePage: TranslateUpdatePage;
  /* let translateDeleteDialog: TranslateDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Translates', async () => {
    await navBarPage.goToEntity('translate');
    translateComponentsPage = new TranslateComponentsPage();
    await browser.wait(ec.visibilityOf(translateComponentsPage.title), 5000);
    expect(await translateComponentsPage.getTitle()).to.eq('artoshApp.translate.home.title');
    await browser.wait(ec.or(ec.visibilityOf(translateComponentsPage.entities), ec.visibilityOf(translateComponentsPage.noResult)), 1000);
  });

  it('should load create Translate page', async () => {
    await translateComponentsPage.clickOnCreateButton();
    translateUpdatePage = new TranslateUpdatePage();
    expect(await translateUpdatePage.getPageTitle()).to.eq('artoshApp.translate.home.createOrEditLabel');
    await translateUpdatePage.cancel();
  });

  /* it('should create and save Translates', async () => {
        const nbButtonsBeforeCreate = await translateComponentsPage.countDeleteButtons();

        await translateComponentsPage.clickOnCreateButton();

        await promise.all([
            translateUpdatePage.setTranslateInput('translate'),
            translateUpdatePage.languageSelectLastOption(),
            translateUpdatePage.wordSelectLastOption(),
        ]);

        expect(await translateUpdatePage.getTranslateInput()).to.eq('translate', 'Expected Translate value to be equals to translate');

        await translateUpdatePage.save();
        expect(await translateUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await translateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Translate', async () => {
        const nbButtonsBeforeDelete = await translateComponentsPage.countDeleteButtons();
        await translateComponentsPage.clickOnLastDeleteButton();

        translateDeleteDialog = new TranslateDeleteDialog();
        expect(await translateDeleteDialog.getDialogTitle())
            .to.eq('artoshApp.translate.delete.question');
        await translateDeleteDialog.clickOnConfirmButton();

        expect(await translateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
