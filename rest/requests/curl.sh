#!/usr/bin/env bash

curl -X GET \
  --location "http://demo.redmine.org/time_entries.json?user_id=533011" \
  -H "Content-Type: application/json" \
  --basic --user test.rest:test.rest!123
