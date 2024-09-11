-Generate the jar
---
  1.    Execute the goal maven clean
  2.    Execute the goal maven install

-On PowerShell execute the build:
---
1.    docker-compose up --build

-Make HTTP requests to port 8000
---
----

----
If you want to make the database an image in docker:

-Add service on docker-compose.yml
```
db:
    image: postgres:13
    environment:
        POSTGRES_DB: premierleague
        POSTGRES_USER: dap
        POSTGRES_PASSWORD: foobar
    ports:
        - "5432:5432"
    volumes:
        - ./sql:/docker-entrypoint-initdb.d
        - postgres_data:/var/lib/postgresql/data
```
-Then add on app service:
```
    depends_on:
      - db
```
-On PowerShell execute:
  1.    docker-compose up --build
  2.    docker-compose exec db psql -U dap -d premierleague -f /docker-entrypoint-initdb.d/patch00.sql

For more info to see the docker-compose.yml file, check: 

[PremierLeagueService/commits/422aea4](https://github.com/nunoedgar/PremierLeagueService/commit/422aea4eae7a4553a6a47cc05bcf6edc4cc46a49)
