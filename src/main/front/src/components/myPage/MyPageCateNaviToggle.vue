<!-- 작성자 우지원 -->


<template>
    <div>
        <div class="d-flex d-block d-xxl-none mb-3">
            <b-form-select
            :options="selectOptions"
            v-model="currentTab"
            @input="tabChange"
            size="lg"
            ></b-form-select>
        </div>
    </div>
</template>


<script>
export default{
    name : 'myPageCateNaviToggle',
    data() {
        return {
            currentTab : this.$route.name,
            selectOptions : [
                {
                    text : '대시보드',
                    value : 'mypage'
                },{ 
                    text : '장바구니',
                    value : 'lecturePayment'
                },{
                    text : '작성한 게시글',
                    value : 'myPost'
                },{
                    text : '북마크',
                    value : 'bookmark'
                },{
                    text : '내 학습',
                    value : 'mypageAll'
                },{
                    text :'프로필',
                    value : 'profile'
                }
            ],
        }
    },
    methods: {
        tabChange(){
            if(this.currentTab == 'bookmark'){
                this.$routerPush(this.currentTab,{
                    order : 'title'
                },true)
            } else if (this.currentTab == 'mypageAll'){
                this.$routerPush(this.currentTab,{
                    group : 'all',
                    order : 'title',
                    keyword : '',
                },true);
            } else {
                this.$routerPush(this.currentTab)
            }
        },
    },
}
</script>