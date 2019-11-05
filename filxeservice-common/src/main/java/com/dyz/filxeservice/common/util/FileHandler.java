package com.dyz.filxeservice.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.common.execption.FileTransferException;
import com.dyz.filxeservice.common.execption.NoDataException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileHandler {

	private static final int BUFFERSIZE = 2 << 10;

	/**
	 * transfer a file from web to local. local path is a parameter.
	 * 
	 * @param file
	 */
	public static File transferToLocalFile(MultipartFile file, String localFilePath, String fileName) {
		File localFile = new File(localFilePath, fileName).getAbsoluteFile();
		try {
			log.info("transfer {} to local filePath {}", file.getOriginalFilename(), localFilePath);
			file.transferTo(localFile);
		} catch (Exception e) {
			log.error("transfer to local file error!", e);
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
		log.info("transfer local file {} to stream", file.getAbsolutePath());
		if (!file.exists()) {
			log.warn("local not exists");
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
			log.error("transfer to local file error!", e);
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

	/**
	 * compressed
	 * 
	 * @param paths:
	 *            target file path
	 * @param fileName:
	 *            zip file name
	 */
	public static void zip(String[] paths, String fileName) {
		log.info("begin to zip file, target file = {}, result file name = {}", Arrays.asList(paths), fileName);
		try {
			zip(paths, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			log.error("zip file fail, no such local file {}", fileName, e);
			throw new FileTransferException(0, "zip file fail!");
		}
		log.info("end of zip file");
	}

	/**
	 * 
	 * @param targetFiles
	 *            target files
	 * @param resultFile
	 */
	public static void zip(List<File> targetFiles, File resultFile) {
		if (CollectionUtils.isEmpty(targetFiles)) {
			return;
		}
		List<String> paths = targetFiles.stream().map(x -> x.getAbsolutePath()).collect(Collectors.toList());
		String[] pathArray = new String[paths.size()];
		paths.toArray(pathArray);
		zip(pathArray, resultFile);
	}

	/**
	 * 
	 * @param paths:
	 *            target file path
	 * @param resultFile:
	 *            zip file
	 */
	public static void zip(String[] paths, File resultFile) {
		log.info("begin to zip file, target file = {}, result file = {}", Arrays.asList(paths), resultFile);
		try {
			zip(paths, new FileOutputStream(resultFile));
		} catch (FileNotFoundException e) {
			log.error("zip file fail, no such local file {}", resultFile.getAbsolutePath(), e);
			throw new FileTransferException(0, "zip file fail!");
		}
		log.info("end of zip file");
	}

	private static void zip(String[] paths, FileOutputStream fileOutputStream) {
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(fileOutputStream);
			for (String filePath : paths) {
				File file = new File(filePath);
				String relativePath = file.getName();
				if (file.isDirectory()) {
					relativePath += File.separator;
				}
				zipFile(file, relativePath, zos);
			}
		} catch (Exception e) {
			log.error("zip file error!", e);
			throw new FileTransferException(0, "zip file fail!");
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void zipFile(File file, String relativePath, ZipOutputStream zos) {
		InputStream is = null;
		try {
			if (!file.isDirectory()) {
				ZipEntry zp = new ZipEntry(relativePath);
				zos.putNextEntry(zp);
				is = new FileInputStream(file);
				byte[] buffer = new byte[BUFFERSIZE];
				int length = 0;
				while ((length = is.read(buffer)) >= 0) {
					zos.write(buffer, 0, length);
				}
				zos.flush();
				zos.closeEntry();
			} else {
				String tempPath = null;
				for (File f : file.listFiles()) {
					tempPath = relativePath + f.getName();
					if (f.isDirectory()) {
						tempPath += File.separator;
					}
					zipFile(f, tempPath, zos);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
