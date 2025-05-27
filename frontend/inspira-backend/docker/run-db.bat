set container-name=postgres-with-logs
set image-name=inspira_db_database-postgres

docker stop %container-name%
docker rm %container-name%
docker rmi %image-name%
docker compose up -d
timeout /t 5