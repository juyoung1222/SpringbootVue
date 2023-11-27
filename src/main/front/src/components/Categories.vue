<!-- 작성자 우지원 -->


<template>
    <div>
        <b-navbar>
            <b-collapse id="categories" class="w-100" is-nav>
                    <b-row class="w-100 mx-auto">
                        <b-col v-for="(item,index) in filds.filter((item)=> item.level ==1)" :key="index" class="col-4 px-1 col-lg-1 text-center justify-content-center mx-auto">
                                <b-link :to="{
                                    name : 'lectureCategories',
                                    params : {
                                        index : item.lectTopCate
                                    },
                                    query : {
                                        cnt_mid_cate : filds.filter((fild)=>(fild.parentCode == item.lectTopCate)  )[0].lectMidCate
                                    }
                                }"  class="text-body text-decoration-none"
                                >
                                    <b-container class="bg-secondary rounded-3 bg-opacity-10 py-3 mb-2">
                                        <b-img class="category-icon" :src="require('@/assets/imgs/categories/'+item.lectIcon+'.png') " fluid></b-img>
                                    </b-container>
                                    <text class="fw-bold text-break">{{ item.lectTopCateKor }}</text>
                                </b-link>
                        </b-col>
                    </b-row>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
import '@/assets/css/lectureStyle.css';

    export default {
        name: 'lectureCategories',
        data() {
            return {
                filds: [],
            }
        },
        methods: {
            // 이미지 주소를 변환시켜주는 함수
            // getImg(imgUrl){
            //     console.log('url ::: ', imgUrl)
            //     // const url = require('@'+imgUrl)
            //     const url = require('@/assets/imgs/categories/free-icon-base.png')
            //     //                      @/assets/imgs/categories/free-icon-base.png'
            //     // api로 받아온 이미지를 변수로 가져옴
            //     // img: require('@/assets/imgs/categories/free-icon-base.png')
            //     console.log('img url ::: ', url)
            //     return url
            // },
            getList(){
                this.$axiosSend('get','/api/lect/getCategory')
                .then((res)=>{
                    this.filds = res.data.item
                })
                .catch((err)=>{
                    console.log(err)
                })
            }
        },
        mounted() {
            this.getList();
        },
        computed:{
            colsLimit(){
                let width = window.innerWidth;
                let result = 9
                result = width >= 768 ? 9 : 3
                console.log(width)
                console.log(result)
                return result;
            }
        }
    }
</script>


<style scoped>

    .row-cols-lg-9 {
        flex: 0 0 auto;
        width: 11.1111111111%;
    }

    .category-icon{
        width: 52px !important;
        height: 52px !important;
    }
    .text-break {
        word-wrap: keep-all !important;
        word-break: keep-all !important;
    }
</style>