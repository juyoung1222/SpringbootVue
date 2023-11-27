<!-- PageHeader.vue -->
<template>
    <div class="w-100">
        <b-navbar v-if="searchToggleData" class="search-toggle-container d-flex">
            <div class="text-center align-item-center">
                <font-awesome-icon
                    icon="fa-solid fa-chevron-left"
                    class="align-self-center fs-1 mx-3"
                    @click="searchToggleFunc"
                    role="button"/>
            </div>
            <div class="h-100 w-100">
                <div class="bg-white w-100 h-100 rounded-5 py-2">
                    <font-awesome-icon class="mx-3 fs-3" :icon="['fas', 'magnifying-glass']"/>
                    <span class="w-100">
                        <input class="border-0 h-100 w-75 bedu-header-search"></span>
                    </div>
                </div>
        </b-navbar>
        <b-navbar v-else class="w-100 d-flex" toggleable="xxl">
            <b-navbar-toggle target="header-toggle"></b-navbar-toggle>
            <font-awesome-icon
                class="fs-3 ms-2 d-block d-xl-none"
                :icon="['fas', 'magnifying-glass']"
                role="button"
                @click="searchToggleFunc"/>
            <b-navbar-brand class="mx-auto">
                <router-link to="/">
                    <img id="bedu-logo" src="@/assets/imgs/Logo.png"></router-link>
            </b-navbar-brand>

            <div
                class="offcanvas  offcanvas-start d-block d-xl-none bedu-bg-custom-yellow"
                id="header-toggle"
                tabindex="-1">
                <div class="offcanvas-header">
                    <button class="btn-close" data-bs-dismiss="offcanvas"></button>
                </div>

                <div class="offcanvas-body">
                    <b-nav vertical="vertical">
                        <b-navbar-nav>
                            <b-nav-item
                                class="fs-4 fw-bold"
                                data-bs-dismiss="offcanvas"
                                v-if='categories.length'
                                :to="{
                                    name : 'lectureCategories',
                                    params : {
                                        index : categories[0].cateCode
                                    },
                                    query :{
                                        cnt_mid_cate : categories[0].children[0].cateCode
                                    }
                                }">
                                분야별 강의
                            </b-nav-item>
                            <div class="fs-4 fw-bold ">
                                커뮤니티
                            </div>
                            <b-container class="fs-5 fw-bold">
                                <b-nav-item data-bs-dismiss="offcanvas" to="/comm/qna">
                                    질문 & 답변
                                </b-nav-item>
                                <b-nav-item data-bs-dismiss="offcanvas" to="/comm/freBd">
                                    자유게시판
                                </b-nav-item>
                            </b-container>
                            <b-nav-item to="/review" data-bs-dismiss="offcanvas" class="fs-4 fw-bold ">수강후기</b-nav-item>
                            <b-nav-item
                                to="/companyStudy"
                                data-bs-dismiss="offcanvas"
                                class="fs-4 fw-bold ">기업교육</b-nav-item>
                            <b-nav-item to="/membership" data-bs-dismiss="offcanvas" class="fs-4 fw-bold ">멤버쉽 안내</b-nav-item>
                            <b-nav-item
                                v-if="!isLoggedIn"
                                to="/login"
                                data-bs-dismiss="offcanvas"
                                class="fs-4 fw-bold ">로그인</b-nav-item>
                            <b-nav-item
                                v-if="!isLoggedIn"
                                to="/regist"
                                data-bs-dismiss="offcanvas"
                                class="fs-4 fw-bold ">회원가입</b-nav-item>
                            <b-nav-item
                                v-if="isLoggedIn"
                                @click="logout"
                                data-bs-dismiss="offcanvas"
                                class="fs-4 fw-bold ">로그아웃</b-nav-item>
                        </b-navbar-nav>
                    </b-nav>
                </div>
            </div>

            <b-collapse id="header-collapse" is-nav>
                <b-navbar-nav id="nav1" class="me-auto ps-3 pt-4">
                    <b-nav-item
                        v-if='categories.length'
                        >
                        <router-link
                            class="fs-5 fw-bold m-0 text-black-50"
                            :class="{ 'router-link-active': $route.name === 'lectureCategories' }"
                            :to="{
                                name : 'lectureCategories',
                                params : {
                                    index : categories[0].cateCode
                                },
                                query : {
                                    cnt_mid_cate : categories[0].children[0].cateCode
                                }
                            }"
                            >
                        분야별 강의
                        </router-link>
                    </b-nav-item>
                    <b-nav-item class="fs-5 fw-bold me-1" >
                        <router-link
                            class="m-0 text-black-50"
                            :class="{'router-link-active': $route.path.includes('comm')}"
                            to="/comm/qna"
                            >
                            커뮤니티
                        </router-link>
                    </b-nav-item>
                    <b-nav-item class="fs-5 fw-bold me-1" to="/review">
                        수강후기
                    </b-nav-item>
                    <b-nav-item class="fs-5 fw-bold me-1" to="/companyStudy">
                        기업교육
                    </b-nav-item>
                    <b-nav-item class="fs-5 fw-bold" to="/membership">
                        멤버쉽 안내
                    </b-nav-item>
                </b-navbar-nav>
                <b-navbar-nav id="nav2" class="ms-auto pt-4">
                    <div class="search-popup">
                        <div
                            style="width: 250px; height: 30px; margin-right: 100px;"
                            class="border-3 rounded-pill d-flex align-middle text-center border-bedu">
                            <font-awesome-icon class="m-auto mx-3" :icon="['fas', 'magnifying-glass']"/>
                            <form @submit.prevent="lectSearch">
                                <b-form-input
                                    style="width: 250px; height: 30px; margin-right: 70px;"
                                    class="border-0 me-2 bedu-header-search"
                                    v-model="keyword"
                                    id=""></b-form-input>
                            </form>
                        </div>
                    </div>
                    <b-nav-item
                        to="/login"
                        style="margin-left: 10px;"
                        class="fs-5 fw-bold"
                        v-if="!isLoggedIn">
                        로그인
                    </b-nav-item>
                    <!-- 사용자 드롭다운 메뉴 -->
                    <b-nav-item
                        v-if="isLoggedIn"
                        class="dropdown fs-5"
                        @click="openDropdown"
                        @mouseleave="closeDropdown">
                        <button id="nicknameToggle" class="dropdown-toggle no-arrow py-0" type="button">
                            <span class="fs-5 fw-bold px-2 py-0">{{ getNickname }}</span>
                        </button>
                        <span style="font-weight: bold;">님</span>
                        <ul class="dropdown-menu" v-show="isDropdownOpen">
                            <b-dropdown-item to="/mypage">
                                마이 페이지
                            </b-dropdown-item>
                            <b-dropdown-item to="/csc">
                                고객 센터
                            </b-dropdown-item>
                            <!-- 영상 업로드 (관리자만 표시) -->
                            <b-dropdown-item v-if="isLoggedIn && getCls === 'ADMIN'" to="/adminPage">
                                관리자 페이지
                            </b-dropdown-item>
                        </ul>
                    </b-nav-item>
                    <b-nav-item
                        id="headerLogout"
                        v-if="isLoggedIn"
                        @click="logout"
                        class="fs-5 fw-bold">
                        로그아웃
                    </b-nav-item>
                    <b-nav-item class="fs-5 fw-bold" v-if="!isLoggedIn" to="/regist">
                        회원가입
                    </b-nav-item>
                </b-navbar-nav>
            </b-collapse>
                <!-- 화면 상단으로 끌어올리는 컨테이너 -->
                <div
                    ref="scrollTop"
                    class="position-fixed d-flex scrollTop rounded-circle"
                    @click="scrollToTop"
                    :class="{ 'show': showButton }">
                    <font-awesome-icon
                        class="text-white fs-3 position-relative"
                        :icon="['fas', 'angles-up']"/>
                </div>
        </b-navbar>
    </div>
