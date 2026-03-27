package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    /*
    @Query(nativeQuery = true, value = """

        SELECT * EXCEPT (TB_MOVIE.SYNOPSIS) FROM TB_MOVIE
        INNER JOIN TB_GENRE ON TB_MOVIE.GENRE_ID = TB_GENRE.ID
        WHERE TB_GENRE.ID = (:genreId)
        """, countQuery = """
     SELECT COUNT(*) FROM (
        SELECT * EXCEPT (TB_MOVIE.SYNOPSIS) FROM TB_MOVIE
        INNER JOIN TB_GENRE ON TB_MOVIE.GENRE_ID = TB_GENRE.ID
        WHERE TB_GENRE.ID = (:genreId) 
          ) AS tb_result    
     """)
    Page<Movie> searchMovieByGenreId(Long genreId, Pageable pageable);
     */


    @Query(value =
    "SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.genre.id = (:genreId)",
        countQuery = "SELECT COUNT(obj) FROM Movie obj JOIN obj.genre")
    Page<Movie> searchByGenre(Long genreId, Pageable pageable);

    @Query(value =
            "SELECT obj FROM Movie obj JOIN FETCH obj.genre",
            countQuery = "SELECT COUNT(obj) FROM Movie obj JOIN obj.genre")
    Page<Movie> searchAll(Long genreId, Pageable pageable);

}
