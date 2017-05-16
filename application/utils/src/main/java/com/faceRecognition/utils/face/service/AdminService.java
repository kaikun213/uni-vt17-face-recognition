package com.faceRecognition.utils.face.service;

import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;

public interface AdminService {
	
	/** Get a link to a photo from an id
	 * 
	 * @param id
	 * @return link to a photo
	 */
	String get(String id);
	
	/** Update the photo of the given Id
	 * 
	 * @param id of entity to update
	 * @param link to new photo
	 */
	void update(String id, String url);

	/** Create a new entity with an id and a link to photo
	 * 
	 * @param id - identifier for new entity
	 * @param url - URL to the photo
	 * @throws FaceClientException 
	 * @throws FaceServerException
	 */
	void create(String id, String url) throws FaceClientException, FaceServerException;
	
	/** Delete the entity identified by the id
	 * 
	 * @param id
	 */
	void delete(String id);

}
