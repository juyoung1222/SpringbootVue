<!--작성자:황용학-->


<template>
    <div class="d-flex">
        <div class="cscDetail">
            <CscCategory></CscCategory>
        </div>
        <div id="csc-detail-main">
            <div id="csc-detail-body">
                <h2 id="csc-detail-title">
                    {{ inquiry.title }} <!-- 1대1 제목-->
                </h2>
                <div id="csc-userinfo">
                    {{ inquiry.userName }} <!-- 1대1 닉네임-->
                    <p id="csc-date">
                        {{ DateTime(inquiry.regDate) }} <!-- 1대1 날짜-->
                    </p>
                </div>
                <hr class="mt-10">
                <div id="csc-detail-contents" ref="test">
                    <div v-html="inquiry.content"></div>
                </div>
                <hr style="margin-top: 9%;" />
            </div>
            <div class="inquiry-rewrite">
                <p class="fw-bold fs-5">
                    <font-awesome-icon :icon="['far', 'comment']" />
                    {{ inquiry.replyCnt }}개의 문의 답글이 있습니다. <!-- 1대1 댓글-->
                </p>
                <div v-show="!tmp">
                    <b-button type="button" id="inquiry-detail-rewrite" @click="tmp = !tmp" class="w-45 p-2">답글작성</b-button>
                    <b-button type="button" @click="inquirydelete(inquiry.vocNum, this.getCls)"
                        class="w-40 p-2">삭제</b-button>
                </div>
            </div>
            <div v-if="tmp">
                <b-form-textarea id="textarea-row" v-model="reply.content" placeholder="답글을 입력해주세요"
                    rows="8"></b-form-textarea>
                <div>
                    <b-button @click="replyWrite()" id="reply-commit" class="w-23 p-2">저장</b-button>
                    <b-button @click="cancelWrite()" id="reply-cancel" class="w-23 p-2">취소</b-button>
                </div>
            </div>
            <div v-for="list in replylist" :key="list.replyNum">
                <div class="d-flex mb-3 mt-4">
                    <div id="inq-reply-icon">
                        <font-awesome-icon :icon="['fas', 'user']" size="xl" />
                    </div>
                    <div id="inq-reply-name">
                        {{ list.userName }} <!-- 1대1 닉네임-->
                    </div>
                    <div id="inq-reply-date">
                        {{ DateTime(list.replyDate) }} <!-- 1대1 댓글 날짜-->
                    </div>
                </div>
                <div id="inq-reply-content">
                    <div id="inq-reply-content2">
                        {{ list.content }}
                    </div>
                    <div v-if="commentDelete(list.userName) == 1"> <!-- 1대1 댓글 삭제-->
                        <b-button type="button" id="inq-reply-del" @click="replydelete(list.replyNum, list.userName)"
                            class="w-45 p-1">답변삭제</b-button>
                    </div>
                    <div class="horizontal-line"></div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
import CscCategory from '@/components/CscCategory.vue';
import router from '@/router';
import '@/assets/css/CscViewStyle.css';


