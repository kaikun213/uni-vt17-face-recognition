package com.faceRecognition.face_library;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

class Parameters {
	protected final Map<String, String> params;

	public Parameters() {
		this.params = new HashMap();
	}

	public Parameters(String key, String value) {
		this();
		this.params.put(key, value);
	}

	public void put(String key, float value) {
		put(key, String.valueOf(value));
	}

	public void put(String key, boolean value) {
		put(key, String.valueOf(value));
	}

	public void put(String key, int value) {
		put(key, String.valueOf(value));
	}

	public void put(String key, String value) {
		if (value != null) {
			this.params.put(key, value);
		}
	}

	public void putAll(Map<String, String> params) {
		for (String key : params.keySet()) {
			if (params.get(key) != null) {
				put(key, (String) params.get(key));
			}
		}
	}

	public Map<String, String> getMap() {
		return Collections.unmodifiableMap(this.params);
	}

	public void remove(String key) {
		this.params.remove(key);
	}

	public boolean isEmpty() {
		return this.params.isEmpty();
	}

	public List<NameValuePair> toPostParams() {
		List<NameValuePair> list = new LinkedList();
		for (String key : this.params.keySet()) {
			list.add(new BasicNameValuePair(key, (String) this.params.get(key)));
		}
		return list;
	}

	public String toString() {
		return this.params.toString();
	}
}
