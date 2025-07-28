import { Component, inject, signal, ViewChild, type OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { ImagesListComponent } from '../../components/lotDetails/images-list/images-list.component';
import { Router, RouterLink } from '@angular/router';
import { LotStateService } from '../../services/lot-state.service';
import { LotInterface, LotStatusEnum, type LotUpdateInterface } from '../../models/lot.model';
import { lotImageInterface, type UpdateLotImageQuetionInterface } from '../../models/lot-image.models';
import { MatDialog } from '@angular/material/dialog';
import { DialogFilterImagesComponent, type LotImagesFiltersData, type LotImagesFiltersFormValues } from '../../components/lotDetails/dialog-filter-images/dialog-filter-images.component';
import { ImagesListPaginationComponent } from '../../components/lotDetails/images-list-pagination/images-list-pagination.component';
import { LotImageService } from '../../services/lot-image.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTabsModule } from '@angular/material/tabs';
import { FormBuilder, FormControl, ReactiveFormsModule, type FormArray, type FormGroup, AbstractControl } from '@angular/forms'; // Importar AbstractControl
import { DecimalPipe } from '@angular/common';
import { MatMenuModule } from '@angular/material/menu';
import { DialogProcessImagesComponent } from '../../components/lotDetails/dialog-process-images/dialog-process-images.component';
import { DialogImageUploadProgressComponent, type DialogImageUploadProgressData } from '../../components/dialog-image-upload-progress/dialog-image-upload-progress.component';
import { ConfirmationDialogService } from '../../../../core/services/confirmation-dialog.service';
import { finalize } from 'rxjs';
import { NotificationService } from '../../../../core/services/notification.service';
import { LotService } from '../../services/lot.service';
import type { PermissionInterface } from '../../../permissions/models/permission.model';
import { DialogUpdateLotComponent, type DialogUpdateLotData, type UpdateLotFormValues } from '../../components/lotDetails/dialog-update-lot/dialog-update-lot.component';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-lot-details-page',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    ImagesListComponent,
    MatFormFieldModule,
    MatSelectModule,
    MatCheckboxModule,
    MatTabsModule,
    ImagesListPaginationComponent,
    ReactiveFormsModule,
    DecimalPipe,
    MatMenuModule,
    RouterLink,
    MatDividerModule
  ],
  templateUrl: './lot-details-page.component.html',
  styleUrl: './lot-details-page.component.scss'
})
export class LotDetailsPageComponent implements OnInit {
  private router = inject(Router);
  private lotService = inject(LotService);
  private lotStateService = inject(LotStateService);
  private lotImageService = inject(LotImageService);
  private confirmationDialogService = inject(ConfirmationDialogService);
  private notification = inject(NotificationService);

  readonly dialog = inject(MatDialog);
  private fb = inject(FormBuilder);
  public answersForm!: FormGroup;

  public lote!: LotInterface;
  public userPermissions!: PermissionInterface;
  public currentFilters = signal<Partial<LotImagesFiltersFormValues> | null>(null);

  public currentPage = signal<number>(0);
  public totalPages = signal<number>(0);
  public pageSize: number = 10;

  public selectedImageId = signal<number | null>(null);
  public selectedImageDetails = signal<lotImageInterface | null>(null);

  private initialAnswers: (string[] | null)[] = [];

  @ViewChild(ImagesListComponent) imagesListComponent!: ImagesListComponent;

  ngOnInit(): void {
    const selectedLot: LotInterface | null = this.lotStateService.selectedLot();
    const userPermissions: PermissionInterface | null = this.lotStateService.userPermissions();
    if(!selectedLot || !userPermissions) {
      this.router.navigate(['/app/home']);
      return;
    }
    this.lote = selectedLot;
    this.userPermissions = userPermissions;
    this.answersForm = this.fb.group({ answers: this.fb.array([]) });
  }

  get isLotCompleted(): boolean {
    return this.lote?.status === LotStatusEnum.COMPLETED;
  }

  openDialogFilterImages() {
    const dialogData: LotImagesFiltersData = {
      student: this.currentFilters()?.student ?? ""
    };

    const dialogRef = this.dialog.open(DialogFilterImagesComponent, {
      width: '500px',
      data: dialogData
    });

    dialogRef.afterClosed().subscribe((result: LotImagesFiltersFormValues | undefined) => {
      if(!result) return;
      this.currentFilters.set(result);
      this.currentPage.set(0);
      this.selectedImageId.set(null);
      this.selectedImageDetails.set(null);

      this.answersForm = this.fb.group({ answers: this.fb.array([]) });
      this.initialAnswers = [];
    });
  }

  onPageChange(newPage: number): void {
    this.currentPage.set(newPage);
  }

  onTotalPagesChange(newTotalPages: number): void {
    this.totalPages.set(newTotalPages);
  }

  onImagesLoaded(images: lotImageInterface[]): void {
      if (!this.selectedImageId() && images.length > 0) {
          this.onImageSelected(images[0].id);
      } else if (this.selectedImageId() && !images.find(img => img.id === this.selectedImageId())) {
          this.selectedImageId.set(null);
          this.selectedImageDetails.set(null);
          this.answersForm = this.fb.group({ answers: this.fb.array([]) });
          this.initialAnswers = [];
          if (images.length > 0) {
            this.onImageSelected(images[0].id);
          }
      }
  }

