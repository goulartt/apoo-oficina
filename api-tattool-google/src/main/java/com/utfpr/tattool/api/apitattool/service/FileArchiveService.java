package com.utfpr.tattool.api.apitattool.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class FileArchiveService {


	private File convertFromMultiPart(BufferedImage multipartFile) throws IOException {

		File file = new File(multipartFile.getClass().getName());
		ImageIO.write(multipartFile, "jpg", file);
		return file;
	}

	public byte[] convertFileToByte(File file) {
		byte[] bFile = new byte[(int) file.length()];

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			return bFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
