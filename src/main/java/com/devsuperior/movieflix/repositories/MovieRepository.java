package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Busca com filtro
    @EntityGraph(attributePaths = {"genre"})
    @Query("SELECT m FROM Movie m WHERE m.genre.id = :genreId")
    Page<Movie> findByGenreId(Long genreId, Pageable pageable);

    // Busca tudo (já existe no JpaRepository, mas adicionamos o EntityGraph)
    @EntityGraph(attributePaths = {"genre"})
    @Override
    Page<Movie> findAll(Pageable pageable);

}
