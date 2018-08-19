package edu.gandhi.prajit.moviecruiser.services;

import java.util.List;

import edu.gandhi.prajit.moviecruiser.exception.MovieAlredayExistsException;
import edu.gandhi.prajit.moviecruiser.exception.MovieNotFoundException;
import edu.gandhi.prajit.moviecruiser.repository.entity.Movie;

public interface MovieService {
	public void createNewMovie(final Movie movie) throws MovieAlredayExistsException;

	public Movie updateMovieInformation(final Movie movie) throws MovieNotFoundException;

	public void deleteMovieByMovieId(final int id) throws MovieNotFoundException;

	public Movie getMovieByMovieId(final int id) throws MovieNotFoundException;

	public List<Movie> getAllMovies();
}
