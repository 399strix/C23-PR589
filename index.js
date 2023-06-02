import express from "express";
import dotenv from "dotenv";
import cors from "cors";
import ProductRoute from "./routes/ProductRoutes.js";

const app = express();
const port = process.env.PORT || 5000;

app.get("/api",(req, res)=>{
    res.json({
        success:1,
        message:"This is Nice One!"
    });
});

app.use(cors());
app.use(express.json());
app.use(ProductRoute);

app.listen(port, () => {
    console.log(`Listening: http://localhost:${port}`);
});