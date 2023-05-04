#!/bin/sh

# Wait for database server to become available
wait-for-it ${DATABASE_HOST}:${DATABASE_PORT} --timeout=120
