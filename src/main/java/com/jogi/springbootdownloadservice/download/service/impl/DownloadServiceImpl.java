package com.jogi.springbootdownloadservice.download.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.jogi.springbootdownloadservice.download.service.DownloadService;

@Service
public class DownloadServiceImpl implements DownloadService {

	static final Logger logger = LogManager.getLogger(DownloadServiceImpl.class.getName());

	@Value("${file.directory:default}")
	String fileDirectory;

	@Override
	public void downloadFiles(HttpServletResponse response, String fileType, String moduleName, String fileName) {

		StringBuilder filePath = new StringBuilder();

		try {
			if ("images".equals(fileType)) {
				filePath.append("/images");
			} else {
				filePath.append("/fileUploads/").append(moduleName);
			}

			File file = new File(fileDirectory);

			if (!file.exists()) {
				file.mkdir();
			}

			if (file.exists()) {
				file = new File(fileDirectory, fileName);
				// get the mimetype
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					// unknown mimetype so set the mimetype to application/octet-stream
					mimeType = "application/octet-stream";
				}

				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
				response.setContentLength((int) file.length());

				downloadFile(file, response);

			} else {
				logger.info("@@@ File not exist !!!: " + file.getName());
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private void downloadFile(File file, HttpServletResponse response) {
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));) {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
