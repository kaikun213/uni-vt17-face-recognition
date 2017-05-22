package com.faceRecognition.face_library.model;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Photo {
	private final Logger logger;
	private final String url;
	private final String pid;
	private final int width;
	private final int height;
	private List<Face> tags;

	public Photo(JSONObject jObj) throws JSONException {
		this.logger = LoggerFactory.getLogger(Photo.class);

		this.url = jObj.getString("url");
		this.pid = jObj.getString("pid");

		this.width = jObj.getInt("width");
		this.height = jObj.getInt("height");

		this.tags = Face.fromJsonArray(jObj.getJSONArray("tags"));
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Photo [height=").append(this.height).append(", pid=").append(this.pid).append(", url=")
				.append(this.url).append(", width=").append(this.width).append("]").append("\ntags=").append(this.tags);

		return builder.toString();
	}

	public int getFaceCount() {
		return getFaces().size();
	}

	public String getURL() {
		return this.url;
	}

	public String getPID() {
		return this.pid;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public List<Face> getFaces() {
		return this.tags;
	}

	public void scaleFaceRects(float width, float height) {
		for (Face f : getFaces()) {
			Rect r = f.getRectangle();

			r.left *= width / 100.0F;
			r.right *= width / 100.0F;
			r.top *= height / 100.0F;
			r.bottom *= height / 100.0F;
		}
	}

	public Face getFace() {
		try {
			return (Face) getFaces().get(0);
		} catch (IndexOutOfBoundsException ioob) {
			if (this.logger.isInfoEnabled()) {
				this.logger.info("No faces found...");
			}
		}
		return null;
	}
}
