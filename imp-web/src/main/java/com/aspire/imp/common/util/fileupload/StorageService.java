package com.aspire.imp.common.util.fileupload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(String subdir, MultipartFile file);

    Stream<Path> loadAll();

    Path load(String subdir, String filename);

    Resource loadAsResource(String subdir, String filename);

    void deleteAll();

}
