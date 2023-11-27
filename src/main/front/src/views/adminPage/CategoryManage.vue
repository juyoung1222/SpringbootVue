<!-- 작성자 우지원 -->


<template>
    <div>
        <b-container class="py-5">
            <p class="fs-1 fw-bold"><b-link class="me-4 text-body" to="adminPage" title="관리자 페이지로 이동"><font-awesome-icon :icon="['fas', 'left-long']" /></b-link>카테고리 관리</p>
            <b-card no-body>
                <b-card-header header-tag="nav">
                    <b-nav card-header fill tabs>
                        <b-nav-item @click="this.currentTab='insert'" :active="currentTab =='insert' ? 'true':'false'">등록</b-nav-item>
                        <b-nav-item @click="this.currentTab='update'" :active="currentTab =='update' ? 'true':'false'">수정</b-nav-item>
                        <b-nav-item @click="this.currentTab='delete'" :active="currentTab =='delete' ? 'true':'false'">삭제</b-nav-item>
                    </b-nav>
                </b-card-header>
                <b-card-body>
                    <category-insert v-if="currentTab == 'insert'"></category-insert>
                    <category-update v-if="currentTab == 'update'"></category-update>
                    <category-delete v-if="currentTab == 'delete'"></category-delete>
                </b-card-body>
            </b-card>
        </b-container>
    </div>
</template>

<script>
import CategoryDelete from '../../components/adminPage/categoryManage/CategoryDelete.vue'
import CategoryInsert from '../../components/adminPage/categoryManage/CategoryInsert.vue'
import CategoryUpdate from '../../components/adminPage/categoryManage/CategoryUpdate.vue'
export default{
  components: { CategoryInsert, CategoryUpdate, CategoryDelete },
    name : 'categoryManage',
    data() {
        return {
            currentTab : 'insert'
        }
    },
}
</script>