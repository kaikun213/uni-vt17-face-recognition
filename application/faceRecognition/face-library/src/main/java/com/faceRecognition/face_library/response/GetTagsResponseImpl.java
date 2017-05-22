package com.faceRecognition.face_library.response;

import java.util.List;
import org.json.JSONException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.model.Photo;

public class GetTagsResponseImpl
  extends AbstractResponse
  implements GetTagsResponse
{
  private final List<Photo> photos;
  
  public GetTagsResponseImpl(String json)
    throws FaceClientException
  {
    super(json);
    try
    {
      this.photos = ResponseHelper.toPhotoList(this.response.getJSONArray("photos"));
    }
    catch (JSONException jex)
    {
      logger.error("Error getting photos: " + jex.getMessage(), jex);
      throw new FaceClientException(jex);
    }
  }
  
  public List<Photo> getPhotos()
  {
    return this.photos;
  }
  
  public String toString()
  {
    return super.toString();
  }
}
