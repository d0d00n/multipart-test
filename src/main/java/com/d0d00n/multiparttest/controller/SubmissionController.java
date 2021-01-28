package com.d0d00n.multiparttest.controller;

import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Thread;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SubmissionController {

    @PostMapping("/text")
    public String submitText(@RequestBody String text) {
        // this isn't multipart: just a test to see if the app is running
        return text;
    }

    @PostMapping("/file")
    public ResponseEntity<Resource> submitFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("No file detected");
        } else {
            ByteArrayResource resource = new ByteArrayResource(file.getBytes());
            return ResponseEntity.ok().contentLength(file.getSize())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
        }
    }
    
    @GetMapping("/headerEcho")
    public String headerEcho(@RequestHeader Map<String, String> headers) {
    	String headersOutput = "";
        for (Map.Entry<String, String> tuple : headers.entrySet()) {
            headersOutput += tuple.getKey() +": "+ tuple.getValue()+'\n';
        }   	    	
    	
    	return headersOutput;
    }
	
    @GetMapping("/wait")
    public String wait(@RequestHeader Map<String, String> headers) {
    	Integer waitTime = Integer.parseInt(headers.get("time"));
		
		try {
			Thread.sleep(waitTime * 1000);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
    	
    	return "Me waited " + waitTime + "seconds, boss! Zug zug.";
    }	
    

}
