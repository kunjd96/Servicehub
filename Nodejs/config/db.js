const mongoose = require("mongoose");
const keys = require ('./keys');

const connectDB = async () => {
    const conn = await mongoose.connect(keys.MONGO_URI,{
            useNewUserParser : true,
            useCreateIndex : true,
            useFindAnyModify: false,
            useUnifiedTopology : true,
            useNewUrlParser: true
    });
    console.log("MongoDB Connected",conn.connection.host);
};

module.exports = connectDB;