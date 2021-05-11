package com.cts.fse.feedback.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadValidationService {

	public String uploadEventFile(MultipartFile[] files) throws IOException;
	public void executeInputEventFile();
}
