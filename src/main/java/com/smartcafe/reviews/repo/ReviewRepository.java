package com.smartcafe.reviews.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smartcafe.reviews.model.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
	
	List<Review> findByEmployeeId(long employeeId);
	
	List<Review> findByVendorId(long vendorId);
	
	List<Review> findByFoodId(long foodId);
	
	List<Review> findByRating(double rating);
	
	@Query("from Review r where r.employeeId=:employeeId and r.vendorId=:vendorId and r.foodId=:foodId and DATE(r.ratingTime)=:ratingTime")
	public Iterable<Review> findByEmpIdVendorIdFoodIdTime(@Param("employeeId") long employeeId,
			@Param("vendorId") long vendorId,
			@Param("foodId") long foodId,
			@Param("ratingTime") Date ratingTime);
	
	@Query("from Review r where r.vendorId=:vendorId and r.foodId=:foodId and DATE(r.ratingTime)=:ratingTime")
	public Iterable<Review> findByVendorIdFoodIdTime(@Param("vendorId") long vendorId, 
			@Param("foodId") long foodId,
			@Param("ratingTime") Date ratingTime);
	
	@Query("from Review r where r.vendorId=:vendorId and r.foodId=:foodId")
	public Iterable<Review> findByVendorIdFoodId(@Param("vendorId") long vendorId, @Param("foodId") long foodId);
	
	@Modifying
	@Transactional
	@Query("delete from Review r where r.employeeId=:employeeId and r.vendorId=:vendorId and r.foodId=:foodId and DATE(r.ratingTime)=:ratingTime")
	public void deleteByEmpIdVendorIdFoodIdTime(@Param("employeeId") long employeeId,
			@Param("vendorId") long vendorId,
			@Param("foodId") long foodId,
			@Param("ratingTime") Date ratingTime);
	
}
