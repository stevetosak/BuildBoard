#!/bin/bash

source ./.env.dbparams

profile=$1

if [ "$profile" == '-r' ]; then
  mvn flyway:clean \
    -Dflyway.user="$REMOTE_USER" \
    -Dflyway.password="$REMOTE_PASS" \
    -Dflyway.url=jdbc:postgresql://localhost:"$REMOTE_PORT"/"$REMOTE_DB" \
    -Dflyway.cleanDisabled=false
    elif [ "$profile" == '-l' ]; then
         mvn flyway:clean \
            -Dflyway.user="$POSTGRES_USER" \
            -Dflyway.password="$POSTGRES_PASS" \
            -Dflyway.url=jdbc:postgresql://localhost:"$POSTGRES_PORT"/"$POSTGRES_DB" \
            -Dflyway.cleanDisabled=false
            else
              echo "INVALID RUN OPTION."
              echo "USE -r TO CLEAN REMOTE DATABASE"
              echo "USE -l TO CLEAN LOCAL DATABASE"
              exit 0
    fi

