:: Hide Commands
@echo off

SETLOCAL

set /p auth_token=<github_auth.priv
set changelog_file=CHANGELOG.md
set release_notes_file=release-notes.txt
set temp_file=TEMP.md

:: generate changelog
call github_changelog_generator -u bskierys -p LockableViewPager --token %auth_token% --include-labels bug,feature,ci --output %changelog_file% --no-verbose
:: generate release notes
call github_changelog_generator -u bskierys -p LockableViewPager --token %auth_token% --include-labels fixed --exclude-labels ci,task,invalid,testing --output %temp_file% --simple-list --no-verbose
ruby edit_release_notes.rb -i %temp_file% -o %release_notes_file%
del %temp_file%

ENDLOCAL