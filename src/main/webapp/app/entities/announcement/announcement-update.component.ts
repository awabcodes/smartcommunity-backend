import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAnnouncement, Announcement } from 'app/shared/model/announcement.model';
import { AnnouncementService } from './announcement.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-announcement-update',
  templateUrl: './announcement-update.component.html',
})
export class AnnouncementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.maxLength(20)]],
    content: [null, [Validators.required]],
    creationDate: [null, [Validators.required]],
    type: [null, [Validators.required]],
    location: [],
    image: [null, []],
    imageContentType: [],
    contact: [],
    announcementDate: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected announcementService: AnnouncementService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ announcement }) => {
      if (!announcement.id) {
        const today = moment().startOf('day');
        announcement.creationDate = today;
        announcement.announcementDate = today;
      }

      this.updateForm(announcement);
    });
  }

  updateForm(announcement: IAnnouncement): void {
    this.editForm.patchValue({
      id: announcement.id,
      title: announcement.title,
      content: announcement.content,
      creationDate: announcement.creationDate ? announcement.creationDate.format(DATE_TIME_FORMAT) : null,
      type: announcement.type,
      location: announcement.location,
      image: announcement.image,
      imageContentType: announcement.imageContentType,
      contact: announcement.contact,
      announcementDate: announcement.announcementDate ? announcement.announcementDate.format(DATE_TIME_FORMAT) : null,
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
    const announcement = this.createFromForm();
    if (announcement.id !== undefined) {
      this.subscribeToSaveResponse(this.announcementService.update(announcement));
    } else {
      this.subscribeToSaveResponse(this.announcementService.create(announcement));
    }
  }

  private createFromForm(): IAnnouncement {
    return {
      ...new Announcement(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      content: this.editForm.get(['content'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      type: this.editForm.get(['type'])!.value,
      location: this.editForm.get(['location'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      announcementDate: this.editForm.get(['announcementDate'])!.value
        ? moment(this.editForm.get(['announcementDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnouncement>>): void {
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
