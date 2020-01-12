# Get base image
FROM node:13.5.0

# Create a directory where our app will be placed
RUN mkdir -p /app

# Change directory so that our commands run inside this new directory
WORKDIR /app

# Copy dependency definitions
COPY package*.json /app/

# Install dependecies
RUN npm install
RUN npm install -g @angular/cli

# Copy all code needed for running the app
COPY . /app/

# Expose the port the app runs in
EXPOSE 4200

# Serve the app (using 0.0.0.0 here - default would be localhost)
CMD ng serve --host 0.0.0.0
