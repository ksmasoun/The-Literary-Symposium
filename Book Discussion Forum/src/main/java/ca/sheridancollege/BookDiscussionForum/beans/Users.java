package ca.sheridancollege.BookDiscussionForum.beans;

import lombok.Data;

@Data
public class Users {

    private long id;
    private String username;
    private String password;
    private String role;
    private final String[] accountRoles = {"ADMIN","USER"};
    
}
