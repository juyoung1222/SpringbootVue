<template>
  <div class="login">
    <div class="change">
      <button @click="$router.push('/login')" class="loginbtn">
        로그인
      </button>
      <button @click="$router.push('/regist')" class="registbtn">
        회원가입
      </button>
    </div>
    <div class="container">
      <div class="logo-container">
        <img src="@/assets/imgs/Logo2.png" width="180"><br/>
        <p>강의는 역시 B:EDU</p>
      </div>
      <div class="button-container">
        <form class="login-form">
          <div class="form-group">
            <button class="kakao-login-btn">카카오 계정으로 로그인</button>
          </div>
          <div class="form-group">
            <button class="email-login-btn">이메일로 로그인</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 80vh;
  background: #EDF0F5;
  text-align: center;
}

.change {
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.loginbtn {
  width: 300px;
  padding: 1rem;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  cursor: pointer;
  color: #FFFFFF;
  background: #303076;
  border-bottom: 3px solid #303076;
  animation: fadein 3s;
}

.registbtn {
  width: 300px;
  padding: 1rem;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  cursor: pointer;
  color: black;
  background: white;
  border-bottom: 3px solid #303076;
}

.container {
  position: relative;
  background: white;
  width: 600px;
  height: 360px;
}

.logo-container {
  position: relative;
  font-size: 1.5rem;
  padding: 0.5rem;
  border-radius: 1rem;
  background: white;
  margin-top: 50px;
}

.kakao-login-btn,
.email-login-btn {
  width: 300px;
  padding: 0.8rem;
  border-radius: 5px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  color: #FFFFFF;
  margin-top: 1rem;
}

.kakao-login-btn {
  background-color: #FEE500;
}

.email-login-btn {
  background-color: #FFFFFF;
  color: #4A4A4A;
}
</style>