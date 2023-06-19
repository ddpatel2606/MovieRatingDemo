package com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.mapper.toMovieDetail
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.mapper.toMovieList
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.mapper.toPersonDetail
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.mapper.toVideoList
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import javax.inject.Inject

class TestMovieRepository @Inject constructor() : MovieRepository {

    private val movies = mutableListOf<FavoriteMovie>()

    override suspend fun getMovieList(
        listId: String,
        page: Int,
        region: String?
    ): Resource<MovieList> {
       return Resource.Success(MovieListModel(listOf(MovieModel(76600,"Set more than a decade",
           "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg","2022-12-14",
           "Avatar: The Way of Water",7.7),MovieModel(980078,"Christopher Robin is headed off to college",
           "/wD2kUCX1Bb6oeIb2uz7kbdfLP6k.jpg","2023-01-27",
           "Winnie the Pooh: Blood and Honey",5.9)),2).toMovieList()) }

    override suspend fun getTrendingMovies(): Resource<MovieList> {
        return Resource.Success(MovieListModel(listOf(MovieModel(980078,"Christopher Robin is headed off to college",
            "/wD2kUCX1Bb6oeIb2uz7kbdfLP6k.jpg","2023-01-27",
            "Winnie the Pooh: Blood and Honey",5.9),MovieModel(76600,"Set more than a decade",
            "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg","2022-12-14",
            "Avatar: The Way of Water",7.7)),2).toMovieList())
    }

    override suspend fun getTrendingMovieTrailers(movieId: Int): Resource<VideoList> {
        return Resource.Success(VideoListModel(listOf(VideoModel("_siNCDIbZ3s",
            "Generations","2023-03-24T14:00:18.000Z","YouTube","Feature"),VideoModel("fNx9lIJSQHU",
            "Featurette","2023-03-24T14:00:18.000Z","YouTube","Feature"),VideoModel("fNx9lIJSQHU",
            "Featurette","2023-03-24T14:00:18.000Z","YouTube","Trailer"),VideoModel("fNx9lIJSQHU",
            "Featurette","2023-03-24T14:00:18.000Z","YouTube","Teaser"))).toVideoList())
    }

    override suspend fun getMovieSearchResults(query: String, page: Int): Resource<MovieList> {
        return Resource.Success(MovieListModel(listOf(MovieModel(76600,"Set more than a decade",
            "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg","2022-12-14",
            "Avatar: The Way of Water",7.7),MovieModel(980078,"Christopher Robin is headed off to college",
            "/wD2kUCX1Bb6oeIb2uz7kbdfLP6k.jpg","2023-01-27",
            "Winnie the Pooh: Blood and Honey",5.9)),2).toMovieList())
    }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail> {
        return Resource.Success(MovieDetailModel(CreditsModel(listOf(PersonModel("Jake Sully","Acting",65731,"test","Acting","Sam Worthington","/blKKsHlJIL9PmUQZB8f3YmMBW5Y.jpg")),
            listOf(PersonModel("Jake Sully","Acting",65731,"test","Acting","Sam Worthington","/blKKsHlJIL9PmUQZB8f3YmMBW5Y.jpg")),
            listOf(PersonModel("Jake Sully","Acting",65731,"test","Acting","Sam Worthington","/blKKsHlJIL9PmUQZB8f3YmMBW5Y.jpg"))),
            "https://www.avatar.com/movies/avatar-the-way-of-water",76600,"en"
        ,"Avatar: The Way of Water","Set more than a decade after the events ","/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
            ,"2022-12-14",2309660236
        ,192,"Released","Avatar: The Way of Water", VideoListModel(listOf(VideoModel("_siNCDIbZ3s",
                "Generations","2023-03-24T14:00:18.000Z","YouTube","Feature"),VideoModel("fNx9lIJSQHU",
                "Generations","2023-03-24T14:00:18.000Z","YouTube","Featurette"))),8.0,7098
        ).toMovieDetail())
    }

    override suspend fun getPersonDetails(personId: Int): Resource<PersonDetail> {
        return Resource.Success(PersonDetailModel("As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.", "1983-07-10","2028-08-15",1,
            "https://test.com",22,"Acting","The Rock","India", "/rRdru6REr9i3WIHv2mntpcgxnoY.jpg"
            ).toPersonDetail())
    }

    override suspend fun getFavoriteMovies(): List<FavoriteMovie> {
        return listOf(FavoriteMovie(76600,"/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg","2022-12-14",9,"Avatar: The Way of Water",9.0,23,98L))
    }

    override suspend fun movieExists(movieId: Int): Boolean {
        return movies.any { it.id == movieId }
    }

    override suspend fun insertMovie(movie: FavoriteMovie) {
        movies.add(movie)
    }

    override suspend fun deleteMovie(movie: FavoriteMovie) {
        movies.remove(movie)
    }
}
