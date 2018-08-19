Spring Boot Movie Cruiser Server Application Codebase
Add Spring Data Jpa+MySql/Local Testing Use Dev  Tools Dependency+H2 Database Application.yml
source ./env-variable.sh-->Before Mvn Run,mvn clean,mvn package,mvn spring-boot:run

#Docker:
docker images 
docker images -q --no-trunc
docker pull mysql
docker history mysql ->Layers

docker ps -a
docker rm db-movie-cruiser-->Remove Container By Name
docker logs db-movie-cruiser
docker inspect db-movie-cruiser

ip a | grep docker | grep inet
docker inspect db-movie-cruiser | grep IPAddress

sudo apt-get install mysql-client
mysql -uroot -proot -h 172.17.0.2 -P 3306

docker rm $(docker ps -a -q)
docker rmi $(docker images -q)
=====================================================
sudo docker pull mysql:5.5
sudo docker images

sudo docker ps --all
sudo docker logs <ContainerId>
sudo docker ps -a
sudo docker stop <ContainerId>
sudo docker rm <ContainerId>

netstat -ano|grep <PortNo>
sudo service mysql status/sudo service mysql stop/sudo service mysql start

sudo docker exec -it <ContainerId> bash
mysql -uroot -proot,show databases
sudo docker run --detach --name=movie-cruiser-mysql --network=host --env MYSQL_ROOT_PASSWORD=root --env MYSQL_DATABASE=MovieCruiser --env MYSQL_USER=app_root --env MYSQL_PASSWORD=root123  --publish 3306:3306 mysql:5.5

mysql -uroot -proot -h localhost -P 3306 --protocol tcp Connect Containerized MySql From Host
sudo docker build -t prajit-gandhi-moviecruiser-springboot .
docker images
sudo docker run --detach --name=MovieCruiserBackEnd --network=host --publish 8080:8080 prajit-gandhi-moviecruiser-springboot

Sample Movie Request For Test:
{
      "vote_count": 6836,
      "id": 299536,
      "video": false,
      "vote_average": 8.3,
      "title": "Avengers: Infinity War",
      "popularity": 309.417,
      "poster_path": "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
      "original_language": "en",
      "original_title": "Avengers: Infinity War",
      "genre_ids": [
        12,
        878,
        14,
        28
      ],
      "backdrop_path": "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg",
      "adult": false,
      "overview": "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
      "release_date": "2018-04-25"
    }

Adding Comment:
{
  "id": 299536,
  "vote_count": 6836,
  "vote_average": 8.3,
  "title": "Avengers: Infinity War",
  "popularity": 309.417,
  "poster_path": "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
  "overview": "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
  "comment": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
  "release_date": "2018-04-25"
}
