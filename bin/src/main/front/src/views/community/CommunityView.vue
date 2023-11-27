<template>
    <div class="community">
      <div class="community-title">
        <h1>커뮤니티</h1>
        <subtitle :subtitle="subtitle"/>
      </div>
      <div class="community-main">
        <div id="community-box">
          <input type="text" class="search-form"> <button class="btn btn-primary">검색</button>
          <b-button class="btn btn-primary" :to="'/communitywrite'" style="margin-left: 300px;">작성하기</b-button>
        </div>
      <table class="w3-table-all">
          <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>날짜</th>
            <th>조회수</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="community in communitylist" :key="community">
            <td>{{ community.num }}</td>
            <td><b-link class="text-start" :to='"/communitydetail"+"/"+community.num'>
              {{ community.title }}
            </b-link></td>
            <td>{{ community.writer }}</td>
            <td>{{ community.writeDate }}</td>
            <td>{{ community.view }}</td>
          </tr>
        
          </tbody>
        
        </table>
    
    </div>
      <!-- <div class="pagination w3-bar w3-padding-16 w3-small" v-if="paging.total_list_cnt > 0">
        <span class="pg">
        <a href="javascript:;" @click="fnPage(1)" class="first w3-button w3-bar-item w3-border">&lt;&lt;</a>
        <a href="javascript:;" v-if="paging.start_page > 10" @click="fnPage(`${paging.start_page-1}`)"
           class="prev w3-button w3-bar-item w3-border">&lt;</a>
        <template v-for=" (n,index) in paginavigation()">
            <template v-if="paging.page==n">
                <strong class="w3-button w3-bar-item w3-border w3-green" :key="index">{{ n }}</strong>
            </template>
            <template v-else>
                <a class="w3-button w3-bar-item w3-border" href="javascript:;" @click="fnPage(`${n}`)" :key="index">{{ n }}</a>
            </template>
        </template>
        <a href="javascript:;" v-if="paging.total_page_cnt > paging.end_page"
           @click="fnPage(`${paging.end_page+1}`)" class="next w3-button w3-bar-item w3-border">&gt;</a>
        <a href="javascript:;" @click="fnPage(`${paging.total_page_cnt}`)" class="last w3-button w3-bar-item w3-border">&gt;&gt;</a>
        </span>
      </div> -->
      <div id="app">
  </div>
    </div>
  </template>
  <script>
  export default {

    data() { //변수생성
      return {
        // requestBody: {}, //리스트 페이지 데이터전송
        communitylist: [], //리스트 데이터
        totalItems : 0,
        totalPage : 0,
        viewpath: 0,
        currentPage : 1,
        order : '최신순',
        keyword : '',
        // paging: {
        //   block: 0,
        //   end_page: 0,
        //   next_block: 0,
        //   page: 0,
        //   page_size: 0,
        //   prev_block: 0,
        //   start_index: 0,
        //   start_page: 0,
        //   total_block_cnt: 0,
        //   total_list_cnt: 0,
        //   total_page_cnt: 0,
        // }, //페이징 데이터
        // page: this.$route.query.page ? this.$route.query.page : 1,
        // size: this.$route.query.size ? this.$route.query.size : 10,
        // keyword: this.$route.query.keyword,
        // subtitle: '커뮤니티',
        // paginavigation: function () { //페이징 처리 for문 커스텀
        //   let pageNumber = [] //;
        //   let start_page = this.paging.start_page;
        //   let end_page = this.paging.end_page;
        //   for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        //   return pageNumber;
        // }
      }
    },
    mounted() {
      // this.boo()
      this.List();
    },
    methods: {
      List(){
          this.$axios.get('/api/community/boardList')
          .then(res => {
          console.log(res);
          this.communitylist = res.data;
        })
          .catch(error => {
            alert('불러오기 실패!');
            console.log(error, '실패함!!')
          })
      },
    },
  }
  </script>
<style scoped>
    
    table{
        margin-left:auto; 
        margin-right:auto;
        width: 100%;
        border: 1px solid #444444;
    }

    .community-main{
      margin-left:auto; 
        margin-right:auto;
      width: 700px;
    }
    #community-box{

    }
</style>