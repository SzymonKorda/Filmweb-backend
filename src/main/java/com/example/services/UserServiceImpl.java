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
        return UserMapper.mapUserToUserResponse(findUser(userId));
    }

    @Override
    public void addFilmToUser(Long userId, Long filmId, UserPrincipal currentUser) {
        User user = findUser(userId);
        validateIfCurrentUser(currentUser, user, "You can't add film to another user's account!");
        user.getUserFilms().add(findFilm(filmId));
        userRepository.save(user);
    }

    @Override
    public Page<SimpleFilmResponse> getUserFilms(Pageable pageable, Long userId) {
        Page<Film> filmPageList = filmRepository.findAllByUsers(findUser(userId), pageable);
        return new PageImpl<>(filmPageList
                .stream()
                .map(FilmMapper::mapFilmToSimpleFilmResponse)
                .collect(Collectors.toList()), pageable, filmPageList.getTotalElements());
    }

    @Override
    public void deleteFilmFromUser(Long filmId, Long userId, UserPrincipal currentUser) {
        User user = findUser(userId);
        validateIfCurrentUser(currentUser, user, "You can't delete film from another user's account!");
        user.getUserFilms().remove(findFilm(filmId));
        userRepository.save(user);
    }

    private void validateIfCurrentUser(UserPrincipal currentUser, User user, String error) {
        if (user.getId() != currentUser.getId()) {
            throw new AccessDeniedException(error);
        }
    }

    private User findUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    private Film findFilm(Long filmId) {
        return filmRepository
                .findById(filmId)
                .orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
    }
}
