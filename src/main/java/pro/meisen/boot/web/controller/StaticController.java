package pro.meisen.boot.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.domain.common.ErrorCode;
import pro.meisen.boot.ext.shiro.encrypt.MD5HashEncryptor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 静态处理器
 * @author meisen
 * 2019-07-26
 */
@RestController
@RequestMapping("/api/static")
@Api(description = "上传文件", tags = "上传文件Tag")
public class StaticController {

    @Value("${web.upload-path}")
    private String path;

    @PostMapping("/file/upload")
    @ApiOperation(notes = "上传文件", value = "上传文件")
    public @ResponseBody
    String fileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (Strings.isEmpty(fileName)) {
            throw new AppException(ErrorCode.APP_FILE_UPLOAD_FAIL, "文件名错误, 请重新上传.");
        }
        String fileExt = fileName.substring(fileName.lastIndexOf('.'));
        String encryptName = new MD5HashEncryptor.Builder(fileName).build().toString() + fileExt;
        // 文件路径需要根据上线之后的路径确定
        String filePath = path + encryptName;
        try {
            Path fPath = Paths.get(filePath);
            if (!Files.exists(fPath)) {
                Files.createFile(fPath);
            }
            Files.write(fPath, file.getBytes());
        } catch (Exception e) {
            throw new AppException(ErrorCode.APP_FILE_UPLOAD_FAIL, "文件上传失败, 请重新上传.");
        }
        return "/api/static/image/" + encryptName;
    }
}
