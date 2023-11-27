<template>
    <div>
        <div class="comm-main-side" id="comm-main-side">
            <ul class="comm-main-cate">
                <p v-if="titleShow" class="fs-2 fw-bold mb-3 mt-15">커뮤니티</p>
                <li class="nav-item me-4" style="width: 150px;">
                <span class="qna-view-qnapath" id="qna-view-qnapath">
                    <router-link class="fs-5 text-body text-decoration-none" to="/comm/qna"
                        style="cursor: pointer; text-align: center;">
                        <p id="comm-p-tag" :class="this.currentTab == 'qna' ? 'fw-bold bedu-text-custom-blue':''">질문 & 답변</p>
                    </router-link>
                </span>
                </li>
                    <li class="nav-item me-4" style="width: 150px;">
                        <span class="qna-view-freepath" id="qna-view-freepath">
                            <router-link class="fs-5 text-body text-decoration-none" id="comm-p-tag" to="/comm/freBd"
                            style="cursor: pointer; text-align: center;">
                            <p id="comm-p-tag" :class="this.currentTab == 'free' ? 'fw-bold bedu-text-custom-blue':''">자유게시판</p>
                            </router-link>
                    </span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import '@/assets/css/freeBoardStyle.css';
export default{

    name:'comm-main-side',
    props:{
        titleShow : {
            type : Boolean,
            required : false,
            default : true,
        },
        currentTab : String,
    },
}

</script>