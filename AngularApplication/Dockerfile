# Get base image
FROM node:13.10.1 as build

# Create a directory where our app will be placed
#RUN mkdir -p /app

# Change directory so that our commands run inside this new directory
WORKDIR /app

# Copy dependency definitions
COPY package*.json /app/

# Install dependecies
RUN npm install
#RUN npm install -g @angular/cli

# Copy all code needed for running the app
COPY ./ /app/

# deployment using ng serve (if needed)
# Expose the port the app runs in
# EXPOSE 4200
# Serve the app (using 0.0.0.0 here - default would be localhost)
# use 'ng serve' during development only (no production ready webserver)
# CMD ng serve --host 0.0.0.0

# production deployment
# generate build, profile as defined in Angular evironment
RUN npm run build -- --output-path=./dist/out --configuration=production

# base image
FROM nginx:1.17.7-alpine

# copy artifact build from the 'build environment'
COPY --from=build /app/dist/out/ /usr/share/nginx/html
# copy nginx custom configuration
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# expose port 80
EXPOSE 80
# expose port 443 (SSL)
EXPOSE 443

# run nginx
CMD ["nginx", "-g", "daemon off;"]
