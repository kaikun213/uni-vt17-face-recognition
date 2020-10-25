package com.faceRecognition.face_library;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

class Credentials extends Parameters {
	private final List<String> vals;

	public Credentials() {
		this.vals = new LinkedList();
	}

	public String getAuthString() {
		this.vals.clear();
		for (String key : this.params.keySet()) {
			this.vals.add(key + ":" + (String) this.params.get(key));
		}
		return StringUtils.join(this.vals, ",");
	}
}
