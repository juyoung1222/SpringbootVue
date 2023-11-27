<!-- PageFooter.vue -->
<template>
    <div class="bedu-ft">
        <div class="bedu-ft-cate-links">
            <div class="bedu-ft-cate-links-lt">
                <div class="item">
                    <router-link class="bedu-ft-cate" to="">비에듀</router-link>            
                </div>
                <div class="item">
                    <router-link class="bedu-ft-cate" to="">기업교육</router-link>
                </div>
                <div class="item">
                    <router-link class ="bedu-ft-cate" to="/tou">사용자 이용약관</router-link>
                </div>
                <div class="item">
                    <router-link class="bedu-ft-cate" to="/privacy">개인정보 보호 방침</router-link>
                </div>
                <div class="item">
                    <router-link class="bedu-ft-cate" to="/csc">고객센터</router-link>
                </div>
                <div class="item">
                    <router-link class="bedu-ft-cate" to="">코드등록</router-link>
                </div>
                <!-- <img src="../assets/imgs/test.png"> -->
            </div>
        </div>
        <div class="bedu-ft-des-details">
            <div class="bedu-ft-des">
                <p><span>(주)Test | 통신판매업번호: 제 2019-서울강남-01371호 | </span></p>
                <p><span>&nbsp; 평생교육시설 신고: 제원-408호 Test 사이버평생교육원 </span></p>
                <p><span>Email: test@test.com | FAX: 02-1234-1234 | 사업자번호: 123-12-1234 | 대표자: test | 개인정보책임자 : test | 대표전화: 1234-1234 </span></p>
                <p><span>주소: 서울시 강남구 강남대로 test</span></p>
            </div>
        </div>  
    </div>
</template>
  
<script>
    export default {}
</script>

<style scoped>

    footer img {
    float: left;
    height: 13px;
    width: 10px;
    margin: 27px;
    }

    .bedu-ft{
    display:grid;
    grid-template-areas: "links" "details" "icons";
    justify-content: center;
    width:100%;
    max-width: 110rem;
    margin:0 auto;
    }
    .bedu-ft-cate-links{
    grid-area: links;
    }
    .bedu-ft-cate-links-lt{
    display:flex;
    justify-content: center;
    flex-wrap: wrap;
    align-items: center;
    column-gap: 2.4rem;
    row-gap: 0.5rem;
    }
    .bedu-ft-cate {
    font-size: 1.2rem;
    font-family: 'Noto Sans CJK KR', sans-serif;
    font-weight: bold;
    color: #2c3e50;
    text-decoration: none;
    }
    .bedu-ft-des-details{
    grid-area: details;
    max-width: 70rem;
    margin-top: 2rem;
    }
    .bedu-ft-des{
    display:flex;
    min-width: 100%;
    flex-wrap: wrap;
    justify-content: center;
    }

</style>