@echo off
setlocal enabledelayedexpansion
set BASE_URL=http://localhost:8080/users

curl -X POST %BASE_URL% ^
     -H "Content-Type: application/json" ^
     -d "{\"firstName\":\"Alice\",\"lastName\":\"Apricot\"}"


curl -X POST %BASE_URL% ^
     -H "Content-Type: application/json" ^
     -d "{\"firstName\":\"Bob\",\"lastName\":\"Beans\"}"
	 
curl -X POST %BASE_URL% ^
     -H "Content-Type: application/json" ^
     -d "{\"firstName\":\"Moon\",\"lastName\":\"Pie\"}"


curl -X POST %BASE_URL% ^
     -H "Content-Type: application/json" ^
     -d "{\"firstName\":\"Amanda\",\"lastName\":\"Cook\"}"
	 
curl -X POST %BASE_URL% ^
     -H "Content-Type: application/json" ^
     -d "{\"firstName\":\"Spike\",\"lastName\":\"Strike\"}"


curl -X POST %BASE_URL% ^
     -H "Content-Type: application/json" ^
     -d "{\"firstName\":\"Brenda\",\"lastName\":\"Looney\"}"

echo All requests sent.