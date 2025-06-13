let stompClient = null;
let subscription = null;
let isConnected = false;

function connectWebSocket(roomId) {
  if (!stompClient || !isConnected) {
    const socket = new SockJS("/stomp");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
      console.log("WebSocket 연결됨");
      isConnected = true;

      subscribeToRoom(roomId);
    });
  } else {
    // 이미 연결되어 있으면 구독만 바꿔줌
    subscribeToRoom(roomId);
  }
}

function subscribeToRoom(roomId) {
  if (subscription) {
    subscription.unsubscribe();
  }

  subscription = stompClient.subscribe("/sub/chat/room" + roomId, function (message) {
    const chat = JSON.parse(message.body);
    renderMessage(chat);
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
  console.log("현재 사용자:", localStorage.getItem("senderName"));
  const currentUsername = localStorage.getItem("senderName");
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
  document.getElementById('chatMessages').scrollTop = document.getElementById('chatMessages').scrollHeight;
}

