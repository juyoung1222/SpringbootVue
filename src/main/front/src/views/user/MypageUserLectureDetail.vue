<!-- 작성자 우지원 -->


<template>
    <div>
        <div class="p-4 p-md-5 w-100 d-flex">
            <!-- 좌측 네비 -->
            <my-page-cate-navi></my-page-cate-navi>

            <!-- 우측 컨테이너 -->
            <div class="w-100">
                <my-page-cate-navi-toggle></my-page-cate-navi-toggle>
                <p class="fs-4 fw-bold">
                    {{ userId }} 님의 현재 수강상세정보
                </p>
                <div class="mb-3">
                    <b-input-group class="w-auto ms-auto">
                        <b-form-input @keyup="keywordBinding" @keyup.enter="keywordBindingWithSearch"></b-form-input>
                        <template #append>
                            <b-button class="bedu-bg-custom-blue" @click="getLectureAllList">검색</b-button>
                        </template>
                    </b-input-group>
                </div>
                <div class="d-block d-md-flex w-100 mb-5">
                    <div class="me-auto d-flex mb-3">
                        <b-button 
                         pill
                         :to="{
                            query:{
                                group : 'learning',
                                order : this.orderSelected,
                                keyword : this.keyword
                            }
                         }"
                        >
                            학습중
                        </b-button>
                        <b-button class="mx-2" 
                         pill
                         :to="{
                            query:{
                                group : 'done',
                                order : this.orderSelected,
                                keyword : this.keyword
                            }
                         }"
                        >
                            완강
                        </b-button>
                        <b-button 
                        pill
                        :to="{
                            query:{
                                group : 'all',
                                order : this.orderSelected,
                                keyword : this.keyword
                            }
                         }"
                        >
                            전체
                        </b-button>
                    </div>
                    <div>
                        <b-form-select :options="orderOptions" v-model="orderSelected" @input="orderChange"></b-form-select>
                    </div>
                </div>
                <div>
                    <b-row cols="1" cols-md="3">
                        <b-col v-for="(item, index) in listArray" :key="index" class="mb-3">
                            <b-container class="border rounded-3 p-3 h-100">
                                <b-link class="text-body text-decoration-none"
                                :to="{
                                    name : 'lectureDetail',
                                    query :{
                                        num : item.lectNum
                                    }
                                }"
                                >
                                    <div class="ratio ratio-16x9 mb-3">
                                        <b-img :src="item.thumbnail"></b-img>
                                    </div>
                                    <div class="mb-3">
                                        {{ item.title }}
                                    </div>
                                    <hr>
                                    <div class="my-3">
                                        {{ item.lectSum }}
                                    </div>
                                    <div>
                                        <span>수강기간 : </span>
                                        {{ item.lectPeriod }}
                                        <span>일</span>
                                    </div>
                                </b-link>
                            </b-container>
                        </b-col>
                    </b-row>
                </div>
                <div>
                    <b-pagination v-model="currentPage" :total-rows="lectureCount" :per-page="numOfPage"></b-pagination>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import MyPageCateNavi from '../../components/myPage/MyPageCateNavi.vue';
import MyPageCateNaviToggle from '../../components/myPage/MyPageCateNaviToggle.vue';
export default {
  components: { MyPageCateNavi, MyPageCateNaviToggle },
    name : "mypageAll",
    data() {
       return {
           item : {
                title : '',
                lectDesc : '',
                lectPeriod : ''
           },
           userNum : this.$store.getters.getUsernum,
           userId : this.$store.getters.getNickname,
           currentPage : 1,
           listArray : [], //수강내역 전체 데이터
           numOfPage : 9,
           lectureInfo : {}, //화면에 노출되는 수강내역 데이터
           lectureCount : 0,  //수강내역 전체보기 출력
           keyword : '',
           orderOptions : [
            {
                text : '제목순',
                value : 'title'
            },{
                text : '최근 결제순',
                value : 'recentlyPay'
            }, {
                text : '최근 수강순',
                value : 'recentlyView'
            }],
           orderSelected : 'title',
           groupSelected : 'all',

        }
    }, 
    mounted(){
        
    },
    methods : {
        // 정렬순서 변경 감지
        orderChange(){
            this.$routerPush('',{
                group : this.groupSelected,
                order : this.orderSelected,
                keyword : this.keyword
            },true);
        },
        // 검색어 입력 바인딩 문제 해결 후 검색
        keywordBindingWithSearch(e){
            this.$routerPush('',{
                group : this.groupSelected,
                order : this.orderSelected,
                page : this.currentPage,
                keyword : e.target.value
            },true)
        },

        // 검색어 입력 바인딩 문제 해결
        keywordBinding(e){
            this.keyword = e.target.value
        },
        /* 마이페이지 홈(유저아이디 가져오기, 데이터 출력) */
        getLectureAllList(){
            if(this.$route.name != 'mypageAll'){
                return;
            }
            const userName = this.$store.getters.getNickname;
            const page = this.currentPage;
            const group = this.groupSelected;
            const order = this.orderSelected;
            const keyword = this.keyword;

            this.$axiosSend('get','/api/mypageAll',{
                userName: userName,
                page : page,
                order : order,
                group : group,
                keyword : keyword
            })
            .then((res)=>{
                this.lectureCount = res.data.count
                this.listArray = res.data.list
            })
            .catch((err)=>{
                console.log(err)
            })
            
        },
    },
    watch:{
        '$route.query':{
            immediate : true,
            handler(val){
                this.groupSelected = val.group;
                this.orderSelected = val.order;
                // this.currentPage = val.page;
                this.keyword = val.keyword;
                this.getLectureAllList();
            }
        },
        currentPage(){
            this.getLectureAllList();
        },
    }
}

</script>