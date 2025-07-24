import { Component, inject, signal, type OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { ImagesListComponent } from '../../components/lotDetails/images-list/images-list.component';
import { Router } from '@angular/router';
import { LotStateService } from '../../services/lot-state.service';
import { LotInterface } from '../../models/lot.model';
import { lotImageInterface } from '../../models/lot-image.models';
import { MatDialog } from '@angular/material/dialog';
import { DialogFilterImagesComponent, type LotImagesFiltersData, type LotImagesFiltersFormValues } from '../../components/lotDetails/dialog-filter-images/dialog-filter-images.component';
import { ImagesListPaginationComponent } from '../../components/lotDetails/images-list-pagination/images-list-pagination.component';
import { LotImageService } from '../../services/lot-image.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatTabsModule } from '@angular/material/tabs';
import { FormBuilder, FormControl, ReactiveFormsModule, type FormArray, type FormGroup } from '@angular/forms';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-lot-details-page',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    ImagesListComponent,
    MatFormFieldModule,
    MatSelectModule,
    MatRadioModule,
    MatTabsModule,
    ImagesListPaginationComponent,
    ReactiveFormsModule,
    DecimalPipe
  ],
  templateUrl: './lot-details-page.component.html',
})
export class LotDetailsPageComponent implements OnInit {
  private router = inject(Router);
  private lotStateService = inject(LotStateService);
  private lotImageService = inject(LotImageService);
  readonly dialog = inject(MatDialog);
  private fb = inject(FormBuilder);
  public answersForm!: FormGroup;

  public lote!: LotInterface;
  public currentFilters = signal<Partial<LotImagesFiltersFormValues> | null>(null);

  // Estado da paginação
  public currentPage = signal<number>(0); // Começa na página 0 (primeira página)
  public totalPages = signal<number>(0); // Total de páginas, atualizado pelo ImagesListComponent
  public pageSize: number = 10; // Tamanho da página, pode ser configurado aqui

  public selectedImageId = signal<number | null>(null);
  public selectedImageDetails = signal<lotImageInterface | null>(null);

  ngOnInit(): void {
    const selectedLot: LotInterface | null = this.lotStateService.selectedLot();
    if(!selectedLot) {
      this.router.navigate(['/app/home']);
      return;
    }
    this.lote = selectedLot;
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
    });
  }

  onPageChange(newPage: number): void {
    this.currentPage.set(newPage);
  }

  onTotalPagesChange(newTotalPages: number): void {
    this.totalPages.set(newTotalPages);
  }

  onImageSelected(imageId: number): void {
    if (this.selectedImageId() === imageId) {
      return;
    }
    this.selectedImageId.set(imageId);
    this.loadSelectedImageDetails(imageId);
  }

  private loadSelectedImageDetails(imageId: number): void {
    this.selectedImageDetails.set(null); // Limpa detalhes antigos enquanto carrega
    this.lotImageService.getLotImageById(this.lote.id, imageId).subscribe({
      next: (details) => {
        this.selectedImageDetails.set(details);
        this.initializeAnswersForm(details.questions);
        // Opcional: Aqui você pode fazer algo com os detalhes, como scrolar para a imagem
      },
      error: (err) => {
        console.error('Erro ao carregar detalhes da imagem:', imageId, err);
        this.selectedImageDetails.set(null); // Limpa em caso de erro
        // Exibir mensagem de erro ao usuário
      }
    });
  }

  private initializeAnswersForm(questions: any[]): void {
    const answerControls = questions.map(question =>
      // Assumindo que 'question.answer' é a propriedade que guarda a resposta inicial
      new FormControl(question.alternative || null)
    );
    this.answersForm = this.fb.group({
      answers: this.fb.array(answerControls)
    });
  }

   // Getter para acessar o FormArray no template
  get answersFormArray(): FormArray {
    return this.answersForm.get('answers') as FormArray;
  }

  // Getter para acessar um FormControl individual no template
  getAnswerFormControl(index: number): FormControl {
    return this.answersFormArray.at(index) as FormControl;
  }

  // Você também precisará de um método para submeter as respostas alteradas
  saveAnswers(): void {
    if (this.answersForm.valid) {
      const updatedAnswers = this.answersForm.value.answers;
      console.log('Respostas a serem salvas:', updatedAnswers);
      // Chame seu serviço para enviar essas respostas para o backend
      // Ex: this.lotImageService.updateAnswers(this.selectedImageId(), updatedAnswers).subscribe(...)
    } else {
      console.error('Formulário de respostas inválido.');
    }
  }
}
