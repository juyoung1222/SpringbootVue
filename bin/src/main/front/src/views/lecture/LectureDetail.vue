<template>
    <div>
        <b-container class="py-5">
            <!-- 카테고리 -->
            <div>
                <p class="text-secondary fs-5 ms-3">{{ form.korCategory }}</p>
            </div>
            <!-- 썸네일, 강의정보 컨테이너-->
            <div class="d-flex mb-4">
                <!-- 썸네일 컨테이너 -->
                <div class="w-50 me-5 ratio ratio-16x9">
                    <b-img class="w-100" thumbnail="thumbnail" rounded="5" :src="form.thumbnail" fluid="fluid"></b-img>
                </div>
                <!-- 강의 정보 컨테이너 시작 -->
                <div class="w-50">
                    <p class="fw-bold fs-3">{{ form.title }}</p>

                    <!-- 강의 평점 컨테이너 시작 -->
                    <div class="d-flex mb-3">
                        <div>
                            <span v-for="(star, i) in 5" :key="i" class="mx-1 fs-5">
                                <font-awesome-icon
                                    :class="form.score >= star ? 'text-danger' : 'text-secondary'"
                                    :icon="['fas','star']"/>
                            </span>
                        </div>
                        <div class="ms-2 pt-1 align-middle">
                            {{ form.score }}
                            점 (
                            {{ form.scoreUsers }}
                            개)
                        </div>
                    </div>
                    <!-- 강의 평점 컨테이너 종료 -->

                    <div class="list-unstyled align-middle w-100">
                        <div class="mb-2 row">
                            <span class="text-secondary col-2">강사</span>
                            <span class="text-body col">{{ form.teacher }}</span>
                        </div>
                        <div class="mb-2 row">
                            <span class="text-secondary col-2">수강기간</span>
                            <span class="text-body col">
                                구매일로부터
                                {{ form.lectPeriod }}일</span>
                        </div>
                        <div class="mb-2 row">
                            <span class="text-secondary col-2">학습시간</span>
                            <span class="text-body col">{{ form.totalTimes }}({{ form.total }}강)</span>
                        </div>
                    </div>

                </div>
                <!-- 강의 정보 컨테이너 종료 -->
            </div>

            <!-- 결제, 장바구니 컨테이너 -->
            <div class="bg-secondary bg-opacity-10 p-3 py-5 mb-5 ">
                <b-container class="d-flex align-items-center">
                    <div class="w-50 d-flex">
                        <span class="fw-5 text-secondary ms-3 fs-6 align-self-baseline">강좌</span>
                        <span class="mx-auto fs-5">{{ form.title }}</span>
                    </div>
                    <div class="ms-auto fw-bold me-4">
                        <span class="fs-2 text-danger">{{ form.price }}</span>
                        <span>원</span>
                    </div>
                    <div v-if="myPageList.filter((item)=>item.lectNum == form.lectNum).length" class="w-10">
                        <b-button class="mt-auto h-100 px-5 py-2 bedu-bg-custom-blue">
                            수강중인 강의
                        </b-button>
                    </div>
                    <div v-else class="w-10">
                        <b-button class="d-block mb-1 w-auto px-5 py-2 bedu-bg-custom-blue" data-bs-toggle="modal" data-bs-target="#paymentTypeModal">
                            결제하기
                        </b-button>
                        <b-button class="px-5" data-bs-toggle="modal" data-bs-target="#cartModal" @click="addToCart">
                            장바구니
                        </b-button>
                    </div>

                    <!-- 결제 모달 -->
                    <div class="modal fade" id="paymentTypeModal">
                        <div class="modal-dialog modal-dialog-centered modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <p class="fs-3">멤버쉽 안내</p>
                                </div>
                                <div class="modal-body">
                                    <b-container class="mb-3">
                                        <p>멤버쉽 가입시 최대 31% 할인된 가격에 모든 강의를 이용할 수 있습니다</p>
                                        <p>멤버쉽에 대해 알아보시겠습니까?</p>
                                    </b-container>
                                </div>
                                <div class="modal-footer my-2">
                                    <b-button class="m-auto fs-5 px-4 py-2 bedu-bg-custom-blue" data-bs-dismiss="modal" @click="toPayment">
                                        결제하기
                                    </b-button>
                                    <b-button class="m-auto fs-5 px-4 py-2" data-bs-dismiss="modal" @click="toMembership">
                                        멤버쉽 알아보기
                                    </b-button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 장바구니 모달 -->
                    <div class="modal fade" id="cartModal">
                        <div class="modal-dialog modal-dialog-centered modal-lg">
                            <div class="modal-content">
                                <div class="modal-body">
                                    강의가 장바구니에 담겼습니다. 이동하시겠습니까?
                                </div>
                                <div class="modal-footer">
                                    <b-button class="bedu-bg-custom-blue" data-bs-dismiss="modal" @click="toPaymentOnly">장바구니</b-button>
                                    <b-button data-bs-dismiss="modal">둘러보기</b-button>
                                </div>
                            </div>
                        </div>
                    </div>
                </b-container>
            </div>

            <div class="lect-dtl-form-contents-container overflow-hidden">
                <!-- 소개, 목록, 후기 탭 -->
                <ul class="nav nav-tabs nav-fill ">
                    <li class="nav-item">
                        <a class="nav-link py-4 text-body " href="#description-body"><span class="py-4">강좌소개</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link py-4 text-body" href="#videoList-container">강의목록</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link py-4 text-body" href="#lecture-review-container">수강후기</a>
                    </li>
                </ul>

                <!-- 강좌 소개 -->
                <b-container class="px-4">
                    <h2 class="fw-bold my-5" id="description-body">강좌 소개</h2>
                    <div class="w-100 overflow-visible">
                        <div id="lect-dtl-content" v-html="form.lectDesc" class="w-100 "></div>
                    </div>

                    <!-- 강의 목록 -->
                    <h2 class="fw-bold my-5" id="videoList-container">강의목록</h2>
                    <ul class="list-unstyled mb-5">
                        <li
                            v-for="(video,i) in videos"
                            :key="i"
                            class="py-3 fs-5 border-bottom border-1">
                            <b-link class="d-flex text-body text-decoration-none" :to="'/lectureLesson?lectDtlNum='+video.lectDtlNum">
                                <span class="me-auto ms-3 fw-bold">
                                    {{ video.lectDtlIndex }}.
                                    {{video.lectDtlTitle}}
                                </span>
                                <span class="ms-auto me-3">
                                    {{ video.times }}
                                </span>
                            </b-link>
                        </li>
                    </ul>

                    <!-- 수강 후기 -->
                    <div class="mb-5">
                        <div class="d-flex mb-5">
                            <p class="fs-2 fw-bold me-auto" id="lecture-review-container">수강후기</p>
                            <div>
                                <span v-for="(star, i) in 5" :key="i" class="mx-1 fs-5">
                                    <font-awesome-icon
                                    :class="form.score < star ? 'text-secondary' : 'text-danger'"
                                    :icon="['fas','star']"
                                        />
                                </span>
                            </div>
                            <div class="ms-2 pt-1 align-middle">
                                {{ form.score }}
                                점 (
                                {{ form.scoreUsers }}
                                개)
                            </div>
                        </div>
                        <div class="">
                            <ul>
                                <li 
                                    class="list-unstyled mb-5"
                                    v-for="(review, index) in reviews" :key="index">
                                    <div>
                                        <div class="d-flex mb-1">
                                            <div class="me-auto">{{ review.writer }}</div>
                                            <div class="ms-auto">
                                                <span v-for="(star,index) in 5" :key="index">
                                                    <font-awesome-icon
                                                        :class="review.star < star ? 'text-secondary' : 'text-danger'"
                                                        :icon ="['fas','star']"
                                                    />
                                                </span>
                                            </div>
                                        </div>
                                        <div class="bg-secondary bg-opacity-10 p-4">
                                            {{ review.content }}
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </b-container>
            </div>
        </b-container>
    </div>
