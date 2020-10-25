package com.faceRecognition.face_library.response;

import java.util.List;
import org.json.JSONException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.model.UserStatus;

public final class TrainResponseImpl
  extends AbstractResponse
  implements TrainResponse
{
  private List<UserStatus> created;
  private List<UserStatus> no_training_set;
  private List<UserStatus> updated;
  private List<UserStatus> unchanged;
  private List<UserStatus> in_progress;
  
  public TrainResponseImpl(String json)
    throws FaceClientException
  {
    super(json);
    try
    {
      this.no_training_set = ResponseHelper.toUserStatusList(this.response.optJSONArray("no_training_set"));
      this.in_progress = ResponseHelper.toUserStatusList(this.response.optJSONArray("in_progress"));
      this.unchanged = ResponseHelper.toUserStatusList(this.response.optJSONArray("unchanged"));
      this.updated = ResponseHelper.toUserStatusList(this.response.optJSONArray("updated"));
      this.created = ResponseHelper.toUserStatusList(this.response.optJSONArray("created"));
    }
    catch (JSONException jex)
    {
      logger.error("Error getting user statuses: " + jex.getMessage(), jex);
      throw new FaceClientException(jex);
    }
  }
  
  public final List<UserStatus> getCreated()
  {
    return this.created;
  }
  
  public final List<UserStatus> getNoTrainingSet()
  {
    return this.no_training_set;
  }
  
  public final List<UserStatus> getUpdated()
  {
    return this.updated;
  }
  
  public final List<UserStatus> getUnchanged()
  {
    return this.unchanged;
  }
  
  public final List<UserStatus> getInProgress()
  {
    return this.in_progress;
  }
  
  public String toString()
  {
    return super.toString();
  }
}
