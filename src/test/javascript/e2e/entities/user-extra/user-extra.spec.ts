import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserExtraComponentsPage,
  /* UserExtraDeleteDialog, */
  UserExtraUpdatePage,
} from './user-extra.page-object';

const expect = chai.expect;

describe('UserExtra e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userExtraComponentsPage: UserExtraComponentsPage;
  let userExtraUpdatePage: UserExtraUpdatePage;
  /* let userExtraDeleteDialog: UserExtraDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserExtras', async () => {
    await navBarPage.goToEntity('user-extra');
    userExtraComponentsPage = new UserExtraComponentsPage();
    await browser.wait(ec.visibilityOf(userExtraComponentsPage.title), 5000);
    expect(await userExtraComponentsPage.getTitle()).to.eq('artoshApp.userExtra.home.title');
    await browser.wait(ec.or(ec.visibilityOf(userExtraComponentsPage.entities), ec.visibilityOf(userExtraComponentsPage.noResult)), 1000);
  });

  it('should load create UserExtra page', async () => {
    await userExtraComponentsPage.clickOnCreateButton();
    userExtraUpdatePage = new UserExtraUpdatePage();
    expect(await userExtraUpdatePage.getPageTitle()).to.eq('artoshApp.userExtra.home.createOrEditLabel');
    await userExtraUpdatePage.cancel();
  });

  /* it('should create and save UserExtras', async () => {
        const nbButtonsBeforeCreate = await userExtraComponentsPage.countDeleteButtons();

        await userExtraComponentsPage.clickOnCreateButton();

        await promise.all([
            userExtraUpdatePage.setGramarScoreInput('5'),
            userExtraUpdatePage.setVocabularyScoreInput('5'),
            userExtraUpdatePage.setLisningScoreInput('5'),
            userExtraUpdatePage.userSelectLastOption(),
            userExtraUpdatePage.languageSelectLastOption(),
        ]);

        expect(await userExtraUpdatePage.getGramarScoreInput()).to.eq('5', 'Expected gramarScore value to be equals to 5');
        expect(await userExtraUpdatePage.getVocabularyScoreInput()).to.eq('5', 'Expected vocabularyScore value to be equals to 5');
        expect(await userExtraUpdatePage.getLisningScoreInput()).to.eq('5', 'Expected lisningScore value to be equals to 5');

        await userExtraUpdatePage.save();
        expect(await userExtraUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userExtraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserExtra', async () => {
        const nbButtonsBeforeDelete = await userExtraComponentsPage.countDeleteButtons();
        await userExtraComponentsPage.clickOnLastDeleteButton();

        userExtraDeleteDialog = new UserExtraDeleteDialog();
        expect(await userExtraDeleteDialog.getDialogTitle())
            .to.eq('artoshApp.userExtra.delete.question');
        await userExtraDeleteDialog.clickOnConfirmButton();

        expect(await userExtraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
