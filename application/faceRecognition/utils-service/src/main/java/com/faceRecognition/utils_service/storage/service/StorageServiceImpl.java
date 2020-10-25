package com.faceRecognition.utils_service.storage.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class StorageServiceImpl implements StorageService {

	private static final String USER_ID = "8398bc9cd4ca0f5";
	private static final String MY_ID = "90d83aa1ce7efa9";

	@Override
	public String store(String file) throws UnirestException, JSONException {
		HttpResponse<JsonNode> response = Unirest.post("https://api.imgur.com/3/upload")
				.header("Authorization", "Client-ID " + MY_ID).header("type", "base64")
				.header("accept", "application/json").field("image", file).asJson();
		JSONObject obj = response.getBody().getObject();
		JSONObject data = obj.getJSONObject("data");
		System.out.println("DeleteHash: " + data.getString("deletehash"));
		System.out.println("Storing: " + data.getString("link"));
		return data.getString("link");
	}

	@Override
	public void delete(String url) throws UnirestException {
		// TODO Auto-generated method stub
		// deletehash returned by
	}
}