</template>

<script>
import '@/assets/css/lectureStyle.css';

    export default {
        name: 'lectureDetail',
        data() {
            return {
                form: {
                    lectNum: this.$route.query.num,
                    title: '',
                    score: 0,
                    scoreUsers: 0,
                    teacher: '',
                    duration: 0,
                    thumbnail: '',
                    view: 0,
                    regiDate: '',
                    price: 0,
                    description: '',
                    likeYn: '',
                    totalTimes: 0,
                    category: '',
                    stars: [],
                    videoLink:"https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                },
                videos: [],
                reviews: [],
                payPerMonth : "39,800",
                payPerYear : "27,417",
                myPageList : this.$store.getters.getLessons, // 수강목록
                userNum : this.$store.getters.getUsernum,
            }
        },
        methods: {
            test(){
                console.log(this.$store.getters.getLessons)
            },
            /** 동영상 재생 페이지로 이동 */
            toLesson(val){ 
                this.$routerPush('lectureLesson',{ lessonId : val.lectDtlNum},true);
            },
            /** 강의 상세 정보 조회 */
            getDetail() { 
                this.$axiosSend('get', '/api/lect/lectureDetail', {num: this.form.lectNum})
                .then((res) => {
                    if(this.$isNotEmptyObj(res.data)){
                        this.form = res.data;
                    } else {
                        this.$swal({
                            title : 'ERROR!',
                            icon : 'error',
                            text : '데이터를 불러오는데 에러가 발생했습니다.'
                        })
                        return;
                    }
                })
                .catch((err) => {
                    console.log(err)
                })
            },
            /** 동영상 목록 조회 */
            getVideoList() { 
                this.$axiosSend('get', '/api/lect/getVideoList', {num: this.form.lectNum})
                .then((res) => {
                    if(this.$isNotEmptyArray(res.data)){
                        this.videos = res.data;
                    } else {
                        this.$swal({
                            title : 'ERROR!',
                            icon : 'error',
                            text : '데이터를 불러오는데 에러가 발생했습니다.'
                        })
                        return;
                    }
                })
                .catch((err) => {
                    console.log(err)
                });
            },
            /** 후기 최신순 5개 조회 */
            getReview(){
                this.$axiosSend('get','/api/lect/getReview',{num : this.form.lectNum})
                .then((res)=>{
                    if(this.$isNotEmptyArray(res.data.item)){
                        this.reviews = res.data.item;
                    } else{
                        this.$swal({
                            title : 'ERROR!',
                            icon : 'error',
                            text : '데이터를 불러오는데 에러가 발생했습니다.'
                        })
                        return;  
                    }
                })
                .catch((err)=>{
                    console.log(err)
                })
            },
            /** 결제 페이지 이동 */
            toPayment(){ 
                const loginChk = localStorage.getItem('user_token');
                
                if(loginChk == undefined){
                    this.$swal({
                        text : '로그인 후 사용 가능합니다',
                        showCancelButton : true,
                        cancelButtonText : '돌아가기',
                        confirmButtonText : '로그인 페이지',
                    })
                    .then((result)=>{
                        if(result.isConfirmed){
                            this.$routerPush('login')
                        }
                    })
                    return;
                } else {
                    this.$axiosSend('post','/api/lect/addToCart',{
                        lectNum : this.form.lectNum,
                        userNum : this.$store.getters.getUsernum,
                    })
                    .then(()=>{
                        this.$routerPush('lecturePayment')
                    }
                    )
                    .catch((err)=>{
                        console.log(err);
                    })
                }
            },
            /** 장바구니로 이동만 ( 서버통신 x ) */
            toPaymentOnly(){
                this.$routerPush('lecturePayment')
            },
            /** 멤버쉽 안내 페이지 이동 */
            toMembership(){
                this.$routerPush('/membership')
            },
            /** 장바구니에 담기만 ( 화면 전환 x ) */
            addToCart(){
                const loginChk = localStorage.getItem('user_token');
                
                if(loginChk == undefined){
                    this.$swal({
                        title: '경고',
                        icon: 'warning',
                        text : '로그인 후 사용해주세요'
                    })
                    return;
                } else {
                    this.$axiosSend('post','/api/lect/addToCart',{
                        lectNum : this.form.lectNum,
                        userNum : this.$store.getters.getUsernum,
                    })
                    .catch((err)=>{
                        console.log(err);
                    })
                }
            },
            /** 나의 수강 목록 조회 */
            getMyPageList(){
                this.$axiosSend('get','/api/lect/getMyPageList',{
                    userNum : this.$store.getters.getUsernum,
                })
                .then((res)=>{
                    console.log(res)
                })
                .catch((err)=>{
                    console.log(err)
                })
            }
        },
        mounted() {
        },
        created() {
            this.getDetail();
            this.getVideoList();
            this.getReview();
        }
    }
</script>