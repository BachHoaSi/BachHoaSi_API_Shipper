package com.swd391.bachhoasi_shipper.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.swd391.bachhoasi_shipper.model.exception.ActionFailedException;
import com.swd391.bachhoasi_shipper.model.exception.ValidationFailedException;
import com.swd391.bachhoasi_shipper.service.CloudStoreService;
import com.swd391.bachhoasi_shipper.util.BaseUtils;
import com.google.cloud.storage.Blob;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CloudStoreServiceImpl implements CloudStoreService {
    @Override
    public String save (MultipartFile file) {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();
            String fileName = BaseUtils.generateFileName(file);
            Blob blob = bucket.create(fileName, file.getBytes() , file.getContentType());
            return blob.getMediaLink();
        } catch (Exception ex) {
            throw new ActionFailedException(String.format("Failed Push File To FireStore: %s", ex.getMessage()));
        }
    }

    public void delete (String name) {
        Bucket bucket = StorageClient.getInstance().bucket();
        if(!StringUtils.hasText(name)) {
            throw new ValidationFailedException("Name is not valid");
        }
        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new ActionFailedException("File not found");
        }
        blob.delete();
    }
}
