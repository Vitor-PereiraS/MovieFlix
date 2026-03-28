package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Movie movie = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("entity not found"));;
        return new MovieDetailsDTO(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(String genreId,Pageable pageable){
        Pageable orderedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("title").ascending()
        );

        if (genreId == null || "0".equals(genreId) || genreId.isEmpty()) {
            Page<Movie> page = repository.findAll(orderedPageable);
            return page.map(MovieCardDTO::new);
        }

        else {
            Page<Movie> page = repository.findByGenreId(Long.parseLong(genreId), orderedPageable);
            return page.map(MovieCardDTO::new);
        }
    }

}
