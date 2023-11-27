<!-- 작성자 우지원 -->


<template>
    <div>
        <div class="p-4 p-md-5 w-100 d-flex">
            <!-- 좌측 네비 -->
            <my-page-cate-navi></my-page-cate-navi>


            <div class="w-100">
                <my-page-cate-navi-toggle></my-page-cate-navi-toggle>
                <div class="d-flex mb-5">
                    <div class="me-auto">
                        <div>
                            <span>
                                전체
                            </span>
                            <span>
                                {{ total }}
                            </span>
                        </div>
                    </div>
                    <div>
                        <b-form-select @input="orderChange" v-model="orderSelected" :options="orderOptions"></b-form-select>
                    </div>
                </div>
                <b-row cols="1" cols-md="3" class="mb-5">
                    <b-col v-for="(item,index) in list" :key="index" class="mb-3">
                        <b-link class="text-decoration-none text-body"
                        :to="{
                            name : 'lectureDetail',
                            query : {
                                num : item.lectNum
                            },
                        }">
                            <b-container class="border rounded-3 p-3">
                                <div class="ratio ratio-16x9 mb-3">
                                    <b-img :src="item.thumbnail"></b-img>
                                </div>
                                <div>
                                    {{ item.title }}
                                </div>
                                <div>
                                    {{ item.teacher }}
                                </div>
                                <div>
                                    <span v-for="(star,index) in 5" :key="index">
                                        <font-awesome-icon
                                        :class="item.score >= star ? 'text-danger' : 'text-secondary'"
                                        :icon="['fas','star']"/>
                                    </span>
                                </div>
                            </b-container>
                        </b-link>
                    </b-col>
                </b-row>
                <div>
                    <b-pagination :total-rows="total" :per-page="9"></b-pagination>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import MyPageCateNavi from '../../components/myPage/MyPageCateNavi.vue'
import MyPageCateNaviToggle from '../../components/myPage/MyPageCateNaviToggle.vue'
export default{
  components: { MyPageCateNavi, MyPageCateNaviToggle },
    name : 'bookmarkList',
    data() {
        return {
            list : [],
            total : 0,
            page : 0,
            orderSelected : 'title',
            orderOptions : [
                {
                    value : 'title',
                    text : '제목순'
                },
                {
                    value : 'newest',
                    text : '최신순'
                },
                {
                    value : 'score',
                    text : '평점순'
                },{
                    value : 'user',
                    text : '학생수순'
                },
            ],
        }
    },
    methods: {
        /** 북마크한 강의 총 갯수 조회 */
        getTotal(){
            this.$axiosSend('get','/api/bookmark/getTotal',{
                userName : this.$store.getters.getNickname
            })
            .then((res)=>{
                this.total = res.data
            })
            .catch((err)=>{
                console.log(err)
            })
        },
        /** 북마크 목록 조회 */
        getList(){
            this.$axiosSend('get','/api/bookmark/getList',{
                userName : this.$store.getters.getNickname,
                begin : this.page,
                order : this.orderSelected
            })
            .then((res)=>{
                console.log(res.data)
                this.list = res.data
            })
            .catch((err)=>{
                console.log(err)
            })
        },
        /** 정렬순서 변경 감지 */
        orderChange(){
            this.$routerPush('',{
                order : this.orderSelected
            },true)
        },
    },
    watch:{
        '$route.query':{
            immediate : true,
            handler(val){
                if(this.$route.name != 'bookmark'){
                    return;
                }
                this.orderSelected = val.order;
                this.getTotal();
                this.getList();
            }
        }
    }
}

</script>