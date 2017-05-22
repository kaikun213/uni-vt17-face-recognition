package com.faceRecognition.face_library.model;

public enum Gender {
	male, female, unknown;

	private Gender() {
	}

	public static Gender getValue(String value) {
		if (value.equalsIgnoreCase("male")) {
			return male;
		}
		if (value.equalsIgnoreCase("female")) {
			return female;
		}
		return unknown;
	}
}
