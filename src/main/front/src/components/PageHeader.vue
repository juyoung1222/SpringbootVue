<!-- PageHeader.vue -->
<template>
    <div class="w-100">
        <b-navbar v-if="searchToggleData" class="search-toggle-container d-flex p-0">
            <div class="text-center align-item-center">
                <font-awesome-icon
                    icon="fa-solid fa-chevron-right"
                    class="align-self-center fs-1 mx-3"
                    @click="searchToggleFunc"
                    role="button"/>
            </div>
            <div class="h-100 w-100">
                <div class="bg-white w-100 h-100 rounded-5 p-2 d-flex">
                    <font-awesome-icon class="mx-3 fs-3" :icon="['fas', 'magnifying-glass']"/>
                    <span class="w-100">
                        <b-form @submit.prevent="lectSearch">
                            <b-form-input v-model="keyword" class="border-0 h-100 w-100 p-0 bedu-header-search"></b-form-input>
                        </b-form>
                    </span>
                </div>
            </div>
        </b-navbar>
        
        <b-navbar v-else class="w-100 d-flex" toggleable="xxl">
                <b-navbar-brand>
                    <router-link to="/">
                        <img id="bedu-logo" src="@/assets/imgs/Logo.png"></router-link>
                </b-navbar-brand>
                <font-awesome-icon
                    class="fs-3 ms-auto me-3 d-block d-xxl-none"
                    :icon="['fas', 'magnifying-glass']"
                    role="button"
                    @click="searchToggleFunc"/>
            <b-navbar-toggle target="header-toggle"></b-navbar-toggle>



            <!-- 헤더 콜랩스 -->
            <b-collapse id="header-collapse" is-nav class="pt-3">
                <b-navbar-nav id="nav1" class="me-auto ms-2">
                    <router-link
                    v-if='categories.length'
                        class="fs-5 fw-bold m-0 bedu-header-nav-item p-2"
                        :class="{ 'header-router-link-active': $route.name === 'lectureCategories'}"
                        :to="{
                            name : 'lectureCategories',
                            params : {
                                index : categories.filter((fild)=>fild.level == 1)[0].lectTopCate
                            },
                            query : {
                                cnt_mid_cate : categories.filter((fild)=>fild.level == 2 )[0].lectMidCate
                            }
                        }"
                    >
                        분야별 강의
                    </router-link>
                    <router-link
                    class="fs-5 fw-bold me-1 bedu-header-nav-item" 
                    :class="{'header-router-link-active': $route.path.includes('comm')}"
                    to="/comm/qna"
                    >
                        커뮤니티
                    </router-link>
                    <router-link class="fs-5 fw-bold me-1 bedu-header-nav-item" 
                    :to="{
                        name : 'review',
                        query : {
                            topCategory : 'all'
                        }
                        }"
                    :class="{'header-router-link-active': $route.name == 'review'}"
                    >
                        수강후기
                    </router-link>
                    <router-link class="fs-5 fw-bold me-1 bedu-header-nav-item" to="/companyStudy"
                    :class="{'header-router-link-active' : $route.name == 'companyStudy'}">
                        기업교육
                    </router-link>
                    <router-link 
                    class="fs-5 fw-bold bedu-header-nav-item" to="/membership"
                    :class="{'header-router-link-active' : $route.name == 'membership'}">
                        멤버쉽 안내
                    </router-link>
                </b-navbar-nav>
                <b-navbar-nav id="nav2" class="ms-auto align-items-center">
                    <div>
                        <div class="search-popup">
                            <b-form @submit.prevent="lectSearch">
                                <div class="d-flex border-0 rounded-pill border-bedu">
                                    <font-awesome-icon class="m-auto mx-3" :icon="['fas', 'magnifying-glass']"/>
                                    <b-form-input v-model="keyword" size="sm" class="border-bedu bedu-header-search"></b-form-input>
                                </div>
                            </b-form>
                        </div>
                    </div>
                    <b-nav-item
                        style="margin-left: 10px;"
                        class="fs-5 fw-bold"
                        v-if="!isLoggedIn">
                        <router-link class="m-0 text-black-50 bedu-header-nav-item" to="/login">
                            로그인
                        </router-link>
                    </b-nav-item>
                    <!-- 사용자 드롭다운 메뉴 -->
                    <b-nav-item
                        v-if="isLoggedIn"
                        class="dropdown fs-5 fw-bold bedu-header-nav-item"
                        @click="openDropdown"
                        @mouseleave="closeDropdown">
                        <button id="nicknameToggle" class="dropdown-toggle no-arrow py-0" type="button">
                            <span class="fs-5 fw-bold px-2 py-0">{{ getNickname }}</span>
                        </button>
                        <span>님</span>
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
                        class="fs-5 fw-bold bedu-header-nav-item">
                        로그아웃
                    </b-nav-item>
                    <router-link v-if="!isLoggedIn" class="m-0 fs-5 fw-bold text-black-50 bedu-header-nav-item" to="/regist">
                        회원가입
                    </router-link>
                </b-navbar-nav>
            </b-collapse>

            <!-- 오프캔버스 -->
            <div
                class="offcanvas offcanvas-end d-xxl-none bedu-bg-custom-yellow-100"
                id="header-toggle"
                tabindex="-1">
                <div class="offcanvas-header">
                    <button class="btn-close" data-bs-dismiss="offcanvas"></button>
                </div>

                <div class="offcanvas-body">
                    <b-nav vertical="vertical" class="w-100">
                        <b-navbar-nav class="w-100">
                            <div class="w-100 d-flex text-center">
                                <b-nav-item
                                    v-if="!isLoggedIn"
                                    to="/login"
                                    data-bs-dismiss="offcanvas"
                                    class="fs-4 fw-bold w-50">
                                    로그인
                                </b-nav-item>
                                <b-nav-item
                                v-else
                                class="w-50"
                                >
                                    <span class="fs-5 fw-bold px-2 py-0">{{ getNickname }}</span>
                                </b-nav-item>
                                <div class="vr"></div>
                                <b-nav-item
                                    v-if="!isLoggedIn"
                                    to="/regist"
                                    data-bs-dismiss="offcanvas"
                                    class="fs-4 fw-bold w-50">
                                    회원가입
                                </b-nav-item>
                                <b-nav-item
                                    v-if="isLoggedIn"
                                    @click="logout"
                                    data-bs-dismiss="offcanvas"
                                    class="fs-4 fw-bold w-50">로그아웃
                                </b-nav-item>
                            </div>
                            <hr>
                            <b-nav-item
                            class="fs-4 fw-bold d-flex"
                            v-if='categories.length'
                            data-bs-toggle="collapse"
                            data-bs-target="#categories-collapse"
                            >
                                분야별 강의
                                <font-awesome-icon 
                                class="text-end" 
                                :icon="'fa-solid fa-chevron-down'"/>
                            </b-nav-item>
                            <b-container class="collapse fs-5 fw-bold" id="categories-collapse" ref="categoriesToggle">
                                <b-nav-item v-for="(item,index) in categories.filter((fild)=>fild.level == 1)" :key="index" data-bs-dismiss="offcanvas"
                                :to="{
                                    name : 'lectureCategories',
                                    params : {
                                        index : item.lectTopCate
                                    },
                                    query : {
                                        cnt_mid_cate : categories.filter((fild)=>(fild.parentCode == item.lectTopCate)  )[0].lectMidCate
                                    }
                                }">
                                    {{ item.lectTopCateKor }}
                                </b-nav-item>
                            </b-container>
                            <b-nav-item class="fs-4 fw-bold"
                            data-bs-toggle="collapse"
                            data-bs-target="#community-collapse"
                            >
                                커뮤니티
                                <font-awesome-icon :icon="'fa-solid fa-chevron-down'"/>
                            </b-nav-item>
                            <b-container class="fs-5 fw-bold collapse" id="community-collapse">
                                <b-nav-item data-bs-dismiss="offcanvas" to="/comm/qna">
                                    질문 & 답변
                                </b-nav-item>
                                <b-nav-item data-bs-dismiss="offcanvas" to="/comm/freBd">
                                    자유게시판
                                </b-nav-item>
                            </b-container>
                            <b-nav-item 
                            :to="{
                                name : 'review',
                                query :{
                                    topCategory : 'all'
                                }}" 
                                data-bs-dismiss="offcanvas" class="fs-4 fw-bold ">
                                수강후기
                            </b-nav-item>
                            <b-nav-item
                                to="/companyStudy"
                                data-bs-dismiss="offcanvas"
                                class="fs-4 fw-bold ">기업교육</b-nav-item>
                            <b-nav-item to="/membership" data-bs-dismiss="offcanvas" class="fs-4 fw-bold ">
                                멤버쉽 안내
                            </b-nav-item>
                            <div v-if="isLoggedIn" class="fs-4 fw-bold">
                                <b-nav-item data-bs-dismiss="offcanvas" to="/mypage">
                                    마이 페이지
                                </b-nav-item>
                                <b-nav-item data-bs-dismiss="offcanvas" to="/csc">
                                    고객 센터
                                </b-nav-item>
                            </div>
                            <div v-if="isLoggedIn && getCls === 'ADMIN'">
                                <hr>
                                <b-nav-item data-bs-dismiss="offcanvas" to="/adminPage" class="fs-4 fw-bold">
                                    관리자 페이지
                                </b-nav-item>
                            </div>
                        </b-navbar-nav>
                    </b-nav>
                </div>
            </div>

            <!-- 화면 상단으로 끌어올리는 컨테이너 -->
            <div
                id="scrollTopCircle"
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
import '@/assets/css/header.css'
    
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
            }
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
                this.$axiosSend('get','/api/lect/getCategory')
                .then((res)=>{
                    this.categories = res.data.item
                })
                .catch((err)=>{
                    console.log(err)
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
                            
                        }).then(function() {
                            window.location.reload(true);
                        });
                        localStorage.removeItem("user_token");
                        localStorage.removeItem('login_time')
                        localStorage.removeItem('cbnumList')
                        localStorage.removeItem('qsbnumList')
                        this.$store.commit('IS_AUTH', false);
                        this.$store.commit('NICKNAME', null);
                        this.$store.commit('USERNUM', null);
                        this.$store.commit('CLS', null);
                        this.$store.commit('UDY', null);
                        this.$store.commit('CBNUMLIST', null);
                        this.$store.commit('QSBNUMLIST', null);
                        this.$store.commit('EMAIL', null);
                        this.$store.commit('LESSONS', []);
                        this.$store.commit('SUBSCRIBE',null);
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
          watch:{ 
            /** url 변경 감지하여 헤더에 있는 검색 입력부분은 비우기 */
            '$route'(){
                this.keyword = '' 
            },
          }
    };
</script>