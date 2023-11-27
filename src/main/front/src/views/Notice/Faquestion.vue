<!--작성자:황용학-->


<template>
  <div class="d-flex">
    <div class="cscView">
      <CscCategory :currentTab="'inquiry'"></CscCategory>
    </div>
    <div id="cscMain">
      <div>
        <div>
          <h2>자주 묻는 질문 </h2>
          <div id="cscBoradSearch">
            <div class="button-container1">
              <div @submit="faqsearch()" class="searchForm"><!--자주 묻는 질문 검색-->
                <font-awesome-icon id="csc-search-icon" :icon="['fas', 'magnifying-glass']" />
                <input class="cscviewkeyword" v-model="form.keyword" @keyup.enter="faqsearch">
                <!--PC 문의하기-->
                <b-button class="bedu-bg-custom-blue csc-writepath-btn" id="csc-writepath-btn1" @click="goToInquiryPage">
                  <font-awesome-icon :icon="['fas', 'pencil']" />
                  문의하기
                </b-button>
              </div>
            </div>
             <!--모바일 문의하기-->
            <div class="button2">
              <b-button class="bedu-bg-custom-blue csc-writepath-btn" id="csc-writepath-btn2" @click="goToInquiryPage">
                <font-awesome-icon :icon="['fas', 'pencil']" />
                문의하기
              </b-button>
            </div>
          </div>
        </div>
      </div>
      <table id="faqboard-table">
        <colgroup>
          <col width="1.38%">
          <col width="20%">
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>질문/대답</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(faq, index) in displayedFaqList" :key="index" :class="{ 'expanded': faq.isExpanded }"><!--자주 묻는 질문 리스트-->
            <td>
              {{ faq.faqNum }}<!--자주 묻는 질문 넘버-->
            </td>
            <td>
              <div class="faq-question" @click="toggleBox(index)">{{ faq.faqTitle }}</div><!--자주 묻는 질문 제목-->
              <div class="faq-answer" v-if="faq.isExpanded">{{ faq.faqContent }}</div><!--자주 묻는 질문 내용-->
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 검색 결과가 없을 때-->
      <p v-if="faqList.length === 0 && form.keyword.trim() !== ''" id="searched">검색되는 결과가 없습니다.</p>

      <!--PC 페이징 진행-->
      <div class="pagination-container1">
        <b-pagination v-model="currentPage" :total-rows="faqList.length" :per-page="itemsPerPage"></b-pagination>
      </div>

      <!--모바일 페이징 진행-->
      <div class="pagination-container2">
        <b-pagination v-model="currentPage" :total-rows="faqList.length" :per-page="itemsPerPage"
          size="sm"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script>

import CscCategory from '@/components/CscCategory.vue';
//import InquiryDetail from '@/Notice/InquiryDetail.vue';

import '@/assets/css/CscViewStyle.css';
export default {
  name: 'faqList',

  data() {
    return {

      faqList: [],
      itemsPerPage: 10, // 페이지 당 아이템 수
      currentPage: 1, // 현재 페이지
      form: {
        keyword: '',
      },
      sortOption: "default", // 정렬 옵션
    };
  },

  components: {
    CscCategory //카테고리
  },

  created() {
    this.faqqList();
  },

  computed: {
    //자주 묻는 질문 리스트
    displayedFaqList() {
      const startIndex = (this.currentPage - 1) * this.itemsPerPage;
      const endIndex = startIndex + this.itemsPerPage;
      return this.faqList.slice(startIndex, endIndex);
    },

  },

  methods: {

    goToInquiryPage() {
      window.location.href = "/inquiry"; //1대1 문의 작성 페이지로 이동
    },

    toggleBox(index) {
      this.displayedFaqList[index].isExpanded = !this.displayedFaqList[index].isExpanded;
    },
    //자주 묻는 질문 리스트
    faqqList() {
      this.$axiosSend('post', '/api/inquiry/faqList',)
        .then(res => {
          console.log(res)
          this.faqList = res.data;

          // 페이징 처리를 위한 변수들을 설정합니다.
          this.pageSize = 10; // 한 페이지에 보여줄 게시물 수를 설정합니다.
          this.totalPages = Math.ceil(this.faqList.length / this.pageSize); // 총 페이지 수를 계산합니다.
          this.currentPage = 1; // 현재 페이지 번호를 초기화합니다.

          // 페이징 처리된 데이터를 가져옵니다.
          this.displayedFaqList = this.faqList.slice(0, this.pageSize);

        })
        .catch(error => {
          alert(error);
        });
    },
    //자주 묻는 질문 리스트 검색
    faqsearch() {

      const keyword = this.form.keyword.trim(); // 입력된 검색어를 양쪽 공백을 제거하여 가져옵니다.

      if (!keyword) {
        alert("검색어를 입력해주세요!");
        return;
      }

      this.$axiosSend('get', '/api/inquiry/faqSerach', { keyword })
        .then((response) => {
          const dataFromBackend = response.data;
          console.log("Response from Backend:", dataFromBackend);
          this.faqList = response.data;
        })
        .catch((error) => {
          alert("검색되는 결과가 없습니다.");
          console.error("Error:", error);
        });
    },
  },
};
</script>