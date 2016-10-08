set /p auth_token=<github_auth.priv

github_changelog_generator -u bskierys -p LockableViewPager --token %auth_token% --include-labels fixed --exclude-labels ci,task,invalid,testing --output 'RELEASE_NOTES.md'