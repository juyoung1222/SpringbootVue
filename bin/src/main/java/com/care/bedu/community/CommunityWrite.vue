<template>
    <b-container class="w-50">
        <h1>커뮤니티</h1>
      <b-form @submit="submit()">
          <b-form-input placeholder="작성자이름을 작성해주세요" class="my-5" v-model="form.writer" ref="writer"></b-form-input>
          <b-form-input placeholder="제목을 작성해주세요" class="my-5" v-model="form.title" ref="title"></b-form-input>
          <b-form-textarea class="form-control col-sm-5" rows="5" v-model="form.contents" placeholder="내용을 작성해주세요" ref="contents"></b-form-textarea>
          <b-container class="my-3 justify-content-md-end d-md-flex">
              <b-button class="" type="reset">취소</b-button>
              <b-button type="submit" class="btn-custom ms-2">등록</b-button>
          </b-container>
      </b-form>
    </b-container>
  </template>

<script>
import router from '@/router';

export default {
  
    name: 'communityWrite',
    data() {
        return {
            form:{
                title:'',
                contents : '',
                writer: '',
            }
        };
    },

    methods: {
      submit(){
         
          if(this.form.title == null || this.form.title == ""){
            this.$swal({
                title :'warning!',
                text :"제목을 입력해주세요",
                type :'warning',
                icon : 'warning',
                didClose: () => {
                  this.$refs.title.focus()
                }
              }
            )
            return;
          }
          if(this.form.contents == null || this.form.contents == ""){
            this.$swal({
              title :'warning!',
              text :"내용을 입력하세요",
              type :'warning',
              icon : 'warning',
            }
            )
            return;
          }
          const form = new FormData();
          form.append("title",this.form.title);
          form.append("contents",this.form.contents);
          form.append("writer",this.form.writer);

          this.$axios.post('/api/community/writer',form)
          .then(
              this.$swal('Success','작성완료!','success'),
              router.push({
                  name:"community"
              })
          )
          .catch((error)=>{
            console.log(error)
            this.$swal('Error','게시글이 정상적으로 작성되지 않았습니다','error')
          })
      }

    },
}
</script>

<style scoped>
.btn-custom{
  --bs-btn-bg:#3498db !important;
  --bs-btn-border-color : null !important;
}

.btn-custom:hover{
  background-color: #7ebae2 !important;
}


</style>