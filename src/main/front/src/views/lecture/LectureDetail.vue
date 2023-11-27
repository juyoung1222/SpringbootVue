<!-- 작성자 우지원 -->


<template>
    <div>
        <b-container class="py-5">
            <!-- 카테고리 -->
            <div>
                {{ subInfo }}
                <p class="text-secondary fs-5 ms-3">{{ form.korCategory }}</p>
            </div>
            <!-- 썸네일, 강의정보 컨테이너-->
            <b-row class="mb-4" cols="1" cols-md="2">
                <!-- 썸네일 컨테이너 -->
                <b-col class="mb-3 mb-md-0">
                    <div class="ratio ratio-16x9">
                        <b-img class="w-100" thumbnail rounded="5" :src="form.thumbnail" fluid="fluid"></b-img>
                    </div>
                </b-col>
                <!-- 강의 정보 컨테이너 시작 -->
                <b-col class="">
                    <p class="fw-bold fs-3 d-flex">
                        {{ form.title }}
                        <span class="ms-auto me-5" role="button" @click="bookmarkFunc" v-if="this.$store.getters.getNickname != undefined"
                        :class="bookmark ? 'bedu-text-custom-yellow':''">
                            <font-awesome-icon :icon="bookmark ? ['fas', 'bookmark'] : ['far', 'bookmark']"/>
                        </span>
                    </p>
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
                            <span class="text-secondary col-4 col-md-2">강사</span>
                            <span class="text-body col">{{ form.teacher }}</span>
                        </div>
                        <div class="mb-2 row">
                            <span class="text-secondary col-4 col-md-2">수강기간</span>
                            <span class="text-body col">
                                구매일로부터
                                {{ form.lectPeriod }}일</span>
                        </div>
                        <div class="mb-2 row">
                            <div class="text-secondary col-4 col-md-2">학습시간</div>
                            <div class="text-body col">
                                <span class="me-1">
                                    {{ form.totalTimes }}
                                </span>
                                <span>
                                    ({{ form.total }}강)
                                </span>
                            </div>
                        </div>
                    </div>

                </b-col>
                <!-- 강의 정보 컨테이너 종료 -->
            </b-row>

            <!-- 결제, 장바구니 컨테이너 -->
            <div class="bg-secondary bg-opacity-10 p-3 py-5 mb-5 d-none d-md-block">
                
                <b-row class="align-items-center" cols="1" cols-md="2">
                    <b-col class="align-items-center">
                        <div class="text-truncate">
                            <span class="fw-bold text-secondary mx-3 fs-6">강좌</span>
                            <span class="mx-auto fs-5">{{ form.title }}</span>
                        </div>
                    </b-col>
                    <b-col>
                        <div class="d-flex align-items-center justify-content-end">
                            <div class="fw-bold me-4">
                                <span class="text-danger fs-2">{{ form.price }}</span>
                                <span>원</span>
                            </div>
                            <div v-if="myPageList" class="w-10">
                                <b-button class="mt-auto h-100 px-5 py-3 bedu-bg-custom-blue">
                                    수강중인 강의
                                </b-button>
                            </div>
                            <div v-else class="w-10">
                                <b-button class="d-block mb-1 w-auto px-5 py-2 bedu-bg-custom-blue" @click="toPayment">
                                    결제하기
                                </b-button>
                                <b-button class="px-5" @click="addToCart">
                                    장바구니
                                </b-button>
                        </div>
                        </div>
                    </b-col>
                </b-row>
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
                            <b-link class="d-flex text-body text-decoration-none" 
                            :to="{
                                name : 'lectureLesson',
                                query : {
                                    lectDtlNum : video.lectDtlNum,
                                    lectNum : this.$route.query.num
                                }
                            }">
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
                        <b-row>
                            <b-col class="col-12 col-md-3">
                                <p class="fs-2 fw-bold me-auto" id="lecture-review-container">
                                    수강후기
                                </p>
                                <b-link class="text-body text-decoration-none"
                                :to="{
                                    name : 'reviewWrite',
                                    query : {
                                        num : this.form.lectNum
                                    }
                                }">
                                    후기쓰기 <font-awesome-icon :icon="['fas', 'pencil']" />
                                </b-link>
                            </b-col>
                            <b-col>
                                <div class="justify-content-end d-block d-md-flex">
                                    <div class="mb-3 mb-md-0">
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
                            </b-col>
                        </b-row>
                        <div>
                            <ul class="p-0">
                                <li 
                                    class="list-unstyled mb-5"
                                    v-for="(review, index) in reviews" :key="index">
                                    <div>
                                        <div class="d-flex mb-1">
                                            <div class="me-auto">{{ review.writer }}</div>
                                            <div class="ms-auto">
                                                <span v-for="(star,index) in 5" :key="index">
                                                    <font-awesome-icon
                                                        :class="review.rwGrade < star ? 'text-secondary' : 'text-danger'"
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

                    <!-- 모바일 화면 하단 장바구니 -->
                    <div v-if="!myPageList" class="d-md-none position-fixed d-flex shadow-lg w-100 start-0 p-3 bg-white bottom-0">
                        <div class="align-self-center">
                            <span>
                                {{ form.price }}
                            </span>
                            원
                        </div>
                        <div class="ms-auto gap-2 d-flex">
                            <b-button class="bedu-bg-custom-blue" @click="toPayment">결제하기</b-button>
                            <b-button @click="addToCart">장바구니</b-button>
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
                myPageList : false, // 수강목록에 있는지 여부 체크
                userNum : this.$store.getters.getUsernum,
                bookmark : 0,
            }
        },
        methods: {
            bookmarkFunc(){
                this.$axiosSend('get','/api/bookmark/inOut',{
                    lectNum : this.form.lectNum,
                    userName : this.$store.getters.getNickname
                })
                .then((res)=>{
                    console.log(res.data)
                    
                    if(res.data == 2){
                        this.bookmark = 0;
                    } else {
                        this.bookmark = 1
                    }
                })
                .catch((err)=>{
                    console.log(err)
                })
            },
            getBookmark(){
                this.$axiosSend('get','/api/bookmark/getBookmark',{
                    lectNum : this.form.lectNum,
                    userName : this.$store.getters.getNickname
                })
                .then((res)=>{
                    if(res.data > 0){
                        this.bookmark = 1;
                    } else {
                        this.bookmark = 0;
                    }
                })
                .catch((err)=>{
                    console.log(err)
                })
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
                    console.log(res.data.item)
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

                // 장바구니 담는 익명 함수
                const payment = () => {
                    this.$axiosSend('post','/api/lect/addToCart',{
                        lectNum : this.form.lectNum,
                        userNum : this.$store.getters.getUsernum,
                        lectName : this.form.title,
                    })
                    .then(()=>{
                        this.$routerPush('lecturePayment')
                    }
                    )
                    .catch((err)=>{
                        console.log(err);
                    })
                }
                            
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
                } else {
                    const subInfo = this.$store.getters.getSubInfo;
                    if(subInfo == null || subInfo == null || subInfo == ''){
                        this.$swal({
                            title :'멤버쉽 안내',
                            icon : 'info',
                            html : '<p>멤버쉽 가입시 최대 31% 할인된 가격에</p><p>모든 강의를 이용할 수 있습니다</p><p>멤버쉽에 대해 알아보시겠습니까?</p>',
                            showDenyButton : true,
                            confirmButtonText : '멤버쉽 알아보기',
                            denyButtonText : '결제하기',
                            customClass : {
                                denyButton : 'bg-secondary'
                            }
                        })
                        .then((result)=>{
                            if(result.isDenied){
                                payment();
                            }
                            else if(result.isConfirmed){
                                this.$routerPush('membership')
                            }
                        })
                    } else {
                        payment();
                    }

                }
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
                } else {
                    this.$axiosSend('post','/api/lect/addToCart',{
                        lectNum : this.form.lectNum,
                        userNum : this.$store.getters.getUsernum,
                        lectName : this.form.title,
                    })
                    .then(()=>{
                        this.$swal({
                            icon : 'success',
                            text : '강의가 장바구니에 담겼습니다. 이동하시겠습니까?',
                            showCancelButton : true,
                            cancelButtonText : '돌아보기',
                            confirmButtonText : '장바구니',
                        })
                        .then((result)=>{
                            if(result.isConfirmed){
                                this.$routerPush('lecturePayment')
                            }
                        })
                    })
                    .catch((err)=>{
                        console.log(err);
                    })
                }
            },
            /** 나의 수강 목록 조회 */
            myPageListCheck(){
                if(this.$store.getters.getNickname == null){
                    return;
                }

                this.$axiosSend('get','/api/lect/getMyPagetChk',{
                            userName : this.$store.getters.getNickname,
                            lectNum : this.form.lectNum
                })
                .then((res)=>{
                    this.myPageList = res.data
                })
                .catch((err)=>{
                    console.log(err)
                })
            },
        },
        mounted() {
        },
        created() {
            this.myPageListCheck();
            this.getDetail();
            this.getVideoList();
            this.getReview();
            this.getBookmark();
        }
    }
</script>