import { Component, inject, OnInit, ViewChild, signal, Input, type OnChanges, type SimpleChanges, Output, EventEmitter, ChangeDetectionStrategy, computed, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs';

// Importações do Angular Material
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { Client } from '../../models/client.model';
import { NgxMaskPipe } from 'ngx-mask';
import { MatMenuModule } from '@angular/material/menu';
import { RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-client-list',
  imports: [
    CommonModule,
    NgxMaskPipe,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    RouterLink
  ],
  templateUrl: './client-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ClientListComponent implements OnChanges {
  private authService = inject(AuthService);

  public authUserId = toSignal(this.authService.currentUserId$);

  @Input() clients: Client[] = [];
  @Input() isLoading: boolean = false;
  @Input() totalElements: number = 0;
  @Input() pageSize: number = 10;
  @Input() pageIndex: number = 0;
  @Input() isComputexClientUser: boolean = false;
  @Input() authClientCnpj: string | null = null;

  @Output() pageChange = new EventEmitter<PageEvent>();
  @Output() updateClient = new EventEmitter<Client>();
  @Output() updateComputexPostUrl = new EventEmitter<Client>();
  @Output() deleteClient = new EventEmitter<Client>();
  @Output() restoreClient = new EventEmitter<Client>();
  @Output() restorePassword = new EventEmitter<Client>();

  public displayedColumns: string[] = ['name', 'cnpj', 'status', ' '];
  public dataSource = new MatTableDataSource<Client>();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['clients']) {
      this.dataSource.data = this.clients ?? [];
    }
  }
}