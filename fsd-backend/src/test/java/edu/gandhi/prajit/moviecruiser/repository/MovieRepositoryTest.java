package edu.gandhi.prajit.moviecruiser.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.gandhi.prajit.moviecruiser.repository.entity.Movie;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
@Transactional
public class MovieRepositoryTest {
	@Autowired
	private MovieRepository movieRepository;

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

	@After
	public void clearRepo() {
		movieRepository.deleteAll();
	}

	@Test
	public void testCreateNewMovie() {
		movieRepository.save(createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i"));
		final Movie movieFixture = movieRepository.findById(1).orElse(null);
		assertThat(movieFixture).isNotNull();
		assertThat("The Shawshank Redemption").isEqualToIgnoringCase(movieFixture.getTitle());
		assertThat("1994").isEqualToIgnoringCase(movieFixture.getComment());
	}

	@Test
	public void testUpdateMovieInformation() {
		movieRepository.save(createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i"));
		final Movie movieFixture = movieRepository.findById(1).orElse(null);
		movieFixture.setComment("Updated");
		movieRepository.save(movieFixture);

		Movie movieActual = movieRepository.findById(1).orElse(null);
		assertThat(movieActual).isNotNull();

		assertThat("The Shawshank Redemption").isEqualToIgnoringCase(movieActual.getTitle());
		assertThat(movieActual.getComment()).isEqualTo(movieFixture.getComment());
	}

	@Test
	public void testDeleteMovieByMovieId() {
		movieRepository.save(createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i"));
		movieRepository.deleteById(1);
		assertThat(movieRepository.findById(1).orElse(null)).isNull();
	}

	@Test
	public void testGetMovieByMovieId() {
		movieRepository.save(createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i"));
		final Movie movie = movieRepository.findById(1).orElse(null);
		assertThat(movie).isNotNull();
		assertThat("The Shawshank Redemption").isEqualToIgnoringCase(movie.getTitle());
		assertThat("1994").isEqualToIgnoringCase(movie.getComment());
	}

	@Test
	public void testGetAllMovies() {
		this.movieRepository.save(createMovie(1, "The Shawshank Redemption", "1994",
				"https://www.imdb.com/title/tt0111161/?ref_=adv_li_i"));
		this.movieRepository
				.save(createMovie(2, "The Godfather", "1972", "https://www.imdb.com/title/tt0068646/?ref_=adv_li_i"));
		this.movieRepository.save(
				createMovie(3, " The Dark Knight", "2008", "https://www.imdb.com/title/tt0468569/?ref_=adv_li_i"));

		final List<Movie> movieList = movieRepository.findAll();
		assertThat(movieList).isNotNull();
		assertThat(movieList).hasSize(3);
	}
}
