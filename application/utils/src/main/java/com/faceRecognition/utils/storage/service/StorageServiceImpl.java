package com.faceRecognition.utils.storage.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.github.imgur.ImgUr;
import com.github.imgur.api.upload.UploadRequest;
import com.github.imgur.api.upload.UploadResponse;

public class StorageServiceImpl implements StorageService {

	@Value("${storage.api.key}")
	private String apiKey;

	@Value("${storage.api.secret}")
	private String secret;

	private ImgUr imgur;

	@PostConstruct
	public void init() {
		System.err.println("APIKEY: " + apiKey + " : " + secret);
		imgur = new ImgUr(apiKey, secret);
	}

	@Override
	public String upload(MultipartFile file) throws IOException {
		File image = new File(file.getOriginalFilename());
		file.transferTo(image);

		UploadRequest.Builder builder = new UploadRequest.Builder();
		UploadRequest request = builder.withImageFile(image).build();
		UploadResponse response = imgur.call(request);
		String url = response.getLinks().getImgurPage();
		System.err.println("URL : " + url);
		return url;
	}
}
