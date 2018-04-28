package com.aspire.imp.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aspire.imp.aop.annotation.Auth;
import com.aspire.imp.common.contanst.ApiResult;
import com.aspire.imp.common.util.DateTools;
import com.aspire.imp.common.util.fileupload.StorageService;
import com.aspire.imp.web.entity.SysUser;

@RestController
@RequestMapping("/rest/file")
public class FileUploadRestController {
	private final static Logger log = LoggerFactory.getLogger(FileUploadRestController.class);
    private final StorageService storageService;

    @Autowired
    public FileUploadRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public ApiResult listUploadedFiles(Model model) throws IOException {
        List<String> files = storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadRestController.class,
                		"serveFile", "", path.toString()).build().toString())
        		.collect(Collectors.toList());
        return new ApiResult(files);
    }

    @GetMapping("/{subdir1}/{subdir2}/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String subdir1, 
    		@PathVariable String subdir2, 
    		@PathVariable String filename) {

        Resource file = storageService.loadAsResource(subdir1 + "/" + subdir2, filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @Auth
    @PostMapping("/upload")
    public ApiResult handleFileUpload(@RequestParam("file") MultipartFile file, 
    		RedirectAttributes redirectAttributes, 
    		HttpServletRequest request) {
    	SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		Long authorId = user.getId();
		String subdir = new StringBuffer().append(authorId)
				.append("/")
				.append(DateTools.getDateTimeString(new Date(), "yyyyMMdd"))
				.toString();
    	storageService.store(subdir, file);
    	
    	String url = "/rest/file/" + subdir + "/" + file.getOriginalFilename();
        return new ApiResult(url);
    }

}
