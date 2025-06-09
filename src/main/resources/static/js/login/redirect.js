window.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search);
  const code = urlParams.get("code");

  if (code) {
    fetch("http://localhost:8081/api/v1/oauth/google", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ code: code.trim() }),
      credentials: "include"
    })
      .then(res => res.json())
      .then(data => {
        console.log("백엔드 응답:", data);
        alert("로그인 성공!");
        localStorage.setItem("accessToken", data.accessToken);
        window.location.href = "/html/chat/chat.html";
      })
      .catch(err => {
        console.error("로그인 실패:", err);
        alert("로그인 실패");
      });
  } else {
    alert("구글 인가 코드 없음");
  }
});
