import { Component, Input } from '@angular/core';
import { LotStatusEnum } from '../../models/lot.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lot-status-badge',
  imports: [
    CommonModule
  ],
  templateUrl: './lot-status-badge.component.html',
})
export class LotStatusBadgeComponent {
  @Input() status!: LotStatusEnum;
}
