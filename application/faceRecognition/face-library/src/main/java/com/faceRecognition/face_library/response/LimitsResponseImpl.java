package com.faceRecognition.face_library.response;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import com.faceRecognition.face_library.exception.FaceClientException;

public class LimitsResponseImpl extends AbstractResponse implements LimitsResponse {
	private final Date resetDate;
	private final int remaining;
	private final int limit;
	private final int used;
	private int namespaceLimit;
	private int namespaceUsed;
	private int namespaceRemaining;
	private String restTimeString;

	public LimitsResponseImpl(String json) throws FaceClientException {
		super(json);
		try {
			JSONObject usage = this.response.getJSONObject("usage");
			this.namespaceRemaining = ResponseHelper.optInt(usage, "namespace_remaining");
			this.namespaceUsed = ResponseHelper.optInt(usage, "namespace_used");
			this.namespaceLimit = ResponseHelper.optInt(usage, "namespace_limit");
			this.restTimeString = usage.getString("reset_time_text");
			this.resetDate = new Date(usage.getLong("reset_time"));
			this.remaining = usage.getInt("remaining");
			this.used = usage.getInt("used");
			this.limit = usage.getInt("limit");
		} catch (JSONException jex) {
			logger.error("Error: ", jex);
			throw new FaceClientException(jex);
		}
	}

	public int getUsed() {
		return this.used;
	}

	public int getRemaining() {
		return this.remaining;
	}

	public int getLimit() {
		return this.limit;
	}

	public String getRestTimeString() {
		return this.restTimeString;
	}

	public Date getResetDate() {
		return this.resetDate;
	}

	public int getNamespaceLimit() {
		return this.namespaceLimit;
	}

	public int getNamespaceUsed() {
		return this.namespaceUsed;
	}

	public int getNamespaceRemaining() {
		return this.namespaceRemaining;
	}

	public String toString() {
		return super.toString();
	}
}
