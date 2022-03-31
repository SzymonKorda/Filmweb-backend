package com.example.repositories;

import com.example.model.Actor;
import com.example.model.Film;
import com.example.model.User;
import com.example.specification.FilmSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
    Page<Film> findAllByActors(Actor actor, Pageable pageable);
    Page<Film> findAllByUsers(User user, Pageable pageable);
}
