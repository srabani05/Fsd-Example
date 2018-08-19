package edu.gandhi.prajit.moviecruiser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.gandhi.prajit.moviecruiser.exception.MovieAlredayExistsException;
import edu.gandhi.prajit.moviecruiser.exception.MovieNotFoundException;
import edu.gandhi.prajit.moviecruiser.repository.entity.Movie;
import edu.gandhi.prajit.moviecruiser.services.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
	public static final String MIME_JSON = "application/json";

	@Autowired
	private MovieService movieService;

	@PostMapping(consumes = { MIME_JSON }, produces = { MIME_JSON })
	@ApiOperation(value = "Create New Movie", notes = "Used For Creating New Movie", nickname = "createNewMovie")
	@ApiResponses({ @ApiResponse(code = 201, message = "Movie Created"),
			@ApiResponse(code = 409, message = "Movie Already Exists In Database") })
	public ResponseEntity<?> createNewMovie(
			@ApiParam(value = "Movie Request", required = true) @RequestBody final Movie movie) {
		System.out.println("Movie:createNewMovie:" + movie);
		try {
			movieService.createNewMovie(movie);
		} catch (MovieAlredayExistsException exception) {
			return new ResponseEntity<>("ErrorMessage:Create::" + exception.getMessage(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(movie, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}", consumes = { MIME_JSON }, produces = { MIME_JSON })
	@ApiOperation(value = "Update Existing Movie", notes = "Used For Updating Movie Information", nickname = "updateMovieInformation")
	@ApiResponses({ @ApiResponse(code = 200, message = "Movie Information Updated"),
			@ApiResponse(code = 409, message = "Movie Not Exists In Database") })
	public ResponseEntity<?> updateMovieInformation(
			@ApiParam(value = "Movie Id For Update", required = true) @PathVariable final Integer id,
			@ApiParam(value = "Movie Request", required = true) @RequestBody final Movie movie) {
		try {
			System.out.println("Movie:updateMovieInformation:" + movie + "[" + id + "]");
			final Movie fechedMovie = movieService.updateMovieInformation(movie);
			return ResponseEntity.ok(fechedMovie);
		} catch (MovieNotFoundException exception) {
			return new ResponseEntity<>("ErrorMessage:Update::" + exception.getMessage() + "\"}", HttpStatus.CONFLICT);
		}

	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete Existing Movie", notes = "Used For Deleting Movie Information", nickname = "deleteMovieByMovieId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Movie Information Deleted"),
			@ApiResponse(code = 404, message = "Movie Not Exists In Database") })
	public ResponseEntity<String> deleteMovieByMovieId(
			@ApiParam(value = "Movie Id For Delete", required = true) @PathVariable final Integer id) {
		System.out.println("Movie:deleteMovieByMovieId:" + "[" + id + "]");
		try {
			movieService.deleteMovieByMovieId(id);
		} catch (MovieNotFoundException e) {
			return new ResponseEntity<>("ErrorMessage:Delete::" + e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok("Movie Deletion Sucessful.");
	}

	@GetMapping(path = "/{id}", produces = { MIME_JSON })
	@ApiOperation(value = "Get Existing Movie", notes = "Used For Get Movie Information", nickname = "getMovieByMovieId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Movie Information Retrieved"),
			@ApiResponse(code = 404, message = "Movie Not Exists In Database") })
	public ResponseEntity<?> getMovieByMovieId(
			@ApiParam(value = "Movie Id For Retrieval", required = true) @PathVariable final Integer id) {
		System.out.println("Movie:getMovieByMovieId:" + "[" + id + "]");
		try {
			return ResponseEntity.ok(movieService.getMovieByMovieId(id));
		} catch (MovieNotFoundException e) {
			return new ResponseEntity<>("ErrorMessage:Get::" + e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(produces = { MIME_JSON })
	@ApiOperation(value = "Get All Existing Movie", notes = "Used For Get All Movie Information", nickname = "getAllMovies")
	@ApiResponses({ @ApiResponse(code = 200, message = "All Movie Information Retrieved") })
	public ResponseEntity<List<Movie>> getAllMovies() {
		return ResponseEntity.ok(movieService.getAllMovies());
	}
}
