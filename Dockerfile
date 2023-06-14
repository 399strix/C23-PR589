# Build dependencies
FROM node:18.14.2 

ENV PORT 8080
ENV DATABASE_URL="mysql://root:satadb123@34.123.66.68/satadb?unix_socket=/cloudsql/ecstatic-armor-387013:us-central1:sql-satadb"
ENV NODE_ENV production

WORKDIR /app
COPY package.json .
RUN npm install

COPY . .

# Build production image
# FROM dependencies as builder
# RUN npm run build
EXPOSE 8080

# start command
CMD ["npm", "run", "start:prod"]