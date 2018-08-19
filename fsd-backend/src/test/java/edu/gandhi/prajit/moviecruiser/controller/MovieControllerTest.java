package edu.gandhi.prajit.moviecruiser.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.gandhi.prajit.moviecruiser.controller.MovieController;
import edu.gandhi.prajit.moviecruiser.repository.entity.Movie;
import edu.gandhi.prajit.moviecruiser.services.MovieService;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MovieService movieService;
	/**
	 * List Of Movie
	 */
	private List<Movie> movies;
	private ObjectMapper mapper;

	@Before
	public void setUp() throws Exception {
		this.movies = Arrays.asList(
				createMovie(1, "The Shawshank Redemption", "1994",
						"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i"),
				createMovie(2, "The Godfather", "1972", "https://www.imdb.com/title/tt0068646/?ref_=adv_li_i"),
				createMovie(3, " The Dark Knight", "2008", "https://www.imdb.com/title/tt0468569/?ref_=adv_li_i"));
		this.mapper = new ObjectMapper();
	}

	private Movie createMovie(int id, String name, String comments, String posterPath) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(name);
		movie.setComment(comments);
		movie.setPosterPath(posterPath);
		movie.setReleaseDate(LocalDate.now().toString());
		movie.setVoteAverage((float) (Math.random() * 100) / 100);
		movie.setVoteCount((int) Math.ceil(Math.random() * 1000));
		return movie;
	}

	@Test
	public void testCreateNewMovie() throws Exception {
		final Movie movieToCreate = this.movies.get(0);
		Mockito.doNothing().when(this.movieService).createNewMovie(movieToCreate);

		this.mockMvc.perform(post("/api/v1/movie").contentType(MovieController.MIME_JSON)
				.content(this.mapper.writeValueAsString(movieToCreate))).andExpect(status().isCreated());
		verify(this.movieService, times(1)).createNewMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(this.movieService);
	}

	@Test
	public void testUpdateMovieInformation() throws Exception {
		final Movie movieToCreate = this.movies.get(0);
		movieToCreate.setVoteCount(movieToCreate.getVoteCount() + 1000);

		Mockito.when(this.movieService.updateMovieInformation(Mockito.<Movie>any())).thenReturn(movieToCreate);

		this.mockMvc.perform(put("/api/v1/movie/{id}", 1).contentType(MovieController.MIME_JSON)
				.content(this.mapper.writeValueAsString(movieToCreate))).andExpect(status().isOk());
		// Assert On Response Body:Code_Debt
		verify(this.movieService, times(1)).updateMovieInformation(Mockito.any(Movie.class));
		verifyNoMoreInteractions(this.movieService);
	}

	@Test
	public void testDeleteMovieByMovieId() throws Exception {
		Mockito.doNothing().when(this.movieService).deleteMovieByMovieId(1);
		this.mockMvc.perform(delete("/api/v1/movie/{id}", 1)).andExpect(status().isOk());

		verify(this.movieService, times(1)).deleteMovieByMovieId(1);
		verifyNoMoreInteractions(this.movieService);
	}

	@Test
	public void testGetMovieByMovieId() throws Exception {
		Mockito.when(this.movieService.getMovieByMovieId(1)).thenReturn(this.movies.get(0));
		this.mockMvc.perform(get("/api/v1/movie/{id}", 1)).andExpect(status().isOk());
		// Assert On Response Body:Code_Debt
		verify(this.movieService, times(1)).getMovieByMovieId(1);
		verifyNoMoreInteractions(this.movieService);
	}

	@Test
	public void testGetAllMovies() throws Exception {
		Mockito.when(this.movieService.getAllMovies()).thenReturn(this.movies);
		this.mockMvc.perform(get("/api/v1/movie")).andExpect(status().isOk());
		// Assert On Response Body:Code_Debt
		verify(this.movieService, times(1)).getAllMovies();
		verifyNoMoreInteractions(this.movieService);
	}
}
