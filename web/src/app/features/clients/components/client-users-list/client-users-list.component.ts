import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ClientUserInterface } from '../../models/clientUsers.model';
import { BadgeStatusClientUserComponent } from '../badge-status-client-user/badge-status-client-user.component';

@Component({
  selector: 'app-client-users-list',
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    BadgeStatusClientUserComponent
  ],
  templateUrl: './client-users-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ClientUsersListComponent {
  @Input() clientUsers: ClientUserInterface[] = [];
  @Input() isLoading: boolean = false;
  @Input() totalElements: number = 0;
  @Input() pageSize: number = 10;
  @Input() pageIndex: number = 0;

  @Output() pageChange = new EventEmitter<PageEvent>();
  @Output() updateClientUser = new EventEmitter<ClientUserInterface>();
  @Output() deleteClientUser = new EventEmitter<ClientUserInterface>();
  @Output() restoreClientUser = new EventEmitter<ClientUserInterface>();
  @Output() restorePassword = new EventEmitter<ClientUserInterface>();

  public displayedColumns: string[] = ['name', 'email', 'permission', 'status', ' '];
  public dataSource = new MatTableDataSource<ClientUserInterface>();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['clientUsers']) {
      this.dataSource.data = this.clientUsers ?? [];
    }
  }
}
