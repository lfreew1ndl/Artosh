import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ArtoshSharedModule } from 'app/shared/shared.module';
import { ArtoshCoreModule } from 'app/core/core.module';
import { ArtoshAppRoutingModule } from './app-routing.module';
import { ArtoshHomeModule } from './home/home.module';
import { ArtoshEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { ArtoshGameModule } from 'app/game/game.module';
import { GameListComponent } from './game/game-list/game-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    BrowserModule,
    ArtoshSharedModule,
    ArtoshCoreModule,
    ArtoshHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ArtoshEntityModule,
    ArtoshGameModule,
    ArtoshAppRoutingModule,
    BrowserAnimationsModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class ArtoshAppModule {}
