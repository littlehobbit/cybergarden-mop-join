const env = require("./../index").env;
const nodemailer = require("nodemailer");

var smtpTransport = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: env.MAIL_USER,
        pass: env.MAIL_PASSWORD
    }
})

module.exports.mailer = smtpTransport;
module.exports.messageDataTemplate = {
    from:env.MAIL_USER
}

module.exports.plainResetMessage = (code) =>{
    return "Your reset code is\n" + code;
}

//TODO: Crete html version of message
module.exports.htmlResetMessage = (code) =>{

}