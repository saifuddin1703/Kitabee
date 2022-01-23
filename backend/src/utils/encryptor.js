const crypto = require('crypto');
// const dotenv = require("dotenv");
// dotenv.config( {path: 'src/.env'} );
const algorithm = 'aes-256-ctr';
const secretKey = process.env.encryptionKey;
const iv = crypto.randomBytes(16);

const encrypt = (object) => {

    // console.log(secretKey)
    const cipher = crypto.createCipheriv(algorithm, secretKey, iv);

    const encrypted = Buffer.concat([cipher.update(object), cipher.final()]);

    // const encryptedMessage = 
    return {
        iv: iv.toString('hex'),
        content: encrypted.toString('hex')
    };
};

const decrypt = (hash) => {

    const decipher = crypto.createDecipheriv(algorithm, secretKey, Buffer.from(hash.iv, 'hex'));

    const decrpyted = Buffer.concat([decipher.update(Buffer.from(hash.content, 'hex')), decipher.final()]);

    return decrpyted.toString();
};

module.exports = {
    encrypt,
    decrypt
};