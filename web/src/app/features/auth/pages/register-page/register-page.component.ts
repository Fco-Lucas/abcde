import { Component, signal } from '@angular/core';
import { RegisterFormComponent } from '../../components/register-form/register-form.component';
import type { RegisterFormValues } from '../../models/register.model';

@Component({
  selector: 'app-register-page',
  imports: [RegisterFormComponent],
  templateUrl: './register-page.component.html',
})
export class RegisterPageComponent {
  public isLoading = signal(false);

  handleRegisterSubmit(formValues: RegisterFormValues): void {
    console.log(formValues);
  }
}
