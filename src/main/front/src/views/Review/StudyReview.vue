<!-- 작성자 원준용 -->


<template>
    <div class="review-list">
        <div class="review-group">
            <div class="colTab d-none d-xl-block">
                <ul class="mk-c-tab col8 tab-event ">
                    <li><router-link class="text-decoration-none" :class = "topCate == 'all' ? 'buttonColor':'' " :to ="{ path: 'review', query: { topCategory: 'all' }}">전체</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '1000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '1000' }}">기초강의</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '2000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '2000' }}">웹 개발</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '3000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '3000' }}">데이터분석</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '4000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '4000' }}">인공지능</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '5000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '5000' }}">프로그래밍 교양</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '6000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '6000' }}">컴퓨터공학 전공지식</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '7000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '7000' }}">개발도구</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '8000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '8000' }}">프로그래밍 언어</router-link></li>
                    <li><router-link class="text-decoration-none" :class = "topCate == '9000' ? 'buttonColor':'' " :to ="{ name: 'review', query: { topCategory: '9000' }}">디자인</router-link></li>
                </ul>
            </div>
            <div class = "selectBox d-block d-xl-none">
                <select v-model="topCate" @change="selectTopCate">
                    <option
                    v-for="(item, index) in selectList"
                    :key="index"
                    :value="item.value"
                    >{{ item.name }}</option>
                </select>
            </div>
            <hr>
            <h2 class="fw-bold mb-0 fs-2 text-start">수강후기</h2>
            <div class="review-search">
                <div id="review-sort">
                    <select id="sortOption" v-model="sortOption" @change="sortReviews">
                        <option value="default">최신 순</option>
                        <option value="highRating">평점 높은 순</option>
                        <option value="lowRating">평점 낮은 순</option>
                    </select>
                </div>  
                <div class="review-search-bar">
                    <div id="review-search-input">
                        <font-awesome-icon id="review-search-icon" :icon="['fas', 'magnifying-glass']" />
                        <input
                        type="text"
                        v-model="searchKeyword"
                        @keyup.enter="fetchSearchedReviews"
                        />
                    </div>  
                </div>
                
            </div>
        </div>
        <div id="scroll-container">
            <table class="review-table">
                <colgroup>
                    <col width="25%">
                    <col width="10%">
                    <col width="20%">
                    <col width="10%">
                    <col width="20%">
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col">강의</th>
                        <th scope="col">수강후기</th>
                        <th scope="col">별점</th>
                        <th scope="col">작성자</th>
                        <th scope="col">작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(reviews, index) in fetchedReviews" :key="index">
                        <td class="title">{{ reviews.cateKor }}</td>
                        <td>
                            <span class="review-content">{{ reviews.content }}</span>
                        </td>
                        <td>
                            <div class="review-star-rating">
                                <span class="review-star" v-for="rwGrade in reviews.rwGrade" :key="rwGrade"
                                >&#9733;</span
                                >
                            </div>
                        </td>
                        <td>{{ reviews.userName }}</td>
                        <td>{{ formatDateTime(reviews.rwDate) }}</td>
                    </tr>
                    <!-- 웹사이트 수강후기 리스트 종료 -->
                </tbody>
            </table>
            <!-- 무한스크롤 화면상태 코드 시작 -->
            <infinite-loading id="infiniteReview" @infinite="fetchMoreReviews" :force-use-infinite-wrapper="true">
                <template #no-more>마지막 후기 입니다.</template>
            </infinite-loading>
            <tr v-if="isLoading">
                <td colspan="5" class="loading-text">로딩 중...</td>
            </tr>
            <!-- 무한스크롤 화면상태 코드 종료 -->
        </div>
    </div>
</template>

