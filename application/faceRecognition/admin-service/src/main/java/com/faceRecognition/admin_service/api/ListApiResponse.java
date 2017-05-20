package com.faceRecognition.admin_service.api;

import java.util.List;
import com.faceRecognition.admin_service.api.ApiResponse.ApiError;
import com.faceRecognition.admin_service.api.ApiResponse.Status;
import com.faceRecognition.utils_service.database.model.UserEntity;

/**
 * ApiResponse format for a list of objects
 * @author kaikun
 *
 */

public class ListApiResponse {

    private final Status status;
    private final List<UserEntity> data;
    private final ApiError error;
    private final int pageNumber;
    private final String nextPage;
    private final long total;

    public ListApiResponse(Status status, List<UserEntity> data, ApiError error, int pageNumber, String nextPage,
                           long total) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.pageNumber = pageNumber;
        this.nextPage = nextPage;
        this.total = total;
    }

    public Status getStatus() {
        return status;
    }

    public List<UserEntity> getData() {
        return data;
    }

    public ApiError getError() {
        return error;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getNextPage() {
        return nextPage;
    }

    public long getTotal() {
        return total;
    }
}