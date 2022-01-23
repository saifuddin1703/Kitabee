const express = require('express');
const multer = require('multer');
const router = express.Router();

const storage = multer.diskStorage({
    destination : (req,file,cb) =>{
        cb(null,'src/uploads/images');
    },
    filename : (req,file,cb) =>{
        cb(null,file.originalname.replace(" ","_"));
    }
})

const imageFileFilter = (req, file, cb) => {
    if(!file.originalname.match(/\.(jpg|jpeg|png|gif)$/)) {
        return cb(new Error('You can upload only image files!'), false);
    }
    cb(null, true);
};

const uploader = multer({storage : storage, fileFilter:imageFileFilter});

router.post("/uploadImage",uploader.single("imageFile"),(req,res,next)=>{
    const resultedImageUrl = `http://${req.hostname}:${process.env.port}/images/${req.file.filename}`;
    res.status(200).send(resultedImageUrl);
})
module.exports = router;