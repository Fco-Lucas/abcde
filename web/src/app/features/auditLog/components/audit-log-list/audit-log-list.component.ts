import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import moment from 'moment';
import { AuditLogAction, AuditLogInterface, AuditLogProgram } from '../../models/audit-log.model';

@Component({
  selector: 'app-audit-log-list',
  standalone: true, // Adicionar standalone: true
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatTableModule
  ],
  templateUrl: './audit-log-list.component.html',
  styleUrl: './audit-log-list.component.scss'
})
export class AuditLogListComponent {
  formatToBr(datetime: string | null): string {
    if (!datetime) return '-';
    return moment(datetime).format('DD/MM/YYYY HH:mm');
  }

  formatActionToBr(action: AuditLogAction): string {
    switch(action) {
      case "CREATE":
        return "Criação";
      case "UPDATE":
        return "Atualização";
      case "DELETE":
        return "Exclusão";
      case "RESTORE":
        return "Restaurar";
      case "LOGIN":
        return "Login";
      case "PROCESSED":
        return "Upload de imagens";
      case "DOWNLOADTXT":
        return "Baixar .txt"
      default:
        return '-';
    }
  }
  
  formatProgramToBr(program: AuditLogProgram): string {
    switch(program) {
      case "CLIENT":
        return "Clientes";
      case "CLIENT_USER":
        return "Usuário do clientes";
      case "LOT":
        return "Lotes";
      case "LOT_IMAGE":
        return "Gabaritos";
      case "AUTH":
        return "Autenticação";
      default:
        return '-';
    }
  }

  public displayedColumns: string[] = ['createdAt', 'client', 'user', 'action', 'program', 'details'];
  
  public dataSource = new MatTableDataSource<AuditLogInterface>();
  
  // 2. @Inputs para receber todo o estado do pai
  @Input() set data(entries: AuditLogInterface[]) {
    this.dataSource.data = entries;
  }
  @Input() totalElements = 0;
  @Input() pageSize = 10;
  @Input() pageIndex = 0;
  @Input() isLoading = false;
  @Input() error: string | null = null;

  @Output() pageChange = new EventEmitter<PageEvent>();
}