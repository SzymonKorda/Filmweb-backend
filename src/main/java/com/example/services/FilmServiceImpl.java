package com.example.services;

import com.example.exceptions.NotFoundException;
import com.example.model.Film;
import com.example.repositories.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Page<Film> findAllFilms(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    @Override
    public Film newFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film updateFilm(Long filmId, Film filmUpdated) {
        return filmRepository.findById(filmId).map(film -> {
            film.setTitle(filmUpdated.getTitle());
            film.setBoxoffice(filmUpdated.getBoxoffice());
            film.setDuration(filmUpdated.getDuration());
            return filmRepository.save(film);
        }).orElseThrow(() -> new NotFoundException("Film not found with id:" + filmId));
    }
}