</template>

<script>
    
    export default {
        name: 'PageHeader',
        mounted() {
            window.addEventListener('scroll', this.scrollHandler)
        },
        created(){
            this.getCategory();
            this.isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
        },
        data() {
            return {
                isDropdownOpen: false, 
                categories: [], 
                showButton: false,
                keyword : '',
                searchToggleData : false,
            };
        },
        computed: {
            isLoggedIn() {
                return this.$store.getters.getIsAuth
            },
            getNickname() {
                return this.$store.getters.getNickname;
            },
            getCls() {
                return this.$store.getters.getCls;
            },
            getCbnumList() {
                return this.$store.getters.getCbnumList;
            },

        },
        methods: {
            /** 검색 메서드 */
            lectSearch(){
                this.$routerPush(
                    'lectureSearch',
                    {
                        keyword : this.keyword
                    },
                    true
                )
            },
            /** 받은 카테고리를 트리 구조로 변경하는 함수 */
            convertToHierarchy(data) {
                const map = {}; // 부모-자식 관계를 저장할 맵
                // 맵에 카테고리 코드를 키로하여 카테고리 객체를 저장
                data.forEach(category => {
                    category.children = []; // 자식 카테고리를 저장할 배열 생성
                    map[category.cateCode] = category;
                });

                const hierarchy = []; // 최상위 부모 카테고리를 저장할 배열

                // 부모-자식 관계 설정
                data.forEach(category => {
                    const parentNum = category.parentNum;
                    if (parentNum != 0) {
                        const parent = map[parentNum];
                        if (parent) parent.children.push(category)
                    } else {
                        hierarchy.push(category);
                    }
                    this.categories = hierarchy;
                });
            },
            scrollHandler() {/** 스크롤이 내려감에 따라 showButton의 값 변경해주는 핸들러 */
                this.showButton = window.scrollY > 200;
            },
            scrollToTop() {/** 최상단으로 올리는 메서드 */
                window.scrollTo({top: 0})
            },
            openDropdown() {
                this.isDropdownOpen = true;
            },
            closeDropdown() {
                this.isDropdownOpen = false;
            },
            /** 카테고리 조회 */
            getCategory() { 
                let cateData = [];
                this.$axiosSend('get', '/api/lect/getCategory')
                .then((res) => {
                    console.log('res::: ', res)
                    if (this.$isNotEmpty(
                        res?.data
                    )) {
                        cateData = res?.data;
                    }
                    console.log('cateData ::: ', cateData)
                    this.convertToHierarchy(cateData)
                })
            },
            logout() {
                this.$swal({
                    title: '로그아웃 하시겠습니까?',
                    
                    showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                    cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                    confirmButtonColor: '#303076',
                    confirmButtonText: '확인', // confirm 버튼 텍스트 지정
                    cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                    
                    }).then(result => {
                    // 만약 Promise리턴을 받으면,
                    if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
                        this.$swal({
                            confirmButtonColor: '#303076',
                            title: '로그아웃이 완료되었습니다.',
                            timer: 3000,
                        });
                        localStorage.removeItem("user_token");
                        localStorage.removeItem('login_time')
                        this.$store.commit('IS_AUTH', false);
                        this.$store.commit('NICKNAME', null);
                        this.$store.commit('USERNUM', null);
                        this.$store.commit('EMAIL', null);
                        this.$store.commit('LESSONS', null);
                    } else {
                        // 취소(No)를 선택한 경우
                        // 아무 작업도 하지 않음
                    }
                });
                // "로그아웃 하시겠습니까?" 알림 표시

                // const confirmLogout = window.confirm("로그아웃 하시겠습니까?");

                // if (confirmLogout) {
                //     // 확인(Yes)을 선택한 경우
                //     localStorage.removeItem("user_token");
                //     this.$store.commit('IS_AUTH', false);
                //     this.$store.commit('NICKNAME', null);
                //     this.$store.commit('USERNUM', null);
                //     this.$store.commit('EMAIL', null);
                //     location.reload();
                // } else {
                //     // 취소(No)를 선택한 경우
                //     // 아무 작업도 하지 않음
                // }
            },
            searchToggleFunc(){
                if(this.searchToggleData){
                    this.searchToggleData = false;
                } else{
                    this.searchToggleData = true;
                }
            }
          },
          watch:{ /** url 변경 감지하여 헤더에 있는 검색 입력부분은 비우기 */
            '$route'(){
                this.keyword = '' 
            }
          }
    };
