// API do Whatsapp
// const sulla = require('sulla');
const sulla = require('venom-bot');
const fs = require('fs');

// Fazer Download de Arquivos
const url = require('url');
const exec = require('child_process').exec;
// Tambem usada na Rota de Reload
const spawn = require('child_process').spawn;

// Fazer a Request
const axios = require('axios');
// URL do Webhook
const URL_Webhook = 'http://localhost:5000/api/webhook'

// Subir a API
var express = require('express');
var app = express();
const dir_img = __dirname + '/img';
// Verificando se o diretorio existe e criando se necessario
if (!fs.existsSync(dir_img)) {
  fs.mkdirSync(dir_img);
}
// Linkando o diretorio no express
app.use('/img', express.static(dir_img));

// Usar JSON
const bodyParser = require('body-parser');
app.use(bodyParser.json());

// Pegar o Path
const path = require('path');

// Second create() parameter is the QR callback
sulla.create('session-bot', (base64Qr, asciiQR) => {
  // To log the QR in the terminal
  console.log(asciiQR);

  // To write it somewhere else in a file
  exportQR(base64Qr, path.join(__dirname, './img/qr-code.png'));

}).then((client) => start(client));

// Salva o QR code no lugar espeficicado
function exportQR(qrCode, path) {
  qrCode = qrCode.replace('data:image/png;base64,', '');
  const imageBuffer = Buffer.from(qrCode, 'base64');

  // Creates 'marketing-qr.png' file
  fs.writeFileSync(path, imageBuffer);
}

// Subindo a aplicacao com o express
app.listen(3000, function () {
  console.log('App listening on port 3000!');
});

// FUNCOES

function get_phone_number(phone) {
  // Removendo o + caso exista
  // phone = phone.replace('+', '');

  // // Caso exista um 9 a mais no digito
  // var phone_base = /^....9........$/;

  // // Buscando a correspondencia da expressao regular com a string
  // var result = phone.match(phone_base);

  // // Caso haja correspondencia remove o 9
  // if (result){
  //   phone = phone.slice(0,4) + phone.slice(5);
  // };

  return phone;
}


// ROTAS DA API

// Rota da API que mostra o QR Code a ser scanneado
app.get('/generate_qrcode', function (req, res) {
  res.sendFile(path.join(__dirname, './template/qr-code.html'));
});


// Rota da API que reinicia toda a aplicacao
app.get('/reload', function (req, res) {
  // var spawn = require('child_process').spawn;
  res.send('Aplicação reiniciada !!!')
  var child = spawn(
    'bash'
    , ['restart.sh']
    , { detached: true, stdio: 'inherit' }
  );

  child.unref();
});

// Definindo os types que devem ser enviados por POST
const msg_types = ['chat', 'ptt', 'image'];

