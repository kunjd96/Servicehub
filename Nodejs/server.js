const express = require("express");
const dotenv = require("dotenv");
const morgan = require("morgan");
const connectDB = require("./config/db");
const mongoSanitize = require('express-mongo-sanitize');
const helmet = require("helmet");
const xss = require('xss-clean');
const rateLimit = require("express-rate-limit");
const hpp = require('hpp');
const path = require('path');
const cors = require('cors')
const errorHandler = require('./middleware/error');

//Load env vars
dotenv.config({ path: "./config/config.env" });

//Connect to Database
connectDB();

const limiter = rateLimit({
    windowMs: 10 * 60 * 1000, // 15 minutes
    max: 100 // limit each IP to 100 requests per windowMs
});

// Route files 

const user = require("./routes/user");
const services = require("./routes/services");
const availibility = require("./routes/availibility");

const app = express();
app.use(express.static(path.join(__dirname, 'public')));

//Body Parser
app.use(express.json());
app.use(mongoSanitize());
app.use(helmet());
app.use(xss());
app.use(limiter);
app.use(hpp());
app.use(cors());


// Dev logging middleware
if (process.env.NODE_ENV === "development") {
    app.use(morgan("dev"));
}

//Mount Routes
app.use("/api/v1/auth", user);
app.use("/api/v1/services", services);
app.use("/api/v1", availibility);

app.use(errorHandler);

const PORT = process.env.PORT || 0825

app.listen(PORT, console.log("Server is running on ", PORT));