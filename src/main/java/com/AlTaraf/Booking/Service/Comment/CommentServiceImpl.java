package com.AlTaraf.Booking.Service.Comment;

import com.AlTaraf.Booking.Entity.Comment;
import com.AlTaraf.Booking.Repository.Comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
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
