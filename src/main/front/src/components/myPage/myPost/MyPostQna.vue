<template>
    <div>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">제목</th>
                    <th scope="col">작성일자</th>
                    <th scope="col">조회수</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in list" :key="index">
                    <td>{{ item.title }}</td>
                    <td>{{ qnaDateTime(item.qnaDate) }}</td>
                    <td>{{ item.qnaCnt }}</td>
                </tr>
                <InfiniteLoading
                @infinite="infiniteHandler"
                ref="InfiniteLoading"
                >
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
                        </b-container>
                    </template>
                </InfiniteLoading>
            </tbody>
        </table>
    </div>
</template>

<script>
import { InfiniteLoading } from 'infinite-loading-vue3-ts'

export default{
    components : {InfiniteLoading},
    name : 'myPostQnaComp',
    data() {
        return {
            list : [],
            page : 1,
        }
    },
    methods: {
        infiniteHandler($state){
            this.$axiosSend('get','/api/qna/nameList',{
                userName : this.$store.getters.getNickname,
                page : this.page
            })
            .then((res)=>{
                if(res.data.length){
                    this.page++;
                    this.list.push(...res.data);
                    $state.loaded();
                } else {
                    if(this.page == 1){
                        $state.reset();
                    }
                    $state.complete();
                }
            })
            .catch((err)=>{
                console.log(err)
            })
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
},    },

}

</script>