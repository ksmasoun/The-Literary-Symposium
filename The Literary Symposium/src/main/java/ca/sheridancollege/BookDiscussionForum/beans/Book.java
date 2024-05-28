package ca.sheridancollege.BookDiscussionForum.beans;

import java.util.List;
import lombok.Data;

@Data   
public class Book {

    private Long id;
    private String title;
    private String author;
    private List<Review> reviews;
    
}
