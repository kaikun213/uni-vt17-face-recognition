var nodemailer = require('nodemailer');
var smtpTransport = require('nodemailer-smtp-transport');

var transporter = nodemailer.createTransport(smtpTransport({
    service: 'Gmail',
    auth: { user: 'kontakt.norr1945@gmail.com',
          pass: 'bajssnurra123'
    },
    tls: {
      rejectUnauthorized: false
    }
})); // set the credentials

// https://www.google.com/settings/security/lesssecureapps **less secure**

function send(data, callback){
  transporter.sendMail(data, callback);
}
exports.send = send;
