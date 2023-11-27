<!-- 작성자 이준원 -->


<template>
    <div id="loginChangeContainer">
        <div id="loginChangeSection">
            <!-- 로그인 버튼 -->
            <button @click="$router.push('/login')" id="loLoginChangeBtn">
                로그인
            </button>
            <!-- 회원가입 버튼 -->
            <button @click="registMove" id="loRegistChangeBtn" class="registbtn">
                회원가입
            </button>
        </div>
        <div id="loginContainer">
            <div id="loginLogoContainer">
                <!-- 로고 이미지 -->
                <img id="loginLogo" src="@/assets/imgs/Logo2.png" /><br />
                <p>강의는 역시 B:EDU</p>
            </div>
            <div id="loginSection">
                <form class="login-form" @submit.prevent="fnLogin">
                    <div class="form-group" >
                        <!-- 이메일 입력 필드 -->
                        <input
                            type="email"
                            id="emailInputSection"
                            v-model="email"
                            placeholder="사용자 이메일"
                        />
                    </div>
                    <div class="form-group" id="passwordInputContatiner">
                        <!-- 비밀번호 입력 필드 -->
                        <input
                        id="pwdInputSection"
                        v-model="password"
                        type="password"
                        placeholder="비밀번호"
                        />
                    </div>
                    <!-- 아이디 저장 체크박스 -->
                    <div id="idRememberSection">
                        <input
                            class="form-check-input"
                            type="checkbox"
                            v-model="isEmailSaved"
                            @change="handleEmailSaving"
                        />
                        <span style="cursor: pointer; margin-left: 10px;" @click="toggleCheckbox">아이디 저장</span>
                    </div>
                    <div>
                        <!-- 로그인 버튼 -->
                        <button id="loginSubmitBtn" type="submit">로그인</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import '@/assets/css/userStyle.css';
import { mapActions, mapGetters } from 'vuex'

export default {
    data() {
        return {
            email: '',
            password: '',
            isEmailSaved: false,
        };
    },
    methods: {
        ...mapActions(['login']),
        // 체크박스 토글
        toggleCheckbox() {
            this.isEmailSaved = !this.isEmailSaved;
            this.handleEmailSaving();
        },
        handleEmailSaving() {
            if (this.isEmailSaved) {
                localStorage.setItem('savedEmail', this.email);
            } else {
                localStorage.removeItem('savedEmail');
                this.email = '';
            }
        },
        async fnLogin() {
            // 이메일 입력 유효성 검사
            if (this.email === '') {
                this.$swal('<span style="font-size: 23px;"><b style="color:red;">이메일</b>을 입력해주세요.</span>')
                return
            }

            // 이메일 유효성 검사
            const emailRegex = /^[a-z0-9_\\.\\-]+@[a-z0-9\\-]+\.[a-z0-9\\-]+/;
            if (!emailRegex.test(this.email)) {
                this.$swal('<span style="font-size: 23px;"><b style="color:red;">올바른 이메일</b> 형식이 아닙니다.</span>');
                return;
            }

            // 비밀번호 입력 유효성 검사
            if (this.password === '') {
                this.$swal('<span style="font-size: 23px;"><b style="color:red;">비밀번호</b>를 입력해주세요.</span>')
                return
            }
            try {
                // 로그인 액션 호출 (actions.js의 login을 실행)
                let loginResult = await this.login({ email: this.email, password: this.password })
                if (loginResult) {
                    if (this.isEmailSaved) {
                        localStorage.setItem('savedEmail', this.email);
                    }
                    this.goToPages()
                }
            } catch (err) {
                // 네트워크 에러인 경우
                if (err.message.indexOf('Network Error') > -1) {
                    this.$swal('서버에 접속할 수 없습니다. 상태를 확인해주세요.')
                } else {
                    this.$swal('로그인 정보를 <br>확인할 수 없습니다.')
                }
            }
        },
        // 회원가입 버튼 클릭시 RegistView.vue로 이동
        registMove() {
                // 스크롤을 페이지 상단으로 이동
                window.scrollTo({
                    top: 0,
                    behavior: 'smooth',
                });
                this.$router.push({
                    name: "regist",
                });
        },
        // 로그인 버튼 클릭시 LoginView.vue로 이동
        loginMove() {
            // 스크롤을 페이지 상단으로 이동
            window.scrollTo({
                top: 0,
                behavior: 'smooth',
            });
            this.$router.push({
                name: "login",
            });
        },
        goToPages() {
            // 메인 페이지로 이동
            this.$router.push({
                name: 'main'
            })
        },
    },
    computed: {
        ...mapGetters({
            errorState: 'getErrorState'
        })
    },
    created() {
        const savedEmail = localStorage.getItem('savedEmail');
        if (savedEmail) {
            this.email = savedEmail;
            this.isEmailSaved = true;
        }
    },
}
</script>