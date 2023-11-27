<!--작성자:황용학-->


<template>
    <div>
        <!--<div class="w-25 ps-5 me-5 d-none d-sm-block">-->
        <div class="csc-main-side d-none d-xxl-block">
            <p v-if="titleShow" class="fs-2 fw-bold mb-3 mt-15 ">고객센터</p>
            <router-link class="fs-5 text-body text-decoration-none" id="cscside-view" to="/faq">
                <p>자주 묻는 질문</p>
            </router-link>
            <router-link class="fs-5 text-body text-decoration-none" id="csc-view-freepath" to="/csc">
                <p>이용 문의</p>
            </router-link>
        </div>
    </div>
</template>
  
<script>
import '@/assets/css/CscViewStyle.css';
export default {

    name: 'comm-main-side',
    props: {
        titleShow: {
            type: Boolean,
            required: false,
            default: true,
        },
        currentTab: String,
    },

}

</script>