export default {

    components: {
        CscCategory
    },

    name: 'inquiryDetail',
    data() {
        return {
            params: parseInt(this.$route.query.vocNum), //CscView에서 넘버를 받아올 때 문, String으로 받아와 져서 parseInt로 형변환
            inquiry: { //1대1 문의사항 상세조회
                vocNum: null, //삽입되어 있는 숫자들은 null 부터 시작
                title: '',
                userName: '',
                content: '',
            },
            replylist: [],

            reply: { //1대1 문의사항 글쓰기
                vocNum: null,
                replyCnt: 0, //추가 할 숫자들은 0 부터 시작
                userName: '',
                content: '',
                replyDate: '',
                regId: '',
            },
            tmp: false, // 사용자가 댓글 컨테이너를 보는 상태인지 여부를 체크하는 변수
        };
    },
    mounted() {
        this.fetchInquiryDetail(this.params) //return에서 params로 받아 옴
        this.replyList(this.params)
        this.userName = this.$store.getters.getNickname;
        this.regId = this.$store.getters.getEmail;
    },
    computed: {
        getCls() {
            const cls = this.$store.getters.getCls;
            return cls;

        },
    },

    methods: {

        //게시글 조회
        fetchInquiryDetail(vocNum) {
            this.$axiosSend('get', '/api/inquiry/inquiryDetail', {
                vocNum: vocNum
            })
                .then(res => {
                    this.inquiry = res.data;
                    this.reply.vocNum = this.inquiry.vocNum;
                    console.log(this.inquiry);
                })
                .catch(error => {
                    console.log(error);
                });
        },

        //1대1 게시글 삭제
        inquirydelete(vocNum, userName) {
            if (userName !== 'ADMIN') {
                this.$swal('관리자만 삭제가 가능합니다.', 'FAIL');
                return;
            } else {
                this.$axiosSend('get', 'api/inquiry/inquiryDelete', {
                    vocNum: vocNum,
                    userName: this.getCls
                })
                    .then(res => {
                        if (res.data === 1) {
                            this.$swal('success', '답변 삭제가 완료 되었습니다.', 'success');
                            router.push({
                                name: "csc"
                            })
                            console.log(res.data);
                            return;
                        } else {
                            this.$swal('error', '답변삭제실패???!!!!!!!!!!!', 'error');
                            console.log(res.data);
                            return;
                        }
                    })
            }
        },

        //댓글 조회
        replyList(vocNum) {
            this.$axiosSend('get', '/api/reply/getinquiry', {
                vocNum: vocNum,
            })
                .then((res) => {
                    this.replylist = res.data;
                    console.log(this.replylist);

                    // list에 데이터가 담겨 있는지 확인
                    if (this.replylist && Object.keys(this.replylist).length > 0) {
                        // 데이터가 담겨 있을 때의 처리 로직
                        console.log("list에 데이터가 있습니다.");
                    } else {
                        // 데이터가 없을 때의 처리 로직
                        console.log("list에 데이터가 없습니다.");
                    }
                })
                .catch((error) => {
                    console.log(error);
                })
        },

        cancelWrite() {
            this.tmp = false;
        },

        //댓글 삽입
        replyWrite() {
            if (this.userName !== 'ADMIN') {
                this.$swal('관리자만 등록이 가능합니다.', 'FAIL');
                router.push({
                })
                return;
            }
            if (this.reply.content == null || this.reply.content == "") {
                this.$swal({
                    title: 'warning!',
                    text: "내용을 입력해주세요",
                    type: 'warning',
                })
                return;
            }
            this.reply.userName = this.userName
            this.$axiosSend('post', '/api/reply/inquiryWrite', {
                vocNum: this.reply.vocNum,
                userName: this.reply.userName,
                content: this.reply.content,
                regId: this.regId,
            })
                .then((res) => {
                    if (res.data === 1) {
                        this.$swal('Success', '작성완료!', 'success')
                        this.tmp = !this.tmp
                    }
                })
                .catch((error) => {
                    this.$swal('Error', '답글이 정상적으로 작성되지 않았습니다.', error);
                    this.tmp = !this.tmp
                })
                .finally(() => {
                    this.replyList(this.params)
                })
        },

        //1대1 답변 삭제
        replydelete(replyNum, userName) {
            if (userName !== 'ADMIN') {
                this.$swal('관리자만 삭제가 가능합니다.', 'FAIL');
                return;
            } else {
                this.reply.userName = this.userName
                this.$axiosSend('get', 'api/reply/inquirydelete', {
                    replyNum: replyNum,
                    userName: this.reply.userName
                })
                    .then(res => {
                        if (res.data === 1) {
                            this.$swal('success', '답변 삭제가 완료 되었습니다.', 'success');
                            return;
                        } else {
                            this.$swal('error', '답변삭제실패???!!!!!!!!!!!', 'error');
                            return;
                        }
                    })
                    .catch(error => {
                        this.$swal(error, '무슨 오류일까?', 'error');
                    })
                    .finally(() => {
                        this.replyList(this.params)
                    })
            }
        },
        commentDelete(userName) {
            if (this.userName === userName) {
                return 1;
            } else {
                return 0;
            }
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

    },
}
</script>