</script>
  
<style scoped>
    .scrollTop {
        background-color: var(--black);
        top : 85%;
        right : 3%;
        width: 80px;
        height: 80px;
        align-items: center;
        justify-content: center;
        opacity: 0;
        transition: opacity 0.25s ease;
        cursor: default;
        z-index: 100;
    }

    .scrollTop.show {
        opacity: 1;
        cursor: pointer;
    }

    .dropdown:hover .dropdown-menu {
        display: block;
    }

    #nav1 {
        display: flex;
        align-items: center;
    }

    #nav2 {
        display: flex;
        align-items: center;
    }

    #bedu-logo {
        width: 230px;
    }

    #nav1 a {
        margin-right: 30px;
        font-family: 'Noto Sans CJK KR', sans-serif;
        font-weight: bold;
        color: #2c3e50;
        text-decoration: none;
    }

    #nav2 a {
        margin-left: 20px;
        font-family: 'Noto Sans CJK KR', sans-serif;
        font-weight: bold;
        color: #2c3e50;
        text-decoration: none;
    }

    #nav2 .search-popup {
        align-items: center;
        display: inline-block;
        margin-left: -20px;
        margin-right: -50px;
    }

    #nav2 .search-popup input {
        width: 100%;
        height: 15px;
        padding: 5px 10px;
        border: none;
        background-color: #F6E0B3;
        border-radius: 30px;
    }

    .border-bedu {
        background-color: #F6E0B3;
        width: 70%;
        border-color: #EDC268;
    }

    .no-arrow::after {
        display: none;
    }

    #nicknameToggle {
        background-color: #F6E0B3;
        border: none;
        color: black;
        margin-left: 10px;
        margin-right: 8px;
        border-radius: 40px;
        padding: 5px 12px 5px 12px;
        box-shadow: 0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1);
        transition: box-shadow 0.1s linear;
    }

    #nicknameToggle:hover{
        box-shadow: inset 1px 1px 5px 1px rgb(77, 71, 71);
    }

    #headerLogout {
        background-color: #EDC268;
        border: #EDC268;
    }

    .bedu-header-search:focus{
        box-shadow: none !important;
    }

    .nav-item{
        --bs-nav-link-hover-color: var(--blue) !important;
    }

    .text-black-50:hover{
        color: var(--blue) !important;
        transition: all 0.2s;
    }

    .search-toggle-container{
        height: 80px;
    }

    .router-link-active{
        color : var(--black) !important;
        font-weight: bolder !important;
        transition: all 0.15s
    }

</style>