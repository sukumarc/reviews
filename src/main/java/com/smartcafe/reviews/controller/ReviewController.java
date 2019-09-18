package com.smartcafe.reviews.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartcafe.reviews.model.Review;
import com.smartcafe.reviews.service.ReviewService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("smartcafe")
@Api(value="smartcafe", description="Review module API Operaions") //swagger 
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@RequestMapping(value = "/reviews", method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "retrieves all the reviews", response = Review.class) //swagger
	public List<Review> getReviews() {
		return reviewService.getReviews();
	} 
	
	@RequestMapping(value = "/reviews", method = RequestMethod.POST,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "create a new review", response = Review.class) //swagger
	public ResponseEntity<Review> createReview(@RequestBody Review review) {
		Review createdReview = reviewService.addReview(review);
		return new ResponseEntity<Review>(createdReview, HttpStatus.CREATED);
	} 
	
	@RequestMapping(value = "/reviews", method = RequestMethod.PUT,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "update the review", response = Review.class) //swagger
	public ResponseEntity<Review> updateReview(@RequestBody Review review) {
		Review updatedReview = reviewService.updateReview(review);
		return new ResponseEntity<Review>(updatedReview, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/reviews/employees/{employeeId}", method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "retrieves all the reviews based on employeeId", response = Review.class) //swagger
	public List<Review> getReviewByEmployeeId(@PathVariable("employeeId") long employeeid) {
		return reviewService.getReviewByEmployeeId(employeeid);
	}
	
	@RequestMapping(value = "/reviews/vendors/{vendorId}", method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "retrieves all the reviews based on vendorId", response = Review.class) //swagger
	public List<Review> getReviewByVendorCode(@PathVariable("vendorId") long vendorId) {
		return reviewService.getReviewByVendorId(vendorId);
	}
	
	@RequestMapping(value = "/reviews/foods/{foodId}", method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "retrieves all the reviews based on foodId", response = Review.class) //swagger
	public List<Review> getReviewByItemCode(@PathVariable("foodId") long foodId) {
		return reviewService.getReviewByFoodId(foodId);
	}
	
	@RequestMapping(value = "/reviews/ratings/{rating}", method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "retrieves all the reviews based on rating", response = Review.class) //swagger
	public List<Review> getReviewByRating(@PathVariable("rating") double rating) {
		return reviewService.getReviewByRating(rating);
	}
	
	@RequestMapping(value = "/reviews/average/vendors/{vendorId}/foods/{foodId}", method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "retrieves all the reviews based on vendorId,foodId and optional date", response = Double.class) //swagger
	public Map<String,Object> getAverageRatingByDate(@PathVariable("vendorId") long vendorId, 
			@PathVariable("foodId") long foodId, 
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern="dd-MM-yyyy") Date ratingTime) {
		
		if(ratingTime == null) {
			return reviewService.getAverageRatingByAll(vendorId, foodId);
		} else {
			return reviewService.getAverageRatingByDate(vendorId, foodId, ratingTime);
		}
	}
	
	@RequestMapping(value = "/reviews", method = RequestMethod.DELETE,
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "delete a review") //swagger
	public ResponseEntity<Review> deleteReview(@RequestBody Review review) {
		reviewService.deleteReview(review);
		return new ResponseEntity<Review>(review,HttpStatus.OK);
	}
	
}