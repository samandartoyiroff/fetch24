package uz.tenzorsoft.fetch24.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tenzorsoft.fetch24.service.FileService;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Override
    public String base64(MultipartFile headImage) {
        try {
            byte[] bytes = headImage.getBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert file to Base64 string", e);
        }
    }
}