  onImageSelected(imageId: number): void {
    if (this.selectedImageId() === imageId) {
      return;
    }
    this.selectedImageId.set(imageId);
    this.loadSelectedImageDetails(imageId);
  }

  private loadSelectedImageDetails(imageId: number): void {
    this.selectedImageDetails.set(null);
    this.answersForm = this.fb.group({ answers: this.fb.array([]) });
    this.initialAnswers = [];

    this.lotImageService.getLotImageById(this.lote.id, imageId).subscribe({
      next: (details) => {
        this.selectedImageDetails.set(details);
        this.initializeAnswersForm(details.questions);
        if (this.isLotCompleted || !this.userPermissions.upload_files) {
          this.answersForm.disable();
        } else {
          this.answersForm.enable();
        }
      },
      error: (err) => {
        console.error('Erro ao carregar detalhes da imagem:', imageId, err);
        this.selectedImageDetails.set(null);
        this.answersForm = this.fb.group({ answers: this.fb.array([]) });
        this.initialAnswers = [];
        this.notification.showError("Erro ao carregar detalhes da imagem.");
      }
    });
  }

  private initializeAnswersForm(questions: any[]): void {
    const alternatives = ['A', 'B', 'C', 'D', 'E', 'Z'];

    this.initialAnswers = questions.map(question => {
      if (Array.isArray(question.alternative)) {
        return question.alternative;
      } else if (typeof question.alternative === 'string') {
        return question.alternative.split('');
      }
      return [];
    });

    const questionFormGroups = questions.map((question, qIndex) => {
      const formControls: { [key: string]: FormControl<boolean | null> } = {};
      alternatives.forEach(alt => {
        const isSelected = this.initialAnswers[qIndex]?.includes(alt) ?? false;
        formControls[alt] = this.fb.control(isSelected);
      });
      return this.fb.group(formControls);
    });

    this.answersForm = this.fb.group({
      answers: this.fb.array(questionFormGroups)
    });

    if (this.isLotCompleted || !this.userPermissions.upload_files) {
      this.answersForm.disable();
    }
  }

  get answersFormArray(): FormArray {
    return this.answersForm.get('answers') as FormArray;
  }

  getQuestionFormGroup(index: number): FormGroup {
    return this.answersFormArray.at(index) as FormGroup;
  }

