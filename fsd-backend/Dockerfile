FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_USER=app_root
ENV MYSQL_PASSWORD=root123
ENV MYSQL_MOVIE_DB_URL=jdbc:mysql://localhost:3306/MovieCruiser
ENV MYSQL_DATABASE=MovieCruiser
ADD ./target/fsd-moviecruiser-service-1.0.jar /usr/src/fsd-moviecruiser-service-1.0.jar
ENTRYPOINT ["java","-jar","fsd-moviecruiser-service-1.0.jar"]