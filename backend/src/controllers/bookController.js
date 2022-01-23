const db = require('../config/database');

module.exports = {
    async addBook(
        bookDetails
    ){
        try {
            const{title,description,
                author,edition,title_image,price,
                publisher,seller_id} = bookDetails;
        
            const columns = 'title,description,author,edition,title_image,price,publisher,seller_id';
            const values = `'${title}','${description}','${author}','${edition}','${title_image}',${price},'${publisher}',${seller_id}`;
            const result = await db(`insert into book(${columns}) values (${values})`);
            console.log("book insert successfully with id : "+result.insertId);     
        } catch (err) {
            console.log(err)
            throw err;
        }
        
    },
    async getBookById(book_id){
        try {
            const result =await db(`select * from book where _id = ${book_id}`);
            console.log(result);
            return result[0];
        } catch (err) {
            console(err)
            throw err;
        }
    },
    async getAllBooks(){
        try {
            const result = await db(`select * from book`);
            return result;
        } catch (err) {
            throw err;
        }
    }
}
