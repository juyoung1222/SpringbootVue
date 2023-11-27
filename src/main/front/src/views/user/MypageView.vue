<!-- 작성자 우지원 -->


<template>
    <div >
        <div class="p-4 p-md-5 w-100 d-flex">
            <!-- 좌측 네비-->
            <my-page-cate-navi></my-page-cate-navi>

            <div class="w-100">
                <my-page-cate-navi-toggle></my-page-cate-navi-toggle>
                <p class="fs-4 fw-bold">현재 수강정보</p>
                <b-container class="text-dark fw-bold">
                        <!-- v-if문 이용하여  수강내역이 없을때 수강내역이 없다는 문구 보이게-->
                        <div v-if="lectureListFirst == 0">
                            <b-container class="w-75 ms-auto py-5">
                                <p class="text-center">수강내역이 없습니다.</p>
                            </b-container>
                        </div>
                        <!--v-else를 이용하여 수강내역이 있을때이라면 현재 수강정보가 보이게-->
                        <div v-else>
                            <div class="text-end mb-3">
                                <b-link :to="{
                                    name : 'mypageAll',
                                    query : {
                                        group : 'all',
                                        order : 'title',
                                        keyword : '',
                                    }
                                }" class="text-end text-body text-decoration-none">전체보기</b-link>
                            </div>
                            <b-row cols="1" cols-md="3" class="mb-5">
                                <b-col v-for="(item, index) in lectureListFirst" :key="index" role="button" class="mb-3">
                                        <!--  링크걸어서 화면 이동 테스트중 -->
                                        <b-link class="text-decoration-none text-body"
                                        :to="{
                                            name : 'lectureDetail',
                                            query:{
                                                num : item.lectNum
                                            }
                                        }"
                                        >
                                            <b-container class="border rounded-3 py-3 text-start">
                                                <div class="ratio ratio-16x9 mb-3">
                                                    <b-img :src="item.thumbnail"></b-img>
                                                </div>
                                                <div> {{ item.title }} </div>
                                                <hr>
                                                <div class="my-3">{{ item.teacher }}</div>
                                                <div class="mb-3">
                                                    <span>수강기간 : </span> {{ item.lectPeriod }}
                                                    <span>일</span>
                                                </div>
                                            </b-container>
                                        </b-link>
                                </b-col>
                            </b-row>
                        </div>
                </b-container>
                <b-row cols="1" cols-md="2">
                    <b-col>
                        <p class="fs-4 fw-bold">최근 학습 강의</p>
                        <b-container v-if="!recentlyViewed" class="py-5">
                            <div class="py-5 text-center">
                                최근 학습한 강의가 없습니다
                            </div>
                        </b-container>
                        <b-container v-else class="mb-5 py-3 border rounded-3">
                            <b-link class="text-decoration-none text-body"
                            :to="{
                                name : 'lectureDetail',
                                query :{
                                    num : recentlyViewed.lectNum
                                }
                            }">
                                <div class="ratio ratio-16x9 mb-3">
                                    <b-img :src="recentlyViewed.thumbnail"></b-img>
                                </div>
                                <div>
                                    {{ recentlyViewed.title }}
                                </div>
                                <hr>
                                <div class="my-3">
                                    {{ recentlyViewed.lectSum }}
                                </div>
                                <div>
                                    <span>수강기간 : </span>
                                    {{ recentlyViewed.lectPeriod }}
                                    <span>일</span>
                                </div>
                            </b-link>
                        </b-container>
                    </b-col>
                    <b-col>
                        <p class="fw-bold fs-4">나의 멤버쉽 정보</p>
                        <div class="py-5 text-center h-100">
                            <div v-if="this.$store.getters.getSubInfo">
                                <div class="mb-5">
                                    <span>
                                        {{ this.$store.getters.getNickname }}
                                    </span>
                                    <span>
                                        님의 구독정보
                                    </span>
                                </div>
                                <div class="mb-3">
                                    <span v-if="subInfo.subType == 'year'">
                                        연간
                                    </span>
                                    <span v-else>
                                        월간
                                    </span>
                                    <span>
                                        구독
                                    </span>
                                </div>
                                <div class="mb-3">
                                    <span>
                                        멤버쉽 시작일 : 
                                    </span>
                                    <span>
                                        {{ formattedBeginDate }}
                                    </span>
                                </div>
                                <div>
                                    <span>
                                        멤버쉽 종료일 : 
                                    </span>
                                    <span>
                                        {{ formattedEndDate }}
                                    </span>
                                </div>
                            </div>
                            <div v-else>
                                구독 정보가 없습니다
                            </div>
                        </div>
                    </b-col>
                </b-row>
                <div>
                    <p class="fs-4 fw-bold">북마크</p>
                    <b-container class="w-100 ms-auto fw-bold">
                            <div v-if="!newestBookmark" class="curr-subjectInfo" >
                                <b-container class="w-75 ms-auto py-5">
                                        <p class="text-center">북마크가 없습니다.</p>
                                </b-container>
                            </div>
                            <div v-else class="w-100 py-5">
                                <b-row cols="1" cols-md="3">
                                    <b-col class="mb-3" v-for="(item, index) in newestBookmark" :key="index">
                                        <!-- {{ item }} -->
                                        <b-link class="text-decoration-none text-body"
                                        :to="{
                                            name : 'lectureDetail',
                                            query:{
                                                num : item.lectNum
                                            }
                                        }">
                                            <b-container class="border h-100 rounded-3 py-3 text-start">
                                                <div class="ratio ratio-16x9 mb-3">
                                                    <b-img :src="item.thumbnail"></b-img>
                                                </div>
                                                <div>
                                                    {{ item.title }}
                                                </div>
                                                <hr>
                                                <div>
                                                    {{ item.teacher }}
                                                </div>
                                                <div>
                                                    <span>
                                                        수강기간 : 
                                                    </span>
                                                    <span>
                                                        {{ item.lectPeriod }} 일
                                                    </span>
                                                </div>
                                            </b-container>
                                        </b-link>
                                    </b-col>
                                </b-row>
                            </div>
                    </b-container>
                </div>
            </div>
        </div>
    </div>
