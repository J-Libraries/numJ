#!/bin/bash

# Step 1: Generate Javadocs
mvn javadoc:javadoc

# Step 2: Checkout or Create gh-pages branch
git checkout gh-pages || git checkout --orphan gh-pages

# Step 3: Remove old files
rm -rf *

# Step 4: Copy the new Javadocs to the gh-pages branch
cp -r target/site/apidocs/* .

# Step 5: Commit and Push to gh-pages
git add .
git commit -m "Deploy updated Javadocs"
git push origin gh-pages

echo "Javadocs deployed successfully!"
chmod +x deploy_javadoc.sh
./deploy_javadoc.sh
