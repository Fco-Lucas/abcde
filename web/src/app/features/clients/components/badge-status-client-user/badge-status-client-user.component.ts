import { Component, Input } from '@angular/core';
import type { ClientUserStatus } from '../../models/clientUsers.model';

@Component({
  selector: 'app-badge-status-client-user',
  imports: [],
  templateUrl: './badge-status-client-user.component.html',
})
export class BadgeStatusClientUserComponent {
  @Input() status!: ClientUserStatus;
}
