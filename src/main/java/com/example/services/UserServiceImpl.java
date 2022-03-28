package com.example.services;

import com.example.mapper.FilmMapper;
import com.example.specification.FilmSpecification;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Film;
import com.example.model.User;
import com.example.payload.SimpleFilmResponse;
import com.example.payload.UserProfileResponse;
import com.example.repositories.FilmRepository;
import com.example.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private FilmRepository filmRepository;

    public UserServiceImpl(UserRepository userRepository, FilmRepository filmRepository) {
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
    }


    @Override
    public UserProfileResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setId(user.getId());
        userProfileResponse.setName(user.getName());
        userProfileResponse.setUsername(user.getUsername());
        userProfileResponse.setEmail(user.getEmail());

        user.getUserFilms().stream()
                .map(FilmMapper::mapFilmToSimpleFilmResponse)
                .forEach(film -> userProfileResponse.getUserFilms().add(film));


//        for (Film film : user.getUserFilms()) {
//            SimpleFilmResponse simpleFilmResponse = new SimpleFilmResponse();
//            simpleFilmResponse.setId(film.getId());
//            simpleFilmResponse.setTitle(film.getTitle());
//            simpleFilmResponse.setDuration(film.getDuration());
//            userProfileResponse.getUserFilms().add(simpleFilmResponse);
//        }

        return userProfileResponse;
    }

    @Override
    public Page<SimpleFilmResponse> getUserFilms(FilmSpecification filmSpecification, Pageable pageable, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Set<Film> films = user.getUserFilms();
        Page<Film> filmPage = new PageImpl<>(new ArrayList<>(films));

        int totalElements = (int) filmPage.getTotalElements();

        return new PageImpl<>(filmPage
                .stream()
                .map(FilmMapper::mapFilmToSimpleFilmResponse)
                .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    @Transactional
    public void deleteUserFilmById(Long filmId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Set<Film> films = user.getUserFilms();
        for (Film film : films) {
            if (film.getId() == filmId) {
                films.remove(film);
                break;
            }
        }
    }
}
