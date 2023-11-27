<!-- 작성자 우지원 -->


<template>
    <div>
        <b-container class="p-5">
            <b-form class="mx-auto" @submit="search()">
                <div
                    class="border border-3 rounded-pill p-2 d-flex align-middle text-center m-auto mb-5 lect-search-border-bedu mt-5">
                    <font-awesome-icon class="m-auto mx-3" :icon="['fas', 'magnifying-glass']"/>
                    <b-form-input class="border-0 me-2 lect-search-input" v-model="keyword"></b-form-input>
                </div>
            </b-form>
            <p class="fs-2 fw-bold">강좌 검색결과 ({{ total }})</p>
            <div
                class="lect-search-result-container p-3 border-opacity-25 border-secondary border mb-5">
                <ul class="list-unstyled">
                    <li v-for="(item, index) in lectures" :key="index">
                        <b-link
                            :to='"/lectureDetail?num="+item.lectNum'
                            class="text-body text-decoration-none">
                            <b-row class="d-block d-sm-flex">
                                <b-col class="p-3" cols="12" lg="4">
                                    <div class="ratio ratio-16x9">
                                        <b-img
                                            thumbnail="thumbnail"
                                            rounded="rounded"
                                            class="w-100 h-100"
                                            :src="item.thumbnail"></b-img>
                                    </div>
                                </b-col>
                                <b-col class="p-3" cols="12" lg="5">
                                    <p class="fs-5 fw-bold">{{ item.title }}</p>
                                    <span class="d-flex">
                                        <p class="lect-search-teacher-name">{{ item.teacher }}
                                            선생님</p>
                                        <p>총
                                            {{ item.total }}강</p>
                                    </span>
                                    <p class="text-secondary">수강기간 :
                                        {{ item.lectPeriod }}일</p>
                                </b-col>
                                <b-col class="my-auto ms-auto w-auto" cols="12" lg="2">
                                    <b-button class="px-4 py-2 rounded-5">수강신청</b-button>
                                </b-col>
                            </b-row>
                        </b-link>
                    </li>
                    <InfiniteLoading
                        @infinite="infiniteHandler"
                        ref="infiniteLoading">
                        <template #spinner>
                            <!-- 로딩중일때 보여질 부분 -->
                            <div class="text-center">
                                <div class="spinner-border" role="status">
                                    <span class="visually-hidden"></span>
                                </div>
                            </div>
                        </template>
                        <template #no-more>
                            <!-- 처리 완료 후, 최하단에 보여질 부분-->
                            <span></span>
                        </template>
                        <template #no-results>
                            <!-- 조회 데이터가 없을 때 보여질 부분 -->
                            <b-container class="text-center py-5">
                                <p class="fw-bold">검색 결과가 없습니다</p>
                                <p>단어의 철자가 정확한지 확인해 보세요.</p>
                            </b-container>
                        </template>
                    </InfiniteLoading>
                </ul>
            </div>
        </b-container>
    </div>
</template>


<script>
import { InfiniteLoading } from 'infinite-loading-vue3-ts';
import '@/assets/css/lectureStyle.css';

export default{
    name: "lectureSearch",
    data() {
        return {
            keyword: this.$route.query.keyword,
            total: 0,
            lectures: [],
            page : 1,
        };
    },
    methods: {
        /** 스크롤 이벤트 동작 메서드 */
        infiniteHandler($state){
            this.$axiosSend("get","/api/lect/lectureSearch", {
                    keyword: this.keyword,
                    page : this.page
            })
            .then((res) => {
                if(res.data.item.length){ // 조회 데이터의 길이가 0이 아닐때 동작
                    this.page++;
                    this.lectures.push(...res.data.item);
                    $state.loaded(); // 로드를 계속하도록 함
                } else {
                    if(this.page===1){
                        $state.reset()
                    }
                    $state.complete(); // 더이상 로드할 데이터가 없음을 명시. 스크롤 이벤트의 동작이 멈춤
                }
            })
            .catch((err) => { console.log(err); });
        },
        getTotal(){ // 20개씩이 아니라 키워드에 해당하는 전체 강의 갯수
            this.$axiosSend('get','/api/lect/searchTotal',{
                    keyword : this.keyword
            })
            .then((res)=>{
                this.total = res.data
            })
            .catch((err)=>{console.log(err)});
        },
        search(){ /** 키워드 바꿔서 검색하면 동작 */
            this.$routerPush(
                'lectureSearch',
                {
                    keyword : this.keyword
                },
                true
            )
        }
    },
    watch:{
        '$route.query.keyword':{
            immediate:true,
            handler(newKeyword){
                this.keyword = newKeyword;
                if(this.$refs.infiniteLoading){
                    this.lectures = [];
                    this.page = 1;
                    this.$refs.infiniteLoading.stateChanger.reset();
                    this.getTotal();
                }
            }
        }
    },
    created() {
    },
    mounted() {
        // this.keyword = this.$route.query.keyword,
        this.getTotal();
    },
    components: { InfiniteLoading }
}


</script>
