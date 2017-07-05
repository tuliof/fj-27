package br.com.casadocodigo.loja.helper;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {

	// Check S3Ninja, it emulates AWS S3
	public String storeFile(String baseFolder, MultipartFile file) {
		String basePath = System.getProperty("user.home");
		String baseFolderPath = basePath + baseFolder;
		String filePath = baseFolderPath + file.getOriginalFilename();

		try {
			file.transferTo(new File(filePath));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filePath;
	}
}
