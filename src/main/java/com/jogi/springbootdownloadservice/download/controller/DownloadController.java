package com.jogi.springbootdownloadservice.download.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jogi.springbootdownloadservice.download.service.DownloadService;

@RestController
public class DownloadController {

	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	DownloadService downloadService;

	@GetMapping(value = { "download/{fileType}/{moduleName}/{fileName:.+}", "download/{fileType}/{fileName:.+}",
			"download/{fileName:.+}" })
	public void downloadFiles(HttpServletResponse response,
			@PathVariable(name = "fileType", required = false, value = "") String fileType,
			@PathVariable(name = "moduleName", required = false, value = "") String moduleName,
			@PathVariable("fileName") String fileName) {
		try {
			downloadService.downloadFiles(response, fileType, moduleName, fileName);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
