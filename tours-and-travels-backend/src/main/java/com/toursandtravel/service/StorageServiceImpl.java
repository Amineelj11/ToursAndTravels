package com.toursandtravel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StorageServiceImpl implements StorageService {

	@Value("${com.toursandtravel.image.folder.path}")
	private String BASEPATH;

	@Override
	public List<String> loadAll() {
		File dirPath = new File(BASEPATH);
		return Arrays.asList(dirPath.list());
	}

	public String store(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || !originalFilename.contains(".")) {
			// Handle null or invalid file name
			throw new IllegalArgumentException("Invalid file or file name");
		}

		String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
		File filePath = new File(BASEPATH, fileName);

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			FileCopyUtils.copy(file.getInputStream(), out);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Resource load(String fileName) {
		File filePath = new File(BASEPATH, fileName);
		if (filePath.exists())
			return new FileSystemResource(filePath);
		return null;
	}

	@Override
	public void delete(String fileName) {
		File filePath = new File(BASEPATH, fileName);
		if (filePath.exists()) {
			boolean deleted = filePath.delete();
			if (!deleted) {
				// Log the failure or throw an exception to handle the error
				System.err.println("Failed to delete the file: " + fileName);
				// Optionally, throw an exception
				// throw new IOException("File deletion failed for " + fileName);
			} else {
				System.out.println("File deleted successfully: " + fileName);
			}
		} else {
			System.err.println("File not found: " + fileName);
		}
	}


}
