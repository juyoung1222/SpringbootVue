<!-- 작성자 우지원 -->


<template>
    <div>  
        <b-container class="py-5">
            <p class="fs-1 fw-bold mb-5"><b-link class="me-4 text-body" to="adminPage" title="관리자 페이지로 이동"><font-awesome-icon :icon="['fas', 'left-long']" /></b-link>동영상 관리</p>
            <b-card no-body>
                <b-card-header  header-tag="nav">
                    <b-nav card-header fill tabs>
                        <b-nav-item @click="this.currentTab='upload'" :active="currentTab =='upload' ? 'true':'false'">등록</b-nav-item>
                        <b-nav-item @click="this.currentTab='delete'" :active="currentTab =='delete' ? 'true':'false'">삭제</b-nav-item>
                    </b-nav>
                </b-card-header>
                <b-card-body>
                    <FileUpload v-if="currentTab == 'upload'" :totalLists="totalLists"></FileUpload>
                    <FileDelete v-if="currentTab == 'delete'" :totalLists="totalLists"></FileDelete>
                </b-card-body>
            </b-card>
        </b-container>
    </div>
</template>


<script>
import FileUpload from '@/components/adminPage/fileManage/FileUpload.vue';
import FileDelete from '@/components/adminPage/fileManage/FileDelete.vue';
import '@/assets/css/admin.css'

export default{
    name : 'fileManage',
    components:{ FileUpload, FileDelete },
    data() {
        return {
            currentTab : 'upload',

        }
    },
    created(){

    },
    methods: {

    },
    mounted() {
    
    },
}
</script>

<style scoped>


</style>