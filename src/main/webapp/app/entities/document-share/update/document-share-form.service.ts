import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocumentShare, NewDocumentShare } from '../document-share.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocumentShare for edit and NewDocumentShareFormGroupInput for create.
 */
type DocumentShareFormGroupInput = IDocumentShare | PartialWithRequiredKeyOf<NewDocumentShare>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocumentShare | NewDocumentShare> = Omit<T, 'grantedAt'> & {
  grantedAt?: string | null;
};

type DocumentShareFormRawValue = FormValueOf<IDocumentShare>;

type NewDocumentShareFormRawValue = FormValueOf<NewDocumentShare>;

type DocumentShareFormDefaults = Pick<NewDocumentShare, 'id' | 'grantedAt'>;

type DocumentShareFormGroupContent = {
  id: FormControl<DocumentShareFormRawValue['id'] | NewDocumentShare['id']>;
  permission: FormControl<DocumentShareFormRawValue['permission']>;
  grantedAt: FormControl<DocumentShareFormRawValue['grantedAt']>;
  document: FormControl<DocumentShareFormRawValue['document']>;
  user: FormControl<DocumentShareFormRawValue['user']>;
};

export type DocumentShareFormGroup = FormGroup<DocumentShareFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentShareFormService {
  createDocumentShareFormGroup(documentShare: DocumentShareFormGroupInput = { id: null }): DocumentShareFormGroup {
    const documentShareRawValue = this.convertDocumentShareToDocumentShareRawValue({
      ...this.getFormDefaults(),
      ...documentShare,
    });
    return new FormGroup<DocumentShareFormGroupContent>({
      id: new FormControl(
        { value: documentShareRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      permission: new FormControl(documentShareRawValue.permission, {
        validators: [Validators.required],
      }),
      grantedAt: new FormControl(documentShareRawValue.grantedAt),
      document: new FormControl(documentShareRawValue.document, {
        validators: [Validators.required],
      }),
      user: new FormControl(documentShareRawValue.user, {
        validators: [Validators.required],
      }),
    });
  }

  getDocumentShare(form: DocumentShareFormGroup): IDocumentShare | NewDocumentShare {
    return this.convertDocumentShareRawValueToDocumentShare(form.getRawValue() as DocumentShareFormRawValue | NewDocumentShareFormRawValue);
  }

  resetForm(form: DocumentShareFormGroup, documentShare: DocumentShareFormGroupInput): void {
    const documentShareRawValue = this.convertDocumentShareToDocumentShareRawValue({ ...this.getFormDefaults(), ...documentShare });
    form.reset(
      {
        ...documentShareRawValue,
        id: { value: documentShareRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DocumentShareFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      grantedAt: currentTime,
    };
  }

  private convertDocumentShareRawValueToDocumentShare(
    rawDocumentShare: DocumentShareFormRawValue | NewDocumentShareFormRawValue,
  ): IDocumentShare | NewDocumentShare {
    return {
      ...rawDocumentShare,
      grantedAt: dayjs(rawDocumentShare.grantedAt, DATE_TIME_FORMAT),
    };
  }

  private convertDocumentShareToDocumentShareRawValue(
    documentShare: IDocumentShare | (Partial<NewDocumentShare> & DocumentShareFormDefaults),
  ): DocumentShareFormRawValue | PartialWithRequiredKeyOf<NewDocumentShareFormRawValue> {
    return {
      ...documentShare,
      grantedAt: documentShare.grantedAt ? documentShare.grantedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
