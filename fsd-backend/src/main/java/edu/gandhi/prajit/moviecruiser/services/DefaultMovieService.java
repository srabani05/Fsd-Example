package edu.gandhi.prajit.moviecruiser.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.gandhi.prajit.moviecruiser.exception.MovieAlredayExistsException;
import edu.gandhi.prajit.moviecruiser.exception.MovieNotFoundException;
import edu.gandhi.prajit.moviecruiser.repository.MovieRepository;
import edu.gandhi.prajit.moviecruiser.repository.entity.Movie;

@Service
public class DefaultMovieService implements MovieService {
	private final MovieRepository movieRepository;

	@Autowired
	public DefaultMovieService(final MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public void createNewMovie(Movie movie) throws MovieAlredayExistsException {
		final Optional<Movie> optionalMovie = movieRepository.findById(movie.getId());
		if (optionalMovie.isPresent()) {
			throw new MovieAlredayExistsException("Unable To Save Movie, Movie Already Exists In DataBase:" + movie);
		}
		movieRepository.save(movie);
	}

	@Override
	public Movie updateMovieInformation(Movie updateMovie) throws MovieNotFoundException {
		final Optional<Movie> optionalMovie = movieRepository.findById(updateMovie.getId());

		final Movie optionalMovieIfExist = optionalMovie.orElseThrow(() -> new MovieNotFoundException(
				"Unable To Update Movie, Movie Not Exists In DataBase:" + updateMovie));

		// Set All Values:As Those Are Updated Values:For Now We Can Only Update
		// Following Value
		Optional.ofNullable(updateMovie.getComment()).ifPresent(optionalMovieIfExist::setComment);

		movieRepository.save(optionalMovieIfExist);
		return optionalMovieIfExist;
	}

	@Override
	public void deleteMovieByMovieId(int id) throws MovieNotFoundException {
		final Optional<Movie> movie = movieRepository.findById(id);
		if (!movie.isPresent()) {
			throw new MovieNotFoundException("Unable To Delete Movie, Movie Id Not Exists In DataBase:" + id);
		}
		movie.ifPresent(movieRepository::delete);
	}

	@Override
	public Movie getMovieByMovieId(int id) throws MovieNotFoundException {
		final Optional<Movie> movie = movieRepository.findById(id);
		return movie.orElseThrow(
				() -> new MovieNotFoundException("Unable To Retrieve Movie, Movie Id Not Exists In DataBase:" + id));
	}

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
}
