<!-- PageFooter.vue -->
<template>
    <div class="bedu-ft">
        <div class="bedu-ft-cate-links">
            <div class="bedu-ft-cate-links-lt">
                <div class="item">
                    <router-link class="bedu-ft-cate" to="">비에듀</router-link>            
                </div>
                <div class="item">
                    <router-link class="bedu-ft-cate" to="">기업교육</router-link>
                </div>
                <div class="item">
                <span
                    class="bedu-ft-cate"
                    :class="{ checked: selectedAgreements.includes(1) }"
                    style="cursor: pointer"
                    @click="agreeOpen(1)"
                >사용자 이용약관</span>
                </div>
                <div class="item">
                <span 
                    class="bedu-ft-cate"
                    :class="{ checked: selectedAgreements.includes(2) }"
                    style="cursor: pointer"
                    @click="agreeOpen(2)"
                >개인정보 보호 방침</span>
                </div>
                <div class="item">
                    <router-link class="bedu-ft-cate" to="/csc">고객센터</router-link>
                </div>
                <div class="item">
                    <router-link class="bedu-ft-cate" to="">코드등록</router-link>
                </div>
                <!-- <img src="../assets/imgs/test.png"> -->
            </div>
        </div>
        <div class="bedu-ft-des-details">
            <div class="bedu-ft-des">
                <p><span>(주)Test | 대표: TEST NAME KING, 테스트 </span></p>
                <p><span>| 개인정보책임자 : test | 대표전화: 1234-1234</span></p>
                <p><span>| Email: test@test.com | 사업자번호: 123-12-1234</span></p>
                <p><span>| 통신판매업번호: 제 2019-서울강남-01371호</span></p>
                <p><span>| 평생교육시설 신고: 제원-408호 Test 사이버평생교육원</span></p>
                <p><span>주소: 서울시 강남구 강남대로 test</span></p>
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
            <span>
            &nbsp;&nbsp;{{ selectedAgreementTitle }}&nbsp;&nbsp;
            </span>
        </div>
        <!-- 모달 내용 -->
        <div id="agreeItem" v-html="selectedAgreementContent"></div>
        </div>
    </div>
</template>
  
<script>
    export default {
        data() {
            return {
            showModal: false, // 모달 창 표시 여부를 나타내는 변수
            selectedAgreementContent: "", // 선택된 약관의 내용을 저장하는 변수
            selectedAgreementTitle: "",
            allChecked: false, // "모든 이용 약관에 동의" 체크박스의 상태를 나타내는 데이터 속성
            selectedAgreements: [], // 선택된 약관 체크박스의 값을 저장하는 배열
            selectedAgreement: null, // 선택된 약관의 값을 저장하는 변수
            fileText1: '', // 이용약관에 대한 내용을 담을 변수
            fileText2: '', // 개인정보 수집에 대한 내용을 담을 변수
            }
        },
        created() {
            // 컴포넌트가 생성될 때 약관 내용을 가져오는 메서드 호출
            this.fetchAgreements();
        },
        methods: {
            // 약관 동의 항목을 조회하는 메서드
            fetchAgreements() {
                const ids = [1, 2]; // 변경할 id 값들을 배열로 선언
                // 각 id에 대해 순회하면서 약관 데이터 요청
                ids.forEach(id => {
                    this.$axiosSend("get", `/api/agree/${id}`)
                    .then(response => {
                        if (id === 1) {
                            this.fileText1 = response.data[0].content;
                        } else if (id === 2) {
                            this.fileText2 = response.data[0].content;
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
                    agreeItem.scrollTop = 0;
                }
                this.selectedAgreement = value;
                if (this.showModal) {
                    this.selectedAgreementContent = "";
                }
                if (value === 1) {
                    this.selectedAgreementContent = this.fileText1;
                    this.selectedAgreementTitle = "B:EDU 이용약관";
                } else if (value === 2) {
                    this.selectedAgreementContent = this.fileText2;
                    this.selectedAgreementTitle = "개인정보 수집 이용약관";
                }
                this.showModal = true;
                document.body.style.overflow = 'hidden';
            },
            closeModal() {
                this.showModal = false;
                document.body.style.overflow = '';
            },
        }
    }
</script>

<style scoped>

    footer img {
    float: left;
    height: 13px;
    width: 10px;
    margin: 27px;
    }

    .bedu-ft{
    display:grid;
    grid-template-areas: "links" "details" "icons";
    justify-content: center;
    width:100%;
    max-width: 110rem;
    margin:0 auto;
    }
    .bedu-ft-cate-links{
    grid-area: links;
    }
    .bedu-ft-cate-links-lt{
    display:flex;
    justify-content: center;
    flex-wrap: wrap;
    align-items: center;
    column-gap: 2.4rem;
    row-gap: 0.5rem;
    }
    .bedu-ft-cate {
    font-size: 1.3rem;
    font-family: 'Noto Sans CJK KR', sans-serif;
    font-weight: 900;
    color: #2c3e50;
    text-decoration: none;
    }
    .bedu-ft-des-details{
    grid-area: details;
    max-width: 70rem;
    margin-top: 2rem;
    }
    .bedu-ft-des{
    display:flex;
    min-width: 100%;
    color: #7e7e86;
    flex-wrap: wrap;
    text-align: center;
    justify-content: center;
    }

    /* 모달 창 CSS */
    .modal {
        display: block; /* 모달 창 표시 */
        position: fixed; /* 위치 고정 */
        left: 0;
        top: 0;
        width: 100%;
        height: 120%;
        background-color: rgba(0, 0, 0, 0.4); /* 배경 어둡게 */
    }

    @media (max-width: 1300px) {
        .bedu-ft-des {
            font-size: 0.8rem
        }

        .bedu-ft-cate {
            font-size: 1rem
        }
    }
    
    #agreeItem {
        padding: 2%;
        margin-top: 2%;
        overflow: auto;
        text-align: left;
        background-color: #f5f4f4;
    }
    
    .agreeOpen span {
        color: #888;
        font-weight: bold;
        padding-bottom: 2%;
    }
    
    .agreeOpen {
        border-bottom: 1px solid #cfcfcf;    
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
    
    .agreeOpen > span {
        cursor: pointer;
    }
    
    /* 모달 내용 */
    .modal-content {
        background-color: #fefefe;
        margin: 10% auto;
        padding: 2%;
        border: 1px solid #888;
        width: 80%;
        max-width: 800px;
        max-height: 50%;
        display: flex;
    }

    #agreeCloseBtn {
        position: absolute;
        top: -5px; 
        right: -5px; 
        z-index: 2; 
        cursor: pointer;
    }
</style>