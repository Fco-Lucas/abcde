import { ChangeDetectionStrategy, Component, Inject, inject, Input, type OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

// Importações do Material e ngx-mask
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgxMaskDirective } from 'ngx-mask';
import { cnpjValidator } from '../../../../shared/utils/custom-validators';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ClientService } from '../../services/client.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { delay, finalize, of } from 'rxjs';
import type { CreateClientInterface } from '../../models/client.model';
import { MatSelectModule } from '@angular/material/select';
import { HttpUtilsRequestsService } from '../../../../core/services/http-utils-requests.service';

// --- Interface para os dados do formulário ---
export interface CreateFormValues {
  customerComputex: string;
  numberContract: number|null;
  name: string;
  email: string;
  cnpj: string;
  // password: string;
  urlToPost: string;
  imageActiveDays: number;
}

@Component({
  selector: 'app-dialog-create-client',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NgxMaskDirective,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatSelectModule
  ],
  templateUrl: './dialog-create-client.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DialogCreateClientComponent implements OnInit {
  // --- Injeção de Dependências ---
  private fb = inject(FormBuilder);
  readonly dialogRef = inject(MatDialogRef<DialogCreateClientComponent>);
  private clientService = inject(ClientService);
  private notification = inject(NotificationService);
  private httpUtilsRequestsService = inject(HttpUtilsRequestsService);
  showContractField = false;

  // constructor(@Inject(MAT_DIALOG_DATA) public initialData: CreateFormValues | null) {}

  public isLoading = signal(false);

  // --- Formulário Reativo ---
  createForm = this.fb.group({
    customerComputex: ["S", [Validators.required, Validators.minLength(1), Validators.maxLength(1)]],
    numberContract: ['', [Validators.required]],
    name: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    cnpj: ['', [Validators.required, Validators.minLength(18), Validators.maxLength(18)]],
    // password: ['', [Validators.required, Validators.minLength(6)]],
    urlToPost: ['', []],
    imageActiveDays: [30, [Validators.required]]
  });

  ngOnInit(): void {
    this.cnpjControl?.valueChanges.subscribe(value => {
      this.updateCnpjValidators(value);
    });

    this.onCustomerComputexChange(this.createForm.get('customerComputex')?.value ?? "N");
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

    const numberContractControl = this.createForm.get('numberContract');

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
    const numberContractControl = this.createForm.get('numberContract'); // Lembre-se de usar o nome correto do seu formGroup

    if(!numberContractControl) {
      console.error('NumberContractControl não identificado');
      this.createForm.patchValue({ urlToPost: '' });
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
          this.createForm.patchValue({ urlToPost: '' });
          return;
        }

        const data = response[0];
        const usa_nuvens = Number(data.usa_nuvens);
        const baseUrl = usa_nuvens === 1 ? data.url_interno : data.url_externo;
        const finalUrl = `${baseUrl}webhook/retornoAsaas.php`;
        
        this.createForm.patchValue({ urlToPost: finalUrl });
      },
      error: (err) => {
        console.error(`Erro ao obter informações do contrato digitado: ${err.message}`);
        this.createForm.patchValue({ urlToPost: '' });
      }
    });
  }

  get customerComputexControl() { return this.createForm.get("customerComputex"); }
  get numberContractControl() { return this.createForm.get("numberContract"); }
  get nameControl() { return this.createForm.get("name"); }
  get emailControl() { return this.createForm.get("email"); }
  get cnpjControl() { return this.createForm.get('cnpj'); }
  // get passwordControl() { return this.createForm.get('password'); }
  get urlToPostControl() { return this.createForm.get('urlToPost'); }
  get imageActiveDaysControl() { return this.createForm.get('imageActiveDays'); }

  hidePassword = signal(true);
  togglePasswordVisibility() {
    this.hidePassword.set(!this.hidePassword());
  }

  onSubmit(): void {
    if (!this.createForm.valid) {
      this.createForm.markAllAsTouched();
      return;
    }

    // 2. Inicia o estado de carregamento
    this.isLoading.set(true);

    // Desabilita o formulário durante a chamada para evitar edições
    this.createForm.disable();

    const formValues = this.createForm.getRawValue() as CreateFormValues;

    const correctCustomerComputex = formValues.customerComputex === "S" ? true : false;
    const correctNumberContract = formValues.customerComputex === "S" ? formValues.numberContract : null;
    const createClientData: CreateClientInterface = { ...formValues, customerComputex: correctCustomerComputex, numberContract: correctNumberContract };
    
    // 3. A chamada à API é feita AQUI
    this.clientService.createClient(createClientData).pipe(
      // O finalize reabilita o form e desliga o spinner
      finalize(() => {
        this.isLoading.set(false);
        this.createForm.enable();
      })
    ).subscribe({
      next: () => {
        this.notification.showSuccess("Cliente criado com sucesso!");
        this.dialogRef.close(true); 
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  isSubmitButtonDisabled(): boolean {
    return this.createForm.invalid || this.isLoading();
  }
}