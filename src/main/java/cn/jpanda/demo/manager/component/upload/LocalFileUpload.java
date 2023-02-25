package cn.jpanda.demo.manager.component.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

/**
 * 本地文件上传
 */
@Slf4j
public class LocalFileUpload implements FileUpload {
    @Override
    public String generatorName(MultipartFile multipartFile) {
        return UUID.randomUUID().toString().replaceAll("-","").concat(getSuffix(Objects.requireNonNull(multipartFile.getOriginalFilename())));
    }

    @Override
    public boolean upload(MultipartFile multipartFile, String path, String name) {
        Path directory = Paths.get(path);
        File dir = directory.toFile();
        if (!dir.exists()) {
            // 初始化目录
            dir.mkdirs();
        }
        // 上传图片
        try {
            multipartFile.transferTo(Paths.get(path, name));
        } catch (IOException e) {
            log.error("{}", e);
            return false;
        }
        return true;
    }


    protected String getSuffix(String name) {
        return name.substring(name.lastIndexOf("."));
    }

}
