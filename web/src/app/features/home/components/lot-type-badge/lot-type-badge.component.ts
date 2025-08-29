import { Component, Input } from '@angular/core';
import { LotTypeEnum } from '../../models/lot.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lot-type-badge',
  imports: [
    CommonModule
  ],
  templateUrl: './lot-type-badge.component.html',
})
export class LotTypeBadgeComponent {
  @Input() type!: LotTypeEnum;
}
