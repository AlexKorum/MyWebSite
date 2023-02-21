package com.example.MyWebSite.controller;

import com.example.MyWebSite.domain.Author;
import com.example.MyWebSite.domain.Book;
import com.example.MyWebSite.domain.Genre;
import com.example.MyWebSite.domain.User;
import com.example.MyWebSite.repos.AuthorRepo;
import com.example.MyWebSite.repos.BookRepo;
import com.example.MyWebSite.repos.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private GenreRepo genreRepo;

    @GetMapping
    public String bookList(@RequestParam(required = false) String search_text,
                           @RequestParam(required = false) Author author_selected,
                           @RequestParam(required = false) Genre genre_selected,
                           @AuthenticationPrincipal User user, Model model) {


        // Добавление значении фильтров
        model.addAttribute("authorsList", authorRepo.findAll());
        model.addAttribute("genresList", genreRepo.findAll());

        List<Book> booksDb;
        if (search_text != null && !search_text.isEmpty()) {
            booksDb = bookRepo.findByNameLike("%" + search_text + "%");
        } else {
            booksDb = bookRepo.findAll();
        }

        if (author_selected != null && author_selected.getId() != 0) {
            booksDb = intersection(booksDb, bookRepo.findByAuthor(author_selected));
        }
        if (genre_selected != null && genre_selected.getId() != 0) {
            booksDb = intersection(booksDb, bookRepo.findByGenre(genre_selected));
        }



        model.addAttribute("user", user);
        model.addAttribute("books", booksDb);
        return "books";
    }

    @GetMapping("{book}")
    public String book(@PathVariable Book book, Model model) {
        model.addAttribute("book", book);
        model.addAttribute("author", book.getAuthor());
        return "book";
    }


    private <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
}
