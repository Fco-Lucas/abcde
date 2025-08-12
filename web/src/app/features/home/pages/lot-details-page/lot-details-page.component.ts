import { Component, computed, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule, DecimalPipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent, MatPaginatorModule } from '@angular/material/paginator';
import { FormBuilder, FormGroup, FormArray, FormControl, ReactiveFormsModule, AbstractControl } from '@angular/forms';

// ✅ Imports do RxJS e Interop
import { toObservable } from '@angular/core/rxjs-interop';
import { switchMap, tap, catchError, of, filter, finalize, firstValueFrom } from 'rxjs';

// Models, Services, e Components
import { LotStateService } from '../../services/lot-state.service';
import { LotInterface, LotStatusEnum, LotUpdateInterface } from '../../models/lot.model';
import { lotImageInterface, LotImagePageableInterface, UpdateLotImageQuetionInterface, LotImageHashInterface } from '../../models/lot-image.models';
import { LotImageService } from '../../services/lot-image.service';
import { LotService } from '../../services/lot.service';
import { PermissionInterface } from '../../../permissions/models/permission.model';
import { ConfirmationDialogService } from '../../../../core/services/confirmation-dialog.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { HttpClient } from '@angular/common/http';

// Components de UI
import { ImagesListComponent } from '../../components/lotDetails/images-list/images-list.component';
import { DialogFilterImagesComponent, LotImagesFiltersData, LotImagesFiltersFormValues } from '../../components/lotDetails/dialog-filter-images/dialog-filter-images.component';
import { DialogProcessImagesComponent, ProcessImagesData } from '../../components/lotDetails/dialog-process-images/dialog-process-images.component';
import { DialogImageUploadProgressComponent, DialogImageUploadProgressData } from '../../components/dialog-image-upload-progress/dialog-image-upload-progress.component';
import { DialogUpdateLotComponent, DialogUpdateLotData, UpdateLotFormValues } from '../../components/lotDetails/dialog-update-lot/dialog-update-lot.component';
import { DialogAuditlogQuestionsComponent, DialogAuditLogQuestionData } from '../../components/lotDetails/dialog-auditlog-questions/dialog-auditlog-questions.component';
import { UiNotFoundComponent } from '../../../../shared/components/ui-not-found/ui-not-found.component';

// Material
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTabsModule } from '@angular/material/tabs';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UiErrorComponent } from '../../../../shared/components/ui-error/ui-error.component';
import { LoadingService } from '../../../../core/services/loading.service';

interface LotDetailsState {
  lot: LotInterface;
  userPermissions: PermissionInterface;
  lotImages: LotImagePageableInterface[];
  totalImages: number;
  loadingImages: boolean;
  selectedImageDetails: lotImageInterface | null;
  loadingImageDetails: boolean;
  answersForm: FormGroup;
  initialAnswers: (string[] | null)[];
  error: string | null;
}

interface ImagesQuery {
  filters: Partial<LotImagesFiltersFormValues>;
  pagination: PageEvent;
  reload: number;
}

