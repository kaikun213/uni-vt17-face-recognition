package com.faceRecognition.face_library.model;

import org.json.JSONException;
import org.json.JSONObject;

public final class Point {
	public float x;
	public float y;

	public Point() {
		this.x = 0.0F;
		this.y = 0.0F;
	}

	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void negate() {
		this.x = (-this.x);
		this.y = (-this.y);
	}

	public void offset(float dx, float dy) {
		this.x += dx;
		this.y += dy;
	}

	public final String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + Float.floatToIntBits(this.x);
		result = 31 * result + Float.floatToIntBits(this.y);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Point other = (Point) obj;
		if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		return true;
	}

	public boolean equals(float x, float y) {
		return (this.x == x) && (this.y == y);
	}

	static Point fromJson(JSONObject jObj) throws JSONException {
		if (jObj != null) {
			Point p = new Point();

			p.x = ((float) jObj.getDouble("x"));
			p.y = ((float) jObj.getDouble("y"));

			return p;
		}
		return null;
	}
}
