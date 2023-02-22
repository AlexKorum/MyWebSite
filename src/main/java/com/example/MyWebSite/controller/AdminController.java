package com.example.MyWebSite.controller;

import com.example.MyWebSite.domain.Author;
import com.example.MyWebSite.domain.Book;
import com.example.MyWebSite.domain.Genre;
import com.example.MyWebSite.repos.AuthorRepo;
import com.example.MyWebSite.repos.BookRepo;
import com.example.MyWebSite.repos.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private BookRepo bookRepo;

    @GetMapping
    public String adminPanels(Model model) {
        model.addAttribute("authorsList", authorRepo.findAll());
        model.addAttribute("genresList", genreRepo.findAll());
        return "admin";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@RequestParam String author_name, Model model) {
        authorRepo.save(new Author(author_name));
        return "redirect:/admin";
    }

    @PostMapping("/addGenre")
    public String addGenre(@RequestParam String genre_name, Model model) {
        genreRepo.save(new Genre(genre_name));
        return "redirect:/admin";
    }

    @PostMapping("/addBook")
    public String addBook(@RequestParam String book_name,
                          @RequestParam String book_description,
                          @RequestParam Author author_selected,
                          @RequestParam Genre genre_selected,
                          Model model) {
        if (book_name != null && !book_name.isEmpty()
                && book_description != null && !book_description.isEmpty()
                && author_selected != null && genre_selected != null) {

            bookRepo.save(new Book(book_name, book_description, author_selected, genre_selected));
        }
        return "redirect:/admin";
    }
}
