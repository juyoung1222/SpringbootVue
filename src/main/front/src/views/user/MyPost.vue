<!-- 작성자 우지원 -->


<template>
    <div>
        <div class="p-4 p-md-5 w-100 d-flex">
            <!-- 좌측 네비 -->
            <my-page-cate-navi></my-page-cate-navi>

            <div class="w-100">
                <my-page-cate-navi-toggle></my-page-cate-navi-toggle>

                <!-- 네비 -->
                <div>
                    <b-nav>
                        <b-nav-item @click="currentTab = 'qna'">질문&답변</b-nav-item>
                        <b-nav-item @click="currentTab = 'free'">자유게시판</b-nav-item>
                    </b-nav>
                </div>

                <!-- 리스트 목록 -->
                <div class="w-100">
                    <my-post-qna v-if="currentTab == 'qna'"></my-post-qna>
                    <my-post-free v-else></my-post-free>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import MyPageCateNavi from '../../components/myPage/MyPageCateNavi.vue'
import MyPageCateNaviToggle from '../../components/myPage/MyPageCateNaviToggle.vue'
import MyPostQna from '../../components/myPage/myPost/MyPostQna.vue'
import MyPostFree from '../../components/myPage/myPost/MyPostFree.vue'

export default{
  components: { MyPageCateNavi, MyPageCateNaviToggle, MyPostQna, MyPostFree },
    name : 'myPost',
    data() {
        return {
            currentTab : 'qna',
            orderSelected : 'title',
            orderOptions : [
                {
                    text : '제목순',
                    value: 'title'
                },
                {
                    text : '최신순',
                    value : 'newst'
                },
                {
                    text : '오래된순',
                    value : 'oldest'
                },
            ],
        }
    },
    methods: {

    },
    watch:{
    },
}

</script>