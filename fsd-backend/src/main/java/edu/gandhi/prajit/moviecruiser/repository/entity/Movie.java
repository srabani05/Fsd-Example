package edu.gandhi.prajit.moviecruiser.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Movie")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
	@Id
	@Column(name = "MovieId")
	private int id;

	@Column(name = "VoteCount")
	@JsonProperty("vote_count")
	private int voteCount;

	@Column(name = "VoteAverage")
	@JsonProperty("vote_average")
	private float voteAverage;

	@Column(name = "Title")
	@JsonProperty("title")
	private String title;

	@Column(name = "Popularity")
	@JsonProperty("popularity")
	private float popularity;

	@Column(name = "PosterPath")
	@JsonProperty("poster_path")
	private String posterPath;

	@Lob
	@Column(name = "Overview")
	@JsonProperty("overview")
	private String overview;

	@Lob
	@Column(name = "Comment")
	@JsonProperty("comment")
	private String comment;

	@Column(name = "ReleaseDate")
	@JsonProperty("release_date")
	private String releaseDate;

	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the voteCount
	 */
	public final int getVoteCount() {
		return voteCount;
	}

	/**
	 * @param voteCount
	 *            the voteCount to set
	 */
	public final void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	/**
	 * @return the voteAverage
	 */
	public final float getVoteAverage() {
		return voteAverage;
	}

	/**
	 * @param voteAverage
	 *            the voteAverage to set
	 */
	public final void setVoteAverage(float voteAverage) {
		this.voteAverage = voteAverage;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the popularity
	 */
	public final float getPopularity() {
		return popularity;
	}

	/**
	 * @param popularity
	 *            the popularity to set
	 */
	public final void setPopularity(float popularity) {
		this.popularity = popularity;
	}

	/**
	 * @return the posterPath
	 */
	public final String getPosterPath() {
		return posterPath;
	}

	/**
	 * @param posterPath
	 *            the posterPath to set
	 */
	public final void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	/**
	 * @return the overview
	 */
	public final String getOverview() {
		return overview;
	}

	/**
	 * @param overview
	 *            the overview to set
	 */
	public final void setOverview(String overview) {
		this.overview = overview;
	}

	/**
	 * @return the releaseDate
	 */
	public final String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate
	 *            the releaseDate to set
	 */
	public final void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the comment
	 */
	public final String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public final void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return String.format(
				"Movie [id=%s, voteCount=%s, voteAverage=%s, title=%s, popularity=%s, posterPath=%s, overview=%s, comment=%s, releaseDate=%s]",
				id, voteCount, voteAverage, title, popularity, posterPath, overview, comment, releaseDate);
	}
}