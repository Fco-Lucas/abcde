import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import type { ClientStatus } from '../../models/client.model';

@Component({
  selector: 'app-badge-status-client',
  imports: [CommonModule],
  templateUrl: './badge-status-client.component.html',
})
export class BadgeStatusClientComponent {
  @Input() status!: ClientStatus;
}
