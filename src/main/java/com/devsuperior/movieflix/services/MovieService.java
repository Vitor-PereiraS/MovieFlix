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
        Pageable newPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("title").ascending()
        );
        Long genreIdAux = 0L;
        if((!"0".equals(genreId))){
            genreIdAux = Long.parseLong(genreId);
          Page<Movie> page = repository.searchByGenre(genreIdAux, newPageable );
            return page.map(movie -> new MovieCardDTO(movie));
        }
         else {
            Page<Movie> page = repository.searchAll(genreIdAux, newPageable );
            return page.map(movie -> new MovieCardDTO(movie));
            }
    }

}
