document.addEventListener("DOMContentLoaded", () => {
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
  document.getElementById('chatMessages').scrollTop = document.getElementById('chatMessages').scrollHeight;
}
