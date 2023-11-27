<!--작성자:황용학-->


<template>
  <div class="cscView" id="cscView">
    <CscCategory :currentTab="'inquiry'"></CscCategory>
  </div>
  <p id="Cschead">이용 문의</p>
  <div class="card"> 
    <form @submit="inquiryWrite()">
      <div>
        <label id="title" for="title">문의 목록</label>
        <b-form-select id="selectbox" v-model="selected" :options="options"></b-form-select>
        <label id="title" for="title">제목</label>
        <b-form-input placeholder="제목을 입력해주세요" id="titletext" v-model="form.title" ref="title"></b-form-input>
      </div>
      <div>
        <label id="title" for="password">비밀번호</label>
        <b-form-input placeholder="숫자만 입력해주세요" id="titletext" v-model="form.password" ref="title"></b-form-input>
      </div>
      <div>
        <label id="textarea" for="content">질문 내용</label>
        <div>
          <b-form-textarea id="textarea-rows" v-model="form.content" placeholder="내용을 작성해주세요" ref="content" rows="7"></b-form-textarea>
        </div>
      </div>
      <div>
        <b-button @click="inquiryWrite()" id="commit" class="w-25 p-1">저장</b-button>
        <b-button id="inquiry-cancel" :to="'/csc'" class="w-25 p-1">취소</b-button>
      </div>
    </form>
  </div>
</template>

<script>
import CscCategory from '@/components/CscCategory.vue';
import '@/assets/css/inquiryStyle.css';
import router from '@/router';
export default {

  components: {
    CscCategory
  },

  name: 'inquiryWrite',
  data() {
    
    return {

      selected: null,
      options: [
        { value: null, text: '강의상세' },
        { value: 'a', text: '계정설정' },
        { value: 'b', text: '기기 및 재생환경' },
        { value: 'c', text: '결제' },
        { value: 'd', text: '해지환불'},
        { value: 'e', text: '기타'}
      ],

      form: {
        userName: '', 
        title: '',
        content: '',
        regId: '',
        password: '',
      },
    };
  },

  mounted() {
            this.form.userName =this.$store.getters.getNickname;
            if(this.form.userName === '' || this.form.userName === null){
                this.$swal('Error','로그인을 해주세요!');
                router.push({
                    name: "login"
                })
                return;
            }
            this.form.regId = this.$store.getters.getEmail;
            console.log(this.form.regId);
        },

        created() {
            this.form.userName = this.$store.getters.getNickname;
            if(this.form.userName === '' || this.form.userName === null){
                this.$swal('Error','로그인을 해주세요!');
                router.push({
                    name: "login"
                })
                return;
            }
            
        },

  methods: {
    //문의하기 작성 페이지
    inquiryWrite() {

      if (this.form.title == null || this.form.title == "") {
        this.$swal({
          title: 'warning!',
          text: "제목을 입력해주세요",
          type: 'warning',
          didClose: () => {
            this.$refs.title.focus()
          }
        })
        return;
      }
      if (this.form.content == null || this.form.content == "") {
        this.$swal({
          title: 'warning!',
          text: "내용을 입력하세요",
          type: 'warning',
        })
        return;
      }
      if (this.form.password == null || this.form.content == "") {
        this.$swal({
          title: 'warning!',
          text: "비밀번호를 입력하세요",
          type: 'warning',
        })
        return;
      }
      this.$axiosSend('post', '/api/inquiry/inquiryWrite', this.form)
                .then((res) => {
                    if (res.data === 1) {
                        this.$swal('Success', '작성완료!', 'success'),
                        router.push({
                            name: "csc"
                        })
                    }
                })
                .catch((error) => {
                    this.$swal('Error', '게시글이 정상적으로 작성되지 않았습니다.', error);
                })
            }

        },
    }
</script>

