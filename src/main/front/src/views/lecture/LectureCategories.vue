<!-- 작성자 우지원 -->


<template>
    <div>
        <div class="p-4 p-md-5 w-100 d-flex">
            <!-- 좌측 네비, 커리큘럼 컨테이너 -->
            <!-- 좌측 카테고리 네비게이션 컴포넌트화 -->
            <CategoryNaviVue @emitTest="cateEmit"></CategoryNaviVue> 
            
            <div class="w-100 pe-0 pe-sm-3">
                
                <!-- 모바일 화면에서 대분류 & 중분류 변경을 위한 콤보 -->
                <!-- 강의 기본정보 & 커리큘럼 컨테이너-->
                <div class="d-flex gap-1 d-block d-xxl-none">
                    <b-form-select 
                    class="w-50" 
                    v-model="cnt_top_cate" :options="categories.filter((item)=>item.level == 1)"
                    value-field="lectTopCate"
                    text-field="lectTopCateKor"
                    size="lg"
                    ></b-form-select>

                    <b-form-select
                    class="w-50"
                    v-model="cnt_mid_cate"
                    :options="categories.filter((first)=>first.level == 2).filter((second)=>second.lectTopCate == this.cnt_top_cate)"
                    value-field="lectMidCate"
                    text-field="lectMidCateKor"
                    ></b-form-select>
                </div>

                <div class="d-flex gap-5 d-none d-xxl-block">
                    <p class="fs-2 fw-bold pb-3">{{ cnt_mid_cate_kor }}</p>
                </div>
                <p class="fs-3 fw-bold curriculum-head my-3">커리큘럼</p>
                <ul class="list-unstyled">
                    <!-- 소분류 목록 -->
                    <li
                        :class="index%2 == 0 ? 'curriculum-list-even':'curriculum-list-odd'"
                        v-for="(bot, index) in lectures"
                        :key="index"
                        class="my-4 align-middle">
                        <div>
                            <a
                                class="d-flex text-body text-decoration-none my-auto fs-4 mb-4"
                                data-bs-toggle="collapse"
                                :href='"#bot-cate-list-"+index'>
                                <span class="bot-cate-step">STEP
                                    {{ index+1 }}</span>
                                <div class="bot-cate-split"></div>
                                <span class="text-body">{{ bot.lectBotCateKor }}</span>
                                <span class="ms-auto">
                                    <font-awesome-icon :icon="['fas','caret-down']"/>
                                </span>
                            </a>
                            <!-- 커리큘럼 목록 콜랩스 -->
                            <div
                                class="collapse multi-collapse"
                                :id='"bot-cate-list-"+index'
                                ref="lecture_list"
                                :class='index == 0 ? "show":""'>
                                <b-container>
                                    <b-row cols="1" cols-lg="3">
                                        <!-- 커리큘럼 목록 -->
                                        <b-col v-for="(lect, index) in bot.item" :key="index" class="mb-3">
                                            <b-link
                                                class="text-body text-decoration-none"
                                                :to='"/lectureDetail?num="+lect.lectNum'>
                                                <b-container class="border rounded-3 p-3 lecture_item h-100">
                                                    <p class="fs-5 fw-bold">{{ lect.title }}</p>
                                                    <div>
                                                        <p class="text-secondary overflow-hidden">
                                                            {{ lect.lectSum }}
                                                        </p>
                                                    </div>
                                                        <p class="overflow-hidden">{{ lect.teacher }} 선생님</p>
                                                    <hr>
                                                    <p>총{{ lect.total }}강</p>
                                                    <p class="text-secondary">수강 기간 : {{ lect.lectPeriod }}일</p>
                                                    <p class="text-end"><span class="fw-bold">{{ lect.price }}</span>원</p>
                                                </b-container>
                                            </b-link>
                                        </b-col>
                                    </b-row>
                                </b-container>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
import CategoryNaviVue from '@/components/CategoryNavi.vue';
import '@/assets/css/lectureStyle.css';
    export default {
        name: 'lectureCategories',
        components:{CategoryNaviVue},
        data() {
            return {
                categories: [], 
                cnt_mid_cate: '', // 현재 어떤 중분류를 보고있는지를 코드로 저장
                cnt_mid_cate_kor: '', // 현재 보고 있는 대분류의 한글 코드명
                cnt_top_cate: '', // 현재 어떤 중분류를 보고있는지를 코드로 저장
                cnt_top_cate_kor: '', // 현재 보고 있는 대분류의 한글 코드명
                lectures: [], // 소분류에 있는 강의 목록
            }
        },
        methods: {
            cateEmit(newTop, newMid){
                console.log(this.$route.params.index)
                this.cnt_top_cate_kor = newTop;
                this.cnt_mid_cate_kor = newMid;
            },
            /** 중분류에 따른 강의 정보 조회 함수 */
            getLectureList() {
                this
                    .$axiosSend('get', '/api/lect/lectureList', {category: this.cnt_mid_cate})
                    .then((res) => {
                        if(this.$isNotEmptyArray(res.data.item)){
                            this.lectures = res.data.item;
                        } else {
                            this.$swal({
                                title : '에러',
                                icon:'error',
                                text : '목록을 불러오는데 에러가 발생했습니다.'
                            })
                        }
                    })
                    .catch((err) => {
                        console.log(err)
                    })
            },
            getList(){
                this.$axiosSend('get','/api/lect/getCategory')
                .then((res)=>{
                    this.categories = res.data.item
                    this.cnt_top_cate_kor = this.categories.filter((first)=>first.level == 1).filter((second)=>second.lectTopCate == this.cnt_top_cate)[0].lectTopCateKor
                    this.cnt_mid_cate_kor = this.categories.filter((first)=>first.level==2).filter((second)=>second.lectMidCate == this.cnt_mid_cate)[0].lectMidCateKor
                    
                })
                .catch((err)=>{
                    console.log(err)
                })
            }
        },
        created() {
            
        },
        mounted() {
            this.cnt_top_cate = this.$route.params.index;
            this.getList();
        },
        watch: {
            cnt_mid_cate: function () { // 중분류 변경되면 그에맞는 소분류, 강의 목록 조회
                this.getLectureList();
                var collapses = this.$refs.lecture_list;
                if (collapses) {
                    collapses.forEach((col) => {
                        col
                            .classList
                            .remove('show')/* 중분류 변경시 콜랩스 닫기 */
                            collapses[0]
                            .classList
                            .add('show')/* 첫번째 스텝만 보여주기 */
                        })
                }
            },
            '$route.params.index': { 
                immediate: true,
                handler(newTop) {
                    this.cnt_top_cate = newTop;
                }
            },
            '$route.query.cnt_mid_cate': {
                immediate: true,
                handler(newMid) {
                    if (newMid != undefined) {
                        this.cnt_mid_cate = newMid;
                    }
                }
            }
        }
    }
</script>