<script>
    import InfiniteLoading from "infinite-loading-vue3-ts";
    import '@/assets/css/reviewStyle.css';

    export default {
        components: {
            InfiniteLoading,
        },
        data() {
            return {
                searchKeyword: "", // 검색어
                fetchedReviews: [], // 검색 전에 모든 후기를 저장하는 배열
                searchedReviews: [], // 검색 결과를 저장하는 배열
                sortOption: "default", // 정렬 옵션
                isLoading: false, // 로딩 중 여부
                currentPage: 1, // 현재 페이지 번호
                itemsPerPage: 20, // 한 페이지에 보여줄 아이템 수
                totalItems: 0, // 총 아이템 수
                topCate : '',
                selectList : [
                    {name : "전체", value: "all"},
                    {name : "기초강의", value: "1000"},
                    {name : "웹 개발", value: "2000"},
                    {name : "데이터분석", value: "3000"},
                    {name : "인공지능", value: "4000"},
                    {name : "프로그래밍 교양", value: "5000"},
                    {name : "컴퓨터공학 전공지식", value: "6000"},
                    {name : "개발도구", value: "7000"},
                    {name : "프로그래밍 언어", value: "8000"},
                    {name : "디자인", value: "9000"},
                    ],

            };
        },
        computed: {
            displayedReviews() {
                return this.searchKeyword ? this.fetchMoreReviews : this.fetchSearchedReviews;
            },
        },
        created() {
        
        },
        methods: {
            selectTopCate(){
                this.$routerPush('',{
                    topCategory: this.topCate
                },true)
            },
            // 후기 불러오는거
            fetchMoreReviews($state) {
                this.isFetching = true;
                // 데이터를 불러오는 비동기 작업 수행
                this.$axiosSend("get", "/api/reviews", {
                    page: this.currentPage,
                    size: this.itemsPerPage,
                })
                .then((response) => {
                    if (response.data.length) {
                        // 가져온 후기를 fetchedReviews 배열에 추가
                        this.fetchedReviews.push(...response.data);
                        this.currentPage++;
                        $state.loaded();
                    } else {
                        $state.complete(); 
                    }
                    this.isFetching = false;
                    // 검색 및 정렬 수행
                    this.fetchSearchedReviews(); // 검색 수행
                    this.sortReviews(); // 정렬 수행
                })
                .catch((error) => {
                    console.error(error);
                    this.isFetching = false;
                });
            },
            // 검색했을때 실행되는 메소드
            fetchSearchedReviews(){
                const param = {
                    keyword: this.searchKeyword, // 검색어를 파라미터로 전달합니다.
                    topCate : this.topCate, // 사용자 누르는 카테고리 코드값
                }
                this.$axiosSend("get", "/api/reviews/search", param)
                .then((response) => {
                    this.fetchedReviews = [];
                    this.fetchedReviews.push(...response.data);
                    this.sortReviews(); // 정렬 수행
                })
                .catch((error) => {
                    console.error(error);
                });
            },
            //별점순, 최신순 정렬
            sortReviews() {
                switch (this.sortOption) {
                    case "highRating":
                        // 별점 높은 순으로 후기 정렬
                        this.fetchedReviews = [...this.fetchedReviews].sort(
                            (a, b) => b.rwGrade - a.rwGrade
                        );
                    break;
                    case "lowRating":
                        // 별점 낮은 순으로 후기 정렬
                        this.fetchedReviews = [...this.fetchedReviews].sort(
                            (a, b) => a.rwGrade - b.rwGrade
                        );
                    break;
                    default:
                        // 최신 순으로 후기 정렬
                        this.fetchedReviews = [...this.fetchedReviews].sort((a, b) => {
                            const dateA = new Date(a.rwDate);
                            const dateB = new Date(b.rwDate);
                        return dateB - dateA;
                        });
                    break;
                }
            },
            // 작성일자 형식 메소드
            formatDateTime(value) {
                // value는 날짜 값입니다
                const now = new Date();
                const date = new Date(value);

                const diffInMilliseconds = now - date;
                const diffInSeconds = Math.floor(diffInMilliseconds / 1000);
                const diffInMinutes = Math.floor(diffInSeconds / 60);
                const diffInHours = Math.floor(diffInMinutes / 60);
                const diffInDays = Math.floor(diffInHours / 24);

                if (diffInDays > 0) {
                    return `${diffInDays}일 전`;
                } else if (diffInHours > 0) {
                    return `${diffInHours}시간 전`;
                } else if (diffInMinutes > 0) {
                    return `${diffInMinutes}분 전`;
                } else {
                    return '방금 전';
                }
            },
        },
        watch : {
            '$route.query.topCategory':{
                immediate : true,

                handler(newTopCategory){
                    if(newTopCategory == "" ){  
                        this.searchedReviews = [];
                        this.currentPage = 1;
                        this.fetchSearchedReviews();
                    } else {
                        this.topCate = newTopCategory;
                        this.fetchSearchedReviews(this.topCate);
                    }
                }
            }
        }
    };
</script>
