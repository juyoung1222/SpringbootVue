<template>
    <div class="review-form">
        <h2 id="review-write">수강후기 작성</h2>
        <form @submit.prevent="submitReview">
            <div class="form-group">
                <label id="review-label" for="title">강좌</label>
                <input type="text" id="title" v-model="reviews.title" required>
            </div>
            <div class="form-group">
                <label id="review-label" for="writer">수강후기</label>
                <textarea id="writer" v-model="reviews.writer" required></textarea>
            </div>
            <div class="form-group">
                <label id="review-label" for="stars">별점</label>
                <div class="star-rating">
                <span v-for="star in 5" :key="star" @click="setStars(star)" :class="{ 'filled': star <= reviews.star }">&#9733;</span>
                </div>
            </div>
            <button id="review-WriteBtn" type="submit">작성 완료</button>
        </form>
    </div>
</template>

<script>
    import '@/assets/css/reviewStyle.css';

    export default {
        data() {
            return {
                reviews: {
                    title: '',
                    writer: '',
                    star: 0,
                }
            };
        },
        methods: {
            submitReview() {
                const reviewData = {
                    title: this.reviews.title,
                    writer: this.reviews.writer,
                    star: this.reviews.star
                };

                this.$axiosSend("post", "/api/reviews/write", reviewData)
                    .then(response => {
                        console.log(response);
                        // 요청이 성공적으로 완료된 후 후기 목록 페이지로 리디렉션
                        this.$router.push('/review');
                    })
                    .catch(error => {
                        // 요청 실패 시 에러 처리
                        console.error(error);
                    });
            },
            setStars(star) {
                this.reviews.star = star;
            }
        }
    }
</script>
  