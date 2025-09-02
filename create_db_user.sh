set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER retrobuddy WITH PASSWORD 'dev';
    GRANT ALL PRIVILEGES ON DATABASE $POSTGRES_DB TO retrobuddy;
    ALTER USER retrobuddy WITH CREATEROLE;
EOSQL
