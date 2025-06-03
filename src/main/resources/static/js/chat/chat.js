document.addEventListener("DOMContentLoaded", () => {
  const userItems = document.querySelectorAll('.user-item');
  const chatHeader = document.getElementById('chatHeader');
  const chatMessages = document.getElementById('chatMessages');

  userItems.forEach(item => {
    item.addEventListener('click', () => {
      const userName = item.textContent;
      chatHeader.textContent = `${userName}와의 채팅`;
      chatMessages.innerHTML = '';
    });
  });

  document.getElementById("sendButton").addEventListener("click", sendMessage);
});

function sendMessage() {
  const input = document.getElementById('chatInput');
  const message = input.value.trim();
  if (message === '') return;

  const msgDiv = document.createElement('div');
  msgDiv.textContent = message;
  msgDiv.style.marginBottom = '10px';
  msgDiv.style.padding = '10px';
  msgDiv.style.backgroundColor = '#dcedc8';
  msgDiv.style.borderRadius = '8px';

  document.getElementById('chatMessages').appendChild(msgDiv);
  input.value = '';
  document.getElementById('chatMessages').scrollTop = chatMessages.scrollHeight;
}