</template>

<style>
.mypagecontainer{
    border-radius : 15px;
    float:left;
    width : 20rem;
    padding : 4px;
    margin-right: 10px;
    text-align : left;
    border : 1px solid rgb(15, 10, 1);
}
.mypagebookmark{
    margin-top : 20rem;
    display: flex;
    flex-direction: column;
}
</style>
<script>
import MyPageCateNavi from '../../components/myPage/MyPageCateNavi.vue';
import MyPageCateNaviToggle from '../../components/myPage/MyPageCateNaviToggle.vue';

export default {
  components: { MyPageCateNavi, MyPageCateNaviToggle },
    name : "mypage",
    data() {
       return {
           // bookmarkList : [],
            title : '',
            lectDesc : '',
            lectPeriod : '',
            lectureListFirst : [], //수강내역 전체 데이터
            lectureInfo : {}, //화면에 노출되는 수강내역 데이터
            lectureCount : 0, //수강내역 전체보기 출력
            numOfLecture : 3, //처음에 출력할 수강내역 개수
            dataFull : false,
            userName : this.$store.getters.getNickname,
            userlectnum : 0,
            lectNum : 0,
            lectregdate : '',
            regdate : '',
            regid : '',
            lessonInfo : { //동영상 나오는 거 체크를 위해 숫자를 넣었음.
                lectDtlNum : 6,
            },
            recentlyViewed : {}, // 최근 학습 강의
            subInfo : {},
            newestBookmark : [], // 최신 북마크 3개

        }
    }, 
    created (){
        this.getLectureList();
        this.getRecentlyViewd();
        this.getSubInfo();
        this.getNewBookmark();
    },
    methods : {
        /* 마이페이지 홈(유저아이디 가져오기, 데이터 출력) */
        getLectureList(){
            let data = [];
            this.$axiosSend('get','/api/mypage',{userName: this.$store.getters.getNickname},true)
            .then((res)=>{
                for(var i = 0; i < this.numOfLecture; i++) {
                    data.push(res.data[i])
                }
                this.lectureListFirst = res.data
                this.lectureInfo = data
                this.lectureCount = this.lectureListFirst.length
                
            })
            .catch((err)=>{
                console.log(err)
            })
        },
         getLectureCount() {
            this.$axiosSend("get", "/api/mypageAll", {userName: this.$store.getters.getNickname}, true)
                    .then(res => {
                        if(this.numOfLecture < this.lectureCount) {
                            this.numOfLecture += 3; //수강정보 3개 증가

                        var data = []
                        for (var k = 0; k < this.lectureCount; k++){ //전체 수강정보에서 노출 개수만큼 데이터 추출하여 data배열에 추가
                                data.push(this.lectureListFirst[k].lectureInfo) //전체 수강정보에서 노출
                        }
                                this.lectureInfo = data //lectureInfo객체에 data 배열 업데이트
                        }else {
                                this.dataFull = true //dataFull 객체를 true로 변경
                        }

                        // 요청이 성공적으로 완료된 후 전체보기 수강정보 목록 페이지로 리디렉션
                        this.$routerPpush('/mypageAll',{userName: this.$store.getters.getNickname}, true)
                    })
                    .catch(error => {
                        // 요청 실패 시 에러 처리
                        console.error(error);
                    });
             
        },
        getRecentlyViewd(){
            this.$axiosSend('get','/api/getRecentlyViewd',{
                userName : this.$store.getters.getNickname
            })
            .then((res)=>{
                this.recentlyViewed = res.data;
            })
            .catch((err)=>{
                console.log(err)
            })
        },
        getSubInfo(){
            this.$axiosSend('get','/api/membership/getSubInfo',{
                nickname : this.$store.getters.getNickname
            })
            .then((res)=>{
                this.subInfo = res.data.item
            })
            .catch((err)=>{
                console.log(err)
            })
        },
        getNewBookmark(){
            this.$axiosSend('get','/api/bookmark/getNewest',{
                userName : this.$store.getters.getNickname
            })
            .then((res)=>{
                console.log(res)
                this.newestBookmark = res.data;
            })
            .catch((err)=>{
                console.log(err)
            })
        },
    },
    computed : {
        formattedEndDate(){
            const originDate = new Date(this.subInfo.end);
            const options = {year: 'numeric', month: 'long', day: 'numeric' };
            return originDate.toLocaleDateString('ko-KR', options);
        },
        formattedBeginDate(){
            const originDate = new Date(this.subInfo.begin);
            const options = {year: 'numeric', month: 'long', day: 'numeric' };
            return originDate.toLocaleDateString('ko-KR', options);
        }
    }
}
</script>
