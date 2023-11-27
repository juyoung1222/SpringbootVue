<!-- 작성자 원준용 -->


<template>
    <div>
        <br>
        <h2 style="margin-left: 15%;">수강후기 작성</h2>
            <b-form class="writeForm" @submit.prevent="submitReview">
                <b-row>
                    <b-col>
                        <b-form-group
                        style="width: 80%;"
                        class = "inptucontent"
                        label-for="inputContent"
                        >
                        <div class="star-rating">
                            <span v-for="rwGrade in 5" :key="rwGrade" @click="setStars(rwGrade)" :class="{ 'filled': rwGrade <= reviews.rwGrade }">&#9733;</span>
                        </div>
                            <b-form-textarea maxlength = "100" class = "reviewContent" v-model="reviews.content" :state="state" trim></b-form-textarea>
                        </b-form-group>
                    </b-col>
                </b-row>
                    <button id="review-WriteBtn" type="submit">작성 완료</button>
            </b-form>
        </div>
</template>

<script>
    import '@/assets/css/reviewStyle.css';
    import axios from 'axios'

    export default {
        data() {
            return {
                reviews: {
                    lectNum: this.$route.query.num,
                    content: '',
                    rwGrade: '',
                    userName: this.$store.getters.getNickname,
                },
            };
        },
        methods: {
            submitReview() {
                this.$axiosSend("post", "/api/reviews/write", this.reviews)
                    .then(response => {
                        console.log(response);
                        // 요청이 성공적으로 완료된 후 후기 목록 페이지로 리디렉션
                        this.$router.push({ name: 'review', query:{topCategory: 'all'}});
                    })
                    .catch(error => {
                        // 요청 실패 시 에러 처리
                        console.error(error);
                    });
            },

            setStars(rwGrade) {
                this.reviews.rwGrade = rwGrade;
            }
        },
        computed:{
        },
        created(){
        },
        mounted(){

        }
    }
</script>