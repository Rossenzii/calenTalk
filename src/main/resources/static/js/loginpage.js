document.addEventListener("DOMContentLoaded", () => {
  const button = document.getElementById("google-login-btn");
  const GOOGLE_CLIENT_ID = "179393727431-qppqju1suj1l62dvprccfs18mhnds1sk.apps.googleusercontent.com";
  const REDIRECT_URI = "http://localhost:8081/html/redirect.html";
  const SCOPE = "email profile";

  button.addEventListener("click", () => {
    const url =
      `https://accounts.google.com/o/oauth2/v2/auth` +
      `?client_id=${GOOGLE_CLIENT_ID}` +
      `&redirect_uri=${REDIRECT_URI}` +
      `&response_type=code` +
      `&scope=${SCOPE}` +
      `&access_type=offline&prompt=consent`;
    window.location.href = url;
  });
});
