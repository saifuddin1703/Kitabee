const express = require('express');
const { authenticate } = require('../controllers/authController');
const bookController = require('../controllers/bookController');
const router = express.Router();


router.get("/",async function(req,res,next){
    try {
        const books = await bookController.getAllBooks();
        res.status(200).send(books);
    } catch (err) {
        res.status(501);
    }
});

router.get("/getBook",authenticate,async function(req,res,next){
    // get book by id
    const book_id = req.query.book_id;
    console.log(book_id);
    try {
        const book = await bookController.getBookById(book_id);
        res.status(200).send(book);
    } catch (err) {
        res.status(501);
    }
});

router.get("/searchBook",async function(req,res,next){
    // search book using the given query
});

router.post("/postBook",authenticate,async function(req,res,next){
    // upload book
    console.log(req.user)
    const posted_by = req.user;
    const bookDetails = req.body;
    bookDetails.seller_id = posted_by;
    try {
      const book_id = await bookController.addBook(bookDetails);
       res.status(200).send("book posted successfull with id : "+book_id);
    } catch (err) {
        res.status(501).send("error is posting book");
    }

});

router.put("/buyBook",authenticate,async function(req,res,next){
    
});

router.delete("/deleteBook",async function(req,res,next){
    // delete book
});
module.exports = router;