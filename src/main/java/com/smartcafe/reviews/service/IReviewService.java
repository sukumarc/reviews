package com.smartcafe.reviews.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.smartcafe.reviews.model.Review;

public interface IReviewService {
	
	public List<Review> getReviews();

	public Review addReview(Review review);
	
	public Review updateReview(Review review);

	public List<Review> getReviewByEmployeeId(long employeeId);

	public List<Review> getReviewByVendorId(long vendorId);

	public List<Review> getReviewByFoodId(long foodId);

	public List<Review> getReviewByRating(double rating);

	public  Map<String,Object> getAverageRatingByDate(long vendorId, long foodId, Date ratingTime);

	public  Map<String,Object> getAverageRatingByAll(long vendorId, long foodId);

	public void deleteReview(Review review);

}
