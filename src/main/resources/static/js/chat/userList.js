document.addEventListener("DOMContentLoaded", () => {
  const accessToken = localStorage.getItem("accessToken");
  const currentUserId = parseInt(localStorage.getItem("userId"), 10);


  if (!accessToken || !currentUserId) {
    console.error("accessToken 또는 userId 없음 로그인 먼저 하세요");
    alert("로그인이 필요합니다.");
    return;
  }

  fetch("http://localhost:8081/api/v1/userlist", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${accessToken}`
    },
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) throw new Error("사용자 목록 요청 실패");
      return res.json();
    })
    .then(users => {
      const userList = document.getElementById("userList");
      const chatHeader = document.getElementById("chatHeader");
      const chatMessages = document.getElementById("chatMessages");

      users.forEach(user => {
        if (user.id == currentUserId) return; // 본인 제외

        const li = document.createElement("li");
        li.className = "user-item";
        li.textContent = user.name;

        li.addEventListener("click", () => {
          chatHeader.textContent = `${user.name}와의 채팅`;
          chatMessages.innerHTML = '';

          // 채팅방 생성 요청
          createChatRoom(currentUserId, user.id, user.name);
        });

        userList.appendChild(li);
      });
    })
    .catch(err => {
      console.error("사용자 불러오기 실패:", err);
    });
});

function createChatRoom(fromUserId, toUserId, toUserName) {
  console.log("fromUserId:", fromUserId, "typeof:", typeof fromUserId);
  console.log("toUserId:", toUserId, "typeof:", typeof toUserId);

  const accessToken = localStorage.getItem("accessToken");

  fetch("http://localhost:8081/api/v1/chatroom", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${accessToken}`
    },
    credentials: "include",
    body: JSON.stringify({
      fromUserId: fromUserId,
      toUserId: toUserId
    })
  })
    .then(res => {
      if (!res.ok) throw new Error("채팅방 생성 실패");
      return res.json();
    })
    .then(data => {
      console.log(" 채팅방 생성됨:", data);
      localStorage.setItem("currentRoomId", data.roomId);
      localStorage.setItem("senderName", data.fromUser);
      connectWebSocket(data.roomId);
      // 여기서 WebSocket 연결하거나 메시지 로드 가능
    })
    .catch(err => {
      console.error("채팅방 생성 실패:", err);
    });
}
