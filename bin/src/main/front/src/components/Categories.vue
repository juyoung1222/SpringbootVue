<template>
    <div>
        <b-navbar >
            <b-collapse id="categories" class="w-100" is-nav>
                    <b-row class="w-100">
                        <b-col v-for="(item, index) in categories" :key="index" class="col-4 px-1 col-lg-1 text-center justify-content-center mx-auto">
                                <b-link :to='"/lectureCategories/"+item.cateCode+"?cnt_mid_cate="+item.children[0].cateCode' class="text-body text-decoration-none">
                                    <b-container class="bg-secondary rounded-3 bg-opacity-10 py-3 mb-2">
                                        <b-img class="category-icon" :src="require('@/assets/imgs/categories/'+item.icon+'.png') " fluid></b-img>
                                    </b-container>
                                    <text class="fw-bold d-block text-wrap">{{ item.cateKor }}</text>
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
                categories: [],
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
            getData(){
                let cateData = [];
                this.$axiosSend('get', '/api/lect/getCategory')
                .then((res) => {
                    console.log('res::: ', res)
                    if (this.$isNotEmpty(res?.data)) {
                        cateData = res?.data;
                    }
                    console.log('cateData ::: ', cateData)
                    if(cateData.length != 0){
                        this.convertToHierarchy(cateData)
                    }
                })
            },
            /** 받은 카테고리를 트리 구조로 변경하는 함수 */
            convertToHierarchy(data) {
                const map = {}; // 부모-자식 관계를 저장할 맵
                // 맵에 카테고리 코드를 키로하여 카테고리 객체를 저장
                data.forEach(category => {
                    category.children = []; // 자식 카테고리를 저장할 배열 생성
                    map[category.cateCode] = category;
                });
                
                const hierarchy = []; // 최상위 부모 카테고리를 저장할 배열
                
                // 부모-자식 관계 설정
                data.forEach(category => {
                    const parentNum = category.parentNum;
                    if (parentNum) {
                        const parent = map[parentNum];
                        if (parent) parent.children.push(category)
                    } else {
                        hierarchy.push(category);
                    }
                    this.categories = hierarchy;
                });
            }
        },
        mounted() {
            this.getData();
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
</style>