package ca.sheridancollege.BookDiscussionForum.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.BookDiscussionForum.beans.Book;
import ca.sheridancollege.BookDiscussionForum.beans.Review;
import ca.sheridancollege.BookDiscussionForum.database.DatabaseAccess;

@RestController
@RequestMapping("/books")
public class BookController {
    
    private DatabaseAccess databaseAccess;

    public BookController (DatabaseAccess databaseAccess){
        this.databaseAccess = databaseAccess;
    }

    @GetMapping
    public List<Book> getBooks(){
        return databaseAccess.getBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        Book book = databaseAccess.getBook(id);
        
        if (book != null) {
            List<Review> reviews = databaseAccess.getReviewsForBook(id);
            book.setReviews(reviews);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 }

