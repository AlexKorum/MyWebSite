package com.example.MyWebSite.repos;

import com.example.MyWebSite.domain.Author;
import com.example.MyWebSite.domain.Book;
import com.example.MyWebSite.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findByNameLike(String name);
    List<Book> findByAuthor(Author author);
    List<Book> findByGenre(Genre genre);


}
