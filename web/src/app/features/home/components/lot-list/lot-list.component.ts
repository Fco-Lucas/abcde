import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { LotInterface } from '../../models/lot.model';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { LotStatusBadgeComponent } from '../lot-status-badge/lot-status-badge.component';
import { UiNotFoundComponent } from '../../../../shared/components/ui-not-found/ui-not-found.component';

@Component({
  selector: 'app-lot-list',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinner,
    MatPaginatorModule,
    LotStatusBadgeComponent,
    UiNotFoundComponent
  ],
  templateUrl: './lot-list.component.html',
})
export class LotListComponent {
  @Input() lots: LotInterface[] = [];
  @Input() isLoading: boolean = false;
  @Input() totalElements: number = 0;
  @Input() pageSize: number = 10;
  @Input() pageIndex: number = 0;

  @Output() pageChange = new EventEmitter<PageEvent>();
  @Output() onClickedLot = new EventEmitter<LotInterface>();
}
