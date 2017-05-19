package com.faceRecognition.utils.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.faceRecognition.utils.storage.service.StorageService;
import com.mashape.unirest.http.exceptions.UnirestException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServiceTest {
	
	@Autowired
	StorageService storageService;
	
	@Test
	public void test() throws UnirestException, FileNotFoundException, JSONException{
		File file = new File("/home/kaikun/bla.txt");
		
		FileReader fr = new FileReader(file);
		BufferedReader reader = new BufferedReader(fr);
		StringBuilder image = new StringBuilder();
		reader.lines().forEach(line -> image.append(line));
		
		storageService.store(image.toString());
		//storageService.delete("test");
	}

}
