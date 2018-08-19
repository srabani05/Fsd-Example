package edu.gandhi.prajit.moviecruiser.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.gandhi.prajit.moviecruiser.exception.MovieAlredayExistsException;
import edu.gandhi.prajit.moviecruiser.exception.MovieNotFoundException;
import edu.gandhi.prajit.moviecruiser.repository.MovieRepository;
import edu.gandhi.prajit.moviecruiser.repository.entity.Movie;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DefaultMovieService.class })
public class DefaultMovieServiceTest {

	@MockBean
	private MovieRepository movieRepository;
	private Movie movieFixture;
	@Autowired
	private DefaultMovieService defaultMovieService;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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

	@Test(expected = MovieAlredayExistsException.class)
	public void testCreateNewMovie() throws MovieAlredayExistsException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");
		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.of(this.movieFixture));
		this.defaultMovieService.createNewMovie(this.movieFixture);
	}

	@Test
	public void testCreateNewMovie_Rule() throws MovieAlredayExistsException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");
		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.of(this.movieFixture));

		expectedException.expect(MovieAlredayExistsException.class);
		expectedException.expectMessage("Unable To Save Movie, Movie Already Exists In DataBase:" + this.movieFixture);

		this.defaultMovieService.createNewMovie(this.movieFixture);
	}

	@Test
	public void testCreateNewMovie_NotInDb() throws MovieAlredayExistsException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");
		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.empty());
		when(this.movieRepository.save(this.movieFixture)).thenReturn(this.movieFixture);

		this.defaultMovieService.createNewMovie(this.movieFixture);

		verify(this.movieRepository, times(1)).findById(this.movieFixture.getId());
		verify(this.movieRepository, times(1)).save(this.movieFixture);
	}

	@Test
	public void testUpdateMovieInformation() throws MovieNotFoundException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");
		Movie movieToSave = createMovie(1, "The Shawshank Redemption", "1994 Updated Comment",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");

		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.of(this.movieFixture));
		when(this.movieRepository.save(this.movieFixture)).thenReturn(movieToSave);

		this.defaultMovieService.updateMovieInformation(movieToSave);

		verify(this.movieRepository, times(1)).findById(this.movieFixture.getId());
		verify(this.movieRepository, times(1)).save(this.movieFixture);
		assertThat(movieToSave.getComment()).isEqualTo("1994 Updated Comment");
	}

	@Test
	public void testUpdateMovieInformation_Exception() throws MovieNotFoundException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");

		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.empty());
		expectedException.expect(MovieNotFoundException.class);
		expectedException.expectMessage("Unable To Update Movie, Movie Not Exists In DataBase:" + this.movieFixture);

		this.defaultMovieService.updateMovieInformation(this.movieFixture);

		verify(this.movieRepository, times(1)).findById(this.movieFixture.getId());
	}

	@Test
	public void testDeleteMovieByMovieId_MovieNotFoundException() throws MovieNotFoundException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");

		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.empty());
		expectedException.expect(MovieNotFoundException.class);
		expectedException
				.expectMessage("Unable To Delete Movie, Movie Id Not Exists In DataBase:" + this.movieFixture.getId());

		this.defaultMovieService.deleteMovieByMovieId(this.movieFixture.getId());

		verify(this.movieRepository, times(1)).findById(this.movieFixture.getId());
	}

	@Test
	public void testDeleteMovieByMovieId() throws MovieNotFoundException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");

		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.of(this.movieFixture));

		this.defaultMovieService.deleteMovieByMovieId(this.movieFixture.getId());

		verify(this.movieRepository, times(1)).findById(this.movieFixture.getId());
	}

	@Test
	public void testGetMovieByMovieId_MovieNotFoundException() throws MovieNotFoundException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");

		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.empty());
		expectedException.expect(MovieNotFoundException.class);
		expectedException.expectMessage(
				"Unable To Retrieve Movie, Movie Id Not Exists In DataBase:" + this.movieFixture.getId());

		this.defaultMovieService.getMovieByMovieId(this.movieFixture.getId());

		verify(this.movieRepository, times(1)).findById(this.movieFixture.getId());
	}

	@Test
	public void testGetMovieByMovieId() throws MovieNotFoundException {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");

		when(this.movieRepository.findById(this.movieFixture.getId())).thenReturn(Optional.of(this.movieFixture));

		final Movie actualMovie = this.defaultMovieService.getMovieByMovieId(this.movieFixture.getId());

		verify(this.movieRepository, times(1)).findById(this.movieFixture.getId());
		assertThat(actualMovie).isEqualToComparingFieldByFieldRecursively(this.movieFixture);
	}

	@Test
	public void testGetAllMovies() {
		this.movieFixture = createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i");

		when(this.movieRepository.findAll()).thenReturn(Arrays.asList(this.movieFixture));

		List<Movie> movieActual = this.defaultMovieService.getAllMovies();

		verify(this.movieRepository, times(1)).findAll();
		assertThat(movieActual).hasSize(1);
		assertThat(movieActual.get(0)).isEqualToComparingFieldByFieldRecursively(this.movieFixture);
	}
}