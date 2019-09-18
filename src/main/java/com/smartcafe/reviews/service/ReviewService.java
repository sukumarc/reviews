package com.smartcafe.reviews.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcafe.reviews.exception.ReviewNotFoundException;
import com.smartcafe.reviews.model.Review;
import com.smartcafe.reviews.repo.ReviewRepository;

@Service
public class ReviewService implements IReviewService {

	@Autowired
	ReviewRepository reviewRepository;

	@Override
	public List<Review> getReviews() {
		List<Review> reviewList =  (List<Review>) reviewRepository.findAll();
		if(reviewList.isEmpty()) {
			throw new ReviewNotFoundException("review is not found");
		}
		return reviewList;
	}
	
	@Override
	public Review addReview(Review review) {
		List<Review> reviewList = (List<Review>) reviewRepository.findByEmpIdVendorIdFoodIdTime(review.getEmployeeId(), review.getVendorId(), 
				review.getFoodId(), Date.from(review.getRatingTime().atZone(ZoneId.systemDefault()).toInstant()));
		if(!reviewList.isEmpty()) {
			Review oldReview = reviewList.get(0);
			review.setReviewId(oldReview.getReviewId());
		}
		review.setRatingTime(LocalDateTime.now());
		return reviewRepository.save(review);
	}
	
	
	@Override
	public Review updateReview(Review review) {
		
		List<Review> reviewList = (List<Review>) reviewRepository.findByEmpIdVendorIdFoodIdTime(review.getEmployeeId(), review.getVendorId(), 
				review.getFoodId(), Date.from(review.getRatingTime().atZone(ZoneId.systemDefault()).toInstant()));
		if(reviewList.isEmpty()) {
			throw new ReviewNotFoundException("review is not found");
		}
		
		Review oldReview = reviewList.get(0);
		review.setReviewId(oldReview.getReviewId());
		review.setRatingTime(LocalDateTime.now());
		return reviewRepository.save(review);
	}
	
	@Override
	public List<Review> getReviewByEmployeeId(long employeeId) {
		List<Review> reviewList =  (List<Review>) reviewRepository.findByEmployeeId(employeeId);
		if(reviewList.isEmpty()) {
			throw new ReviewNotFoundException("review is not found");
		}
		return reviewList;
	}
	
	@Override
	public List<Review> getReviewByVendorId(long vendorId) {
		List<Review> reviewList = (List<Review>) reviewRepository.findByVendorId(vendorId);
		if(reviewList.isEmpty()) {
			throw new ReviewNotFoundException("review is not found");
		}
		return reviewList;
	}
	
	@Override
	public List<Review> getReviewByFoodId(long foodId) {
		List<Review> reviewList =  (List<Review>) reviewRepository.findByFoodId(foodId);
		if(reviewList.isEmpty()) {
			throw new ReviewNotFoundException("review is not found");
		}
		return reviewList;
	}
	
	@Override
	public List<Review> getReviewByRating(double rating) {
		List<Review> reviewList =  (List<Review>) reviewRepository.findByRating(rating);
		if(reviewList.isEmpty()) {
			throw new ReviewNotFoundException("review is not found");
		}
		return reviewList;
	}
	
	@Override
	public Map<String,Object> getAverageRatingByDate(long vendorId, long foodId, Date ratingTime) {
		List<Review> reviewList = (List<Review>) reviewRepository.findByVendorIdFoodIdTime(vendorId, foodId, ratingTime);
		if(reviewList.isEmpty()) {
			throw new ReviewNotFoundException("review is not found");
		}
		Double average = reviewList.stream().mapToDouble(emp -> emp.getRating()).average().orElse(0);
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("reviews", reviewList);
		returnMap.put("average", average);
		return returnMap;
	}
	
	@Override
	public  Map<String,Object> getAverageRatingByAll(long vendorId, long foodId) {
		List<Review> reviewList = (List<Review>) reviewRepository.findByVendorIdFoodId(vendorId, foodId);
		if(reviewList.isEmpty()) {
			throw new ReviewNotFoundException("review is not found");
		}
		Double average = reviewList.stream().mapToDouble(emp -> emp.getRating()).average().orElse(0);
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("reviews", reviewList);
		returnMap.put("average", average);
		return returnMap;
	}
	
	@Override
	public void deleteReview(Review review) {
		reviewRepository.deleteByEmpIdVendorIdFoodIdTime(review.getEmployeeId(), review.getVendorId(), 
				review.getFoodId(), Date.from(review.getRatingTime().atZone( ZoneId.systemDefault()).toInstant()));
	}
	
	
}