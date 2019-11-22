package com.example.repositories;

import com.example.model.Actor;
import com.example.model.Film;
import com.google.common.base.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
//    Optional<Film> findByTitle(String title);
}
