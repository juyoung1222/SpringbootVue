//작성자 변의준


<template>
    <div class= "d-flex">
        <div class="qnaView d-none d-xxl-block" id="qnaView">
            <CommCategory :currentTab="'qna'"></CommCategory>
        </div> 
        <div id="qnaMain">
            <div class="qnamain-search-cont w-100">
                <!-- 모바일 화면 -->
                <b-navbar v-if="qsSearch" class="d-flex p-0">
                    <div class="text-center align-item-center">
                     <font-awesome-icon
                        icon="fa-solid fa-chevron-right"
                        class="fs-3 d-flex"
                        @click="searchToggleFunc"
                        role="button"/>
                  </div>
                  <div class="w-100 h-100">
                     <div class="w-100 h-100 rounded-5 p-2 ">
                        <div @submit="qnasearch()" id="qnaSearch">
                           <font-awesome-icon class="fs-3 d-flex" style="position: absolute; padding-left: 2%; margin-top: 6px;" id="free-search-icon ms-3 fs-3" :icon="['fas', 'magnifying-glass']" />
                           <input class="qnaviewkeyword" @keyup.enter="qnasearch()" ref="keyword" style="padding-left: 11%; padding-right: 2%;" v-model="form.keyword">       
                        </div>
                     </div>
                  </div>
                </b-navbar>
                <!-- pc화면 -->
                <b-navbar v-else toggleable="xxl">
                    <h2>질문 & 답변</h2>
                    <div class="qnaBoradSearch" id="qnaBoradSearch">
                    <div @submit="qnasearch()" class = "searchForm ">
                        <font-awesome-icon id="qna-search-icon" :icon="['fas', 'magnifying-glass']"
                         role="button" />
                        <input class="qnaviewkeyword" v-model="form.keyword" ref="keyword" @keyup.enter="qnasearch()"/>
                        <b-button :to="'/comm/qnaWrite'" class="bedu-bg-custom-blue qna-writepath-btn" id="qna-writepath-btn">
                            <font-awesome-icon :icon="['fas', 'pencil']" />
                            글쓰기
                        </b-button>
                    </div>
                </div>
                </b-navbar>
                <div class = "selectBox">
                    <select id="qnaSortOption" v-model="sortOption" @change="sortReviews">
                        <option value="default">최신 순</option>
                        <option value="highViews">조회수 순</option>
                        <option value="likeview">좋아요 순</option>
                    </select>
                </div>
                
            </div>
            <div class="w-100">
                <table class="w3-table-all" id="qnaboard-table">
                    <thead>
                        <tr>
                            <th id = "qnaTitle">제목</th>
                            <th>작성자</th>
                            <th>작성일자</th>
                            <th>조회 수</th>
                            <th>좋아요</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="qna in qnalist" :key="qna">
                            <td id="qnaboard-table-tds">
                                <b-link class="text-start text-body" :to="'/comm/qnaDetail/' + qna.qnaBdNum">
                                    {{ qna.title }}
                                </b-link>
                            </td>
                            <td>{{ qna.userName }}</td>
                            <td>{{ qnaDateTime(qna.qnaDate) }}</td>
                            <td>
                                <font-awesome-icon :icon="['fas', 'eye']" /> {{ qna.qnaCnt }}
                            </td>
                            <td>
                                <font-awesome-icon :icon="['fas', 'heart']" /> {{ qna.qnaLikeCnt }}
                            </td>  
                        </tr>
                    </tbody>
                </table>
                <InfiniteLoading @infinite="infiniteHandler" @distance="1">
                    <template #spinner> <!-- 로딩중일때 보여질 부분 -->
                    </template>
                    <template #no-more> <!-- 처리 완료 후, 최하단에 보여질 부분-->
                    <span></span>
                    </template>
                    <template #no-results> <!-- 처리 실패 후, 보여질 부분 -->
                    </template>
                </InfiniteLoading>
            </div>
                
        </div>
    </div>
</template>

<script>
    import CommCategory from '@/components/CommCategory.vue';
    import { InfiniteLoading } from 'infinite-loading-vue3-ts';
    
    import '@/assets/css/qnaStyle.css';
    export default {

        components:{
            InfiniteLoading,
            CommCategory
        },

        data() {
            return {
                qnalist: [],
                form: {
                    keyword: '',
                },
                sortOption: "default", // 정렬 옵션
               
                currentPage: 1,

                qsSearch: false
            };

        },

        methods: {

            //정렬 옵션
            List() {
                
                this.$axiosSend('get','/api/qna/qnaList', {
                    page: this.currentPage,
                })
                .then(res => {
                    this.qnalist = res.data;
                    this.sortReviews();
                })
                .catch(error => {
                    console.log(error);
                });
            },
        

            //정렬 옵션
            sortReviews() {
                if (this.sortOption === "default") {
                    // 최신 순으로 정렬
                    
                    this.qnalist.sort((a, b) => {
                    return new Date(b.qnaDate) - new Date(a.qnaDate);
                });
                } else if (this.sortOption === "highViews") {
                    // 조회수 순으로 정렬
                    this.qnalist.sort((a, b) => {
                        return b.qnaCnt - a.qnaCnt;
                    });

                }else if(this.sortOption === "likeview"){
                    this.qnalist.sort((a, b) => {
                        return b.qnaLikeCnt- a.qnaLikeCnt;
                    });
                }
            },

            //날짜 변환
            qnaDateTime(dates) {

                // value는 날짜 값입니다
                const now = new Date();
                const date = new Date(dates);

                const diffInMilliseconds = now - date;
                const diffInSeconds = Math.floor(diffInMilliseconds / 1000);
                const diffInMinutes = Math.floor(diffInSeconds / 60);
                const diffInHours = Math.floor(diffInMinutes / 60);
                const Days = Math.floor(diffInHours / 24);

                if (Days > 0) {
                    return `${Days}일 전`;
                } else if (diffInHours > 0) {
                   
                    return `${diffInHours}시간 전`;
                } else if (diffInMinutes > 0) {
                    return `${diffInMinutes}분 전`;
                } else {
                    return '방금 전';
                }
            },

            searchToggleFunc(){
                if(this.qsSearch){
                    this.qsSearch = false;
                } else{
                    this.qsSearch = true;
                }
            },

            //게시글 검색
            qnasearch() {
                console.log(this.form.keyword);
                if(this.form.keyword === null || this.form.keyword ===''){
                    alert('검색어를 입력해주세요!');
                    return;
                }

                this.$axiosSend('post','/api/qna/qnaList', this.form)
                .then(res => {
                    
                    this.qnalist = res.data;
                    // this.sortReviews(); // 정렬 수행
                })
                .catch(error => {
                    alert(error);
                });
            },     

            //게시글 조회 이벤트 헨들러
            infiniteHandler($state){
                
                this.$axiosSend('get','/api/qna/qnaList',{
                    page : this.currentPage,
                })
                .then(res=>{    
                    if(res.data.length){
                        this.qnalist.push(...res.data);
                        this.currentPage++;
                        $state.loaded();
                        
                    } else{
                        $state.complete();
                    }
                })
                .catch(err=>{
                    console.log(err);
                })

               
            }
        },
    };
</script>