<!-- 작성자 우지원 -->


<template>
    <div class="w-25 ps-5 me-5 d-none d-xxl-block">
        <p class="fs-2 fw-bold">마이페이지</p>
        <b-nav vertical>
            <b-nav-item class="fs-5"
            :to="{
                name : 'mypage'
            }">
                대시보드
            </b-nav-item>
            <b-nav-item class="fs-5"
            :to="{
                name: 'lecturePayment'
            }">
                장바구니
            </b-nav-item>
            <b-nav-item class="fs-5"
            :to="{
                name:'myPost'
            }">
                작성한 게시글
            </b-nav-item>
            <b-nav-item class="fs-5"
            :to="{
                name : 'bookmark',
                query: {
                    order : 'title'
                }
            }">
                북마크
            </b-nav-item>
            <b-nav-item class="fs-5"
            :to="{
                name : 'mypageAll',
                query : {
                    group : 'all',
                    order : 'title',
                    keyword : '',
                }
            }">
                내 학습
            </b-nav-item>
            <b-nav-item class="fs-5"
            :to="{
                name : 'profile'
            }">
                프로필
            </b-nav-item>
        </b-nav>
    </div>
</template>


<script>
export default{
    name : 'myPageCateNavi'
    
}
</script>