  saveAnswers(): void {
    if (this.isLotCompleted) {
      this.notification.showWarning("Não é possível salvar respostas em um lote finalizado.");
      return;
    }

    if (!this.answersForm.valid) {
      this.notification.showError("Verifique as respostas. O formulário não é válido.");
      console.error('Formulário de respostas inválido.');
      return;
    }

    const selectedImageId = this.selectedImageId();
    if(!selectedImageId) {
      console.error(`ID da imagem selecionada não encontrada`);
      return;
    }
    const lotImageId: number = selectedImageId;

    const currentAnswersFormValue = this.answersForm.value.answers;
    const changedQuestions: UpdateLotImageQuetionInterface[] = [];

    this.answersFormArray.controls.forEach((control: AbstractControl, index) => { // CORREÇÃO AQUI: 'control: AbstractControl'
      const questionFormGroup = control as FormGroup; // CORREÇÃO AQUI: Casting para FormGroup
      const originalAlternatives = this.initialAnswers[index] || [];
      const currentAlternativesMap = questionFormGroup.value;
      const questionId = this.selectedImageDetails()?.questions[index]?.id;

      const currentSelectedAlternatives = Object.keys(currentAlternativesMap).filter(key => currentAlternativesMap[key]);

      const hasChanged = !(
        originalAlternatives.length === currentSelectedAlternatives.length &&
        originalAlternatives.every(alt => currentSelectedAlternatives.includes(alt))
      );

      if (questionId !== undefined && hasChanged) {
        changedQuestions.push({
          lotImageQuestionId: questionId,
          alternative: currentSelectedAlternatives.join('')
        });
      }
    });

    if (changedQuestions.length === 0) {
      this.notification.showWarning("Nenhuma alteração para salvar.");
      return;
    }

    this.lotImageService.updateLotImageQuestions(this.lote.id, lotImageId, changedQuestions).subscribe({
      next: () => {
        this.notification.showSuccess("Respostas salvas com sucesso!");
        this.answersForm.markAsPristine();
        this.initialAnswers = currentAnswersFormValue.map((q: any) =>
          Object.keys(q).filter(key => q[key])
        );
        const currentDetails = this.selectedImageDetails();
        if(currentDetails) {
          const updatedDetails = { ...currentDetails, haveModification: true };
          this.selectedImageDetails.set(updatedDetails);
        }
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  get shouldShowSaveFab(): boolean {
    return !!this.answersForm && this.answersForm.dirty && !this.isLotCompleted;
  }

  openDialogProcessImages() {
    if (this.isLotCompleted) {
      this.notification.showWarning("Não é possível processar novas imagens em um lote finalizado.");
      return;
    }

    const dialogRef = this.dialog.open(DialogProcessImagesComponent, {
      width: "500px",
    });

    dialogRef.afterClosed().subscribe(result => {
      if(!result) return;

      const { images } = result;

      this.openProgressDialog(images);
    });
  }

  private openProgressDialog(images: FileList): void {
    const dialogData: DialogImageUploadProgressData = { lotId: this.lote.id, images };

    const progressDialogRef = this.dialog.open(DialogImageUploadProgressComponent, {
      width: '500px',
      data: dialogData,
      disableClose: true
    });

    progressDialogRef.afterClosed().subscribe(() => {
      this.currentPage.set(0);
      this.selectedImageId.set(null);
      this.selectedImageDetails.set(null);
      this.answersForm = this.fb.group({ answers: this.fb.array([]) });
      this.initialAnswers = [];
      this.imagesListComponent.loadImages();
      this.notification.showSuccess("Imagens processadas com sucesso!");
    });
  }

  deleteSelectedImage(): void {
    if (this.isLotCompleted) {
      this.notification.showWarning("Não é possível excluir gabaritos de um lote finalizado.");
      return;
    }

    const selectedImage = this.selectedImageDetails();
    if (!selectedImage) {
      this.notification.showError("Nenhuma imagem selecionada para excluir.");
      return;
    }

    const dialogData = {
      title: 'Tem certeza?',
      message: `Você realmente deseja excluir o gabarito da matrícula ${selectedImage.matricula}? Esta ação não poderá ser desfeita.`,
      confirmButtonText: 'Excluir'
    };

    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if(!confirmed) return;
      this.proceedWithDeletion(selectedImage.id);
    });
  }

  private proceedWithDeletion(lotImageId: number): void {
    this.lotImageService.deleteLotImage(this.lote.id, lotImageId).pipe(
      finalize(() => {
        if (this.selectedImageId() === lotImageId) {
            this.selectedImageId.set(null);
            this.selectedImageDetails.set(null);
            this.answersForm = this.fb.group({ answers: this.fb.array([]) });
            this.initialAnswers = [];
        }
        this.imagesListComponent.loadImages();
      })
    ).subscribe({
      next: (_) => {
        this.notification.showSuccess("Gabarito desativado com sucesso!");
      },
      error: (_) => {
        this.notification.showError("Ocorreu um erro ao excluir o gabarito, tente novamente mais tarde");
      }
    });
  }

  finalizeLot(): void {
    if (this.isLotCompleted) {
      this.notification.showWarning("Este lote já está finalizado.");
      return;
    }

    if (this.shouldShowSaveFab) {
      this.notification.showWarning("Salve as alterações pendentes antes de finalizar o lote.");
      return;
    }

    const dialogData = {
      title: "Tem certeza?",
      message: `Você realmente deseja finalizar o lote com nome ${this.lote.name}? Esta ação não poderá ser desfeita.`,
      confirmButtonText: 'Finalizar'
    };

    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if(!confirmed) return;
      this.proceedWithFinalize();
    });
  }

  proceedWithFinalize(): void {
    const data: LotUpdateInterface = {
      status: LotStatusEnum.COMPLETED
    };

    this.lotService.updateLot(this.lote.id, data).subscribe({
      next: (_) => {
        this.notification.showSuccess("Lote finalizado com sucesso!");
        this.lote.status = LotStatusEnum.COMPLETED;
        this.answersForm.disable();
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  onDownloadTxt() {
    this.lotService.downloadTxt(this.lote.id).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `gabarito-lote-${this.lote.name}.txt`;
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        this.notification.showError("Ocorreu um erro ao gerar o arquivo .txt do lote, tente novamente mais tarde!");
      }
    });
  }

  onOpenDialogUpdateLot(): void {
    const dialogData: DialogUpdateLotData = {
      lotId: this.lote.id,
      lotName: this.lote.name
    };

    const dialogRef = this.dialog.open(DialogUpdateLotComponent, {
      width: '500px',
      data: dialogData
    });

    dialogRef.afterClosed().subscribe((result: UpdateLotFormValues | null) => {
      if(!result) return;

      this.lote = { ...this.lote, name: result.name }; // Atualiza o nome do lote no componente pai
    });
  }

  deleteLot(): void {
    const dialogData = {
      title: "Tem certeza?",
      message: `Você realmente deseja excluir o lote com nome ${this.lote.name}? Esta ação não poderá ser desfeita.`,
      confirmButtonText: 'Excluir'
    };

    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if(!confirmed) return;
      this.proceedWithLotDeletion();
    });
  }

  proceedWithLotDeletion(): void {
    this.lotService.deleteLot(this.lote.id).subscribe({
      next: (_) => {
        this.notification.showSuccess(`Lote excluído com sucesso`);
        this.router.navigate(["/app/home"])
      },
      error: (err) => {
        this.notification.showError(`Ocorreu um erro ao excluir o lote, tente novamente mais tarde`);
      }
    });
  }
}
