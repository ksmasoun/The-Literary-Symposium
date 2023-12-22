package ca.sheridancollege.BookDiscussionForum.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.BookDiscussionForum.beans.Book;
import ca.sheridancollege.BookDiscussionForum.beans.Review;
import ca.sheridancollege.BookDiscussionForum.beans.Users;
import ca.sheridancollege.BookDiscussionForum.database.DatabaseAccess;


@Controller 
public class HomeController {

    @Autowired
    DatabaseAccess databaseAccess;

    @GetMapping("/")
    public String goHome(Model model){

        List<Book> books = databaseAccess.getBooks();
        model.addAttribute("bookList", books);
        model.addAttribute("book", new Book());
        return "index";
    }

    @GetMapping("/login")
    public String goToLogin(){
        return "login";
    }

    @GetMapping("/register")
    public String goToRegister(Model model){
        model.addAttribute("Users", new Users());
        return "/register";
    }

    @GetMapping("/addBook")
    public String goToAddBookAdmin(){
        return "/admin/add-book";
    }


    @GetMapping("/permission-denied")
    public String goToPermissionDenied(){
        return "/error/permission-denied";
    }

    @GetMapping("/viewBook")
    public String goToReview(@RequestParam("id") Long bookId, Model model) {
    
        Book book = databaseAccess.getBook(bookId);
        String authorName = book.getAuthor();
        List<Review> reviews = databaseAccess.getReviewsForBook(bookId);
    
        model.addAttribute("reviews", reviews);
        model.addAttribute("authorName", authorName);
        model.addAttribute("bookId", bookId);
    
        return "/view-book";
    }    

        @PostMapping("/submitBook")
    public String addBook(@ModelAttribute Book book) throws Exception {
        databaseAccess.addBook(book);
        return "redirect:/";
    }


     @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute Users users) { 

        String encodedPassword = passwordEncoder.encode(users.getPassword());
        UserDetails userDetails = User.builder()
                .username(users.getUsername())  
                .password(encodedPassword)
                .roles(users.getRole())  
                .build();

        userDetailsManager.createUser(userDetails);

        return "redirect:/login";
    }

    @GetMapping("/addReview")
    public String goToAddReview(Model model, 
        @RequestParam("bookId") Long bookId) {                                            
            model.addAttribute("review", new Review());
            model.addAttribute("bookId", bookId);
            return "/user/add-review";
    }

    @PostMapping("/submitReview")
    public String submitReview(@ModelAttribute Review review) throws Exception {
        databaseAccess.addReview(review);
        return "redirect:/";
    }

}
