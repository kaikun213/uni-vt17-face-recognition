package com.faceRecognition.face_library.model;

import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Guess extends Pair<String, Integer> implements Comparable<Guess> {
	public Guess() {
		this.first = "Unknown";
		this.second = Integer.valueOf(100);
	}

	public Guess(JSONObject jObj) throws JSONException {
		this.first = jObj.getString("uid");
		this.second = Integer.valueOf(jObj.getInt("confidence"));
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Guess [uid=").append((String) this.first).append(", confidence=").append(this.second)
				.append("]");

		return builder.toString();
	}

	static List<Guess> fromJsonArray(JSONArray jArr) throws JSONException {
		List<Guess> guesses = new LinkedList();
		if (jArr != null) {
			for (int i = 0; i < jArr.length(); i++) {
				guesses.add(new Guess(jArr.getJSONObject(i)));
			}
		}
		return guesses;
	}

	public int compareTo(Guess that) {
		if (((Integer) this.second).intValue() > ((Integer) that.second).intValue()) {
			return 1;
		}
		if (((Integer) this.second).intValue() < ((Integer) that.second).intValue()) {
			return -1;
		}
		return 0;
	}
}
