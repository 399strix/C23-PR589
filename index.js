import express from "express";
import helmet from "helmet";
import morgan from "morgan";
import cors from "cors";
import ProductRoute from "./routes/ProductRoutes.js";
import { config } from 'dotenv';
config();

const app = express();
const env = process.env.NODE_ENV || 'development';
const port = 8080;

if (env === 'production') {
    dotenv.config({ path: `.env.${env}` });
} else {
    dotenv.config({ path: '.env' });
}

app.use(cors());
app.use(morgan('dev'));
app.use(helmet());
app.use(express.json());
app.use(ProductRoute);

app.listen(port, () => {
    console.log(`Listening: ${port}`);
});