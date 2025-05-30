set container-name=postgres-with-logs-inspira
set image-name=inspira_db_database-postgres

docker stop %container-name%
docker rm %container-name%
docker rmi %image-name%
docker-compose -f docker-compose.db.yaml up
timeout /t 5