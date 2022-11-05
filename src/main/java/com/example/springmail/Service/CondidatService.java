package com.example.springmail.Service;

import com.example.springmail.Repository.CondidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class CondidatService {
    @Autowired
    private CondidatRepository candidatRepo ;

    private final Path fileStorageLocation = Paths.get("C:\\Users\\ASUS\\Desktop\\candidats");

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = (Resource) new UrlResource(filePath.toUri());
            if(((AbstractFileResolvingResource) resource).exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + fileName, ex);
        }
    }
}
