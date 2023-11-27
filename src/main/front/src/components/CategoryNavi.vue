<!-- 작성자 우지원 -->


<template>
    <div class="w-25 ps-5 me-5 d-none d-xxl-block">
        <!-- 네비 컨테이너 -->
        <p class="fs-2 fw-bold mb-3">분야별 강의</p>
        <ul class="nav flex-column w-100">
            <!-- 대분류 -->
            <li
                class="nav-item me-4"
                v-for="(top, top_index) in categories.filter((fild)=>fild.level == 1)"
                :key="top_index">
                <span>
                    <a
                        class="fs-5 text-body text-decoration-none d-flex"
                        data-bs-toggle="collapse"
                        :href='"#top-"+top.lectTopCate'
                        >
                        <p>{{ top.lectTopCateKor }}</p>
                        <p class="ms-auto">
                            <font-awesome-icon :icon="['fas','caret-down']"/>
                        </p>
                    </a>
                </span>
                <div
                    class="collapse text-secondary text-secondary mid-cate-container"
                    :id='"top-"+top.lectTopCate'
                    :class='cnt_top_cate == top.lectTopCate ? "show":""'
                    >
                    <ul class="ps-2">
                        <!-- 중분류 -->
                        <li
                            v-for="(mid, mid_index) in categories.filter((first)=>first.level == 2).filter((second)=>second.parentCode == top.lectTopCate)"
                            :key="mid_index"
                            class="list-unstyled py-2">
                            <router-link
                                @click="cnt_mid_cate_kor=mid.lectMidCateKor, cnt_top_cate_kor=top.lectTopCateKor"
                                :to="{
                                    name:'lectureCategories',
                                    params:{
                                        index : top.lectTopCate,
                                    },
                                    query:{
                                        cnt_mid_cate : mid.lectMidCate
                                    }
                                }"
                                class="text-decoration-none"
                                :class="mid.lectMidCate == cnt_mid_cate && top.lectTopCate == cnt_top_cate ? 'cnt_selected':'text-body'"
                                >
                                {{ mid.lectMidCateKor }}
                            </router-link>
                        </li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
export default{
    name : 'cateNavi',
    data() {
        return {
            categories: [], 
            cnt_mid_cate: '', // 현재 어떤 중분류를 보고있는지를 코드로 저장
            cnt_mid_cate_kor: '', // 현재 보고 있는 대분류의 한글 코드명
            cnt_top_cate: '', // 현재 어떤 중분류를 보고있는지를 코드로 저장
            cnt_top_cate_kor: '', // 현재 보고 있는 대분류의 한글 코드명
        }
    },
    methods: {
        /** 마운트시 카테고리 조회하는 함수 */
        getCategory() {
            this.$axiosSend('get','/api/lect/getCategory')
                .then((res)=>{
                    this.categories = res.data.item
                })
                .catch((err)=>{
                    console.log(err)
                })
            },
            topHandler(newTop){
                this.cnt_top_cate = newTop;
            },
            midHandler(newMid){
                this.cnt_mid_cate = newMid;
                this.$emit('emitTest', this.cnt_top_cate_kor, this.cnt_mid_cate_kor)
            },
    },
    created() {
        
    },
    mounted() {
        this.getCategory(); // 마운트시 카테고리 목록부터 조회
    },
    watch:{
        '$route.params.index': { 
                immediate: true,
                handler(newTop) {
                    if(newTop != undefined){
                        this.topHandler(newTop);
                    }
                }
            },
            '$route.query.cnt_mid_cate': {
                immediate: true,
                handler(newMid) {
                    if (newMid != undefined) {
                        this.midHandler(newMid);
                    }
                }
            }
    }
}


</script>

<style scoped>
    a {
        cursor: pointer;
    }

.lecture_item:hover{
    transition: 0.1s;
    box-shadow: 0px 0px 0px 3px black inset !important;
    scale: 102%;
}

    .mid-cate-container::after {
        content: '';
        width: 100%;
        margin: 1rem 0;
        background: gray;
        height: 1px;
        display: flex;
    }

    .cnt_selected {
        color: var(--blue) !important;
        font-weight: bold !important;
    }


</style>