package com.dyz.filxeservice.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.common.execption.FileTransferException;
import com.dyz.filxeservice.common.execption.NoDataException;

public class FileHandler {

	/**
	 * transfer a file from web to local. local path is a parameter.
	 * 
	 * @param file
	 */
	public static File transferToLocalFile(MultipartFile file, String localFilePath, String fileName) {
		File localFile = new File(localFilePath, fileName).getAbsoluteFile();
		try {
			file.transferTo(localFile);
		} catch (Exception e) {
			throw new FileTransferException(0, "file transfer error");
		}
		return localFile;
	}

	/**
	 * 
	 * @param file
	 * @param os
	 * @return file name
	 */
	public static String transferLocalFileToStream(File file, OutputStream os) {
		if (!file.exists()) {
			throw new NoDataException(0, "file is not exist!");
		}
		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
		} catch (Exception e) {
			throw new FileTransferException(0, "transfer file to stream fail!");
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file.getName();
	}
}
