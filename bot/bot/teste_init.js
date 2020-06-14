const sulla = require('sulla');

sulla.create().then((client) => start(client));
console.log("Starting...");
function start(client) {
    client.onMessage((message) => {
        if (message.body === 'Hi') {
            client.sendText(message.from, '👋 Hello from sulla!');
        }
    });
    
    process.on('exit', function(code) {
        client.close();
        return console.log('Client Closed');
    });

}
