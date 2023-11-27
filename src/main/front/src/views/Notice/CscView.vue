<!--작성자:황용학-->


<template>
  <div class="d-flex">
    <div class="cscView">
      <CscCategory :currentTab="'inquiry'"></CscCategory>
    </div>
    <div id="cscMain">
      <div>
        <div>
          <h2>이용 문의 </h2>
          <div id="cscBoradSearch">
            <div class="button-container1">
              <div @submit="inquirysearch()" class="searchForm">
                <font-awesome-icon id="csc-search-icon" :icon="['fas', 'magnifying-glass']" />
                <input class="cscviewkeyword" v-model="form.keyword" @keyup.enter="inquirysearch">
                
                <!--웹 1대1 문의하기 버튼-->
                <b-button class="bedu-bg-custom-blue csc-writepath-btn" id="csc-writepath-btn1" @click="goToInquiryPage">
                  <font-awesome-icon :icon="['fas', 'pencil']" />
                  문의하기
                </b-button>
              </div>
              </div>

              <!--모바일 1대1 문의하기 버튼-->
              <div class="button2">
                <b-button class="bedu-bg-custom-blue csc-writepath-btn" id="csc-writepath-btn2" @click="goToInquiryPage">
                  <font-awesome-icon :icon="['fas', 'pencil']" />
                  문의하기
                </b-button>
            </div>
          </div>
        </div>
      </div>
      <table id="cscboard-table">
        <thead>
          <tr>
            <th id="cscTitle">제목</th>
            <th>작성자</th>
            <th>작성일자</th>
            <th>답변여부</th>
          </tr>
        </thead>
        <tbody>
          <tr :key="index" v-for="(inquiry, index) in paginatedInquiryList"  @click="password(getCls, inquiry)"><!--1대1 문의사항 리스트-->
            <td id="cscboard-table-tds">
              <b-link class="text-start text-body">
                <font-awesome-icon :icon="['fas', 'lock']" /> {{ inquiry.title }} <!--1대1 문의사항 제목-->
              </b-link>
            </td>
            <td>
              {{ inquiry.userName }}</td> <!--1대1 문의사항 닉네임-->
            <td>{{ formatDateTime(inquiry.regDate) }}</td> <!--1대1 문의사항 날짜-->
            <td id="replyCnt">
              <div v-if="inquiry.replyCnt > 0">답변완료</div>
              <div v-else>답변대기</div>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 검색 결과가 없을 때 -->
      <p v-if="inquirylist.length === 0 && form.keyword.trim() !== ''" id="searched">검색되는 결과가 없습니다.</p>
      <!--페이징 진행-->
      <div class="pagination-container1">
      <b-pagination  v-model="currentPage" :total-rows="inquirylist.length" :per-page="pageSize"></b-pagination>
    </div>
    </div>
  </div>
</template>

<script>

import CscCategory from '@/components/CscCategory.vue';
//import InquiryDetail from '@/Notice/InquiryDetail.vue';

import '@/assets/css/CscViewStyle.css';
export default {
  name: 'inquirylist',

  data() {
    return {
      inquirylist: [
      ],

      form: {
        keyword: '',
      },
      sortOption: "default", // 정렬 옵션
      currentPage: 1,
      replyCnt: '',
      inquirybutton: false,
    };
  },
  created() {
    this.inquiryList();
  },

  components: {
    CscCategory
  },

  mounted() {
  },

  computed: {
    paginatedInquiryList() {

      // 현재 페이지에 해당하는 데이터의 시작과 끝 인덱스를 계산합니다.
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;

      // 현재 페이지에 해당하는 데이터를 추출하여 반환합니다.
      return this.inquirylist.slice(startIndex, endIndex);
    },

    //관리자 권한 비밀번호 없이 게시글 입장
    getCls() {
      const cls = this.$store.getters.getCls;
      console.log('getCls:', cls); // 디버깅용으로 cls 값을 출력
      return cls;

    },
  },

  methods: {
    //1대1 문의하기 페이지로 이동
    goToInquiryPage() {
      window.location.href = "/inquiry"; // 원하는 문의 페이지의 URL로 변경해주세요
    },
    //1대1 문의 게시 리스트
    inquiryList() {
      this.$axiosSend('post', '/api/inquiry/inquiryList',)
        .then(res => {
          console.log(res)
          this.inquirylist = res.data;

          // 페이징 처리를 위한 변수들을 설정합니다.
          this.pageSize = 15; // 한 페이지에 보여줄 게시물 수를 설정합니다.
          this.totalPages = Math.ceil(this.inquirylist.length / this.pageSize); // 총 페이지 수를 계산합니다.
          this.currentPage = 1; // 현재 페이지 번호를 초기화합니다.

          // 페이징 처리된 데이터를 가져옵니다.
          this.paginatedInquiryList = this.inquirylist.slice(0, this.pageSize);

        })
        .catch(error => {
          alert(error);
        });
    },

    //1대1 문의 검색
    inquirysearch() {

      const keyword = this.form.keyword.trim(); // 입력된 검색어를 양쪽 공백을 제거하여 가져옵니다.

      if (!keyword) {
        alert("검색어를 입력해주세요!");
        return;
      }
      this.$axiosSend('get', '/api/inquiry/inquirySerach', { keyword })
        .then((response) => {
          const dataFromBackend = response.data;
          console.log("Response from Backend:", dataFromBackend);
          this.inquirylist = response.data;
        })
        .catch((error) => {
          alert("검색되는 결과가 없습니다.");
          console.error("Error:", error);
        });
    },
    //비밀번호 비교 하는 함수 "ADMIN"은 그냥 입장 가능
    password(userName, inquiry) {
      this.$swal({
        title: '비밀번호를 입력하세요',
        html: '<input id="test" type="password">',
      })
        .then((result) => {
          if (result.isConfirmed) {
            // 사용자가 입력한 비밀번호
            const userInput = document.getElementById("test").value;
            this.$axiosSend('get', '/api/inquiry/inquiryDetail', {
              vocNum: inquiry.vocNum,
              userInput: userInput
            })
            if (userInput !== null) {
              // 입력받은 비밀번호와 inquiry.password 비교
              if (userName == "ADMIN" || userInput == inquiry.password) {
                this.$routerPush('inquiryDetail', { vocNum: inquiry.vocNum }, true)
              } else {
                this.$swal({
                  title: '올바른 비밀번호를 입력해주세요',
                  icon: 'error',
                })
              }
            }
          }
        })
    },

    //날짜
    formatDateTime(value) {
      // value는 날짜 값입니다
      const now = new Date();
      const date = new Date(value);

      const diffInMilliseconds = now - date;
      const diffInSeconds = Math.floor(diffInMilliseconds / 1000);
      const diffInMinutes = Math.floor(diffInSeconds / 60);
      const diffInHours = Math.floor(diffInMinutes / 60);
      const diffInDays = Math.floor(diffInHours / 24);

      if (diffInDays > 0) {
        return `${diffInDays}일 전`;
      } else if (diffInHours > 0) {
        return `${diffInHours}시간 전`;
      } else if (diffInMinutes > 0) {
        return `${diffInMinutes}분 전`;
      } else {
        return '방금 전';
      }
    },
  },
};
</script>