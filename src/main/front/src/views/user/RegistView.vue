<!-- 작성자 이준원 -->


<template>
    <div id="registChangeContainer">
        <!-- 로그인 및 회원가입 이동 버튼 -->
        <div id="registChangeSection">
            <!-- 로그인 버튼 -->
            <button @click="loginMove" id="reLoginChangeBtn">
                로그인
            </button>
            <!-- 회원가입 버튼 -->
            <button @click="registMove" id="reRegistChangeBtn">
                회원가입
            </button>
        </div>
        <div id="registContainer">
            <!-- 회원가입 양식 섹션 -->
            <div id="registSection">
                <div class="regist-container2">
                    <form class="regist-form" @submit.prevent="register">
                        <!-- 이메일 입력 필드 -->
                        <div id="registFormGroup">
                            <input
                                type="email"
                                id="registEmail"
                                placeholder="이메일 입력"
                                v-model="emailValue"
                                @input="showEmailOptions"
                            />
                            <!-- 이메일 목록 -->
                            <ul v-show="showEmailList" id="emailOptions">
                                <li v-for="(email, index) in filteredEmailList" :key="index" @click="selectEmailOption(email)">
                                {{ email }}
                                </li>
                            </ul>
                            <button
                                id="registEmailChk"
                                @click="checkEmailDuplicate"
                                :disabled="
                                    isChecking || valid.email || !member.email
                                "
                            >
                                중복체크
                            </button>
                            <p
                                v-show="valid.email && member.email"
                                id="registInputError"
                            >
                                이메일 주소를 정확히 입력해주세요. 예)
                                bedu@bedu.com
                            </p>
                            <p
                                v-show="emailChecked && !valid.emailChk && valid.emailDomain"
                                id="registInputError"
                            >
                                중복된 이메일 입니다.
                            </p>
                            <p v-show="!valid.email && !valid.emailDomain && emailChecked" id="registInputError">
                                도메인을 포함한 올바른 이메일 주소를 입력해주세요.
                            </p>
                            <p
                                v-show="emailChecked && valid.emailChk && valid.emailDomain"
                                id="registInputCorrect"
                            >
                                사용가능한 이메일 입니다.
                            </p>
                        </div>
                        <!-- 닉네임 입력 필드 -->
                        <div id="registFormGroup">
                            <input
                                id="registNickname"
                                placeholder="닉네임 입력"
                                v-model="member.nickname"
                            />
                            <button
                                id="registNickChk"
                                @click="checkNickDuplicate"
                                :disabled="
                                    isChecking ||
                                    valid.nickname ||
                                    !member.nickname
                                "
                            >
                                중복체크
                            </button>
                            <p
                                v-show="valid.nickname && member.nickname"
                                id="registInputError"
                            >
                                닉네임은 2자리 이상 10자리 이하이며 특수문자는
                                사용하실 수 없습니다.
                            </p>
                            <p
                                v-show="nickChecked && !valid.nickChk"
                                id="registInputError"
                            >
                                중복된 닉네임 입니다.
                            </p>
                            <p
                                v-show="nickChecked && valid.nickChk"
                                id="registInputCorrect"
                            >
                                사용 가능한 닉네임 입니다.
                            </p>
                        </div>
                        <!-- 비밀번호 입력 필드 -->
                        <div id="registFormGroupPassword">
                            <input
                                :type="showPassword ? 'text' : 'password'"
                                id="registPassword"
                                placeholder="비밀번호 입력"
                                v-model="member.password"
                            />
                            <font-awesome-icon
                            id="passwordEyeToggle"
                            :icon="showPassword ? ['fas', 'eye'] : ['fas', 'eye-slash']"
                            @click="togglePasswordVisibility"
                            />
                        </div>
                        <p
                            v-show="
                                valid.password &&
                                member.password &&
                                (member.password.length < 6 ||
                                    member.password.length > 15)
                            "
                            id="registInputErrorPassword"
                        >
                            비밀번호는 6자리 이상 15자리 이하로
                            작성해주세요.
                        </p>
                        <p
                            v-show="
                                valid.password &&
                                member.password &&
                                member.password.length >= 6 &&
                                    member.password.length <= 15
                            "
                            id="registInputErrorPassword"
                        >
                            올바른 비밀번호 형식이 아닙니다.
                        </p>
                        <!-- 비밀번호 확인 필드 -->
                        <div id="registFormGroup">
                            <input
                                type="password"
                                id="registConfirmPassword"
                                placeholder="비밀번호 입력 확인"
                                v-model="confirmPassword"
                            />
                            <p
                                v-show="
                                    member.password !== confirmPassword
                                "
                                id="registInputError"
                            >
                                비밀번호가 일치하지 않습니다.
                            </p>
                            <p
                                v-show="
                                    !valid.password &&
                                    member.password &&
                                    member.password == confirmPassword &&
                                    member.password.length >= 6 &&
                                    member.password.length <= 15
                                "
                                id="registInputCorrect"
                            >
                                사용가능한 비밀번호 입니다.
                            </p>
                        </div>
                        <!-- 이용약관 동의 섹션 -->
                        <div  id="registCheckBox">
                            <!-- "모든 이용 약관에 동의" 체크박스 -->
                            <label id="registChkAll" for="agree_all">
                                <input
                                    class="form-check-input"
                                    type="checkbox"
                                    name="agree_all"
                                    id="agree_all"
                                    v-model="allChecked"
                                    @change="toggleAllAgreements"
                                />
                                <span style="cursor: pointer">모든 이용 약관에 동의.</span>
                            </label>
                            <!-- 개별 약관 -->
                            <!-- 개별약관 제목 -->
                            <div
                                id="registChoice"
                                v-for="(item, index) in agreements"
                                :key="index"
                                :class="{ checked: selectedAgreements.includes(item.value) }"
                                @click="toggleCheckbox(item.value)"
                            >
                                <!-- 개별약관 체크박스 -->
                                <input
                                    class="form-check-input"
                                    type="checkbox"
                                    :id="'agree_' + item.value"
                                    :name="'agree'"
                                    :value="item.value"
                                    v-model="selectedAgreements"
                                    @change="updateAllChecked"
                                />
                                <label :for="'agree_' + item.value" style="cursor: pointer; margin-left: 2%;">
                                    <strong v-if="!item.optional">[필수]</strong>
                                    <strong v-if="item.optional">[선택]</strong>
                                    {{ item.label }}
                                </label>
                                <!-- 개인정보 이용약관 Open 버튼 -->
                                <span
                                    :for="'agree_' + item.value"
                                    id="agreeFaIcon"
                                    style="cursor: pointer"
                                    @click="agreeOpen(item.value)"
                                >
                                    <font-awesome-icon
                                        :icon="['fas', 'circle-chevron-right']"
                                        style="color: #d5d8dc;"
                                    />
                                </span>
                            </div>
                        </div>
                        <!-- 회원가입 Submit 버튼 -->
                        <div id="registFormGroup">
                            <button
                                id="registSubmitFormBtn"
                                type="submit"
                            >
                                회원가입
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- 모달 창 -->
    <div v-if="showModal" class="modal" @click="closeModal">
        <div class="modal-content" @click.stop>
            <!-- 모달 닫기 버튼 -->
            <div class="agreeClose">
                <font-awesome-icon class="fa-3x" id="agreeCloseBtn" style="color: #303076;" @click="closeModal" :icon="['fas', 'circle-xmark']" />
            </div>
            <div class="agreeOpen">
                <!-- 모달 제목 -->
                <span
                    v-for="(item, index) in agreements"
                    :key="index"
                    @click="agreeOpen(item.value)"
                    v-show="isMobileView ? item.showContent : true"
                    :style="{ color: selectedAgreement === item.value && showModal ? '#303076' : '', borderBottom: selectedAgreement === item.value && showModal ? '3px solid #303076' : ''}"
                >
                    &nbsp;&nbsp;{{ item.label }}&nbsp;&nbsp;
                </span>
            </div>
            <!-- 모달 내용 -->
            <div id="agreeItem" v-html="selectedAgreementContent"></div>
        </div>
    </div>
