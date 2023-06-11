# Build dependencies
FROM node:18.14.2 as dependencies
WORKDIR /app
ENV PORT 8080
ENV HOST 0.0.0.0
COPY package.json .

# COPY ENV variable

# generated prisma files
COPY prisma ./prisma/
# COPY .env.development ./
COPY .env.production ./
COPY . .
RUN npm install

# Set NODE_ENV environment variable
ENV DATABASE_URL="mysql://root:7Lk#JL;bRqtcs{V}3@35.238.125.132:8080/sata-tiketdb?unix_socket=/cloudsql/ecstatic-armor-387013:us-central1:sata-tiketdb"
ENV NODE_ENV production

# Build production image
# FROM dependencies as builder
# RUN npm run build
EXPOSE 8080

# start command
CMD ["npm", "run", "start:prod"]