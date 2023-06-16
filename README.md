# C23-PR589
Bangkit Capstone BE_API

The project builds RESTful APIs using node.js, Express and Prisma

## Manual Installation

Clone the repo:

```bash
git clone -b BE_Sata https://github.com/C23-PR589/C23-PR589.git
```

Install the dependencies:

```bash
npm install
```

Set the environment variables:

```bash
cp .env.example .env
# open .env and modify the environment variables
```

## Table of Contents

- [Commands](#commands)
- [Environment Variables](#environment-variables)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)

## Commands

Running in development:

```bash
nodemon index  #on local
# or
npm run dev
#or
npm start
```

Using mysql database :
```bash
#in schema.prisma file provider set "mysql"
#set "DATABASE_URL"
```

Update databaase :
```bash
npm install prisma --save-dev
make update in 'schema.prisma'
npx prisma migrate dev
```

to Use database :
```bash
import {PrismaClient} from "@prisma/client";

const prisma = new PrismaClient();
```


## Environment Variables

The environment variables can be found and modified in the `.env.production` file.

```bash
DATABASE_URL="mysql://root:satadb123@34.123.66.68/satadb?unix_socket=/cloudsql/ecstatic-armor-387013:us-central1:sql-satadb"
#satadb = nama database, 34.123.66.68 = public IP db, ecstatic-armor-387013:us-central1:sql-satadb = connection name

PORT=8000
```

## Project Structure

```
  └───C23-PR589
│       ├───controllers/                                    
│       │   └───ProductController.js                              #Controllers        
│       ├───node_modules/                                         #jsmodule
│       ├───prisma
│       │   └───migrations                                        #Database migration
│       │       ├───20230530181150_making_product_wisata
│       │       ├───20230603165149_buat_wisata
│       │       ├───20230604134849_beberapa_migration
│       │       ├───20230604145429_nambahin_label
│       │       └───20230612155245_last_yok
│       ├───routes/
│       │   └───ProductRoutes.js                                  #Routes
│       ├───.env.production                                       #Environment variables
│       ├───cloudbuild.yaml                                       #Building Steps
│       ├───Dockerfile                                            #Container config
│       ├───index.js                                              #App entry point
│       ├───package.json                                          #Module package
```

### API Endpoints

List of available routes:

**Product Routes**:\
`GET /products` - getAllProducts\
`GET /products/:label` -  getPriceByLabel\
`GET /products/:label/location` -  getLocationByLabel\
`GET /products/byId/:id` -  getProductById\
`POST /products` - createProducts\
`PATCH /products/:id` - updateProducts\
`DELETE /products/:id` - deleteProduct\
