<!-- 작성자 우지원 -->


<template>
    <div>
        <b-form @submit.prevent="lectureInsert" ref="lectForm">
            <b-row cols="1" cols-md="3">
                <b-col>
                    <b-form-group
                    description="대분류를 선택해주세요"
                    label="대분류">
                        <b-form-select v-model="currentTop" @input="this.currentMid = null" required>
                            <template #first>
                                <b-form-select-option disabled>대분류</b-form-select-option>
                            </template>
                            <b-form-select-option v-for="(item, index) in topCate" :key="index" :value="item.lectTopCate">{{ item.lectTopCateKor }}</b-form-select-option>
                        </b-form-select>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group
                    description="중분류를 선택해주세요"
                    label="중분류">
                        <b-form-select v-model="currentMid" required>
                            <template #first>
                                <b-form-select-option disabled>중분류</b-form-select-option>
                            </template>
                                <b-form-select-option v-for="(item, index) in midCate.filter((i)=>i.parentCode == currentTop)" :key="index" :value="item.lectMidCate">{{ item.lectMidCateKor }}</b-form-select-option>
                        </b-form-select>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group
                    description="소분류를 선택해주세요"
                    label="소분류">
                        <b-form-select v-model="currentBot" required>
                            <template #first>
                                <b-form-select-option disabled>소분류</b-form-select-option>
                            </template>
                                <b-form-select-option v-for="(item, index) in botCate.filter((i)=>i.parentCode == currentMid)" :key="index" :value="item">{{ item.lectBotCateKor }}</b-form-select-option>
                        </b-form-select>
                    </b-form-group>
                </b-col>
            </b-row>
            <b-row cols="1" cols-md="3">
                <b-col>
                    <b-form-group
                    description="제목을 입력해주세요"
                    label="제목"
                    label-for="lect-manage-title"
                    >
                        <b-form-input id="lect-manage-title" v-model="form.title" required></b-form-input>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group
                    description="강사의 이름을 입력해주세요"
                    label="강사명"
                    label-for="lect-manage-teacher"
                    >
                        <b-form-input id="lect-manage-teacher"  v-model="form.teacher" required></b-form-input>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group
                    description="가격을 입력해주세요"
                    label="가격"
                    label-for="lect-manage-price"
                    >
                        <b-form-input id="lect-manage-price" type="number" v-model="form.price" required ref="price"></b-form-input>
                    </b-form-group>
                </b-col>
            </b-row>
            <b-row cols="1" cols-md="2">
                <b-col>
                    <b-form-group
                    description="썸네일을 업로드해주세요"
                    label="썸네일"
                    label-for="lect-manage-thumbnail"
                    >
                        <input id="lect-manage-thumbnail" ref="file-input" type="file" class="form-control" required @change="fileChange"/>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group
                    description="수강 기간을 입력해주세요"
                    label="수강 기간"
                    label-for="lect-manage-period"
                    >
                        <b-form-input type="number" id="lect-manage-period" v-model="form.period" required ref="period"></b-form-input>
                    </b-form-group>
                </b-col>
            </b-row>
            <div>
                <b-form-group
                description="강의 요약 설명을 입력해주세요"
                label="강의 요약"
                >
                    <b-form-textarea
                    v-model="form.summary"
                    max-rows="5"
                    rows="3"
                    no-resize
                    required
                    ></b-form-textarea>
                </b-form-group>
            </div>
            <div class="mb-3">
                <ckeditor :editor="editor" :config="editorConfig" v-model="form.contents" ref="contents"></ckeditor>
            </div>
            <div class="d-flex">
                <div class="ms-auto">
                    <b-button class="bedu-bg-custom-blue me-3 px-5 py-2" type="submit">저장</b-button>
                    <b-button class="px-5 py-2">취소</b-button>
                </div>
            </div>
        </b-form>
    </div>
</template>

<script>
import Editor from 'ckeditor5-custom-build/build/ckeditor'
import axios from 'axios'

export default{
    name : 'lectInsert',
    data() {
        return {
            form : {
                title : '',
                teacher : '',
                price : '',
                thumbnail : null,
                period : '',
                contents : '',
                summary : '',
            },
            topCate : [],
            midCate : [],
            botCate : [],
            currentTop : '',
            currentMid : '',
            currentBot : {
                lectBotCate : '',
                lectBotCateKor : '',
            },
            editor : Editor,
            editorConfig : {
                // The configuration of the editor.
                simpleUpload: {
                    // 업로드 URL
                    uploadUrl: '/api/admin/lectManage/ImageUpload',
                    method : 'POST'
                },
                mediaEmbed: {
                    previewsInData: true
                },
                removePlugins: ["MediaEmbedToolbar"],
            },
        }
    },
    methods: {
        /** 강의 저장 */
        lectureInsert(){
            // 데이터 무결성 검사
            if( this.form.price <= 0){
                this.$swal({
                    icon : 'info',
                    text : '가격은 0원 이하가 될 수 없습니다'
                })
                .then(()=>{
                    this.$refs.price.focus()
                })
                return;
            }

            if(this.form.period <= 0){
                this.$swal({
                    icon : 'info',
                    text : '수강 기간은 0일 이하가 될 수 없습니다'
                })
                .then(()=>{
                    this.$refs.period.focus()
                })
                return;
            }
            if(this.form.contents == '' || this.form.contents == null){
                this.$swal({
                    icon : 'info',
                    text : '강의 설명을 입력해주세요'
                })
                return;
            }
            // 데이터 무결성 검사 종료

            const formData = new FormData();
            formData.append("title",this.form.title);
            formData.append("teacher",this.form.teacher);
            formData.append("price",this.form.price);
            formData.append("period",this.form.period);
            formData.append("summary",this.form.summary);
            formData.append("contents",this.form.contents);
            formData.append("thumbnail",this.form.thumbnail);

            formData.append("cateCode",this.currentBot.lectBotCate);
            formData.append("cateKor", this.currentBot.lectBotCateKor);
            formData.append("regNum",this.$store.getters.getUsernum);

            axios.post('/api/admin/lectManage/lectInsert',formData,{
                headers:{
                        "Content-Type" : "multipart/form-data"
                }
            })
            .then(()=>{
                this.$swal({
                    title : '성공',
                    icon : 'success',
                    text : '강의가 성공적으로 등록 되었습니다'
                })
                .then(()=>{
                    
                    this.form.contents = '';
                    this.form.period = '';
                    this.form.price = '';
                    this.form.summary = '';
                    this.form.teacher = '';
                    this.form.thumbnail = ''; 
                    this.form.title = '';
                    this.currentTop = '';
                    this.currentMid = '';
                    this.currentBot = '';
                    this.$refs["file-input"].value = "";
                    window.scrollTo({top: 0})
                })
            })
            .catch((err)=>{
                console.log(err)
            })
        },

        /** 카테고리 조회 */
        getCategories(){
            this.$axiosSend('get','/api/lect/getCategoryForLevel')
            .then((res)=>{
                this.topCate = res.data.top;
                this.midCate = res.data.mid;
                this.botCate = res.data.bot;
            })
            .catch((err)=>{
                console.log(err)
            })
        },

        /** 파일 업로드시 변경 */
        fileChange(e){
           this.form.thumbnail = e.target.files[0]
        }
    },
    computed:{

    },
    created() {
        this.getCategories();
    },
    mounted() {
        
    },
}

</script>

