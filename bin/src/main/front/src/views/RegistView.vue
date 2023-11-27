<template>
  <div class="regist">
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
        <p>회원가입</p>
      </div>
      Test
    </div>
  </div>
</template>

<style scoped>
.regist {
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
  color: black;
  background: white;
  border-bottom: 3px solid #303076;
}

.registbtn {
  width: 300px;
  padding: 1rem;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  cursor: pointer;
  color: #FFFFFF;
  background: #303076;
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
</style>