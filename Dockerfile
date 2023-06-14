# Build dependencies
FROM node:18.14.2 as dependencies
WORKDIR /app

COPY package.json .

# COPY ENV variable

# generated prisma files
COPY prisma ./prisma/
COPY package.json ./
# COPY .env.development ./
COPY .env.production ./

RUN npm install

# Set NODE_ENV environment variable
ENV DATABASE_URL="mysql://root:satadb123@34.123.66.68/satadb?unix_socket=/cloudsql/ecstatic-armor-387013:us-central1:sql-satadb"

ENV PORT 8000
ENV HOST 0.0.0.0

# Build production image
# FROM dependencies as builder
# RUN npm run build
EXPOSE 8000
COPY . .
# start command
CMD ["node", "index.js"]