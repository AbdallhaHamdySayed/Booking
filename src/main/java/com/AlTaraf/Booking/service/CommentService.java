package com.AlTaraf.Booking.service;


import com.AlTaraf.Booking.database.entity.Comment;
import com.AlTaraf.Booking.database.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {

        try {
            return commentRepository.save(comment);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e;
        }
    }
}
