package com.faceRecognition.utils.face.service;

import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;

public interface AdminService {
	
	/** Update the photo of the given Id
	 * 
	 * @param id of entity to update
	 * @param link to new photo
	 */
	void update(String id, String newUrl) throws FaceClientException, FaceServerException;

	/** Create a new entity with an id and a link to photo
	 * 
	 * @param id - identifier for new entity
	 * @param url - URL to the photo
	 * @return tag_id - of the added face
	 * @throws FaceClientException 
	 * @throws FaceServerException
	 */
	void create(String id, String url) throws FaceClientException, FaceServerException;
	
	/** Delete the entity identified by the id
	 * 
	 * @param tag_id - identifier of tag to delete
	 * @param url - url of the image from the user
	 */
	void delete(String tag_id) throws FaceClientException, FaceServerException;

}
