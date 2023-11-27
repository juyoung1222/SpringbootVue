<template>
  <p id="Cschead">고객센터</p>
  <div>
    <b-nav id="cscmy-nav">
      <b-nav-item v-for="(item, index) in navItems" :key="index" id="cscnav-item" :to="item.path" class="custom-nav-item">
        <span class="custom-nav-text">{{ item.title }}</span>
      </b-nav-item>
    </b-nav>
  </div>
  <div id="cscline"></div>
  <div id="cscdetail">자주하는 질문<b-button pill variant="outline-secondary" id="cscmo" @click="goToInquiryPage">1:1 문의하기</b-button>
  </div>
  <div class="csc-container">
    <div class="cscbox" v-for="(item, index) in boxItems" :key="index" :class="{ 'expanded': item.isExpanded }"
      @click="toggleBox(index)">
      <div class="cscquestion">{{ item.question }}</div>
      <div class="cscdivider" v-if="item.isExpanded"></div>
      <div class="cscanswer" v-if="item.isExpanded">{{ item.answer }}</div>
    </div>
  </div>
</template>

<script>
import '@/assets/css/CscViewStyle.css';
export default {
  data() {
    return {
      navItems: [
        { title: '강의상세', path: '/' },
        { title: '계정설정', path: '/about' },
        { title: '기기 및 재생환경', path: '/contact' },
        { title: '결제', path: '/contact' },
        { title: '해지/환불', path: '/contact' }
      ],
      boxItems: [
        { question: 'Q. 분야별 강의/취업 로드맵, 토픽, 챕터, 레슨 단위에 대해 알고 싶어요.', answer: 'BEDU 강의 구조는 다음과 같이 이루어져 있어요. 레슨(강의/노트/퀴즈/과제) < 챕터 < 토픽 < 분야별 강의/취업 로드맵 레슨은 동영상 강의, 노트, 퀴즈, 과제를 나타내는 가장 작은 단위의 수업이에요. 이를 묶어 놓은 것이 챕터, 그리고 챕터를 묶어 수강할 수 있도록 만든 것이 토픽입니다. 분야별 강의/취업 로드맵은 이 모든 과정을 가장 효과적으로 학습할 수 있도록 단계별로 배치한 것입니다.', isExpanded: false },
        { question: 'Q. 동영상 강의는 몇 분 정도인가요?', answer: 'BEDU 강의는 5분 내외의 짧은 영상으로 이루져있어요. 예상 소요시간은 각 토픽 페이지에서 확인하실 수 있습니다.', isExpanded: false },
        { question: 'Q. 강의를 듣다가 궁금증이 생기면 어떻게 해야 하나요?', answer: '레슨 하단 질문하기를 통해 커뮤니티에 궁금증을 남기고, 다른 수강생들과 함께 해결해보세요. 커뮤니티 정책을 참고하여 커뮤니티를 200% 활용해 보세요.', isExpanded: false },
        { question: 'Q. 강의 영상을 다운로드할 수 있나요?', answer: '아니요. 강의 내용은 다운로드가 불가능합니다. 다만 노트북, 태플릿, 스마트 폰 등 다양한 기기에서 언제든지 코드잇 강의를 수강하실 수 있습니다.', isExpanded: false }
      ],
    };
  },
  methods: {
    toggleBox(index) {
      this.boxItems[index].isExpanded = !this.boxItems[index].isExpanded;
    },
    goToInquiryPage() {
      window.location.href = "/inquiry"; // 원하는 문의 페이지의 URL로 변경해주세요
    }
  },
};
</script>

