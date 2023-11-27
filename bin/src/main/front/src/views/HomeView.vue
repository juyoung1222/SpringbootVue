<template>
    <div class="lecture py-5">
        <img src="@/assets/imgs/character/im.png" class="character d-none d-xxl-block">
        <p class="fw-bold fs-1 text-center">어떤 강의를 찾고 있나요?</p>
        <p class="fs-5 fw-light fw-bold text-center bedu-text-custom-blue">What's wanna be?</p>
        <!-- <b-form class="w-50 w-md-75 mx-auto" @submit="lectSearch">
            <div
                class="border border-3 rounded-pill p-2 d-flex align-middle text-center w-100 m-auto mb-5 border-bedu">
                <font-awesome-icon class="m-auto mx-3" :icon="['fas', 'magnifying-glass']"/>
                <b-form-input class="border-0 me-2" v-model="keyword" id="bedu-home-search"></b-form-input>
            </div>
        </b-form> -->

        <b-container>
            <p class="fw-bold fs-4 text-start">분야별 강의</p>
            <div >
                <categories></categories>
            </div>
        </b-container>



        <!-- 신규 오픈 강좌 컨테이너 -->
        <b-container class="mb-5 py-3">
            <p class="fs-4 mb-5 fw-bold">신규 오픈 강좌</p>
            <div>
                <b-row class="justify-content-center d-block d-lg-flex">
                    <b-col v-for="(item, index) in newestLectures" :key="index">
                        <b-link
                            class="text-decoration-none text-body h-100 d-block"
                            :to='"/lectureDetail?num="+item.lectNum'>
                            <b-container class="border rounded-4 lecture_item h-100 mb-3">
                                <div class="ps-2 h-100">
                                    <div class="lect-upper-side">
                                        <p class="fs-5 pt-3 d-inline-block text-truncate">{{ item.title }}</p>
                                        <p class="text-secondary ">{{ item.lectSum }}</p>
                                        <p>{{ item.teacher }} 선생님</p>
                                    </div>
                                    <div class="lect-lower-side">
                                        <hr>
                                        <p>총 {{ item.total }}강</p>
                                        <p class="text-secondary">수강기간 : {{ item.lectPeriod }}일</p>
                                        <div class="text-end mb-3">
                                            <span class="fw-bold">{{ item.price }}</span>
                                            원
                                        </div>
                                    </div>
                                </div>
                            </b-container>
                        </b-link>
                    </b-col>
                </b-row>
            </div>
        </b-container>

        <!-- 금주의 인기 강좌 컨테이너 인기 기준이 없는 관계로 임시로 신규 강의와 동일한 데이터 출력 -->
        <b-container class="mb-5">
            <p class="fs-4 mb-5 fw-bold">금주의 인기 강좌</p>
            <div>
                <b-row class="justify-content-center d-block d-md-flex">
                    <b-col v-for="(item, index) in newestLectures" :key="index" class="mb-1">
                        <b-link
                            class="text-decoration-none text-body"
                            :to='"/lectureDetail?num="+item.lectNum'>
                            <b-container class="border rounded-4 lecture_item">
                                <div class="ps-3">
                                    <p class="fs-5 pt-3">{{ item.title }}</p>
                                    <p class="text-secondary">{{ item.lectSum }}</p>
                                    <p>{{ item.teacher }} 선생님</p>
                                    <hr>
                                    <p>총 {{ item.total }}강</p>
                                    <p class="text-secondary">수강기간 : {{ item.lectPeriod }}일</p>
                                    <div class="text-end mb-3">
                                        <span class="fw-bold">{{ item.price }}</span>
                                        원
                                    </div>
                                </div>
                            </b-container>
                        </b-link>
                    </b-col>
                </b-row>
            </div>
        </b-container>

        <!-- 멤버쉽 광고 컨테이너 -->
        <b-container>
            <p class="fs-4 fw-bold">멤버쉽 안내</p>
            <div style="height: 200px; width: 100%;" class="rounded-4 border border-5 border-dark">
                <b-container class="text-body fw-bold d-flex">
                    <div class="me-auto">
                        <p>마참내!</p>
                        <p>월정액을!</p>
                        <p>즐겁다!</p>
                        <p>뭐어?! 최대 31% 할인이라고오~~~!?!?!?! 당장 사야지~~!!</p>
                        <p>대충 광고 문구 주저리주저리...</p>
                    </div>
                    <div>
                        <b-button class="py-2 px-4 fs-3 d-flex" to="/membership">
                            맴버쉽 안내
                        </b-button>
                    </div>
                </b-container>
            </div>
        </b-container>
    </div >
</template>

<script>
    import Categories from '../components/Categories.vue'

    export default{
        components: { Categories },
        name : 'lectureView',
        data() {
            return {
                // keyword : '', /** 검색 키워드 */
                newestLectures:[], /** 최신 강의 목록 데이터 */
            }
        },
        methods: {
            // lectSearch(){ /** 검색할때 동작 */
            // this.$routerPush(
            //     'lectureSearch',
            //     {
            //         keyword : this.keyword
            //     },
            //     true
            // )
            // },

            /** 최근 업로드된 강의 4개 조회하는 메서드 */
            getNewestLecture(){
                this.$axiosSend('get','/api/lect/getNewestLecture')
                .then((res)=>{
                    this.newestLectures = res.data.item;
                })
                .catch((err)=>{console.log(err)})
            }

        },
        computed: {
            
        },
        mounted() {
            this.getNewestLecture();
        },
        created() {
        
        },
    }
</script>

<style scoped>

    a{
        cursor: pointer !important;
    }
    .lecture_item:hover{
        transition: 0.1s;
        box-shadow: 0px 0px 0px 3px black inset !important;
        scale: 102%;
    }

    .border-bedu {
        border-color: var(--yellow) !important;
    }

    .fs-bedu-yellow{
        color : var(--blue) !important;
    }

    .character{
        position: absolute;
        top : 19%;
        left : -130px;
        height: 30%;
    }

    #bedu-home-search:focus{
        box-shadow: none !important;
    }

    .lect-upper-side{
        height: 55%;
    }

    .lect-lower-side{
        height: 45%;
    }

</style>