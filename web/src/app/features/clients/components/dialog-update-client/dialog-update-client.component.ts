import { CommonModule } from '@angular/common';
import { Component, Inject, inject, signal, type OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators, type FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { cnpjValidator } from '../../../../shared/utils/custom-validators';
import { NgxMaskDirective } from 'ngx-mask';
import { NotificationService } from '../../../../core/services/notification.service';
import { ClientService } from '../../services/client.service';
import { finalize } from 'rxjs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';
import { HttpUtilsRequestsService } from '../../../../core/services/http-utils-requests.service';
import { MatSelectModule } from '@angular/material/select';
import type { UpdateClientInterface } from '../../models/client.model';

export interface UpdateClientFormValues {
  customerComputex: string;
  numberContract: number|null;
  name: string;
  email: string;
  cnpj: string;
  urlToPost: string;
  imageActiveDays: number;
}

export interface DialogUpdateClientData {
  clientId: string;
  data: UpdateClientFormValues;  
}

@Component({
  selector: 'app-dialog-update-client.component',
  imports: [
    CommonModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDialogModule,
    NgxMaskDirective,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatIconModule,
    MatSelectModule
  ],
  templateUrl: './dialog-update-client.component.html',
})
export class DialogUpdateClientComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<DialogUpdateClientComponent>);
  private clientService = inject(ClientService);  
  private notification = inject(NotificationService);
  updateForm!: FormGroup;
  private httpUtilsRequestsService = inject(HttpUtilsRequestsService);
  showContractField = false;

  public clientId!: string;
  public initialData!: UpdateClientFormValues;
  public isLoading = signal(false);

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogUpdateClientData) {} 

  ngOnInit(): void {
    this.clientId = this.data.clientId;
    this.initialData = this.data.data;

    this.updateForm = this.fb.group({
      customerComputex: [this.initialData.customerComputex, [Validators.required, Validators.minLength(1), Validators.maxLength(1)]],
      numberContract: [this.initialData.numberContract, [Validators.required]],
      name: [this.initialData.name, [Validators.required]],
      email: [this.initialData.email, [Validators.required, Validators.email]],
      cnpj: [this.initialData.cnpj, [Validators.required, Validators.minLength(18), Validators.maxLength(18)]],
      urlToPost: [this.initialData.urlToPost, []],
      imageActiveDays: [this.initialData.imageActiveDays, [Validators.required]]
    });

    this.updateCnpjValidators(this.cnpjControl?.value);

    this.cnpjControl?.valueChanges.subscribe(value => {
      this.updateCnpjValidators(value);
    });

    this.onCustomerComputexChange(this.updateForm.get('customerComputex')?.value ?? "N");
  }

  updateCnpjValidators(value: string | null): void {
    const onlyNumbers = value?.replace(/\D/g, '') || '';
    
    if (onlyNumbers.length === 14) {
      this.cnpjControl?.setValidators([Validators.required, cnpjValidator()]);
    } else {
      this.cnpjControl?.setValidators([Validators.required, Validators.minLength(18), Validators.maxLength(18)]);
    }
    this.cnpjControl?.updateValueAndValidity({ emitEvent: false });
  }

  onCustomerComputexChange(value: string) {
    this.showContractField = value === 'S';

    const numberContractControl = this.updateForm.get('numberContract');

    if (this.showContractField) {
      // Ativa e exige preenchimento
      numberContractControl?.setValidators([
        Validators.required,
      ]);
      numberContractControl?.enable();
    } else {
      // Reseta e desativa
      numberContractControl?.reset();
      numberContractControl?.clearValidators();
      numberContractControl?.disable();
    }

    numberContractControl?.updateValueAndValidity();
  }

  onContractBlur(): void {
    // Pega o controle do formulário para acessar o valor
    const numberContractControl = this.updateForm.get('numberContract'); // Lembre-se de usar o nome correto do seu formGroup

    if(!numberContractControl) {
      console.error('NumberContractControl não identificado');
      this.updateForm.patchValue({ urlToPost: '' });
      return;
    }

    const currentValue = Number(numberContractControl.value);
    if (!currentValue) {
      return;
    }

    this.httpUtilsRequestsService.getUrls(currentValue).subscribe({
      next: (response) => {
        if(!response || response.length == 0) {
          console.log(response);
          console.error(`Informações não encontradas para o contrato digitado`);
          this.updateForm.patchValue({ urlToPost: '' });
          return;
        }

        const data = response[0];
        const usa_nuvens = Number(data.usa_nuvens);
        const baseUrl = usa_nuvens === 1 ? data.url_interno : data.url_externo;
        const finalUrl = `${baseUrl}webhook/retornoAsaas.php`;
        
        this.updateForm.patchValue({ urlToPost: finalUrl });
      },
      error: (err) => {
        console.error(`Erro ao obter informações do contrato digitado: ${err.message}`);
        this.updateForm.patchValue({ urlToPost: '' });
      }
    });
  }

  get customerComputexControl() { return this.updateForm.get("customerComputex"); }
  get numberContractControl() { return this.updateForm.get("numberContract"); }
  get nameControl() { return this.updateForm.get("name"); }
  get emailControl() { return this.updateForm.get("email"); }
  get cnpjControl() { return this.updateForm.get('cnpj'); }
  get urlToPostControl() { return this.updateForm.get('urlToPost'); }
  get imageActiveDaysControl() { return this.updateForm.get('imageActiveDays'); }

  onSubmit(): void {
    if (!this.updateForm.valid) {
      this.updateForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.updateForm.disable();

    const formValues = this.updateForm.getRawValue() as UpdateClientFormValues;

    const correctCustomerComputex = formValues.customerComputex === "S" ? true : false;
    const correctNumberContract = formValues.customerComputex === "S" ? formValues.numberContract : null;
    const updateClientData: UpdateClientInterface = { ...formValues, customerComputex: correctCustomerComputex, numberContract: correctNumberContract };

    this.clientService.updateClient(this.clientId, updateClientData).pipe(
      finalize(() => {
        this.isLoading.set(false);
        this.updateForm.enable();
      })
    ).subscribe({
      next: () => {
        this.notification.showSuccess("Informações do cliente atualizadas com sucesso!");
        this.dialogRef.close(true);
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  isSubmitButtonDisabled(): boolean {
    return this.updateForm.invalid;
  }
}
