<!-- 작성자 우지원 -->


<template>
    <div class="adminPage">
        <b-container class="py-5">
            <p class="fs-1 fw-bold ps-5">관리자 페이지</p>
            <b-row class="p-5 fs-3" cols="1" cols-md="2" cols-lg="3">
                <b-col class="mb-3">
                    <b-container>
                        <p class="fs-3 fw-bold ">강의</p>
                        <ul>
                            <li class="mb-3 ">
                                <b-link to="fileManage" class="text-decoration-none text-body">
                                    동영상 관리
                                </b-link>
                            </li>
                            <li class="mb-3">
                                <b-link to="lectureManage" class="text-decoration-none text-body">
                                    강의 관리
                                </b-link>
                                </li>
                            <li class="mb-3">
                                <b-link class="text-decoration-none text-body" to="categoryManage">
                                    카테고리 관리
                                </b-link>
                            </li>
                        </ul>
                    </b-container>
                </b-col>
                <b-col class="mb-3">
                    <p class="fs-3 fw-bold">사용자</p>
                    <ul>
                        <!-- 사용자 관리 페이지는 추후 개발 예정 -->
                    </ul>
                </b-col>
                <b-col class="mb-3">
                    <p class="fs-3 fw-bold">게시판</p>
                    <ul>
                        <!-- 게시판 관리 페이지는 추후 개발 예정-->
                    </ul>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
export default{
    name : 'adminPage',
    data() {
        return {

        }
    },
    methods: {
        
    },
    computed : {

    },
    mounted() {
        
    },
    created() {
        
    },
}

</script>