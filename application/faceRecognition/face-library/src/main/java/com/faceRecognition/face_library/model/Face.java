package com.faceRecognition.face_library.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Face {
	private final boolean confirmed;
	private final boolean manual;
	private final float width;
	private final float height;
	private final float yaw;
	private final float roll;
	private final float pitch;
	private final int threshold;
	private final int faceConfidence;
	private final Point center;
	private final Point leftEye;
	private final Point rightEye;
	private final Point mouthLeft;
	private final Point mouthRight;
	private final Point mouthCenter;
	private final Point leftEar;
	private final Point rightEar;
	private final Point chin;
	private final Point nose;
	private final Rect faceRect;
	private final String tid;
	private final List<Guess> guesses;
	private boolean smiling;
	private boolean glasses;
	private Gender gender;
	private String label;
	private String mood;
	private String lips;
	private int ageEst;
	private int ageMax;
	private int ageMin;

	public Face(JSONObject jObj) throws JSONException {
		this.tid = jObj.getString("tid");
		this.label = jObj.optString("label");

		this.confirmed = jObj.getBoolean("confirmed");
		this.manual = jObj.getBoolean("manual");

		this.width = ((float) jObj.getDouble("width"));
		this.height = ((float) jObj.getDouble("height"));

		this.yaw = ((float) jObj.getDouble("yaw"));
		this.roll = ((float) jObj.getDouble("roll"));
		this.pitch = ((float) jObj.getDouble("pitch"));

		this.threshold = jObj.optInt("threshold");

		this.center = Point.fromJson(jObj.optJSONObject("center"));

		this.leftEye = Point.fromJson(jObj.optJSONObject("eye_left"));
		this.rightEye = Point.fromJson(jObj.optJSONObject("eye_right"));

		this.leftEar = Point.fromJson(jObj.optJSONObject("ear_left"));
		this.rightEar = Point.fromJson(jObj.optJSONObject("ear_right"));

		this.chin = Point.fromJson(jObj.optJSONObject("chin"));

		this.mouthCenter = Point.fromJson(jObj.optJSONObject("mouth_center"));
		this.mouthRight = Point.fromJson(jObj.optJSONObject("mouth_right"));
		this.mouthLeft = Point.fromJson(jObj.optJSONObject("mouth_left"));

		this.nose = Point.fromJson(jObj.optJSONObject("nose"));

		this.guesses = Guess.fromJsonArray(jObj.optJSONArray("uids"));

		jObj = jObj.getJSONObject("attributes");
		if (jObj.has("smiling")) {
			this.smiling = jObj.getJSONObject("smiling").getBoolean("value");
		}
		if (jObj.has("glasses")) {
			this.glasses = jObj.getJSONObject("glasses").getBoolean("value");
		}
		if (jObj.has("gender")) {
			this.gender = Gender.valueOf(jObj.getJSONObject("gender").getString("value"));
		}
		if (jObj.has("mood")) {
			this.mood = jObj.getJSONObject("mood").getString("value");
		}
		if (jObj.has("lips")) {
			this.lips = jObj.getJSONObject("lips").getString("value");
		}
		if (jObj.has("age-est")) {
			this.ageEst = jObj.getJSONObject("age-est").getInt("vaule");
		}
		if (jObj.has("age-min")) {
			this.ageMin = jObj.getJSONObject("age-min").getInt("vaule");
		}
		if (jObj.has("age-max")) {
			this.ageMax = jObj.getJSONObject("age-max").getInt("vaule");
		}
		this.faceConfidence = jObj.getJSONObject("face").getInt("confidence");

		this.faceRect = new Rect(this.center, this.width, this.height);
	}

	public List<Guess> getGuesses() {
		return this.guesses;
	}

	public Guess getGuess() {
		try {
			return (Guess) Collections.max(this.guesses);
		} catch (NoSuchElementException nsee) {
		}
		return new Guess();
	}

	public double getWidth() {
		return this.width;
	}

	public double getHeight() {
		return this.height;
	}

	public int getFaceConfidence() {
		return this.faceConfidence;
	}

	public String getLabel() {
		return this.label;
	}

	public String getLips() {
		return this.lips;
	}

	public String getTID() {
		return this.tid;
	}

	public String getMood() {
		return this.mood;
	}

	public int getThreshHold() {
		return this.threshold;
	}

	public boolean isConfirmed() {
		return this.confirmed;
	}

	public boolean isManual() {
		return this.manual;
	}

	public Point getCenter() {
		return this.center;
	}

	public Point getLeftEye() {
		return this.leftEye;
	}

	public Point getLeftEar() {
		return this.leftEar;
	}

	public Point getRightEar() {
		return this.rightEar;
	}

	public Point getChin() {
		return this.chin;
	}

	public Point getRightEye() {
		return this.rightEye;
	}

	public Point getMouthCenter() {
		return this.mouthCenter;
	}

	public Point getMouthRight() {
		return this.mouthRight;
	}

	public Point getMouthLeft() {
		return this.mouthLeft;
	}

	public boolean isFace() {
		return this.faceConfidence > 50;
	}

	public boolean isWearingGlasses() {
		return this.glasses;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getRoll() {
		return this.roll;
	}

	public float getPitch() {
		return this.pitch;
	}

	public boolean isSmiling() {
		return this.smiling;
	}

	public Gender getGender() {
		return this.gender;
	}

	public Point getNose() {
		return this.nose;
	}

	public Rect getRectangle() {
		return this.faceRect;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getAgeEstimate() {
		return this.ageEst;
	}

	public int getAgeMaximum() {
		return this.ageMax;
	}

	public int getAgeMinimum() {
		return this.ageMin;
	}

	static List<Face> fromJsonArray(JSONArray jArr) throws JSONException {
		List<Face> faces = new LinkedList();
		for (int i = 0; i < jArr.length(); i++) {
			faces.add(new Face(jArr.getJSONObject(i)));
		}
		return faces;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Face [center=").append(this.center).append(", chin=").append(this.chin).append(", confirmed=")
				.append(this.confirmed).append(", faceConfidence=").append(this.faceConfidence).append(", faceRect=")
				.append(this.faceRect).append(", gender=").append(this.gender).append(", glasses=").append(this.glasses)
				.append(", guesses=").append(this.guesses).append(", height=").append(this.height).append(", label=")
				.append(this.label).append(", leftEar=").append(this.leftEar).append(", leftEye=").append(this.leftEye)
				.append(", manual=").append(this.manual).append(", mouthCenter=").append(this.mouthCenter)
				.append(", mouthLeft=").append(this.mouthLeft).append(", mouthRight=").append(this.mouthRight)
				.append(", nose=").append(this.nose).append(", pitch=").append(this.pitch).append(", rightEar=")
				.append(this.rightEar).append(", rightEye=").append(this.rightEye).append(", roll=").append(this.roll)
				.append(", smiling=").append(this.smiling).append(", threshold=").append(this.threshold)
				.append(", lips=").append(this.lips).append(", mood=").append(this.mood).append(", tid=")
				.append(this.tid).append(", width=").append(this.width).append(", yaw=").append(this.yaw).append("]");

		return builder.toString();
	}
}
