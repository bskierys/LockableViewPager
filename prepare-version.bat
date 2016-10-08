@echo off

start "" "%ProgramFiles%\Git\git-bash.exe" -k "update-version.sh"
generate-changelog.bat