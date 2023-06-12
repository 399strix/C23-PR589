import express from "express";
import cors from "cors";
import ProductRoute from "./routes/ProductRoutes.js";
import {config} from 'dotenv';
config();

const app = express();
const port = process.env.PORT || 8080;



app.use(cors());
app.use(express.json());
app.use(ProductRoute);

app.listen(port, () => {
    console.log(`Listening: http://localhost:${port}`);
});