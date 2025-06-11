document.addEventListener("DOMContentLoaded", () => {
  const accessToken = localStorage.getItem("accessToken");
  if (!accessToken) {
    console.error(" accessToken 없음! 로그인 먼저 하세요");
    alert("로그인이 필요합니다.");
    return;
  }
  console.log("accessToken:", accessToken);

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
        const li = document.createElement("li");
        li.className = "user-item";
        li.textContent = user.name;

        li.addEventListener("click", () => {
          chatHeader.textContent = `${user.name}와의 채팅`;
          chatMessages.innerHTML = ''; // 이전 채팅 초기화
        });

        userList.appendChild(li);
      });
    })
    .catch(err => {
      console.error("사용자 불러오기 실패:", err);
    });
});
