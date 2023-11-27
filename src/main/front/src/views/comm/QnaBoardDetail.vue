//작성자 변의준


<template>
    <div class = "d-flex">
        <div class = "qnaDetail d-none d-xxl-block">
            <CommCategory></CommCategory>
        </div>
        <div class="qna-detail-main justify-content-start text-start" id="qna-detail-main">
            <div class="justify-content-start text-start qna-detail-body" id="qna-detail-body">
                <h2 class=" fw-bold qna-detail-title" id="qna-detail-title">
                    {{ qna.title }}
                </h2>
                <div id="qna-userinfo">
                    <p id="qna-userid">
                        {{ qna.userName}}
                    </p>
                    <p id="qna-comm">
                        <font-awesome-icon :icon="['fas', 'eye']"/> {{ qna.qnaCnt }}
                    </p>
                    <p id="qna-date">
                        {{ DateTime(qna.qnaDate) }} 
                    </p>
                </div>
                <div id="qna-detail-contents">
                    <!-- {{ qna.content }} -->
                    <div v-html="qna.content"></div>
                    <div id="qna-likeyn">
                    <button id="qna-likebtn" @click="qnalikeUp(qna.qnaBdNum)" :class="[backLikedClass()]">
                        <font-awesome-icon :icon="['fas', 'heart']"
                        :class="[fontLikedClass()]"
                            />
                            <text class="fw-bold ms-2 qna-detail-likeyn" id="qna-detail-likeyn">
                                {{ qna.qnaLikeCnt }}
                            </text>
                    </button>
                    
                    <b-button type="button" style="margin-top: 120px;"  class="bedu-bg-custom-blue btn-custom ms-2 qnaboard-detail-editbtn" id="qnaboard-detail-editbtn" @click="qnaeditPath(qna.qnaBdNum)">글수정</b-button>
                    <b-button type="button" style="margin-top: 120px;" class="btn-custom ms-2 qnaboard-detail-deletebtn" id="qnaboard-detail-deletebtn" @click="qnadelete(qna.qnaBdNum)">삭제</b-button>    
                </div>
                </div>
                
                <hr style="margin-top: 20%;"/>
                <div class="mb-3 qna-detail-btns" id="qna-detail-btns">
                    <b-button type="button" class="btn-custom ms-2 qnaboard-detail-rewrite"  @click="answrite()" id="qnaboard-detail-rewrite">등록</b-button>
                    <b-button type="button" class="btn-custom ms-2 qnaboard-detail-recensell" @click="censells()" id="qnaboard-detail-recensell">취소</b-button>
                    <b-button type="button" class="btn-custom ms-1 qnaboard-detail-replybtn" id="qnaboard-detail-replybtn" @click="ansopen()">답변작성</b-button>
                    <b-button type="button" class="btn-custom ms-2 qnaboard-detail-viewpath" :to="'/comm/qna'">목록</b-button>
                    <b-button type="button" class="btn-custom ms-1 qnaboard-detail-adminqnadel" v-if="admindelbtn() == 1" id="qnaboard-detail-adminqnadel" @click="adminqnadel(qna.qnaBdNum)">관리자 삭제</b-button>
                    <b-button type="button" class="btn-custom ms-1 qnaboard-detail-adminalldel" v-if="admindelbtn() == 1" id="qnaboard-detail-adminalldel" @click="ansalldel()">답변전체삭제</b-button>
                </div>

                <div style="margin-top: 80px;">
                    <p class = "fw-bold fs-5">
                        <font-awesome-icon :icon="['far', 'comment']" class="qna-detail-icon"/>
                        답변 {{anstotal}}개
                    </p>
                </div>
                <div class="qnaboard-detail-replywrite" id="qnaboard-detail-replywrite" >
                    <h4>답변을 작성해주세요.</h4>
                    <b-form>
                        <textarea v-model="form.content" ref="content" rows="10" cols="100"></textarea>
                    </b-form>
                </div>
                <div class="qna-detail-replylists">
                    <div v-for="(ans,index) in anslist" :key="index" class="qna-detail-replylist">
                        <div class="d-flex mb-3 mt-4">
                            <div class="qnauser">
                                <font-awesome-icon :icon="['fas', 'user']" size="xl" />
                            </div>
                            <div class = "name">
                                {{ ans.userName }}
                            </div>
                            <div class="date">
                                {{ DateTime(ans.ansDate) }}
                            </div>
                        </div>
                        
                        <div class="qna-detail-anscon" :ref="'qna-detail-ans'+index">
                            <div class="qnaReplyContent" v-html="ans.content"></div>
                            <div class="qna-detail-replybtns" id="qna-detail-replybtns">
                                <div class="qna-detail-replywriteBtn" id="qna-detail-replywriteBtn">
                                    <b-button type="button" v-if="anseditbtneqlse(ans.userName) == 1" class="btn-custom btn-custom mt-1 qna-detail-replywrite-btn" id="qna-detail-replywrite-btn" @click="replyopen(ans.ansBdNum, ans.userName, index)">댓글작성</b-button>
                                </div>
                                <div id="qna-detail-replyDelBtn" class="qna-detail-replyDelBtn" style="margin-left: 30px;" v-if="ansdelbtneqlse(ans.userName) == 1">
                                    <b-button type="button" class="btn-custom btn-custom mt-1 qna-detail-replywrite-btn" id="qna-detail-ansdel-btn" @click="ansdelete(ans.ansBdNum, ans.userName, ans.regId)">답변삭제</b-button>
                                </div>

                                <b-button type="button" v-if="btn(ans.userName) == 1" class="btn-custom btn-custom mt-1 qna-detail-editopen-btn" id="qna-detail-editopen-btn" @click="anseditopen(index)">답변수정</b-button>
                                <b-button type="button" v-if="admindelbtn() ==1" class="btn-custom btn-custom mt-1 qna-detail-adminreplyall-btn" id="qna-detail-adminreplyall-btn" @click="replyadminalldel(ans.ansBdNum, ans.userName)">댓글전체삭제</b-button>
                            </div>
                            
                            <div class="qna-detail-rewrites" id="qna-detail-rewrites">
                                <b-form>
                                    <b-form-input placeholder="댓글을 작성해주세요" class="mt-4 mb-2" id="reply-write-content" v-model="reply.content" ref="content"></b-form-input>
                                    <b-button type="button" class="bedu-bg-custom-blue" @click="replywrite(ans.ansBdNum, ans.userName, index)">댓글등록</b-button>
                                    <b-button type="reset" class="qna-detail-replycensell" style="margin-left: 20px;" @click="replycensell(ans.ansBdNum, ans.userName, index)">취소</b-button>
                                </b-form>
                            </div>

                            <div class="qna-detail-ansconedit" id="qna-detail-ansconedit">
                                <b-form-input class="mt-4 mb-2 qna-detail-ansedit" id="qna-detail-ansedit" v-model="ans.content" ref="content"></b-form-input>
                                <b-button type="button" class="btn-custom ms-1 btn-custom ms-2 qna-detail-anseditbtn" id="qna-detail-anseditbtn" @click="ansedit(ans.ansBdNum, ans.userName, ans.content, index)">수정</b-button>
                                <b-button type="button" class="btn-custom ms-1 btn-custom ms-2 qna-detail-anseditcensell" id="qna-detail-anseditcensell" @click="anseditcensell(index)">닫기</b-button>
                            </div>
                            <p class = "fw-bold fs-5 qna-detail-replytotalicon">
                                <font-awesome-icon :icon="['far', 'comment']" />
                            댓글 {{ ans.replyTotal }}개
                            </p>
                            <div id="qna-detail-replyCont">
                                <div v-for="(reply, index) in replylist" :key="index" class="d-flex qna-detail-replylistes" id="qna-detail-replylistes">
                                    <div class="d-flex mb-3 mt-3 qna-detail-replylistescon" id="qna-detail-replylistescon" v-if="ansnumeq(ans.ansBdNum, reply.ansNum) == 1" :ref="'commant-container-'+index">
                                        <div class="qnauser">
                                            <font-awesome-icon :icon="['fas', 'user']" size="xl" />
                                        </div>
                                        <div class="qnaReplyName" style="margin-left: 10px;">
                                            {{ reply.userName }}
                                        </div>
                                        <div class="qnaReplyDate" style="margin-left: 10px;">
                                            {{ DateTime(reply.replyDate) }}
                                        </div>
                                        <div class="qnareplyDel-btn" id="qnareplyDel-btn" v-if="replydeleteEq(reply.userName)==1">
                                            <b-button type="button" class="btn-custom ms-1 btn-custom ms-2 qnareplydel-btn" id="qnareplydel-btn" @click="replydelete(reply.replyNum, reply.qsNum, reply.ansNum, reply.userName)">댓글삭제</b-button>
                                            
                                        </div>
                                        <div class="qnareplyeditBtns" id="qnareplyeditBtns" v-if="replyeqbtn(reply.userName) == 1">
                                            <b-button type="button" class="btn-custom ms-1 btn-custom ms-2 qnareplyeditform-btn" id="qnareplyeditform-btn" @click="replyeditopen(index)">댓글수정</b-button>
                                        </div>
                                        <div class="qnareplyeditcontent" id="qnareplyeditcontent">
                                            <b-form-input class="replyeditcontent" v-model="reply.content" maxlength='1000'></b-form-input>
                                            <b-button type="button" class="btn-custom ms-1 btn-custom ms-2 qnareplyEdit-btn" id="qnareplyEdit-btn" @click="replyedit(reply.replyNum, reply.userName, reply.content, index)">수정</b-button>
                                            <b-button type="button" class="btn-custom ms-1 btn-custom ms-2 qnareplyform-censell" id="qnareplyform-censell" @click="replyeditcensell(index)">닫기</b-button>
                                        </div>
                                        <div class="qnacontent" v-html="reply.content"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                           
                    </div>
                </div>   
            </div>
            <br>
        </div>
    </div>
