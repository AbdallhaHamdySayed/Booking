package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
