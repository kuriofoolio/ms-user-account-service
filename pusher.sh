
#!/bin/bash

# Navigate to the directory containing your local Git repository
cd "$(dirname "$0")"
branch='main'

# Add all changes to the Git staging are
git add .

#check status of files to be committed
git status

# Commit the changes with a descriptive message
git commit -m "added swagger documentation for update and delete logic"

# Push the changes to your GitHub repository
git push origin $branch
