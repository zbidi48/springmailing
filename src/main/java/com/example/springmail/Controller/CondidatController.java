package com.example.springmail.Controller;

import com.example.springmail.Entity.Condidat;
import com.example.springmail.Repository.CondidatRepository;
import com.example.springmail.Service.CondidatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/Condidat")
public class CondidatController {
    @Autowired
    private CondidatRepository candidatRepo;

    private static final Logger logger = LoggerFactory.getLogger(CondidatController.class);

    @Autowired
    private CondidatService candidatService;

    @PostMapping("/create")
    public Condidat createCandidate(@Valid @RequestBody Condidat candidate) {
        return candidatRepo.save(candidate);

    }

    @GetMapping("/candidats")
    public List<Condidat> getAllCandidates() {
        return candidatRepo.findAll();
    }


    private final Path rootLocation = Paths.get("C:\\Users\\ASUS\\Desktop\\candidats");

    @PostMapping("/createcandidate")
    public ResponseEntity<?> createCandidate(@RequestPart("candidate") String candidate, @RequestParam("cv_file")
    MultipartFile file1, @RequestParam("motiv_letter_file") MultipartFile file2) throws JsonParseException, JsonMappingException, IOException, JsonProcessingException {



        Condidat candidate1 = new ObjectMapper().readValue(candidate , Condidat.class);
        System.out.println(candidate1);

        candidate1.setPathCv(file1.getOriginalFilename());
        candidate1.setPathMotivationLetter(file2.getOriginalFilename());
        Condidat candidat2 = candidatRepo.save(candidate1);
        if (candidat2!=null) {
            return  ResponseEntity.status(HttpStatus.ACCEPTED).body("User is saved");
        }else {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not saved");
        }
    }

    @GetMapping("/downloadFile/{fileName:.+}")

    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {

        // Load file as Resource
        Resource resource = candidatService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/candidat/{id}")
    public ResponseEntity<Condidat> findById(@PathVariable("id") Long id) {
        Optional<Condidat> emp = candidatRepo.findById(id);
        if (emp.isPresent())
            return new ResponseEntity<Condidat>(emp.get(), HttpStatus.OK);
        else
            return new ResponseEntity<Condidat>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("/candidate/{id}")
    void deleteNote(@PathVariable Long id) {
        candidatRepo.deleteById(id);
    }
}
