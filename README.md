On PowerShell execute:
  1.    docker-compose up --build
  2.    docker-compose exec db psql -U dap -d premierleague -f /docker-entrypoint-initdb.d/patch00.sql
