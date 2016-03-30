package com.asu.cse.model;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload extends SSFundTransaction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	

}