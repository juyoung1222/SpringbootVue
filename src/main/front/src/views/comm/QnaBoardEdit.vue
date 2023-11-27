//작성자 변의준


<template>
    <div class = "d-flex">
            <div class = "writeMain d-none d-xxl-block">
                <CommCategory></CommCategory>
            </div>
        <div id="qna-write">
            <h2>질문 & 답변 수정</h2>
            <b-form @submit="edit()">
                <input type="hidden" v-model="form.qnaBdNum" ref="qnaBdNum"/>
                    <b-form-input class="mt-4 mb-2" ref="title" id="qna-edit-title" v-model="form.title"></b-form-input>
                        
                <ckeditor :editor="editor" v-model="form.content" :config="editorConfig"></ckeditor>

                <div id="buttonSet">
                    <b-button variant="outline-primary" type="submit" class="px-4 btn-custom ms-2 qna-edit bedu-bg-custom-blue" id="qna-edit">수정</b-button>
                    <b-button class="px-4 btn-custom ms-2 qna-edit-censell" id="qna-edit-censell" type="reset" :to="'/comm/qna'" >취소</b-button>
                </div>
            </b-form>
        </div>
    </div>
</template>

<script>
    import Editor from 'ckeditor5-custom-build/build/ckeditor';
    import CommCategory from '@/components/CommCategory.vue';
    import router from '@/router';
    export default{

        components:{
            CommCategory
        },

        data(){
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
                form:{
                    qnaBdNum: 0,  
                    title: '',
                    content: '',
                   
                }
            }
        },

        mounted() {
            const nick =this.$store.getters.getNickname;
            if(nick === '' || nick === null){
                this.$swal('Error','로그인을 해주세요!');
                router.push({
                    name: "login"
                })
                return;
            }
            const qnum = this.$route.params.num;
            this.qnadetail(qnum);
        },

        created() {
            const nick =this.$store.getters.getNickname;
            
        },

        methods: {

            // 게시글 데이터 조회
            qnadetail(num){ 
                this.$axiosSend('get','/api/qna/editdetail',{
                        num : num,
                })
                .then(response=>{
                    this.form = response.data;
                })
                .catch((error)=>{
                    alert(error);
                })
            },

            //글 수정
            edit(){

                alert('게시글을 수정합니다.');
                this.$axiosSend('post','/api/qna/qnaEdit', this.form)
                .then(res => {
                    if(res.data === 1){
                        this.$swal('Success','수정완료!','success'),
                        router.push({
                            name: "qnaBoard"
                        })
                    }
                })
                .catch((error)=>{
                    this.$swal('Error','게시글이 정상적으로 수정되지 않았습니다',error)
                })
            }
        },
    }
</script>
