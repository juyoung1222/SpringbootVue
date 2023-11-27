//작성자 변의준


<template>
    <div class = "d-block">
        <div class = "writeMain d-none d-xxl-block">
            <CommCategory></CommCategory>
        </div>
        <div id="freeboard-detail-contatiner">
            <div class="justify-content-start text-start">
                <h2 class=" mb-3 fw-bold free-detail-title">
                    {{ free.title }}
                </h2>
                <div id="freeBoardTitle">
                    <div id="freeboard-userinfo">
                        <p id="freeboard-userid">
                            {{ free.userName}}
                        </p>
                        <p id="free-comm">
                            <font-awesome-icon :icon="['fas', 'eye']" /> {{ free.commCnt }}
                        </p>
                        <p id="free-date">
                            {{ DateTime(free.commDate) }} 
                        </p>
                    </div>
                    <div id="freeBoardEditDele">
                        <b-button type="button" class="btn-custom ms-2 qnaboard-detail-viewpath" :to="'/comm/freBd'">목록</b-button>
                        <b-button type="button" class="bedu-bg-custom-blue freeboard-detail-editbtn" id="freeboard-detail-editbtn" @click="freeeditPath()">글수정</b-button>
                        <b-button type="button" class="ms-2 freeboard-detail-deletebtn" id="freeboard-detail-deletebtn" @click="freedelete()">삭제</b-button>
                    </div>
                </div>
                <div id="freeboard-detail-contents">
                    <!-- {{ free.content }} -->
                    <div id="freeContent" v-html="free.content"></div>
                    <div id="free-likeyn">
                        <button id="free-likebtn" @click="freelikeUp(free.commNum)" :class="[backLikedClass()]">
                            <font-awesome-icon :icon="['fas', 'heart']" :class="[fontLikedClass()]" />
                            <text class="fw-bold ms-2 free-detail-likeyn" id="free-detail-likeyn">
                                {{ free.commLikeCnt }}
                            </text>
                        </button>   
                    </div>
                </div>
                <div id="freeLikeReply">
                    <p class = "fw-bold fs-5">
                        <font-awesome-icon :icon="['far', 'comment']" />
                        댓글 {{replytotal}}개
                    </p>
                    <div class="mb-3 freeboard-detail-top" id="freeboard-detail-top">
                        <b-button type="button" class="btn-custom ms-2" id="qna-detail-rewrite" @click="replywrite()">댓글등록</b-button>
                        <b-button type="button" class="btn-custom ms-2 qna-detail-recensell" @click="censells()" id="qna-detail-recensell">취소</b-button>
                        <b-button type="button" class="btn-custom ms-2 free-detail-replybtn" id="free-detail-replybtn" @click="replyopen()">댓글작성</b-button>
                        <b-button type="button" v-if="replyadmindel() == 1" class="btn-custom ms-2 free-detail-adminalldel" id="free-detail-adminalldel" @click="adminreplyalldel(free.commNum)">댓글전체삭제</b-button>
                    </div>
                </div>
                <div>
                    <div class="free-detail-replywrite" id="free-detail-replywrite" style="display: none;">
                        <h4>댓글을 작성해주세요</h4>
                        <textarea class="form-control col-sm-5 qna-detail-replycontent" rows="5" id="qna-detail-replycontent" v-model="form.content" placeholder="내용을 작성해주세요" ref="content"/>
                    </div>
                    <div v-for="(reply, index) in replylist" :key="index" class="free-detail-replylist" id="free-detail-replylist">
                        <div class="mb-3" :ref="'commant-container-'+index">
                            <div class="d-flex mb-3 mt-4 freeReplys">
                                <div class="freeuser">
                                    <font-awesome-icon :icon="['fas', 'user']" size="xl" />
                                </div>
                                <div class="freeReplyName">
                                    {{ reply.userName }}
                                </div>
                                <div class="freeReplyDate">
                                    {{ DateTime(reply.replyDate) }}
                                </div>
                            </div>
                            <div class="freeReplycontent">
                                {{ reply.content }}
                            </div>
                            <div class="freeeditcontent" id="freeeditcontent" ref="sample">
                                <b-form-textarea class="replyeditcontent" v-model="reply.content" :ref="'content_'+index" >{{ reply.content }}</b-form-textarea>
                            </div>
                            <div class="freeReplydelbtn" id="freeReplydelbtn" v-if="replybtneq(reply.userName) == 1">
                                <button type="button" id="free-reply-delb" @click="replyeditopen(index)">수정</button>
                                <button type="button" id="free-reply-delb" @click="replydelete(reply.replyNum, reply.userName)">삭제</button>
                            </div>
                            <div class="freereplyeditall-btns" id="freereplyeditall-btns">
                                <b-button type="button" class="btn-custom btn-custom mt-1" style="background-color: #303076;" @click="replyedit(reply.replyNum, reply.userName, index, reply.content)">수정</b-button>
                                <b-button type="button" class="btn-custom ms-2 mt-1" @click="replyeditcensell(index)">취소</b-button>
                            </div>
                        </div>       
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import CommCategory from '@/components/CommCategory.vue';
import router from '@/router';
import '@/assets/css/freeBoardStyle.css';
export default{

    components:{
        CommCategory
    },
    
    data() {
        return {
            result : 0,
            replylist:[],
            userlist:[],
            likenum:0,
            replytotal:0,
            username:'',
            likeyn:'f',
            userNickName:'',

            form: {
                commNum:0,
                replyNum: 0,
                userName:'',
                regId: '',
            },

            free : {
                commNum:0,
                title : '',
                content : '',
                userName : '',
                strCommDate:'',
                userId:'',
                commCnt : 0,
                commLikeCnt : 0,
            }
        }
    },

    computed: {
        // 'getCbnumList' 속성은 'cbnumList'라는 localStorage 항목을 가져와서 파싱한 후,
        // 파싱한 결과를 반환합니다. 만약 'cbnumList'가 존재하지 않으면 빈 배열을 반환합니다.
        getCbnumList() {
            return JSON.parse(localStorage.getItem('cbnumList')) || [];
        },
        // 'isLiked' 계산된 속성은 'cbnumList'라는 localStorage 항목을 가져와서 파싱한 후,
        // 현재 'free.commNum'이 그 목록에 포함되어 있는지 확인합니다.
        // 포함되어 있다면 'true'를 반환하고, 포함되어 있지 않다면 'false'를 반환합니다.
        isLiked() {
            const cbnumList = JSON.parse(localStorage.getItem('cbnumList')) || [];
            return cbnumList.includes(this.free.commNum);
        }
    },


    mounted() {
        this.userNickName =this.$store.getters.getNickname;
        this.form.regId = this.$store.getters.getEmail;
        const cnum = this.$route.params.num;
        if(this.userNickName === null || this.userNickName ===""){
            this.freeReadtet(cnum);
        }else{
            this.freeRead(cnum);
        }
        this.replygetTotal(cnum);
        this.replyread(cnum);
    },

    created() {
        this.userNickName =this.$store.getters.getNickname;
    },

    methods: {

        // 'backLikedClass' 메서드는 'freeBackColor' 또는 빈 문자열을 반환합니다.
        // 'cbnumList'라는 localStorage 항목을 가져와서 파싱한 후, 
        // 현재 'free.commNum'이 그 목록에 포함되어 있는지 확인합니다.
        // 포함되어 있다면 'freeBackColor' 클래스를 반환하고, 포함되어 있지 않다면 빈 문자열을 반환합니다.
        backLikedClass() {
            const cbnumList = JSON.parse(localStorage.getItem('cbnumList')) || [];
            return cbnumList.includes(this.free.commNum) ? 'freeBackColor' : 'freeDefault';
        },

        //댓글 수정 폼 열기
        replyeditopen(index){
            
            this.$refs['commant-container-'+index][0].children[1].classList.add("d-none");
            this.$refs['commant-container-'+index][0].children[2].classList.add("d-block");
            this.$refs['commant-container-'+index][0].children[3].classList.add("d-none");
            this.$refs['commant-container-'+index][0].children[4].classList.add("d-block");
        },

        //댓글수정폼닫기
        replyeditcensell(index){
            this.$refs['commant-container-'+index][0].children[1].classList.remove("d-none");
            this.$refs['commant-container-'+index][0].children[2].classList.remove("d-block");
            this.$refs['commant-container-'+index][0].children[3].classList.remove("d-none");
            this.$refs['commant-container-'+index][0].children[4].classList.remove("d-block");
        },

        replyadmindel(){
            if(this.userNickName == "ADMIN"){
                return 1;
            }else{
                return 0;
            }
        },

        //게시글의 댓글 전체 삭제(관리자 권한)
        adminreplyalldel(commnum){
            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }

            if(this.replytotal > 0){

                this.$swal({
                title: '관리자 권한으로 댓글을 전체삭제 하시겠습니까?',
                showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                cancelButtonColor: '#6c757d', // cancel 버튼 색깔 지정
                confirmButtonColor: '#303076',
                confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
                cancelButtonText: '취소', // cancel 버튼 텍스트 지정
                }).then(result => {
                    if (result.isConfirmed) {
                        this.$axiosSend('get','/api/reply/replydelete', {
                            commNum: commnum,
                            userName : this.userNickName
                })
                        .then(res => {
                            if (res.data > 0) {
                                this.$swal('Success', '댓글전체삭제완료!', 'success');
                                this.replygetTotal(commnum);
                                this.replyread(commnum);
                                return;
                            }
                        })
                        .catch(error => {
                            this.$swal('Error', '댓글이 정상적으로 삭제되지 않았습니다.', error);
                        })
                    }
                })

            }

            
        },

        // 'fontLikedClass' 메서드는 'fontBackColor' 또는 빈 문자열을 반환합니다.
        // 'cbnumList'라는 localStorage 항목을 가져와서 파싱한 후, 
        // 현재 'free.commNum'이 그 목록에 포함되어 있는지 확인합니다.
        // 포함되어 있다면 'fontBackColor' 클래스를 반환하고, 포함되어 있지 않다면 빈 문자열을 반환합니다.
        fontLikedClass() {
            const cbnumList = JSON.parse(localStorage.getItem('cbnumList')) || [];
            return cbnumList.includes(this.free.commNum) ? 'fontBackColor' : '';
        },

        //닉네임여부 존재 확인
        //비교 결과에 따라 수정및 삭제 버튼 노출(같은면 노출, 다르면 댓글버튼만 노출)  
        nicknameEquals(nickName){                                                                                                                                
            
            if(this.userNickName === null || this.userNickName ===""){
                document.getElementById("free-detail-replybtn").style.display="none";
            }

            if(this.userNickName !== nickName){
                    
                document.getElementById("freeboard-detail-editbtn").style.display="none";
                document.getElementById("freeboard-detail-deletebtn").style.display="none";
            }
        },

        //댓글 수정
        replyedit(replynum, username, index, content){
            if(this.userNickName == null || replynum == 0 || replynum == null){
                this.$swal('로그인을 해주세요');
                return;
            }
            if(this.userNickName == 'ADMIN'){
                this.form.replyNum = replynum;
                this.form.content = content;
                this.$axiosSend('post','/api/reply/replyEdit',{ 
                    replyNum: this.form.replyNum,
                    content: this.form.content
                })
                .then(res => {
                    if(res.data === 1){
                        this.$swal('Success','관리자 권한으로 댓글수정을 완료했습니다.','success');
                        this.replyread(this.form.commNum);
                        this.replyeditcensell(index);
                        return;
                    }
                })
                .catch((error)=>{
                    this.$swal('Error','관리자 권한댓글이 정상적으로 수정되지 않았습니다',error);
                })
                return;
            }
            
            if(this.userNickName != username ){
                this.$swal('댓글은 본인 글만 삭제할수있습니다.');
                return;
            }
            else{

                this.form.replyNum = replynum;
            this.form.content = content;
            this.$axiosSend('post','/api/reply/replyEdit',{ 
                replyNum: this.form.replyNum,
                content: this.form.content
            })
            .then(res => {
                if(res.data === 1){
                    this.$swal('Success','댓글수정완료!','success');
                    this.replyread(this.form.commNum);
                    this.replyeditcensell(index);
                    return;
                }
            })
            .catch((error)=>{
                this.$swal('Error','댓글이 정상적으로 수정되지 않았습니다',error);
            })

            }
            
        },

        //댓글 총개수
        replygetTotal(cnum){
            
            this.$axiosSend('get','/api/reply/replyTotal', {
                commNum: cnum,
            })
            .then(res => {
                this.replytotal = res.data;
                return;
            }).catch((error)=>{
                this.$swal('Error','댓글이 정상적으로 수정되지 않았습니다',error);
            })
        },

        // 게시글 데이터 조회
        freeRead(commnum){ 
           
            this.$axiosSend('get','/api/freBd/detail',{
                    num : commnum,
                    userName : this.userNickName
            })
            .then(response=>{
                this.free = response.data;
                this.free.strCommDate = this.DateTime(this.free.commDate);
                this.nicknameEquals(this.free.userName);

            })
            .catch((error)=>{
                this.$swal('Error', '게시글이 정상적으로 조회되지 않았습니다.', error);
            })
        },

        //비로그인 게시글 조회
        freeReadtet(commnum){ 
            this.$axiosSend('get','/api/freBd/editdetail',{
                num : commnum,
            })
            .then(res=>{
                this.free = res.data;                
                this.free.strQnaDate = this.DateTime(this.free.qnaDate);
                this.nicknameEquals(this.free.userName);
            })
            .catch((error)=>{
                console.log(error);
            })
        },

        //댓글삭제버튼 노출 비노출
        replybtneq(username){
            if(this.userNickName === username || this.userNickName == 'ADMIN'){
                return 1;
            }else{
                return 0;
            }
        },

        //댓글 조회
        replyread(commnum) {
            if(commnum === 0 || commnum === null){
                return;
            }
            
            this.$axiosSend('get', '/api/reply/getreply', {
                commNum: commnum
            }).then(res => {
                this.replylist = res.data;
            })
            .catch((error) => {
                console.log(error);
            })
        },

        //글 삭제
        freedelete() {
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
                    if (result.isConfirmed) {
                        this.$axiosSend('get','/api/freBd/delete', {
                            num: this.free.commNum,
                        })
                        .then(res => {
                            if (res.data === 1) {
                                this.$swal('Success', '글삭제완료!', 'success'),
                                router.push({
                                    name: "freeBoard"
                                })
                            }
                        })
                        .catch(error => {
                            this.$swal('Error', '게시글이 정상적으로 삭제되지 않았습니다.', error);
                        })
                    }
                })    
        },

        //댓글 작성
        replywrite(){
            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }
            if(this.form.content == null || this.form.content == ""){
                    this.$swal({
                        title :'warning!',
                        text :"내용을 입력하세요",
                        type :'warning',
                        icon : 'warning',
                    })
                    return;
                }
                this.form.commNum = this.free.commNum;
                this.form.userName = this.userNickName;
                
                var commNum = this.form.commNum;

                this.$axiosSend('post', '/api/reply/write', this.form)
                .then(res=>{
                    if(res.data === 1){
                        this.$swal('Success','작성완료!','success'),
                        this.censells();
                        this.replyread(commNum);
                        this.replygetTotal(commNum);
                        return;
                    }else{
                        this.$swal('Success','작성실패!','success')
                    }
                })
                .catch((error)=>{
                    this.$swal('Error','댓글이 정상적으로 작성되지 않았습니다', error)
                })

        },

        //댓글삭제
        replydelete(replyNum, userName){
            
            if(this.userNickName === null || this.userNickName ===""){
                this.$swal('로그인을 해주세요.', 'success');
                router.push({
                    name: "login"
                })
                return;
            }

            if(this.userNickName == "ADMIN"){
                this.$axiosSend('get','/api/reply/replydelete', {
                    replyNum : replyNum
                })
                .then(res => {        
                    
                    if(res.data ==1){
                    
                        this.$swal('Success', '관리자 권한으로 댓글삭제가 완료 되었습니다.', 'success');
                        this.replyread(this.free.commNum);
                        this.replygetTotal(this.free.commNum);
                        return;
                    }
                    // else if(res.data == 0){
                    //     this.$swal('관리자 권한으로 댓글삭제실패!');
                    //     this.replyread(this.free.commNum);
                    //     this.replygetTotal(this.free.commNum);
                    //     return;
                    // }    
                })
                .catch((error)=>{
                    this.$swal('Error','댓글이 정상적으로 삭제 되지 않았습니다.', error)
                })
                return;
            }

            if(userName !== this.userNickName){
                this.$swal('댓글은 본인 글만 삭제 가능합니다!', 'success');
                return;
            }else{
                
                this.$axiosSend('get','/api/reply/replydelete', {
                    replyNum : replyNum
                })
                .then(res => {        
                    if(res.data ===1){
                        this.$swal('Success', '댓글삭제가 완료 되었습니다.', 'success');
                        this.replyread(this.free.commNum);
                        this.replygetTotal(this.free.commNum);
                        return;
                    }else{
                        this.$swal('error', '댓글삭제실패!', 'error');
                        this.replyread(this.free.commNum);
                        this.replygetTotal(this.free.commNum);
                        return;
                    }    
                })
                .catch(error => {
                    this.$swal(error, '댓글삭제실패!', 'error');
                })
            }
        },

        //수정페이지이동
        freeeditPath(){
            router.push({
                name: 'freeBoardEdit', 
                params:{
                    num :this.free.commNum
                }
            })
                
        },

        //좋아요 1감소
        freelikedown(){
                
            this.$axiosSend('get','/api/free/likeDown', {
                    num : this.free.commNum,
                    userName : this.userNickName,
                    likebdnum : this.likenum,
            })
            .then(res => {
                const cnum = this.free.commNum;
                if(res.data === 1){
                    this.free.commLikeCnt--;
                    this.$store.commit('CBNUMLIST_REMOVE', cnum);
                    return;
                }else if(res.data === 0){
                    return;
                }    
            })
            .catch(error => {
                alert(error);
            })
        },
        //좋아요 1증가
        freelikeUp(cnum){
            if(this.userNickName === null || this.userNickName==="" || cnum == 0 || cnum == null){
                this.$swal('로그인을 해주세요.');
                return;
            }
            
            var regid = this.form.regId;
            this.$axiosSend('get','/api/free/likeUp', {
                    num: cnum,
                    regId : regid,
                    userName : this.userNickName,
                    likeyn : this.likeyn
            })
            .then(res => {
                if(res.data.result === 1){                      //기존 아이디좋아요 없음
                    this.likenum = res.data.likenum;             //테이블의 LIKE_NUM
                    this.free.commLikeCnt++;
                    this.$store.commit('CBNUMLIST_ADD', cnum);
                    return;
                }else if(res.data.result === 0){                //기존 아이디좋아요 있음
                    this.likenum = res.data.likenum;
                    this.freelikedown();
                    return;
                }    
            })
            .catch(error => {
                alert(error);
            })
        },                

        //조회페이지이동
        freeBoardpath(){
            router.push({
                name: 'freeBoard', 
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
                const freeDays = Math.floor(diffInHours / 24);
                if (freeDays > 0) {
                    return `${freeDays}일 전`;
                } else if (diffInHours > 0) {
                    return `${diffInHours}시간 전`;
                } else if (diffInMinutes > 0) {
                    return `${diffInMinutes}분 전`;
                } else {
                    return '방금 전';
                }
        },

        //댓글작성버튼 클릭에 삭제 수정버튼 노출
        replyopen(){
            document.getElementById("free-detail-replybtn").style.display="none";
            document.getElementById("qna-detail-rewrite").style.display="inline";
            document.getElementById("free-detail-replywrite").style.display="block";
            document.getElementById("freeboard-detail-editbtn").style.display="none";
            document.getElementById("freeboard-detail-deletebtn").style.display="none";
            document.getElementById("qna-detail-recensell").style.display="inline";
            this.form.content = "";

            if(this.userNickName == "ADMIN"){
                document.getElementById("free-detail-adminalldel").style.display="none";
            }
        },

        //댓글작성버튼 클릭에 삭제 수정버튼 노출
        censells(){
            document.getElementById("free-detail-replybtn").style.display="inline";
            document.getElementById("qna-detail-rewrite").style.display="none";
            document.getElementById("free-detail-replywrite").style.display="none";
            document.getElementById("freeboard-detail-editbtn").style.display="inline";
            document.getElementById("freeboard-detail-deletebtn").style.display="inline";
            document.getElementById("qna-detail-recensell").style.display="none";
            if(this.free.userName === this.userNickName && this.userNickName == "ADMIN"){
                document.getElementById("free-detail-replybtn").style.display="inline";
                document.getElementById("freeboard-detail-editbtn").style.display="inline";
                document.getElementById("freeboard-detail-deletebtn").style.display="inline";
                document.getElementById("free-detail-adminalldel").style.display="inline";

                return;
            } else if (this.free.userName === this.userNickName){
                document.getElementById("free-detail-replybtn").style.display="inline";
                document.getElementById("freeboard-detail-editbtn").style.display="inline";
                document.getElementById("freeboard-detail-deletebtn").style.display="inline";
                return;
            } else {
                
                document.getElementById("freeboard-detail-editbtn").style.display="none";
                document.getElementById("freeboard-detail-deletebtn").style.display="none";
                return;
            }
        },


    },
}
</script>