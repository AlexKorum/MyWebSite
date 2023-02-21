package com.example.MyWebSite.controller;

import com.example.MyWebSite.domain.Book;
import com.example.MyWebSite.domain.Role;
import com.example.MyWebSite.domain.User;
import com.example.MyWebSite.repos.BookRepo;
import com.example.MyWebSite.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookRepo bookRepo;

    @GetMapping
    public String bookList(@RequestParam(required = false) String search_text, @AuthenticationPrincipal User user, Model model) {

        List<Book> booksDb;

        if (search_text != null && !search_text.isEmpty()) {
            booksDb = bookRepo.findByNameLike("%" + search_text + "%");
        } else {
            booksDb = bookRepo.findAll();
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("books", booksDb);
        return "books";
    }

    @GetMapping("{book}")
    public String book(@PathVariable Book book, Model model) {
        model.addAttribute("book", book);
        model.addAttribute("author", book.getAuthor().getAuthorName());
        return "book";
    }

}
