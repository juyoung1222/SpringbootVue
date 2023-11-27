<template>
  <div class="review-list">
    <div class="group">
      <div class="m-select">
        <ul class="mk-c-tab col8 tab-event">
          <li><a href="#">전체</a></li>
          <li><a href="#">기초강의</a></li>
          <li><a href="#">데이터분석</a></li>
          <li><a href="#">웹 개발</a></li>
          <li><a href="#">프로그래밍 언어</a></li>
          <li><a href="#">인공지능</a></li>
          <li><a href="#">프로그래밍 교양</a></li>
          <li><a href="#">개발도구</a></li>
          <li><a href="#">컴퓨터 공학 전공 지식</a></li>
          <li><a href="#">디자인</a></li>
        </ul>
      </div>
      <div class="review-search">
        <p class="fw-bold fs-3 text-start" style="margin-top:20px">수강후기</p>
        <div class="search-bar">
          <div class="search-input">
            <input type="text" placeholder="검색어를 입력하세요" v-model="searchKeyword">
            <button @click="searchReviews">검색</button>
          </div>
        </div>
      </div>
    </div>

    <table class="review-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>강좌</th>
          <th>수강후기</th>
          <th>별점</th>
          <th>작성자</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="review in filteredReviews" :key="review.id">
          <td>{{ review.number }}</td>
          <td>{{ review.course }}</td>
          <td>
            <span class="review-content" v-for="content in review.contents" :key="content.id">
              {{ content.text }}
            </span>
          </td>
          <td>
            <div class="star-rating">
              <span class="star" v-for="star in review.stars" :key="star">&#9733;</span>
            </div>
          </td>
          <td>{{ review.author }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>



<script>
export default {
  data() {
    return {
      searchKeyword: '',
      reviews: [
        {
          id: 1,
          number: 1,
          course: "데이터 과학",
          contents: [
            { id: 1, text: "훌륭한 강의입니다! 설명도 너무 잘해주시고" },
          ],
          author: "김아무개",
          stars: [1, 1, 1, 1, 1], // 별점 예시로 5개의 별 표시
        },
        {
          id: 2,
          number: 2,
          course: "웹 개발",
          contents: [
            { id: 2, text: "강의가 매우 실용적입니다 교육자의 설명이 명확하고 이해하기 쉽습니다" },
          ],
          author: "박아무개",
          stars: [1, 1, 1, 1], // 별점 예시로 4개의 별 표시
        },
        // 추가적인 리뷰 데이터 객체를 추가하세요
      ],
    };
  },
  computed: {
    filteredReviews() {
      if (this.searchKeyword) {
        const keyword = this.searchKeyword.toLowerCase();
        return this.reviews.filter(review =>
          review.course.toLowerCase().includes(keyword) ||
          review.contents.some(content => content.text.toLowerCase().includes(keyword)) ||
          review.author.toLowerCase().includes(keyword)
        );
      } else {
        return this.reviews;
      }
    },
  },
  methods: {
    searchReviews() {
      // 검색 기능을 구현하기 위한 메소드입니다.
      // 필요에 따라 서버와의 통신이나 클라이언트 측에서의 데이터 필터링 등을 수행할 수 있습니다.
    },
  },
};
</script>

<style scoped>
.group {
  padding: 25px;
}

.mk-c-tab.col8 {
  border: 1px solid #eee;
  border-bottom: 0;
  border-right: 0;
  padding: 0;
}

.mk-c-tab {
  text-align: center;
  clear: both;
  overflow: hidden;
  margin-bottom: 20px;
}

.mk-c-tab>li {
  display: inline-block;
  width: 20%;
  text-align: center;
}

.mk-c-tab>li>a {
  display: block;
  padding: 12px 10px;
  background: #f8f8f8;
  border-right: 1px solid #eee;
  font-weight: 300;
  color: inherit;
}

.review-search {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.search-bar {
  display: flex;
  align-items: center;
}

.search-input input {
  width: 200px;
  padding: 5px;
  margin-right: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-input button {
  padding: 5px 10px;
  background-color: #f8f8f8;
  border: 1px solid #ccc;
  border-radius: 4px;
  color: inherit;
  cursor: pointer;
}

a {
  text-decoration: none;
  cursor: pointer;
  color: inherit;
  font-size: inherit;
  font-weight: inherit;
}

.review-table {
  width: 100%;
  border-collapse: collapse;
}

.review-table th,
.review-table td {
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #ccc;
}

.star-rating {
  display: flex;
  justify-content: center;
  align-items: center;
}

.star-rating .star {
  color: gold;
}

.review-table .review-content {
  display: block;
  width: 200px; 
  white-space: nowrap;
  text-overflow: ellipsis;
}

@media (max-width: 768px) {
  .mk-c-tab>li {
    width: 50%;
  }
  .review-table th,
  .review-table td {
    font-size: 12px;
    padding: 8px;
  }
  .review-table .review-content {
    width: 150px;
  }
}
</style>