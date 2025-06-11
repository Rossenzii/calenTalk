let stompClient = null;

function connectWebSocket(roomId) {
  const socket = new SockJS("/stomp");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, function () {
    console.log("WebSocket 연결됨");

    // 메시지 구독
    stompClient.subscribe("/sub/chat/room" + roomId, function (message) {
      const chat = JSON.parse(message.body);
      renderMessage(chat);
    });
  });
}

function sendMessageToServer(roomId, sender, content) {
  const message = {
    roomId: roomId,
    sender: sender,
    message: content
  };
  stompClient.send("/pub/chat/message", {}, JSON.stringify(message));
}

function renderMessage(chat) {
  const msgDiv = document.createElement('div');
  msgDiv.textContent = `[${chat.sender}] ${chat.message}`;
  document.getElementById("chatMessages").appendChild(msgDiv);
}

