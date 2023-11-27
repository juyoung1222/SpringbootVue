<!-- 작성자 우지원 -->


<template>
    <div>
        <b-container class="py-5">
            <p class="fs-1 fw-bold"><b-link class="me-4 text-body" to="adminPage" title="관리자 페이지로 이동"><font-awesome-icon :icon="['fas', 'left-long']" /></b-link>강의 관리</p>
            <b-card no-body>
                <b-card-header header-tag="nav">
                    <b-nav card-header fill tabs>
                        <b-nav-item @click="this.currentTab='insert'" :active="this.currentTab == 'insert' ? 'true':'false'">등록</b-nav-item>
                        <b-nav-item @click="this.currentTab='update'" :active="this.currentTab == 'update' ? 'true':'false'">수정</b-nav-item>
                        <b-nav-item @click="this.currentTab='delete'" :active="this.currentTab == 'delete' ? 'true':'false'">삭제</b-nav-item>
                    </b-nav>
                </b-card-header>
                <b-card-body>
                    <LectureInsertVue v-if="this.currentTab == 'insert'"></LectureInsertVue>
                    <LectureUpdate v-if="this.currentTab == 'update'"></LectureUpdate>
                    <LectureDeleteVue v-if="this.currentTab == 'delete'"></LectureDeleteVue>
                </b-card-body>
            </b-card>
        </b-container>
    </div>
</template>

<script>
import LectureInsertVue from '../../components/adminPage/lectureManage/LectureInsert.vue'
import LectureUpdate from '@/components/adminPage/lectureManage/LectureUpdate.vue';
import LectureDeleteVue from '@/components/adminPage/lectureManage/LectureDelete.vue';

export default{
    name : 'lectureManagement',
    components:{LectureInsertVue, LectureUpdate, LectureDeleteVue},
    data() {
        return {
            currentTab : 'insert',
            topCate : '',
            midCate : '',
            botCate : '',
        }
    },
    methods: {
        
    },
    computed : {

    },
    created() {
        
    },
    mounted() {
        
    },
}


</script>

<style scoped>

</style>