<template>
    <div class="py-5 lectureCourse">
        <b-container class="">
            <div class="mb-5">
                <text class="fs-5 text-secondary">
                    {{ korCategory }}
                </text>
            </div>
            <b-row class="row-cols-auto">
                <b-col
                    v-for="(item, index) in lectures"
                    :key="index"
                    class="mb-3 col-lg-3 col-md-4 col-sm-9">
                    <b-link
                        class="text-decoration-none text-body"
                        :to='"/lectureDetail?num="+item.num'
                        :id='"link-"+index'>
                        <div class="border-1 border-opacity-10">
                            <img id="lect-course-img" :src="item.thumbnail">
                            <b-container class="p-4 border">
                                <div>
                                    <p class="fw-bold">{{ item.title }}</p>
                                </div>
                                <p class="fw-bold">
                                    <span class="teacher-name">{{ item.teacher }}
                                        선생님</span>
                                    <span>
                                        총
                                        {{ item.total }}강</span>
                                </p>
                                <p class="text-secondary fs-6">수강기간:{{ item.duration }}일</p>
                                <div class="text-center">
                                    <b-button class="w-100" variant="outline-dark">수강신청</b-button>
                                </div>
                            </b-container>
                        </div>
                    </b-link>
                </b-col>
                    <infinite-loading
                        @infinite="infiniteHandler"
                        ref="infiniteLoading"
                        :distance="10">
                        <template #spinner>
                            <!-- 로딩중일때 보여질 부분 -->
                        </template>
                        <template #no-more>
                            <!-- 처리 완료 후, 최하단에 보여질 부분-->
                            <span></span>
                        </template>
                        <template #no-results>
                            <!-- 처리 실패 후, 보여질 부분 -->
                        </template>

                    </infinite-loading>
            </b-row>
        </b-container>
    </div>
</template>


<script>
    import InfiniteLoading from 'infinite-loading-vue3-ts';

    export default{
        components: {
            InfiniteLoading,
        },
        name : 'LectureCourse',
        data() {
            return {
                category : this.$route.params.category,
                korCategory : this.category == 'base' ? '기초 강의':'데이터 분석',
                lectures : [],
                page : 1,
                likes :[],
            }
        },
        methods: {
            search(){ // 네비에서 버튼 눌러서 왔을때 동작
                this.$axiosSend('get','/api/lectureList',{
                    params:{
                        category : this.category,
                        page : 1,
                    }
                })
                .then((res)=>{
                    console.log(res)
                    this.lectures = res.data.item;
                    this.page++;
                })
                .catch((err)=>{console.log(err)})
            },
            infiniteHandler($state){ // 스크롤 이벤트 핸들러
                this.$axiosSend('get','/api/lectureList',{
                    params:{
                        category : this.category,
                        page : this.page,
                    }
                })
                .then((res)=>{
                    if(res.data.item.length){
                        this.page++;
                        this.lectures.push(...res.data.item);
                        $state.loaded();
                    } else{
                        $state.complete();
                    }
                })
                .catch((err)=>{console.log(err)})
            }
        },
        mounted() {
            this.$axiosSend('get','/api/getLikeList',{
                params:{
                    userId : '123'
                }
            })
            .then((res)=>{console.log(res)})
            .catch((err)=>{console.log(err)});
        },
        created() {
            
        },
        watch:{ // 쿼리 데이터 변경되면 데이터도 변경되도록 설정
            '$route.query.category':{
                immediate: true,
                handler(newCategory){
                    this.category = newCategory;
                    this.lectures = [];
                    this.page = 1;
                    try{
                        this.$refs.infiniteLoading.stateChanger.reset();
                    }catch(error){
                        console.log(error)
                    }
                },
            },
            '$route.query.korCategory':{
                immediate: true,
                handler(newKorCategory){
                    this.korCategory = newKorCategory;
                }
            },
        }
    }
</script>

<style scoped>
    #lect-course-img{
        width: 100%;
        height: 150px;
    }
    .teacher-name::after{
        content: '';
        width: 1px;
        background-color: black;
        margin: 0rem 0.5rem;
        background: black;
        height: 14px;
        display: inline-block;
        vertical-align: middle;
    }

</style>