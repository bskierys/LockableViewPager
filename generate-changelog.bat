set /p auth_token=<github_auth.priv

github_changelog_generator -u bskierys -p LockableViewPager --token %auth_token% --include-labels bug,feature,ci --output 'CHANGELOG.md'