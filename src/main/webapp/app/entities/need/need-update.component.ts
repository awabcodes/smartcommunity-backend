import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { INeed, Need } from 'app/shared/model/need.model';
import { NeedService } from './need.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-need-update',
  templateUrl: './need-update.component.html',
})
export class NeedUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    info: [null, [Validators.required]],
    available: [null, [Validators.required]],
    contact: [null, [Validators.required]],
    image: [null, []],
    imageContentType: [],
    quantity: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected needService: NeedService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ need }) => {
      this.updateForm(need);
    });
  }

  updateForm(need: INeed): void {
    this.editForm.patchValue({
      id: need.id,
      name: need.name,
      info: need.info,
      available: need.available,
      contact: need.contact,
      image: need.image,
      imageContentType: need.imageContentType,
      quantity: need.quantity,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('smartcommunityApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const need = this.createFromForm();
    if (need.id !== undefined) {
      this.subscribeToSaveResponse(this.needService.update(need));
    } else {
      this.subscribeToSaveResponse(this.needService.create(need));
    }
  }

  private createFromForm(): INeed {
    return {
      ...new Need(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      info: this.editForm.get(['info'])!.value,
      available: this.editForm.get(['available'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INeed>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
