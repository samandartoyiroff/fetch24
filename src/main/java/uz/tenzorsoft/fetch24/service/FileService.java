package uz.tenzorsoft.fetch24.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String base64(MultipartFile headImage);
}
