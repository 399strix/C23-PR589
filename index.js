import express from "express";

const app = express();
import cors from "cors";

const port = process.env.PORT || 5000;

app.get("/api",(req, res)=>{
    res.json({
        success:1,
        message:"This is Nice One!"
    });
});

app.use(cors());
app.use(express.json());

app.listen(port, () => {
    console.log(`Listening: http://localhost:${port}`);
});