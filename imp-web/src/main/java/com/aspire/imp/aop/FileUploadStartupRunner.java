package com.aspire.imp.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.aspire.imp.common.util.fileupload.StorageProperties;
import com.aspire.imp.common.util.fileupload.StorageService;

@Component
@Order(0)
@EnableConfigurationProperties(StorageProperties.class)
public class FileUploadStartupRunner implements CommandLineRunner {
	private final static Logger log = LoggerFactory.getLogger(FileUploadStartupRunner.class);
	private final StorageService storageService;
	
	@Autowired
    public FileUploadStartupRunner(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@Override
	public void run(String... args) throws Exception {
        storageService.init();
	}

}
