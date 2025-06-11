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
  const currentUsername = localStorage.getItem("username");
  const msgDiv = document.createElement('div');
  msgDiv.textContent = chat.message;

  // 정렬용 wrapper
  const wrapper = document.createElement('div');
  wrapper.classList.add('message-wrapper');

  if (chat.sender === currentUsername) {
    msgDiv.classList.add("my-message");
    wrapper.style.justifyContent = 'flex-end';
  } else {
    msgDiv.classList.add("other-message");
    wrapper.style.justifyContent = 'flex-start';
  }

  wrapper.appendChild(msgDiv);
  document.getElementById("chatMessages").appendChild(wrapper);
}

