package cn.jpanda.demo.manager.component.upload;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 */
public interface FileUpload {
    /**
     * 为指定文件生成文件名称
     *
     * @param multipartFile 上传的文件
     * @return 文件名称
     */
    String generatorName(MultipartFile multipartFile);

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @param path          文件存放路径
     * @param name          文件名称
     * @return 文件上传是否成功
     */
    boolean upload(MultipartFile multipartFile, String path, String name);

}
