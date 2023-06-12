import express from "express";
import cors from "cors";
import ProductRoute from "./routes/ProductRoutes.js";
import dotenv from 'dotenv';


const app = express();
const env = process.env.NODE_ENV || 'development';

if (env === 'production') {
    dotenv.config({ path: `.env.${env}` });
} else {
    dotenv.config({ path: '.env' });
}

app.use(cors());
app.use(express.json());
app.use(ProductRoute);

app.listen(env, () => {
    console.log(`Listening: http://localhost:${env}`);
});