@Component({
  selector: 'app-lot-details-page',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    CommonModule, MatCardModule, MatButtonModule, MatIconModule, ImagesListComponent,
    MatFormFieldModule, MatSelectModule, MatCheckboxModule, MatTabsModule,
    ReactiveFormsModule, MatMenuModule, RouterLink, MatDividerModule,
    UiNotFoundComponent, MatPaginatorModule, MatProgressSpinnerModule, UiErrorComponent
  ],
  templateUrl: './lot-details-page.component.html',
  styleUrl: './lot-details-page.component.scss'
})
export class LotDetailsPageComponent {
  private router = inject(Router);
  private lotService = inject(LotService);
  private lotStateService = inject(LotStateService);
  private lotImageService = inject(LotImageService);
  private confirmationDialogService = inject(ConfirmationDialogService);
  private notification = inject(NotificationService);
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);
  private fb = inject(FormBuilder);
  private formDirty = signal(false);
  private loader = inject(LoadingService);

  private imagesQuery = signal<ImagesQuery>({
    filters: {},
    pagination: { pageIndex: 0, pageSize: 10, length: 0 },
    reload: 0,
  });
  public selectedImageId = signal<number | null>(null);

  private state = signal<LotDetailsState>({
    lot: this.lotStateService.selectedLot()!,
    userPermissions: this.lotStateService.userPermissions()!,
    lotImages: [],
    totalImages: 0,
    loadingImages: true,
    selectedImageDetails: null,
    loadingImageDetails: false,
    answersForm: this.fb.group({ answers: this.fb.array([]) }),
    initialAnswers: [],
    error: null,
  });

  public readonly lot = computed(() => this.state().lot);
  public readonly userPermissions = computed(() => this.state().userPermissions);
  public readonly lotImages = computed(() => this.state().lotImages);
  public readonly totalImages = computed(() => this.state().totalImages);
  public readonly isLoadingImages = computed(() => this.state().loadingImages);
  public readonly selectedImageDetails = computed(() => this.state().selectedImageDetails);
  public readonly isLoadingImageDetails = computed(() => this.state().loadingImageDetails);
  public readonly answersForm = computed(() => this.state().answersForm);
  public readonly error = computed(() => this.state().error);
  public readonly pagination = computed(() => this.imagesQuery().pagination);
  public readonly isLotCompleted = computed(() => this.lot()?.status === LotStatusEnum.COMPLETED);

  public readonly viewState = computed<'loading' | 'error' | 'success'>(() => {
    if (this.isLoadingImages() && this.lotImages().length === 0) return 'loading';
    if (this.error()) return 'error';
    return 'success';
  });

  public get answersFormArray(): FormArray {
    return this.answersForm().get('answers') as FormArray;
  }
  
  public getQuestionFormGroup(index: number): FormGroup {
    return this.answersFormArray.at(index) as FormGroup;
  }

  public readonly hasUnansweredQuestions = computed<boolean>(() => {
    const details = this.selectedImageDetails();
    const answersFormArray = this.answersForm()?.get('answers') as FormArray | undefined;

    if (!details || !answersFormArray) {
      return false;
    }

    const requiredQuestionsCount = details.qtdQuestoes ?? 0;
    if (requiredQuestionsCount === 0) {
      return false;
    }

    const requiredControls = answersFormArray.controls.slice(0, requiredQuestionsCount);

    return requiredControls.some(questionControl => {
      const alternatives = (questionControl as FormGroup).value;
      return !Object.values(alternatives).some(isSelected => isSelected === true);
    });
  });

  constructor() {
    if (!this.lot() || !this.userPermissions()) {
      this.router.navigate(['/app/home']);
      return;
    }

    // STREAM 1: Reage a filtros/paginação para buscar a LISTA de imagens
    toObservable(this.imagesQuery).pipe(
      tap(() => this.state.update(s => ({ ...s, loadingImages: true, error: null }))),
      switchMap(query => this.lotImageService.getAllLotImages(
          query.pagination.pageIndex, query.pagination.pageSize,
          this.lot()!.id, query.filters.student
      ).pipe(
        catchError(err => {
          this.state.update(s => ({ ...s, loadingImages: false, error: "Falha ao carregar a lista de imagens." }));
          return of(null);
        })
      ))
    ).subscribe(response => {
      if (response) {
        this.state.update(s => ({
          ...s,
          lotImages: response.content,
          totalImages: response.totalElements,
          loadingImages: false
        }));
        
        const images = response.content;
        const currentSelectedId = this.selectedImageId();
        if (currentSelectedId === null && images.length > 0) {
          this.selectedImageId.set(images[0].id);
        } else if (currentSelectedId !== null && !images.find(img => img.id === currentSelectedId)) {
          this.selectedImageId.set(images.length > 0 ? images[0].id : null);
        }
      }
    });

    // STREAM 2: Reage à seleção de uma imagem para buscar seus DETALHES
    toObservable(this.selectedImageId).pipe(
      tap(() => this.state.update(s => ({ ...s, loadingImageDetails: true, selectedImageDetails: null }))),
      switchMap(id => {
        if (id === null) return of(null);
        return this.lotImageService.getLotImageById(this.lot()!.id, id).pipe(
          catchError(err => {
            this.notification.showError("Erro ao carregar detalhes da imagem.");
            this.state.update(s => ({ ...s, loadingImageDetails: false })); // erro não é fatal para a página
            return of(null);
          })
        );
      })
    ).subscribe(details => {
      this.state.update(s => ({ ...s, selectedImageDetails: details, loadingImageDetails: false }));
      if (details) {
        this.initializeAnswersForm(details);
      } else {
        this.state.update(s => ({ ...s, answersForm: this.fb.group({ answers: this.fb.array([]) }), initialAnswers: [] }));
      }
    });
  }

  onPageChange(event: PageEvent): void {
    this.imagesQuery.update(q => ({ ...q, pagination: event }));
  }

  onImageSelected(imageId: number): void {
    if (this.selectedImageId() !== imageId) {
      this.formDirty.set(false);
      this.selectedImageId.set(imageId);
    }
  }

  // Força o recarregamento da lista de imagens.
  forceImagesReload(options?: { pageIndex?: number }): void {
    this.imagesQuery.update(q => ({
      ...q,
      // Se um pageIndex for fornecido, usa-o. Caso contrário, mantém o pageIndex atual.
      pagination: { ...q.pagination, pageIndex: options?.pageIndex ?? q.pagination.pageIndex
      },
      reload: q.reload + 1
    }));
  }

  private initializeAnswersForm(details: lotImageInterface): void {
    const alternatives = ['A', 'B', 'C', 'D', 'E', 'Z'];
    const initialAnswers = details.questions.map(q => q.alternative ? q.alternative.split('') : []);

    const questionFormGroups = details.questions.map((question, qIndex) => {
      const formControls: { [key: string]: FormControl<boolean | null> } = {};
      alternatives.forEach(alt => {
        formControls[alt] = this.fb.control(initialAnswers[qIndex]?.includes(alt) ?? false);
      });
      return this.fb.group(formControls);
    });

    const answersForm = this.fb.group({ answers: this.fb.array(questionFormGroups) });

    if (this.isLotCompleted() || !this.userPermissions()?.upload_files || !details.presenca) {
      answersForm.disable();
    }

    answersForm.statusChanges.subscribe(() => {
      this.formDirty.set(answersForm.dirty);
    });
    
    this.state.update(s => ({ ...s, answersForm, initialAnswers }));
  }

  saveAnswers(): void {
    const answersForm = this.answersForm();
    if (this.isLotCompleted() || !answersForm.valid) return;

    const imageDetails = this.selectedImageDetails();
    if (!imageDetails) return;

    const changedQuestions = this.calculateChangedQuestions();
    if (changedQuestions.length === 0) {
      this.notification.showWarning("Nenhuma alteração para salvar.");
      return;
    }

    this.loader.showLoad("Salvando alterações...");

    this.lotImageService.updateLotImageQuestions(this.lot()!.id, imageDetails.id, changedQuestions)
      .pipe(
        finalize(() => this.loader.hideLoad())
      )
      .subscribe({
        next: () => {
          this.notification.showSuccess("Respostas salvas com sucesso!");
          answersForm.markAsPristine();
          this.formDirty.set(false);

          this.state.update(s => {
            // Encontra a imagem correspondente na lista principal
            const updatedLotImages = s.lotImages.map(image => 
              image.id === imageDetails.id 
                ? { ...image, haveModification: true } // 2. Atualiza a flag nela
                : image
            );

            return {
              ...s,
              lotImages: updatedLotImages, // A lista principal agora tem a informação
              selectedImageDetails: { ...imageDetails, haveModification: true }, // Os detalhes também
              initialAnswers: this.getCurrentAnswersAsArray()
            };
          });
        },
        error: (err) => this.notification.showError(err.message)
      });
  }

  private calculateChangedQuestions(): UpdateLotImageQuetionInterface[] {
    const changedQuestions: UpdateLotImageQuetionInterface[] = [];
    const answersFormArray = this.answersForm().get('answers') as FormArray;
    const initialAnswers = this.state().initialAnswers;
    const questions = this.selectedImageDetails()?.questions || [];

    answersFormArray.controls.forEach((control, index) => {
      const originalAlternatives = initialAnswers[index] || [];
      const currentSelected = Object.keys(control.value).filter(key => control.value[key]);
      if (originalAlternatives.join('') !== currentSelected.join('')) {
        changedQuestions.push({
          lotImageQuestionId: questions[index]?.id,
          alternative: currentSelected.join(''),
          previousAlternative: originalAlternatives.join('')
        });
      }
    });
    return changedQuestions;
  }
  
  private getCurrentAnswersAsArray(): (string[] | null)[] {
    const answersFormArray = this.answersForm().get('answers') as FormArray;
    return answersFormArray.value.map((q: any) => Object.keys(q).filter(key => q[key]));
  }

  onFilterSubmit(filters: LotImagesFiltersFormValues): void {
    this.selectedImageId.set(null);
    this.state.update(s => ({
      ...s,
      selectedImageDetails: null,
      answersForm: this.fb.group({ answers: this.fb.array([]) }),
      initialAnswers: []
    }));
    this.imagesQuery.update(q => ({ ...q, filters, pagination: { ...q.pagination, pageIndex: 0 } }));
  }

  openDialogFilterImages() {
    const dialogData: LotImagesFiltersData = {
      student: this.imagesQuery().filters.student ?? ""
    };

    const dialogRef = this.dialog.open(DialogFilterImagesComponent, {
      width: '500px',
      data: dialogData
    });

    // Controle ao fechar a dialog
    dialogRef.afterClosed().subscribe((result: LotImagesFiltersFormValues | undefined) => {
      if(!result) return;
      this.onFilterSubmit(result);
    });
  }

  public readonly shouldShowSaveFab = computed<boolean>(() => {
    const formDirty = this.formDirty();
    const isLotCompleted = this.isLotCompleted();
    const hasUnansweredQuestions = this.hasUnansweredQuestions();
    
    return formDirty && !isLotCompleted && !hasUnansweredQuestions;
  });

  get usedQuestions() {
    return this.selectedImageDetails()?.questions.slice(0, this.selectedImageDetails()?.qtdQuestoes ?? 90) || [];
  }

  getHashs(): LotImageHashInterface[] {
    let hashs: LotImageHashInterface[] = [];

    this.lotImageService.getHashs(this.lot().id).subscribe({
      next: (response) => hashs = response,
      error: (err) => console.error(`Ocorreu um erro ao buscar as hashs das imagens do lote: ${err.message}`)
    });

    return hashs;
  }

  async openDialogProcessImages() {
    if (this.isLotCompleted()) {
      this.notification.showWarning("Não é possível processar novas imagens em um lote finalizado.");
      return;
    }

    // Busca as hashs das imagens
    const hashs = await firstValueFrom(this.lotImageService.getHashs(this.lot().id));

    const dialogData: ProcessImagesData = {
      hashs: hashs
    }

    const dialogRef = this.dialog.open(DialogProcessImagesComponent, {
      width: "500px",
      data: dialogData,
    });

    dialogRef.afterClosed().subscribe(result => {
      if(!result) return;

      const { images } = result;

      this.openProgressDialog(images);
    });
  }

  private openProgressDialog(images: FileList): void {
    const dialogData: DialogImageUploadProgressData = { lotId: this.lot().id, images };

    const progressDialogRef = this.dialog.open(DialogImageUploadProgressComponent, {
      width: '500px',
      data: dialogData,
      disableClose: true
    });

    progressDialogRef.afterClosed().subscribe(() => {
      this.selectedImageId.set(null);
      this.forceImagesReload({ pageIndex: 0 });
      this.notification.showSuccess("Imagens processadas com sucesso!");
    });
  }

  finalizeLot(): void {
    if (this.isLotCompleted()) {
      this.notification.showWarning("Este lote já está finalizado.");
      return;
    }

    if (this.shouldShowSaveFab()) {
      this.notification.showWarning("Salve as alterações pendentes antes de finalizar o lote.");
      return;
    }

    const dialogData = {
      title: "Tem certeza?",
      message: `Você realmente deseja finalizar o lote com nome ${this.lot().name}? Esta ação não poderá ser desfeita.`,
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

    this.lotService.updateLot(this.lot().id, data).subscribe({
      next: (_) => {
        this.notification.showSuccess("Lote finalizado com sucesso!");
        this.state.update(s => ({ ...s,
          lot: {
            ...s.lot,
            status: LotStatusEnum.COMPLETED
          }
        }));
        this.answersForm().disable();
      },
      error: (err) => {
        this.notification.showError(err.message);
      }
    });
  }

  onDownloadTxt() {
    this.lotService.downloadTxt(this.lot().id).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `lote-${this.lot().name}.txt`;
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        this.notification.showError("Ocorreu um erro ao gerar o arquivo .txt do lote, tente novamente mais tarde!");
      }
    });
  }

  onDownloadLot() {
    this.loader.showLoad("Baixando imagens...");
    
    this.lotImageService.downloadAll(this.lot().id).pipe(
      finalize(() => {
        this.loader.hideLoad();
      })
    ).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `gabaritos_${this.lot().name}.zip`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => this.notification.showError(`Ocorreu um erro ao baixar as imagens do lote, tente novamente mais tarde`)
    });
  }
  
  onOpenDialogUpdateLot(): void {
    const dialogData: DialogUpdateLotData = {
      lotId: this.lot().id,
      lotName: this.lot().name
    };

    const dialogRef = this.dialog.open(DialogUpdateLotComponent, {
      width: '500px',
      data: dialogData
    });

    dialogRef.afterClosed().subscribe((result: UpdateLotFormValues | null) => {
      if(!result) return;

      this.state.update(s => ({
        ...s,
        lot: {
          ...s.lot,
          name: result.name
        }
      }));
    });
  }

  deleteLot(): void {
    const dialogData = {
      title: "Tem certeza?",
      message: `Você realmente deseja excluir o lote com nome ${this.lot().name}? Esta ação não poderá ser desfeita.`,
      confirmButtonText: 'Excluir'
    };

    this.confirmationDialogService.open(dialogData).subscribe(confirmed => {
      if(!confirmed) return;
      this.proceedWithLotDeletion();
    });
  }

  proceedWithLotDeletion(): void {
    this.lotService.deleteLot(this.lot().id).subscribe({
      next: (_) => {
        this.notification.showSuccess(`Lote excluído com sucesso`);
        this.router.navigate(["/app/home"]);
      },
      error: (err) => this.notification.showError(`Ocorreu um erro ao excluir o lote, tente novamente mais tarde`)
    });
  }

  onDownloadImage(): void {
    const selectedImage = this.selectedImageDetails();
    if (!selectedImage) return;

    const imageUrl = selectedImage.url;

    this.http.get(imageUrl, { responseType: 'blob' }).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = this.getFileNameFromUrl(imageUrl);
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  getFileNameFromUrl(url: string): string {
    return url.split('/').pop() || 'imagem.jpg';
  }

  deleteSelectedImage(): void {
    if (this.isLotCompleted()) {
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
    this.lotImageService.deleteLotImage(this.lot().id, lotImageId).subscribe({
      next: (_) => {
        if (this.selectedImageId() === lotImageId) {
          this.selectedImageId.set(null);
        }
        this.forceImagesReload();
        this.notification.showSuccess("Gabarito desativado com sucesso!");
      },
      error: (_) => {
        this.notification.showError("Ocorreu um erro ao excluir o gabarito, tente novamente mais tarde");
      }
    });
  }

  onOpenDialogAuditLogQuestions(imageId: number) {
    const dialogData: DialogAuditLogQuestionData = {
      imageId: imageId
    };

    this.dialog.open(DialogAuditlogQuestionsComponent, {
      width: '90vw',        // Ocupa 90% da largura da tela, garantindo responsividade.
      maxWidth: '1140px',
      data: dialogData
    });
  }
}