// Funcao que inicia o bot
function start(client) {
  client.onMessage(async (message) => {

    // Verificando se a mensagem e do tipo que deve ser enviada para o bot
    if (msg_types.includes(message.type) == true) {

      // Caso seja uma mensagem de texto
      if (message.type === 'chat') {
        var data = [{
          Type: 'receveid_message',
          Body: {
            Info: {
              RemoteJid: (message.from).split('@')[0] + '@s.whatsapp.net',
              SenderJid: ''
            },
            Text: message.body
          }
        }];

        // Caso seja uma mensagem de audio
      } else if (message.type === 'ptt') {
        var data = [{
          Type: 'receveid_audio_message',
          Body: {
            Info: {
              RemoteJid: (message.from).split('@')[0] + '@s.whatsapp.net',
              SenderJid: ''
            }
          },
          Url: '.ogg'
        }];

        // Caso seja outro tipo de mensagem
      } else {

        const buffer = await client.downloadFile(message);
        const imageBuffer = buffer.toString('base64')

        var data = [{
          Type: 'receveid_image_message',
          Body: {
            Info: {
              RemoteJid: (message.from).split('@')[0] + '@s.whatsapp.net',
              SenderJid: (message.from).split('@')[0] + '@s.whatsapp.net',
            }
          },
          image: imageBuffer
        }];

      }

      // Cada mensagem que chega e enviada por post para um webhook
      axios.post(URL_Webhook, data)
        .then((res) => {
          console.log(`Status: ${res.status}`);
          console.log('Body: ', res.message);
        }).catch((err) => {
          console.error(err);
        });

      // Envia que a mensagem foi lida
      client.sendSeen(message.chatId);

    };

  });

  // Rota da API que retorna a lista de contatos
  app.get('/contacts', async (req, res) => {
    let cont = await client.getAllContacts();
    let data = { contacts: cont };

    // Resposta da request
    res.send(data);
  });

  // Rota da API que retorna a lista de contatos
  app.get('/status', async (req, res) => {
    let status = await client.getHostDevice();

    // Resposta da request
    res.send(status);
  });

  // Rota da API que retorna os dados de um contato
  app.post('/get_profile', async (req, res) => {
    // Pegando os dados do post
    let number = get_phone_number(req.body.number);

    // Adicionando conteudo para enviar a mensagem
    number = number.includes('@c.us') ? number : `${number}@c.us`;
    let user = await client.getNumberProfile(number);

    // Resposta da request
    res.send(user);
  });

  // Rota da API de envio de localizacao
  app.post('/send_location', async (req, res) => {
    // Pegando os dados do post
    let address = req.body.address;
    let lat = req.body.lat;
    let lng = req.body.lng;
    let name = req.body.name;
    let number = get_phone_number(req.body.number);

    let data = {
      message: 'Mensagem Enviada',
      messageInfo: {
        RemoteJid: number + '@s.whatsapp.net',
        SenderJid: ''
      },
      status: true
    };

    // Resposta da request
    res.send(data);

    // Adicionando conteudo para enviar a mensagem
    // number = number.includes('@c.us') ? number : `${number}@c.us`;
    // client.sendLocation(number, lat, lng, name, address);

  });

  // Rota da API de envio de mensagens
  app.post('/send_message', async (req, res) => {
    // Pegando os dados do post
    let number = get_phone_number(req.body.number);
    let message = req.body.message;

    let data = {
      message: 'Mensagem Enviada',
      messageInfo: {
        RemoteJid: number + '@s.whatsapp.net',
        SenderJid: ''
      },
      status: true
    };

    // Resposta da request
    res.send(data);

    // Adicionando conteudo para enviar a mensagem
    number = number.includes('@c.us') ? number : `${number}@c.us`;
    client.sendText(number, message);

  });

  // Rota da API de envio de mensagens de arquivo usando uma url
  app.post('/send_message_file_from_url', async (req, res) => {
    // Pegando os dados do post
    let number = get_phone_number(req.body.number);
    let message = req.body.message;
    let file_url = req.body.url;

    let data = {
      message: 'Mensagem Enviada',
      messageInfo: {
        RemoteJid: number + '@s.whatsapp.net',
        SenderJid: '',
        URL: file_url
      },
      status: true
    };

    // Resposta da request
    res.send(data);

    // Local temporario para armazenar arquivos
    var TMP_DIR = __dirname + '/tmp/';
    // Criando o diretorio de downloads
    var mkdir = 'mkdir -p ' + TMP_DIR;
    var child = exec(mkdir, function (err, stdout, stderr) {
      if (err) throw err;
      else download_file_wget(file_url);
    });

    // Funcao que faz o download de arquivos via wget
    var download_file_wget = function (file_url) {
      // Pegando o file_name
      var file_name = decodeURIComponent(url.parse(file_url).pathname.split('/').pop());
      // Comando de wget
      var wget = 'wget -P ' + TMP_DIR + ' ' + file_url;

      // Executando o wget
      var child = exec(wget, function (err, stdout, stderr) {
        if (err) throw err;
        else send_from_bot(TMP_DIR + file_name, file_name);
      });
    };

    // Funcao que envia o arquivo baixado pelo bot
    var send_from_bot = function (path, file_name) {

      // Adicionando conteudo para enviar a mensagem
      number = number.includes('@c.us') ? number : `${number}@c.us`;

      // Enviando a mensagem com o arquivo
      var send_file = client.sendFile(number, path, file_name, message);

      // Removendo o arquivo temporario
      var rmfile = 'rm -r "' + path + '"';
      var child_rm = exec(rmfile, function (err, stdout, stderr) {
        if (err) throw err;
      });
    };

  });


}
