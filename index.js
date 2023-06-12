import express from "express";
import cors from "cors";
import ProductRoute from "./routes/ProductRoutes.js";
import dotenv from 'dotenv';


const app = express();
const port = process.env.NODE_ENV || 'development';

if (port === 'production') {
    dotenv.config({ path: `.env.${port}` });
} else {
    dotenv.config({ path: '.env' });
}

app.use(cors());
app.use(express.json());
app.use(ProductRoute);

app.listen(port, () => {
    console.log(`Listening: http://localhost:${port}`);
});