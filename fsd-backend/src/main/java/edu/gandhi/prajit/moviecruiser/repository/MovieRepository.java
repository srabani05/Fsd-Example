package edu.gandhi.prajit.moviecruiser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.gandhi.prajit.moviecruiser.repository.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
