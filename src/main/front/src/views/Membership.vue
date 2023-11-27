<!-- 작성자 우지원 -->


<template>
    <div>
        <b-container class="h-100 py-5">
            <b-row class="row pt-5" cols="1" cols-lg="2">
                <b-col class="mb-3 mb-lg-0">
                    <b-container class="border-4 border-dark border rounded-5 p-0 overflow-hidden"
                    @mouseenter="mouseover('month')" @mouseleave="mouseleave('month')" >
                        <div class="h-25 bedu-bg-custom-yellow-75 p-5" ref="month">
                            <p class="text-start fs-3 fw-bold">월간 구독</p>
                            <p>B:EDU의 강의를 무제한으로 수강</p>
                        </div>
                        <div class="p-5">
                            <p><span class="fs-3 fw-bold">39,800원</span>/월</p>
                            <b-button class="w-100 p-3 bedu-bg-custom-blue mb-3" @click="getSubscribe('month')">구독하기</b-button>
                            <p>+ 모든 영상/노트/실습 강의 무제한 수강</p>
                            <p>+ 강의/로드맵 추천</p>   
                            <p>+ Q&A 커뮤니티 이용</p>
                            <p>+ 매달 신규 강의 오픈</p>
                            <p>+ 웹 코드 실행기 무제한 이용</p>
                            <p>+ 개인별 맞춤 수강 독려</p>
                            <p>+ 코딩 챌린지 참여</p>
                        </div>
                    </b-container>
                </b-col>
                <b-col>
                    <b-container class="border-4 border-dark border rounded-5 p-0 overflow-hidden"
                    @mouseover="mouseover('year')" @mouseleave="mouseleave('year')" >
                        <div class="h-25 bedu-bg-custom-yellow-75 p-5" ref="year">
                            <p class="text-start fs-3 fw-bold">연간 구독</p>
                            <p>B:EDU의 강의를 무제한으로 수강</p>
                        </div>
                        <div class="p-5 pt-4">
                            <span class="fs-6 fw-bold bedu-text-custom-yellow">월간 결제보다 31% 할인</span>
                            <p><span class="fs-3 fw-bold">27,417원</span>/월</p>
                            <b-button class="w-100 p-3 bedu-bg-custom-blue mb-3" @click="getSubscribe('year')">구독하기</b-button>
                            <p>+ 모든 영상/노트/실습 강의 무제한 수강</p>
                            <p>+ 강의/로드맵 추천</p>
                            <p>+ Q&A 커뮤니티 이용</p>
                            <p>+ 매달 신규 강의 오픈</p>
                            <p>+ 웹 코드 실행기 무제한 이용</p>
                            <p>+ 개인별 맞춤 수강 독려</p>
                            <p>+ 코딩 챌린지 참여</p>
                        </div>
                    </b-container>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import axios from 'axios'

var imp = window.IMP;
imp.init('imp03767037')



export default{
    name : 'membership',
    data() {
        return {
            token : {},
        }
    },
    methods: {
        mouseover(type){
            let tar = this.$refs[type]
            tar.classList.replace('bedu-bg-custom-yellow-75','bedu-bg-custom-yellow')
        },
        mouseleave(type){
            let tar = this.$refs[type]
            tar.classList.replace('bedu-bg-custom-yellow','bedu-bg-custom-yellow-75')
        },
        getSubscribe(type){

             const currentSubInfo = this.$store.getters.getSubscribe;
             const userName = this.$store.getters.getNickname;

             if(userName == null || userName == ''){
                 this.$swal({
                     title : '로그인 해주세요',
                     icon : 'info',
                     text : '로그인 하시겠습니까?',
                     showCancelButton : true,
                     confirmButtonText : '로그인',
                     cancelButtonText : '돌아가기',
                 })
                 .then((result)=>{
                     if(result.isConfirmed){
                         this.$routerPush('/login')
                     }
                 })
                 return;
             }

            // 기존 멤버쉽 구독자 체크
             if(currentSubInfo != null){
                 this.$swal({
                     title: '구독자님 감사합니다!',
                     icon: 'question',
                     html : '감사하신 고객님!<br>고객님께선 이미 B:EDU에 구독해주셨습니다'
                 })
                 return;
             }

             /*

             결제 API 호출 후 완료 되었다는 전제 하에 코드 작성
            
             */

            // 구독자 저장하는 익명 함수
             const addToSub = () =>{
                 this.$axiosSend('get','/api/membership/getSubscribe',{
                     nickname : this.$store.getters.getNickname,
                     type : type,
                 })
                 .then(()=>{
                     this.$axiosSend('get','/api/membership/getSubInfo',{
                         nickname : this.$store.getters.getNickname,
                     })
                     .then((res)=>{
                         this.$store.commit('SUBSCRIBE',res.data.item)
                     })
                     .catch((err)=>{
                         console.log(err)
                     })
                     this.$swal({
                         title : '감사합니다!',
                         icon : 'success',
                         text : 'B:EDU를 구독해주셔서 감사합니다'
                     })
                     .then(()=>{
                         this.$routerPush('/')
                     })
                 })
                 .catch((err)=>{
                     console.log(err)
                 })
             }
            

             this.$swal({
                 title : '구독하시겠습니까?',
                 icon : 'info',
                 text : 'B:EDU에 구독하세요!',
                 showCancelButton: true,
                 cancelButtonText : '돌아가기',
                 confirmButtonText : '결제하기',
             })
             .then((result)=>{
                 if(result.isConfirmed){
                     addToSub();
                 }
             })
            
        },
        requestPay(){

            imp.request_pay({
                pg: "html5_inicis",
                pay_method: "card",
                merchant_uid: "57008833-33004",
                name: "당근 10kg",
                amount: 100,
                buyer_email: "Iamport@chai.finance",
                buyer_name: "포트원 기술지원팀",
                buyer_tel: "010-1234-5678",
                buyer_addr: null,
                buyer_postcode: null,
            },
            function(rsp){
                console.log(rsp)
            }
            )
        },
        getCertification(){
            imp.certification({
                pg : 'inicis_unified',
                merchant_uid : '57008833-33004',
            },function(rsp){
                console.log(rsp)
            })
        },
        getToken(){
            this.$axiosSend('get','/api/membership/test',{
                imp_key : '0863237328336256',
                imp_secret : 'Gy2JNtCERVsYKhV87L8nyeXW0sr3fh7ujJYIQkVwaT8CEzrvZ6QukY5ZYYH6yDdHZn49Cl8ooSOoko9e'
            })
            .then((res)=>{
                console.log(res)
                this.token = res.data
            })
            .catch((err)=>{
                console.log(err)
            })
        }
    },
    
}


</script>