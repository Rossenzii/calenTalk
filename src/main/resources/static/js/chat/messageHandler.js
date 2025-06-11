document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("sendButton").addEventListener("click", sendMessage);
});

function sendMessage() {
  const input = document.getElementById('chatInput');
  const message = input.value.trim();
  if (message === '') return;
  const sender = localStorage.getItem("senderName");
  const roomId = localStorage.getItem("currentRoomId");

  // 서버로 전송
  sendMessageToServer(roomId, sender, message);

  const msgDiv = document.createElement('div');
  msgDiv.textContent = message;
  msgDiv.classList.add('chat-message');

  document.getElementById('chatMessages').appendChild(msgDiv);
  input.value = '';
  document.getElementById('chatMessages').scrollTop = document.getElementById('chatMessages').scrollHeight;
}
