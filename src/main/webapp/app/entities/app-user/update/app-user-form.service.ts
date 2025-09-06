import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAppUser, NewAppUser } from '../app-user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppUser for edit and NewAppUserFormGroupInput for create.
 */
type AppUserFormGroupInput = IAppUser | PartialWithRequiredKeyOf<NewAppUser>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAppUser | NewAppUser> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type AppUserFormRawValue = FormValueOf<IAppUser>;

type NewAppUserFormRawValue = FormValueOf<NewAppUser>;

type AppUserFormDefaults = Pick<NewAppUser, 'id' | 'createdAt' | 'updatedAt'>;

type AppUserFormGroupContent = {
  id: FormControl<AppUserFormRawValue['id'] | NewAppUser['id']>;
  fullName: FormControl<AppUserFormRawValue['fullName']>;
  email: FormControl<AppUserFormRawValue['email']>;
  passwordHash: FormControl<AppUserFormRawValue['passwordHash']>;
  role: FormControl<AppUserFormRawValue['role']>;
  createdAt: FormControl<AppUserFormRawValue['createdAt']>;
  updatedAt: FormControl<AppUserFormRawValue['updatedAt']>;
};

export type AppUserFormGroup = FormGroup<AppUserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppUserFormService {
  createAppUserFormGroup(appUser: AppUserFormGroupInput = { id: null }): AppUserFormGroup {
    const appUserRawValue = this.convertAppUserToAppUserRawValue({
      ...this.getFormDefaults(),
      ...appUser,
    });
    return new FormGroup<AppUserFormGroupContent>({
      id: new FormControl(
        { value: appUserRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fullName: new FormControl(appUserRawValue.fullName, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      email: new FormControl(appUserRawValue.email, {
        validators: [Validators.required, Validators.minLength(5), Validators.maxLength(100)],
      }),
      passwordHash: new FormControl(appUserRawValue.passwordHash, {
        validators: [Validators.required, Validators.minLength(60), Validators.maxLength(255)],
      }),
      role: new FormControl(appUserRawValue.role, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(appUserRawValue.createdAt),
      updatedAt: new FormControl(appUserRawValue.updatedAt),
    });
  }

  getAppUser(form: AppUserFormGroup): IAppUser | NewAppUser {
    return this.convertAppUserRawValueToAppUser(form.getRawValue() as AppUserFormRawValue | NewAppUserFormRawValue);
  }

  resetForm(form: AppUserFormGroup, appUser: AppUserFormGroupInput): void {
    const appUserRawValue = this.convertAppUserToAppUserRawValue({ ...this.getFormDefaults(), ...appUser });
    form.reset(
      {
        ...appUserRawValue,
        id: { value: appUserRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AppUserFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertAppUserRawValueToAppUser(rawAppUser: AppUserFormRawValue | NewAppUserFormRawValue): IAppUser | NewAppUser {
    return {
      ...rawAppUser,
      createdAt: dayjs(rawAppUser.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawAppUser.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertAppUserToAppUserRawValue(
    appUser: IAppUser | (Partial<NewAppUser> & AppUserFormDefaults),
  ): AppUserFormRawValue | PartialWithRequiredKeyOf<NewAppUserFormRawValue> {
    return {
      ...appUser,
      createdAt: appUser.createdAt ? appUser.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: appUser.updatedAt ? appUser.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
