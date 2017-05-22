package com.faceRecognition.face_library.model;

abstract class Pair<A, B> {
	public A first;
	public B second;

	public Pair() {
		this(null, null);
	}

	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}
}
