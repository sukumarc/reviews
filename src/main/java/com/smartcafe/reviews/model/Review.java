package com.smartcafe.reviews.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "The database generated review id") //swagger
	@JsonIgnore
	private long reviewId;
	
	@Min(value = 100000, message = "length should be minimum 6 digit")
	@Max(value = 99999999, message = "length should not more than 10 digit")
	@ApiModelProperty(notes = "Reviewer employee id") //swagger
	private long employeeId;
	
	@NotNull
	@NotBlank
	@ApiModelProperty(notes = "Reviewer employee name") //swagger
	private String employeeName;
	
	
	@Min(value = 1, message = "length should be minimum 1 digit")
	@Max(value = 999999, message = "length should not more than 6 digit")
	@ApiModelProperty(notes = "Reviewer vendor id") //swagger
	private long vendorId;
	
	@NotNull
	@NotBlank
	@ApiModelProperty(notes = "Reviewer vendor name") //swagger
	private String vendorName;
	
	@Min(value = 1, message = "length should be minimum 1 digit")
	@Max(value = 999999, message = "length should not more than 6 digit")
	@ApiModelProperty(notes = "Reviewer food id") //swagger
	private long foodId;
	
	@NotNull
	@NotBlank
	@ApiModelProperty(notes = "Reviewer food name") //swagger
	private String foodName;
	
	@Min(value = 0, message = "rating should be minimum 0")
	@Max(value = 5, message = "rating should not more than 5")
	@ApiModelProperty(notes = "Reviewer rating") //swagger
	private double rating;
	
	@NotNull
	@ApiModelProperty(notes = "Reviewer rating time") //swagger
	private LocalDateTime ratingTime;
	
	@ApiModelProperty(notes = "Reviewer commets") //swagger
	private String comments = ""; // default value

	public Review() {
		
	}
	
	public Review(
			@Min(value = 100000, message = "length should be minimum 6 digit") @Max(value = 99999999, message = "length should not more than 10 digit") long employeeId,
			@NotNull @NotBlank String employeeName,
			@Min(value = 1, message = "length should be minimum 1 digit") @Max(value = 999999, message = "length should not more than 6 digit") long vendorId,
			@NotNull @NotBlank String vendorName,
			@Min(value = 1, message = "length should be minimum 1 digit") @Max(value = 999999, message = "length should not more than 6 digit") long foodId,
			@NotNull @NotBlank String foodName,
			@Min(value = 0, message = "rating should be minimum 0") @Max(value = 5, message = "rating should not more than 5") double rating,
			@NotNull LocalDateTime ratingTime, String comments) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.foodId = foodId;
		this.foodName = foodName;
		this.rating = rating;
		this.ratingTime = ratingTime;
		this.comments = comments;
	}
	
	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	
	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public long getFoodId() {
		return foodId;
	}

	public void setFoodId(long foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public LocalDateTime getRatingTime() {
		return ratingTime;
	}

	public void setRatingTime(LocalDateTime ratingTime) {
		this.ratingTime = ratingTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		return "Review [employeeId=" + employeeId + ", employeeName=" + employeeName + ", vendorId=" + vendorId
				+ ", vendorName=" + vendorName + ", foodId=" + foodId + ", foodName=" + foodName + ", rating=" + rating
				+ ", ratingTime=" + ratingTime + ", comments=" + comments + "]";
	}

}
