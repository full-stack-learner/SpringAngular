# The script does the following:
# For each folder inside the data-setup folder, it reads all folder names and creates a database with the name of the folder.
# For each json file, it creates a collection with the name of the file and persists all json objects contained in the file.
# This is a convenient way of "pre-filling" your database with data

cd /data-setup
pattern="./"
folderpattern="/"
initpattern="data-setup"
echo "Importing data"
for D in `find . -type d`
do
   folder=${D#$pattern}
   echo "Reading folder ${folder}"
   ls -1 ${folder}/*.json | sed 's/.json$//' | while read col; do
        filename=${col#$folder}
        echo "Read folder ${folder#initpattern} and file .${filename}.json"
	mongoimport --host mongo --username rootuser --password rootpassword --authenticationDatabase admin --db ${folder#initpattern} --collection ${filename#$folderpattern} --type json --file ${col}.json --jsonArray
   done
done
echo "Finished importing data"

# Creates a new user used by the Spring Boot Server in production mode (username and password must match those in Spring application-production.yml).
# 'rootuser' and 'rootpassword' as defined in docker-compose.yml, database name and users as defined in Spring MongoDB configuration
echo "Creating user"
mongo -host mongo -u rootuser -p rootpassword --authenticationDatabase admin --eval "db.createUser({user: 'productionuser', pwd: 'dsfaere56e4Uxo', roles: [{role: 'readWrite', db: 'database'}]});" database
echo "Finished creating user"
