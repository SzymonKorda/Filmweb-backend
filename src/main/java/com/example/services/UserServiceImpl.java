package com.example.services;

import com.example.mapper.FilmMapper;
import com.example.mapper.UserMapper;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Film;
import com.example.model.User;
import com.example.payload.response.SimpleFilmResponse;
import com.example.payload.response.UserProfileResponse;
import com.example.repositories.FilmRepository;
import com.example.repositories.UserRepository;
import com.example.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;

    @Override
    public UserProfileResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return UserMapper.mapUserToUserResponse(user);
    }

    @Override
    public void addFilmToUser(Long userId, Long filmId, UserPrincipal currentUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        validateUser(currentUser, user, "You can't add film to another user's account!");
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        user.getUserFilms().add(film);
        userRepository.save(user);
    }

    @Override
    public Page<SimpleFilmResponse> getUserFilms(Pageable pageable, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Page<Film> filmPageList = filmRepository.findAllByUsers(user, pageable);
        return new PageImpl<>(filmPageList
                .stream()
                .map(FilmMapper::mapFilmToSimpleFilmResponse)
                .collect(Collectors.toList()), pageable, filmPageList.getTotalElements());
    }

    @Override
    public void deleteFilmFromUser(Long filmId, Long userId, UserPrincipal currentUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        validateUser(currentUser, user, "You can't delete film from another user's account!");
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        user.getUserFilms().remove(film);
        userRepository.save(user);
    }

    private void validateUser(UserPrincipal currentUser, User user, String error) {
        if (user.getId() != currentUser.getId()) {
            throw new AccessDeniedException(error);
        }
    }
}