</template>

<script>
import CommCategory from '@/components/CommCategory.vue';
import router from '@/router';
import '@/assets/css/qnaStyle.css';
import Editor from 'ckeditor5-custom-build/build/ckeditor'; 

export default{

    components:{
        CommCategory
    },

    data() {
        return {

            editor: Editor,
            editorConfig: {
                resize_minHeight : 800,
                // The configuration of the editor.
                simpleUpload: {
                        // 업로드 URL
                        uploadUrl: '/api/studyUpload',
                        method : 'POST'
                        
                    },
                mediaEmbed: {
                    previewsInData: true
                },
            },
            //질문글번호
            qnum:0,
            //답변 리스트
            anslist:[],
            //답변총개수
            anstotal:0,
            replyTotal:0,
            //like테이블의 글번호
            likenum:0,
            username:'',
            //로그인닉네임
            userNickName:'',
            //질문글 좋아요 여부 문자
            likeyn:'q',
            editcon:'n',
            ansid:'',
            //댓글 리스트
            replylist:[],
            //답변의 댓글 객체
            reply:{
                content:'',
                qnaBdNum:0,
                ansNum:0,
                userName : '',
                regId:''
            },
            // ans(답변객체)
            form:{          
                ansBdNum:0,
                ansDate:'',
                content:'',
                qsBdNum:0,
                regDate:'',
                regId:'',
            },
            // 질문상세글객체
            qna:{
                qnaBdNum:0,
                userName : '',
                regId:''
            }
        }
    },
    computed:{
        getQsnumList() {
            return this.$store.getters.getQsbnumList;
        },
    },
    mounted() {
        const qnanum = this.$route.params.num;
        this.userNickName =this.$store.getters.getNickname;
        this.form.regId = this.$store.getters.getEmail;

        if(this.userNickName === null || this.userNickName ===""){
            this.qnaReadtet(qnanum);
            this.qnaRead(qnanum);
        }else{
            this.qnaRead(qnanum);
        }

        this.ansread(qnanum);
        this.replyread(qnanum);
        this.ansgetTotal(qnanum);
        document.getElementById("qnaboard-detail-recensell").style.display="none";
        this.form.ansBdNum = qnanum;
        
    },
    methods: {

        //게시글 조회한후 시간 표현, 수정 삭제 버튼 노출 메소드
        qnaReadtet(qnanum){ // 게시글 데이터 조회
            this.$axiosSend('get','/api/qna/editdetail',{num : qnanum,})
            .then(res=>{
                this.qna = res.data;                
                this.qna.strQnaDate = this.DateTime(this.qna.qnaDate);
                this.nicknameEquals(this.qna.userName);
            })
            .catch((error)=>{
                console.log(error);
            })
        },
        //답변개수조회
        getCountAns(){
            this.$axiosSend('get', '/api/ans/count', {
            }).then(res => {
                this.qsCount = res.data;
            })
            .catch((error) => {
                this.$swal('Error', '답변이 정상적으로 조회되지 않았습니다.', error);
            })
        },

        //닉네임 존재 여부 확인에 따른 답변 작성 버튼 
        nicknameEquals(nickname){
            if(this.userNickName === null || this.userNickName ===""){
                document.getElementById("qnaboard-detail-replybtn").style.display="none";
            }
            if(this.userNickName !== nickname){
                document.getElementById("qnaboard-detail-editbtn").style.display="none";
                document.getElementById("qnaboard-detail-deletebtn").style.display="none";  
            }
        },

        //전체답변삭제버튼(관리자)
        admindelbtn(){
            if(this.userNickName != null && this.userNickName == 'ADMIN'){
                return 1;
            }else{
                return 0;
            }
        },
        //관리자 질문 게시글 삭제
        adminqnadel(qnanum){
            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }

            if(this.userNickName !='ADMIN'){
                this.$swal('관리자 권한으로만 삭제 가능합니다.', 'success');
                return;
            }

            this.$swal({
                title: '관리자 권한으로 해당 게시글을 삭제 하시겠습니까? (삭제시 모든 답변, 댓글이 강제적으로 전부 삭제 됩니다.)',
                showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                confirmButtonColor: '#303076',
                confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                }).then(result => {
                    if (result.isConfirmed) {                   // 만약 알럿창에서 확인 버튼을 눌렀다면

                        this.$axiosSend('get','/api/qna/qnaDelete', {
                            qnaBdNum: qnanum,
                            userName: this.userNickName
                        })
                        .then(res => {
                            if(res.data ===1){
                            this.$swal('Success', '관리자 권한으로 게시글을 삭제 완료하였습니다.', 'success'),
                                router.push({
                                    name: "qnaBoard"
                                })
                            }    
                        })
                        .catch(error => {
                            alert(error);
                        })
                    } 
                }).catch(error => {
                    alert(error);
                })

        },

        //전체 답변삭제(관리자)
        ansalldel(){

            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }

            if(this.anstotal > 0){

                this.$swal({
                    title: '관리자 권한으로 모든 답변을 강제로 삭제 하시겠습니까?',
                    showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                    cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                    confirmButtonColor: '#303076',
                    confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                    cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                }).then(result => {
                    if (result.isConfirmed) {
                        this.$axiosSend('get','/api/ans/ansdelete', {
                            qsBdNum : this.qna.qnaBdNum,
                            userName: this.userNickName
                    })
                    .then(res => {
                        if(res.data > 0){
                            this.$swal('Success', '관리자 권한에 의한 답변삭제가 완료 되었습니다.', 'success');
                            this.ansread(this.qna.qnaBdNum);
                            this.ansgetTotal(this.qna.qnaBdNum);
                            return;
                        }else{
                            this.$swal('error', '답변전체삭제실패!', 'error');
                            this.ansread(this.qna.qnaBdNum);
                            return;
                        }    
                    })
                    .catch(error => {
                        this.$swal(error, '답변전체삭제실패!', 'error');
                    })
                    }
                })
                .catch((error)=>{
                    this.$swal('Error','답변이 정상적으로 수정되지 않았습니다',error);
                })

            .catch((error)=>{
                this.$swal('Error','답변이 정상적으로 수정되지 않았습니다',error);
            })
            }
        },

        //댓글전체삭제admin
        replyadminalldel(ansnum, username){

            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }
            this.$axiosSend('get','/api/reply/replyTotal', {
                ansNum: ansnum,
            })
            .then(res => {
                
                if(res.data>0){

                    this.$swal({
                        title: '관리자 권한으로 댓글을 전체 삭제 하시겠습니까?',
                        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                        cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                        confirmButtonColor: '#303076',
                        confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                        cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                    }).then(result => {
                        if (result.isConfirmed) {
                            this.$axiosSend('get','/api/reply/replydelete', {
                                ansNum : ansnum,
                                userName: this.userNickName
                            })
                            .then(res => {
                                if(res.data > 0){
                                    this.$swal('Success', '관리자 권한에 모든 댓글이 삭제 되었습니다.', 'success');
                                    this.ansread(this.qna.qnaBdNum);
                                    this.ansgetTotal(this.qna.qnaBdNum);
                                    this.replyread(this.qna.qnaBdNum);
                                    return;
                                }else{
                                    this.$swal('error', '댓글전체삭제실패!', 'error');
                                    this.ansread(this.qna.qnaBdNum);
                                    return;
                                }    
                            })
                            .catch(error => {
                                this.$swal(error, '댓글전체삭제실패!', 'error');
                            })
                        }
                    })
                    .catch((error)=>{
                        this.$swal('Error','답변이 정상적으로 수정되지 않았습니다',error);
                    })
                }else{
                    this.$swal('삭제할 댓글이 존재하지않습니다.');
                }
            }).catch((error)=>{
                console.log(error);
            })
            
        },

        //로그인 닉네임과 글 작성 닉네임 동일하거나 관리자면 답변수정버튼노출
        btn(username){
            if(this.userNickName == username || this.userNickName=='ADMIN'){
                return 1;
            }else{
                return 0;
            }
        },

        //답변수정처리후 답변 조회
        ansedit(ansnum, username, content, index){
            if(this.userNickName =="" || this.userNickName== null || ansnum ==0){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }else if(this.userNickName == username && this.userNickName != 'ADMIN'){
                this.$axiosSend('get','/api/reply/replyTotal', {
                ansNum: ansnum,
                })
                .then(res => {
                    if(res.data >0){                                            //답변에 댓글 개수가 1개라도 존재시 직접삭제불가(관리자문의)
                        this.$swal('이미 댓글이 존재하여 수정할수없습니다. 관리자한테 문의해 주세요.');
                        return;
                    }else{

                        this.$axiosSend('get','/api/ans/ansedit',{
                            ansBdNum: ansnum,
                            content: content
                        })
                        .then(res => {
                            if(res.data === 1){
                                this.$swal('Success','해당답변수정이 완료되었습니다!');
                                this.ansread(this.qna.qnaBdNum);
                                this.anseditcensell(index);
                                return;
                            }
                        })
                        .catch((error)=>{
                            this.$swal('Error','해당답변수정이 실패했습니다.',error);
                        })
                        return;
                    }
                }).catch((error)=>{
                    console.log(error);
                })
                
            }else if(this.userNickName =='ADMIN'){
                this.$swal({
                        title: '관리자 권한으로 답변을 수정 하시겠습니까?',
                        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                        cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                        confirmButtonColor: '#303076',
                        confirmButtonText: '수정', // confirm 버튼 텍스트 지정
                        cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                }).then(result => {
                        if (result.isConfirmed) {
                            this.$axiosSend('get','/api/ans/ansedit', {
                                ansNum : ansnum,
                                userName: this.userNickName
                            })
                            .then(res => {
                                if(res.data > 0){
                                    this.$swal('Success', '관리자권한으로 해당 답변이 수정 되었습니다.', 'success');
                                    this.ansread(this.qna.qnaBdNum);
                                    this.ansgetTotal(this.qna.qnaBdNum);
                                    this.replyread(this.qna.qnaBdNum);
                                    return;
                                }else{
                                    this.$swal('error', '관리자권한으로 답변이 수정실패!', 'error');
                                    this.ansread(this.qna.qnaBdNum);
                                    this.ansgetTotal(this.qna.qnaBdNum);
                                    this.replyread(this.qna.qnaBdNum);
                                    return;
                                }    
                            })
                            .catch(error => {
                                this.$swal(error, '해당답변수정이 실패했습니다', 'error');
                            })
                        }
                    })
                    .catch((error)=>{
                        this.$swal('Error','답변이 정상적으로 수정되지 않았습니다',error);
                    })
                    return;
            }
            else{
                this.$swal('본인 글만 수정가능합니다.');
                return;
            }
            
        },

        backLikedClass() {
           
            const qsbnumList = JSON.parse(localStorage.getItem('qsbnumList')) || [];
            return qsbnumList.includes(this.qna.qnaBdNum) ? 'qnaBackColor' : 'qnaDefault';
        },

        fontLikedClass() {
            
            const qsbnumList = JSON.parse(localStorage.getItem('qsbnumList')) || [];
            return qsbnumList.includes(this.qna.qnaBdNum) ? 'fontBackColor' : '';
        },

        //유저 아이디 동일비교후 수정버튼노출
        replyeqbtn(username){
            if(this.userNickName == username || this.userNickName == 'ADMIN'){
                return 1;
            }else{
                return 0;
            }
        },

        //유저 아이디 동일비교후 삭제버튼노출 관리자 로그인이면 노출
        replydeleteEq(username){
            if(this.userNickName == username || this.userNickName == 'ADMIN'){
                return 1;
            }else{
                return 0;
            }
        },

        //댓글 답변번호 비교
        ansnumeq(ansbdnum, ansNum){
            if(ansbdnum == ansNum){
                return 1;
            }else{
                return 0;
            }
        },

        anseditbtneqlse(username){
            if(this.userNickName == username || this.userNickName == 'ADMIN'){
                return 1;
            }else{
                return 0;
            }
        },

        //댓글 폼 열기
        replyeditopen(index){
            this.$refs['commant-container-'+index][0].children[3].classList.add("d-none");
            this.$refs['commant-container-'+index][0].children[4].classList.add("d-none");
            this.$refs['commant-container-'+index][0].children[5].classList.add("d-block");
            this.$refs['commant-container-'+index][0].children[6].classList.add("d-none");
        },

        //댓글 지우기
        replyeditcensell(index){
                    
            this.$refs['commant-container-'+index][0].children[3].classList.remove("d-none");
            this.$refs['commant-container-'+index][0].children[4].classList.remove("d-none");
            this.$refs['commant-container-'+index][0].children[5].classList.remove("d-block");
            this.$refs['commant-container-'+index][0].children[6].classList.remove("d-none");
        },

        //답변 닫기
        anseditcensell(index){
            this.$refs['qna-detail-ans'+index][0].children[0].classList.remove("d-none");
            this.$refs['qna-detail-ans'+index][0].children[3].classList.remove("d-block");
        },

        //답변 폼 열기
        anseditopen(index){
            this.$refs['qna-detail-ans'+index][0].children[0].classList.add("d-none");
            this.$refs['qna-detail-ans'+index][0].children[3].classList.add("d-block");
            var anscon= document.getElementById('qna-detail-ansedit');
            var ans = anscon.value;
            var textWithoutTag = ans.replace("/(<()>)?/ig", "");
            // var anscon= document.getElementById("qna-detail-ansedit");

            // anscon.replace(/<\/?("[^"]*"|'[^']*'|[^>])*(>|$)/gi, "");
        },

        //댓글 작성폼열기
        //
        replyopen(ansnum, username, index){
            
            this.$refs['qna-detail-ans'+index][0].children[1].classList.add("d-none");
            this.$refs['qna-detail-ans'+index][0].children[2].classList.add("d-block");
        },

        //댓글 닫기
        replycensell(ansnum, username, index){
            this.$refs['qna-detail-ans'+index][0].children[1].classList.remove("d-none");
            this.$refs['qna-detail-ans'+index][0].children[2].classList.remove("d-block");
        },

        //댓글 수정
        replyedit(replynum, replyuser, content, index){

            if(this.userNickName =="" || this.userNickName== null || replynum == 0){            
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }else if(content == null || content == ""){                             
                this.$swal('내용을 수정해주세요.', 'success');
                return;
            }else if(this.userNickName =='ADMIN'){                                  // 로그인 닉네임 관리자일시 어떤 글이든 즉시 수정
                this.reply.content = content;
                this.reply.replyNum = replynum;
                this.$axiosSend('post','/api/reply/replyEdit',{
                    replyNum: this.reply.replyNum,
                    content: this.reply.content
                })
                .then(res => {
                    if(res.data === 1){
                        this.$swal('Success','관리자 권한으로 해당댓글수정이 완료되었습니다!');
                        this.replyread(this.qna.qnaBdNum);
                        this.replyeditcensell(index);
                        return;
                    }
                })
                .catch((error)=>{
                    this.$swal('Error','관리자 권한으로 해당댓글수정이 실패했습니다.',error);
                })
            }else if(this.userNickName == replyuser && this.userNickName != 'ADMIN'){

                this.reply.content = content;
                this.reply.replyNum = replynum;
                this.$axiosSend('post','/api/reply/replyEdit',{
                    replyNum: this.reply.replyNum,
                    content: this.reply.content
                })
            .then(res => {
                if(res.data === 1){
                    this.$swal('Success','댓글수정완료!','success');
                    this.replyread(this.qna.qnaBdNum);
                    this.replyeditcensell(index);
                    return;
                }
            })
            .catch((error)=>{
                this.$swal('Error','댓글이 정상적으로 수정되지 않았습니다',error);
            })
            }else{
                return;
            }
        },

        //관리자 답변에 전체 댓글삭제 
        replyadmindelete(replyNum, qnanum){

            this.$swal({
                    title: '관리자 권한으로 댓글을 삭제 하시겠습니까?',
                    showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                    cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                    confirmButtonColor: '#303076',
                    confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                    cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                }).then(result => {
                    if (result.isConfirmed) {

                        this.$axiosSend('get','/api/reply/replydelete', {
                    replyNum: replyNum
                })
                .then(res => {
                    
                    if(res.data ===1){
                        this.$swal('Success', '관리자 권한으로 댓글삭제가 완료 되었습니다.', 'success');
                        this.ansread(qnanum);
                        this.replyread(qnanum);
                        return;
                    }else{
                        this.$swal('error', '댓글삭제실패!', 'error');
                        this.ansread(qnanum);
                        this.replyread(qnanum);
                        return;
                    }    
                })
                .catch(error => {
                    this.$swal(error, '댓글삭제실패!', 'error');
                    return;
                })
                    }
                }).catch(error => {
                    this.$swal(error, '댓글삭제실패!', 'error');
                    return;
                })
        },


        //댓글삭제
        replydelete(replyNum, qnanum, ansnum, username){
           
            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }
            
            else if(this.userNickName == 'ADMIN'){
                this.replyadmindelete(replyNum, qnanum);
                return;                
            }
            else if(this.userNickName != username && this.userNickName != 'ADMIN'){
                this.$swal('댓글은 본인 글만 삭제 가능합니다!', 'success');
                return;
            }
            else{
                this.$swal({
                    title: '댓글 삭제 하시겠습니까?',
                    showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                    cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                    confirmButtonColor: '#303076',
                    confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                    cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                }).then(result => {
                    if (result.isConfirmed) {
                        this.$axiosSend('get','/api/reply/replydelete', {
                            replyNum: replyNum
                        })
                        .then(res => {
                            
                            if(res.data ===1){
                                this.$swal('Success', '댓글삭제가 완료 되었습니다.', 'success');
                                this.ansread(qnanum);
                                this.replyread(qnanum);
                                return;
                            }else{
                                this.$swal('error', '댓글삭제실패!', 'error');
                                this.ansread(qnanum);
                                this.replyread(qnanum);
                                return;
                            }    
                        })
                        .catch(error => {
                            this.$swal(error, '댓글삭제실패!', 'error');
                        })
                    }
                }).catch(error => {
                    this.$swal(error, '댓글삭제실패!', 'error');
                })
            }
        },

        //댓글쓰기
        replywrite(ansBdNum, username, index){    
            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }
            if(this.reply.content == null || this.reply.content == ""){
                this.$swal({
                    title :'warning!',
                    text :"내용을 입력하세요",
                    type :'warning',
                    icon : 'warning',
                })
                return;
            }
            this.reply.qsNum = this.qna.qnaBdNum;
            this.reply.userName = this.userNickName;
            this.reply.ansNum = ansBdNum;
            this.reply.regId=this.form.regId;           
            var qnum = this.reply.qsNum;
            this.$axiosSend('post', '/api/reply/write', this.reply)
            .then(res=>{
                if(res.data === 1){
                    this.$swal('Success','작성완료!','success');
                    this.replylist = res.data;
                    this.replycensell(ansBdNum, username, index);
                    this.ansread(qnum);
                    this.replyread(qnum);
                    this.reply.content="";
                    return;          
                }else{
                    this.$swal('Success','작성실패!','success');
                    return;
                    }
                }
            )
            .catch((error)=>{
                this.$swal('Error','댓글이 정상적으로 작성되지 않았습니다', error)
            })

        },

        //답변버튼 노출 비노출
        ansdelbtneqlse(username){
            if(this.userNickName === username || this.userNickName == 'ADMIN'){
                return 1;
            }else{
                return 0;
            }
        },

        //답변글 총개수
        ansgetTotal(qnanum){
            this.$axiosSend('get','/api/ans/ansTotal', {
                qnaNum: qnanum,
            })
            .then(res => {
                this.anstotal=res.data;
            })
        },

        //댓글 총개수
        replygetTotal(ansnum){
            this.$axiosSend('get','/api/reply/replyTotal', {
                ansNum: ansnum,
            })
            .then(res => {
                console.log(res.data);
                this.replyTotal = res.data;
            }).catch((error)=>{
                console.log(error);
            })
        },

        //게시글 조회
        qnaRead(qnanum){ // 게시글 데이터 조회
            this.$axiosSend('get','/api/qna/qnaDetail',{
                num : qnanum,
                userName : this.userNickName,
                regid : this.form.regId
            })
            .then(res=>{
                this.qna = res.data;                
                this.qna.strQnaDate = this.DateTime(this.qna.qnaDate);
                this.nicknameEquals(this.qna.userName);
            })
            .catch((error)=>{
                console.log(error);
            })
        },

        //댓글조회
        replyread(qnanum){
            this.$axiosSend('get', '/api/reply/getreply', {
                qsNum: qnanum
            }).then(res => {
                this.replylist = res.data;
            })
            .catch((error) => {
                console.log(error);
            })
        },

        //답변 조회
        ansread(qnanum) {
            this.$axiosSend('get', '/api/ans/getans', {
                qsBdNum: qnanum
            }).then(res => {
                this.anslist = res.data;
            })  
            .catch((error) => {
                console.log(error);
            })
        },

        //게시글 삭제
        qnadelete(qnanum) {
            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }

            this.$swal({
                title: '게시글을 삭제 하시겠습니까?',
                showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                confirmButtonColor: '#303076',
                confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                }).then(result => {
                    if (result.isConfirmed) {                   // 만약 알럿창에서 확인 버튼을 눌렀다면

                        this.$axiosSend('get','/api/qna/qnaDelete', {
                        num: qnanum,
                        })
                        .then(res => {
                            if(res.data ===1){
                            this.$swal('Success', '글삭제완료!', 'success'),
                                router.push({
                                    name: "qnaBoard"
                                })
                            }    
                        })
                        .catch(error => {
                            alert(error);
                        })
                    } 
                }).catch(error => {
                    alert(error);
                })
        },

        //수정페이지로 이동 (질문글번호)
        qnaeditPath(qnum){              
            router.push({
                name: 'qnaBoardedit', 
                    num :qnum,
            })
        },

        //좋아요 1다운
        qnalikedown(){                                                      
        
            this.$axiosSend('get','/api/qna/likeDown', {
                    num : this.qna.qnaBdNum,
                    userName : this.userNickName,
                    likebdnum : this.likenum,
            })
            .then(res => {
                if(res.data === 1){
                    this.qna.qnaLikeCnt--;
                    return;
                }else if(res.data === 0){
                    return;
                }    
            })
            .catch(error => {
                alert(error);
            })
        },

        //성공값인 result값이 1이 있을 경우 기존 아이디좋아요 증가  
        qnalikeUp(qnum){
            if(this.userNickName === null || this.userNickName===""){
                this.$swal('로그인을 해주세요.');
                return;
            }
            var regid = this.form.regId;

            this.$axiosSend('get','/api/qna/likeUp', {
                    num: qnum,                             
                    regId : regid,                          
                    userName : this.userNickName,  
                    likeyn : this.likeyn             
            })
            .then(res => {
                if(res.data.result === 1){                                    
                    this.qna.qnaLikeCnt++;
                    return;
                }else if(res.data.result === 0){                

                    this.likenum = res.data.likenum;
                    this.$store.commit('qsbnumList', qnum);          
                    this.qnalikedown();      
                    return;
                }    
            })
            .catch(error => {
                alert(error);
            })
        },

        //작성날짜 변환
        DateTime(value) {
            // value는 날짜 값입니다
            const now = new Date();
            const date = new Date(value);
            const diffInMilliseconds = now - date;
            const diffInSeconds = Math.floor(diffInMilliseconds / 1000);
            const diffInMinutes = Math.floor(diffInSeconds / 60);
            const diffInHours = Math.floor(diffInMinutes / 60);
            const Days = Math.floor(diffInHours / 24);
            if (Days > 0) {
                return `${Days}일 전`;
            } else if (diffInHours > 0) {
                
                return `${diffInHours}시간 전`;
            } else if (diffInMinutes > 0) {
                return `${diffInMinutes}분 전`;
            } else {
                return '방금 전';
            }
        },

        //답변 글 작성
        answrite(){
            
            // if(this.userNickName === null || this.userNickName ==="" || this.qna.qnaBdNum ==0){
            //     this.$swal('로그인을 해주세요.', 'success');
            //     router.push({
            //         name: "login"
            //     })
            //     return;
            // }
            if(this.form.content == null || this.form.content == ""){
                this.$swal({
                    title :'warning!',
                    text :"내용을 입력하세요",
                    type :'warning',
                    icon : 'warning',
                })
                return;
            }
            this.form.qsBdNum = this.qna.qnaBdNum;
            this.form.userName = this.userNickName;
            var qnanum = this.form.qsBdNum;

            this.$axiosSend('post', '/api/ans/write', this.form)
            .then(res=>{
                console.log('wwqq', res.data);
                if(res.data === 1){
                    this.$swal('Success','작성완료!','success');
                    this.censells();
                    this.ansread(qnanum);
                    this.ansgetTotal(qnanum);
                }else{
                    this.$swal('Success','작성실패!','success')
                }
            })
            .catch((error)=>{
                this.$swal('Error','답변이 정상적으로 작성되지 않았습니다', error)
            })

        },

        //취소클릭에 수정,삭제,답변글 작성 버튼 
        censells(){
            document.getElementById("qnaboard-detail-replybtn").style.display="inline";
            document.getElementById("qnaboard-detail-rewrite").style.display="none";
            document.getElementById("qnaboard-detail-replywrite").style.display="none";
            document.getElementById("qnaboard-detail-editbtn").style.display="inline";
            document.getElementById("qnaboard-detail-deletebtn").style.display="inline";
            document.getElementById("qnaboard-detail-recensell").style.display="none";
            if(this.userNickName =='ADMIN'&& this.userNickName !=null){
                document.getElementById("qnaboard-detail-adminalldel").style.display="inline";
            }
            if(this.qna.userName === this.userNickName){
                document.getElementById("qnaboard-detail-replybtn").style.display="inline";
                document.getElementById("qnaboard-detail-deletebtn").style.display="inline";
                document.getElementById("qnaboard-detail-editbtn").style.display="inline";
                return;
            }else{
                document.getElementById("qnaboard-detail-deletebtn").style.display="none";
                document.getElementById("qnaboard-detail-editbtn").style.display="none";
                return;
            }
        },

        //답변작성버튼 클릭에 삭제 수정버튼
        ansopen(){
            if(this.userNickName == 'ADMIN' && this.userNickName != null){
                document.getElementById("qnaboard-detail-adminalldel").style.display="none";
            }
            console.log('sssdd');
            document.getElementById("qnaboard-detail-replybtn").style.display="none";
            document.getElementById("qnaboard-detail-rewrite").style.display="inline";
            
            document.getElementById("qnaboard-detail-replywrite").style.display="block";
            document.getElementById("qnaboard-detail-editbtn").style.display="none";
            document.getElementById("qnaboard-detail-deletebtn").style.display="none";
            document.getElementById("qnaboard-detail-recensell").style.display="inline";
            this.form.content = "";
        },

        //답변 관리자 삭제시 알림창 띄우고 삭제 클릭시 답변삭제 진행
        ansdelAlert(ansNum){
            this.$swal({
                title: '관리자 권한으로 답변과 해당 댓글들을 삭제 하시겠습니까?',
                showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                confirmButtonColor: '#303076',
                confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                }).then(result => {
                    if (result.isConfirmed) {
                        this.$axiosSend('get','/api/ans/ansdelete', {
                        ansBdNum : ansNum,
                        userName : this.userNickName
                    })
                .then(res => {
                
                    if(res.data ==1){
                        this.$swal('Success', '관리자 권한에 의한 답변과 해당하는 댓글들이 삭제가 완료 되었습니다.', 'success');
                        this.ansread(this.qna.qnaBdNum);
                        this.ansgetTotal(this.qna.qnaBdNum);
                        return;
                    }else{
                        this.$swal('error', '답변삭제실패!', 'error');
                        this.ansread(this.qna.qnaBdNum);
                        return;
                    }    
                })
            .catch(error => {
                this.$swal(error, '답변삭제실패!', 'error');
            })
                    }
            })    
            
        },

        //답변 삭제
        ansdelete(ansNum, userName){   
            if(this.userNickName === null || this.userNickName ==="" || ansNum ==0){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }
            
            this.$axiosSend('get', '/api/reply/replyTotal', {
                ansNum: ansNum
            })
            .then(res=>{
                if(this.userNickName == 'ADMIN'){
                    this.ansdelAlert(ansNum);
                    return;
                }
                else if(userName != this.userNickName){
                    this.$swal('답변은 본인 글만 삭제 가능합니다!', 'success');
                    return;
                }
                
                else if(res.data > 0){
                    this.$swal('댓글이 존재하여 삭제 할수없습니다. 관리자한테 문의 주세요.');
                    return;
                }

                else if(res.data ==0){
                    this.$swal({
                    title: '답변 삭제 하시겠습니까?',
                    showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                    cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                    confirmButtonColor: '#303076',
                    confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                    cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                    }).then(result => {
                        if (result.isConfirmed) {
                            this.$axiosSend('get','/api/ans/ansdelete', {
                            ansBdNum : ansNum,
                            userName : this.userNickName
                        })
                        .then(res => {
                            console.log('값:', res.data);
                            if(res.data ===1){
                                this.$swal('Success', '답변삭제가 완료 되었습니다.', 'success');
                                this.ansread(this.qna.qnaBdNum);
                                this.ansgetTotal(this.qna.qnaBdNum);
                                return;
                            }else{
                                this.$swal('error', '답변삭제실패!', 'error');
                                this.ansread(this.qna.qnaBdNum);
                                return;
                            }    
                        })
                        .catch(error => {
                            this.$swal(error, '답변삭제실패!', 'error');
                        })
                        }

                    }).catch(error => {
                        this.$swal(error, '답변삭제실패!', 'error');
                    })
                }
                else{
                    return;
                }
            })
            .catch((error)=>{
                this.$swal('Error','답변이 정상적으로 작성되지 않았습니다', error)
            })

        }

    },
}

</script>