package com.faceRecognition.face_library.model;

public class Rect {
	public float top;
	public float bottom;
	public float left;
	public float right;

	public Rect() {
	}

	public Rect(Rect rect) {
		this(rect.left, rect.top, rect.right, rect.bottom);
	}

	public Rect(Point center, float width, float height) {
		this(center.x - width / 2.0F, center.y - height / 2.0F, center.x + width / 2.0F, center.y + height / 2.0F);
	}

	public Rect(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	public boolean contains(float x, float y) {
		return (this.left < this.right) && (this.top < this.bottom) && (x >= this.left) && (x < this.right)
				&& (y >= this.top) && (y < this.bottom);
	}

	public boolean contains(Rect r) {
		return contains(r.top, r.bottom, r.left, r.right);
	}

	public boolean contains(float left, float top, float right, float bottom) {
		return (this.bottom <= bottom) && (this.right >= right) && (this.left <= left) && (this.top >= top);
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + Float.floatToIntBits(this.bottom);
		result = 31 * result + Float.floatToIntBits(this.left);
		result = 31 * result + Float.floatToIntBits(this.right);
		result = 31 * result + Float.floatToIntBits(this.top);
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
		Rect other = (Rect) obj;
		if (Float.floatToIntBits(this.bottom) != Float.floatToIntBits(other.bottom)) {
			return false;
		}
		if (Float.floatToIntBits(this.left) != Float.floatToIntBits(other.left)) {
			return false;
		}
		if (Float.floatToIntBits(this.right) != Float.floatToIntBits(other.right)) {
			return false;
		}
		if (Float.floatToIntBits(this.top) != Float.floatToIntBits(other.top)) {
			return false;
		}
		return true;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rect [bottom=").append(this.bottom).append(", left=").append(this.left).append(", right=")
				.append(this.right).append(", top=").append(this.top).append("]");
		return builder.toString();
	}
}
