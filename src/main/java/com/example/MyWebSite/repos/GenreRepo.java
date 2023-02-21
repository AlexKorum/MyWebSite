package com.example.MyWebSite.repos;

import com.example.MyWebSite.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Long> {
}