</template>

<script>
    // 필요한 모듈 및 CSS 파일을 가져옵니다.
    import '@/assets/css/userStyle.css';
    export default {
        name: 'EmailInput',
        data() {
            return {
                // -------------------- 회원가입 Input 관련 변수들 ----------------------
                // ---------------------------------------------------------------------
                isChecking: false, // 중복 체크 상태를 나타내는 변수
                emailChecked: false, // 이메일 중복 체크 상태를 나타내는 변수
                nickChecked: false, // 닉네임 중복 체크 상태를 나타내는 변수
                confirmPassword: "", // 비밀번호 확인을 위해 입력된 값을 저장하는 변수
                showPassword: false,
                emailValue: '', // 이메일 입력 필드의 값
                emailList: [], // 이메일 목록을 담을 배열
                isMobileView: false, // 모바일 화면 여부를 나타내는 변수
                member: {
                    email: "", // 회원의 이메일
                    nickname: "", // 회원의 닉네임
                    password: "", // 회원의 비밀번호
                },
                showEmailList: false, // 이메일 목록 표시 여부를 나타내는 변수
                filteredEmailList: [], // 필터링된 이메일 목록을 저장하는 변수
                valid: {
                    email: false, // 이메일의 유효성 검사 결과
                    nickname: false, // 닉네임의 유효성 검사 결과
                    password: false, // 비밀번호의 유효성 검사 결과
                    emailChk: false, // 이메일 중복 체크 결과
                    nickChk: false, // 닉네임 중복 체크 결과
                    emailDomain: false // 이메일 도메인 유효성 검사 결과
                },
                // -------------------- 모달창 & 이용약관 관련 변수들 --------------------
                // ---------------------------------------------------------------------
                showModal: false, // 모달 창 표시 여부를 나타내는 변수
                selectedAgreementContent: "", // 선택된 약관의 내용을 저장하는 변수
                allChecked: false, // "모든 이용 약관에 동의" 체크박스의 상태를 나타내는 데이터 속성
                selectedAgreements: [], // 선택된 약관 체크박스의 값을 저장하는 배열
                selectedAgreement: null, // 선택된 약관의 값을 저장하는 변수
                agreements: [
                    // 이용약관과 개인정보 수집에 대한 동의 항목을 포함
                    { value: 1, label: "B:EDU 이용약관", optional: false },
                    {
                        value: 2, label: "개인정보 수집 이용약관", optional: false,
                    },
                    { value: 3, label: "마케팅 정보 수신약관", optional: true },
                ],
                fileText1: '', // 이용약관에 대한 내용을 담을 변수
                fileText2: '', // 개인정보 수집에 대한 내용을 담을 변수
                fileText3: '', // 이벤트 수신에 대한 내용을 담을 변수
            };
        },
        mounted() {
            // 모바일 화면 여부를 확인하고 변경사항 감지
            window.addEventListener('resize', this.checkMobileView);
        },
        beforeUnmount() {
            // 이벤트 리스너 제거
            window.removeEventListener('resize', this.checkMobileView);
        },
        watch: {
            // Email Data 변경 감지
            "member.email": function () {
                this.checkEmail();
            },
            // NickName Data 변경 감지
            "member.nickname": function () {
                this.checkNick();
            },
            // Password Data 변경 감지
            "member.password": function () {
                this.checkPassword();
            },
            emailValue(value) {
                // 입력된 이메일 값을 멤버 변수에 할당
                this.member.email = value;
                // 자주 사용되는 이메일 주소 목록
                const frequencyEmails = [
                    '@naver.com',
                    '@gmail.com',
                    '@daum.net',
                    '@hanmail.net',
                    '@yahoo.com',
                    '@outlook.com',
                    '@nate.com',
                    '@kakao.com',
                ];
                // 주어진 이메일 값을 이용하여 사용 가능한 이메일 주소 목록 생성
                const userEmails = frequencyEmails.map((email) => {
                    // 입력된 값에 '@'가 포함되어 있다면 '@'을 기준으로 분리하여 사용자 이름과 이메일 주소를 결합
                    // '@'가 포함되어 있지 않다면 입력된 값 뒤에 이메일 주소를 결합
                    return value.includes('@') ? value.split('@')[0] + email : value + email;
                });
                // 생성된 이메일 주소 목록을 emailList 변수에 할당
                this.emailList = userEmails;
            },
        },
        methods: {
            // 회원가입 버튼 클릭시 RegistView.vue 이동 & top으로 scroll
            registMove() {
                window.scrollTo({
                    top: 0,
                    behavior: 'smooth',
                });
                this.$router.push({
                    name: "regist",
                });
            },
            // 로그인 버튼 클릭시 LoginView.vue 이동 & top으로 scroll
            loginMove() {
                window.scrollTo({
                    top: 0,
                    behavior: 'smooth',
                });
                this.$router.push({
                    name: "login",
                });
            },
            // 이메일 입력 시 이메일 목록을 보여주는 메서드
            showEmailOptions() {
                if (this.emailValue) {
                    // 이메일 목록을 필터링하여 보여줄 목록을 설정합니다.
                    this.filteredEmailList = this.emailList.filter((email) =>
                        email.includes(this.emailValue)
                    );
                    this.showEmailList = true;
                } else {
                    this.showEmailList = false;
                }
            },
            // 선택된 이메일 옵션을 emailValue에 할당하고 이메일 목록을 숨깁니다.
            selectEmailOption(email) {
                this.emailValue = email;
                this.showEmailList = false;
            },
            // 이메일의 유효성을 검사하는 메서드
            checkEmail() {
                // 유효한 이메일 형식인지 확인하기 위한 정규식
                const validateEmail = /^[a-z0-9_\\.\\-]+@[a-z0-9\\-]+\.[a-z0-9\\-]+/;
                // 이메일 값이 없는 경우 유효하지 않은 상태로 처리
                if (!this.member.email) {
                    this.valid.email = false;
                    this.emailChecked = false;
                    return;
                }
                // 이메일 값이 유효한 형식이 아닌 경우 유효하지 않은 상태로 처리
                if (!validateEmail.test(this.member.email)) {
                    this.valid.email = true;
                    this.emailChecked = false;
                    this.valid.emailDomain = false;
                    return;
                }
                // 이메일 값이 유효한 형식인 경우
                this.valid.email = false;
                this.valid.emailChk = false;
                this.emailChecked = false;
                // 이메일 도메인 추출
                const domain = this.member.email.split('@')[1];
                // 허용된 이메일 도메인 목록
                const allowedDomains = [
                    'naver.com',
                    'gmail.com',
                    'daum.net',
                    'hanmail.net',
                    'yahoo.com',
                    'outlook.com',
                    'nate.com',
                    'kakao.com',
                ];
                // 이메일 도메인이 허용된 도메인 목록에 포함되어 있는지 확인하여 유효성 설정
                this.valid.emailDomain = allowedDomains.includes(domain);
            },
            // 이메일 중복 체크를 수행하는 메서드
            checkEmailDuplicate() {
                this.isChecking = true;
                this.$axiosSend("get", `/api/register/email/${this.member.email}`)
                .then((response) => {
                    const isDuplicate = response.data;
                    this.emailChecked = true;
                    this.valid.emailChk = !isDuplicate;
                })
                .catch((error) => {
                    console.log(error);
                    this.$swal("이메일 중복 체크 실패");
                })
                .finally(() => {
                    this.isChecking = false;
                });
            },
            // 닉네임의 유효성을 검사하는 메서드
            checkNick() {
                // 유효한 닉네임 형식인지 확인하기 위한 정규식
                const validateNick = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,10}$/;
                if (!this.member.nickname) {
                    this.valid.nickname = false;
                    this.nickChecked = false;
                    return;
                }
                if (!validateNick.test(this.member.nickname)) {
                    this.valid.nickname = true;
                    this.nickChecked = false;
                    return;
                }
                this.valid.nickname = false;
                this.valid.nickChk = false;
                this.nickChecked = false;
            },
            // 닉네임 중복 체크를 수행하는 메서드
            checkNickDuplicate() {
                this.isChecking = true;
                this.$axiosSend(
                    "get",
                    `/api/register/nickname/${this.member.nickname}`
                )
                .then((response) => {
                    const isDuplicate = response.data;
                    this.nickChecked = true;
                    this.valid.nickChk = !isDuplicate;
                })
                .catch((error) => {
                    console.log(error);
                    this.$swal("닉네임 중복 체크 실패");
                })
                .finally(() => {
                    this.isChecking = false;
                });
            },
            // 비밀번호의 유효성을 검사하는 메서드
            checkPassword() {
                // 유효한 비밀번호 형식인지 확인하기 위한 정규식
                let validatePassword = /^(?=.*[a-zA-Z])(?=.*\d).{6,15}$/;
                if (
                    !validatePassword.test(this.member.password) ||
                    !this.member.password ||
                    this.member.password.length < 6 ||
                    this.member.password.length > 15
                ) {
                    this.valid.password = true;
                    return;
                }
                this.valid.password = false;
            },
            togglePasswordVisibility() {
                this.showPassword = !this.showPassword;
            },
            // "모든 이용 약관에 동의" 체크박스 토글 메서드
            toggleAllAgreements(event) {
                this.selectedAgreements = event.target.checked
                    ? this.agreements.map((item) => item.value)
                    : [];
                this.updateAllChecked();
            },
            // 개별 약관 체크박스 토글 메서드
            toggleCheckbox(value) {
                document.getElementById(`agree_${value}`);
                this.updateAllChecked();
            },
            // 체크박스 상태에 따라 "모든 이용 약관에 동의" 체크박스 업데이트
            updateAllChecked() {
                this.allChecked =
                    this.selectedAgreements.length === this.agreements.length;
            },
            // 화면 너비가 1300px 이하인 경우 모바일 화면으로 판단
            checkMobileView() {
                this.isMobileView = window.innerWidth <= 1300; 
            },
            // 약관 동의 항목을 조회하는 메서드
            fetchAgreements() {
                const ids = [1, 2, 3]; // 변경할 id 값들을 배열로 선언
                // 각 id에 대해 순회하면서 약관 데이터 요청
                ids.forEach(id => {
                    this.$axiosSend("get", `/api/agree/${id}`)
                    .then(response => {
                        if (id === 1) {
                            this.fileText1 = response.data[0].content;
                        } else if (id === 2) {
                            this.fileText2 = response.data[0].content;
                        } else if (id === 3) {
                            this.fileText3 = response.data[0].content;
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });
                });
            },
            // 모달창 Open시 메서드
            agreeOpen(value) {
                const agreeItem = document.getElementById('agreeItem');
                if (agreeItem) {
                    agreeItem.scrollTop = 0; // 스크롤 위치를 맨 위로 이동
                }
                this.selectedAgreement = value; // 선택된 약관의 값을 저장
                document.getElementById(`agree_${value}`);
                if (this.showModal) {
                this.selectedAgreementContent = ""; // 모달이 열릴 때마다 초기화
                }
                // 선택된 약관의 내용을 할당
                if (value === 1) {
                    this.selectedAgreementContent = this.fileText1;
                } else if (value === 2) {
                    this.selectedAgreementContent = this.fileText2;
                } else if (value === 3) {
                    this.selectedAgreementContent = this.fileText3;
                }
                this.showModal = true; // 모달 창을 표시
                document.body.style.overflow = 'hidden'; // 스크롤 막기

                // 모바일 화면에서 선택된 약관 항목만 보이도록 설정
                this.agreements.forEach((item) => {
                    if (item.value === value) {
                    item.showContent = true;
                    } else {
                    item.showContent = false;
                    }
                });
            },
            // 모달창 닫고 다시 스크롤 가능하도록 변경
            closeModal() {
                this.showModal = false;
                document.body.style.overflow = ''; // 스크롤 허용
            },
            // 사용자의 회원가입을 처리하는 메서드
            register() {
                if (
                    !this.emailChecked ||
                    !this.valid.emailChk ||
                    !this.valid.emailDomain
                ) {
                    this.$swal("이메일을 다시 확인해주세요.");
                } else if (
                    !this.nickChecked ||
                    !this.valid.nickChk 
                ) {
                    this.$swal("닉네임을 다시 확인해주세요.");
                } else if (
                    this.valid.password ||
                    !this.member.password ||
                    this.member.password !== this.confirmPassword ||
                    this.member.password.length < 6 ||
                    this.member.password.length > 15
                ) {
                    this.$swal("비밀번호를 다시 확인해주세요.");
                } else if (
                    !this.selectedAgreements.includes(1) ||
                    !this.selectedAgreements.includes(2)
                ) {
                    this.$swal("필수 이용약괸에 동의해주세요.");
                } else if (
                    this.emailChecked &&
                    this.valid.emailChk &&
                    this.valid.emailDomain &&
                    this.nickChecked &&
                    this.valid.nickChk &&
                    !this.valid.password &&
                    this.member.password &&
                    this.member.password == this.confirmPassword &&
                    this.member.password.length >= 6 &&
                    this.member.password.length <= 15 &&
                    this.selectedAgreements.includes(1) && this.selectedAgreements.includes(2)
                ) {
                    this.$axiosSend('post', '/api/register', this.member)
                    .then((response) => {
                        this.$swal(response.data);
                        this.$router.push({
                            name: "login",
                        });
                    })
                    .catch((error) => {
                        console.log(error);
                        this.$swal("회원가입 실패");
                    });
                } else {
                    this.$swal("회원가입 양식을 <br>다시 확인해주세요.");
                    return;
                }
            },
        },
        created() {
            // 컴포넌트가 생성될 때 약관 동의 항목과 내용을 조회하고 모바일 화면 여부를 확인하는 초기화 작업을 수행
            this.fetchAgreements();
            this.checkMobileView();
        },
    };
</script>