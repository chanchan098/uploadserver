package com.example.uploadserver;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@RestController
public class MainController {


    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("image") String imageBase64,
                                   @RequestParam("folderRoot") String folderRoot,
                                   @RequestParam("folder") String folder,
                                   @RequestParam("filename") String filename,
                                   @RequestParam("idx") int idx) {
        if (!imageBase64.isEmpty()) {
            try {

//              folder checking
                String fdd = folder.replaceAll("[<>:\"/\\\\|?*]", "");
                File fd = new File(folderRoot + fdd);
                if (!fd.exists()) {
                    boolean created = fd.mkdirs();
                    if (created) {
                        System.out.println("Folder created: " + fd.getAbsolutePath());
                    } else {
                        return new ResponseEntity<String>("Failed to create the folder.", HttpStatusCode.valueOf(501));
                    }
                }

//              base64 filtering
                String b64 = imageBase64;
                if (b64.contains(",")) {
                    b64 = b64.split(",")[1];
                }

//              filename checking
                String fname = filename.replaceAll("[<>:\"/\\\\|?*]", "");

//              writing
                byte[] decodedBytes = Base64.getDecoder().decode(b64);
                String fn = folderRoot + "\\" + fdd + "\\" +  fname;
                try (OutputStream os = new FileOutputStream(fn)) {
                    os.write(decodedBytes);
                }
                boolean saved = new File(fn).exists();

                return new ResponseEntity<String>("File uploaded successfully. " +idx +" " + saved + " " + fdd + fname, HttpStatusCode.valueOf(200));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<String>("Failed to upload file.  " +idx, HttpStatusCode.valueOf(501));

            }
        } else {
            return new ResponseEntity<String>("No file selected.", HttpStatusCode.valueOf(501));
        }
    }

}
