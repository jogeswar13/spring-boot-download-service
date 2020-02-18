package com.jogi.springbootdownloadservice.download.service;

import javax.servlet.http.HttpServletResponse;

public interface DownloadService {

	public void downloadFiles(HttpServletResponse response, String fileType, String moduleName, String fileName);

}
