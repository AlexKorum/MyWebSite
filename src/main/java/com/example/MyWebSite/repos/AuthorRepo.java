package com.example.MyWebSite.repos;

import com.example.MyWebSite.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
}
