<!-- 작성자 우지원 -->


<template>
    <div>
        <!-- 상단 검색 부분 -->
        <div class="py-5">
            <p class="text-center fs-4 fw-bold">강의를 조회하세요</p>
            <div class="d-flex m-auto w-75">
                <div class="me-4 w-75">
                    <b-form-input 
                    v-model="keyword" 
                    class="w-100 m-auto form-control-lg py-1"
                    type="search"
                    @keyup="getLectureList"
                    ></b-form-input>
                </div>
                <div class="w-25">
                    <b-button class="fs-5 px-1 py-2 px-lg-5  bedu-bg-custom-blue w-100" type="submit">검색</b-button>
                </div>
            </div>
        </div>
            
        <!-- 메인 컨테이너-->
        
        <!-- 강의 목록 -->
        <b-container class="border rounded-4 pt-3 pb-5 scroll-y mb-3">
            <p class="text-center fs-3 mt-2 mb-4">조회된 강의 목록</p>
            <b-table hover :fields="lectFields" :items="lists" class="fs-6" fixed small striped size="sm">
                <template #cell()="checkbox">
                    <b-form-radio 
                    v-model="form.lectNum" 
                    name="lect-num" 
                    :value="checkbox.item.lectNum"
                    @change="getVideoList"
                    ></b-form-radio>
                </template>
                <template #cell(lectNum)="num">
                    {{ num.value }}
                </template>
                <template #cell(title)="title">
                    {{ title.value }}
                </template>
                <template #cell(teacher)="teacher">
                    {{ teacher.value }}
                </template>
            </b-table>
        </b-container>

        <!-- 동영상 목록 -->
        <b-container class="border rounded-4 mb-3 scroll-y video-container">
            <b-table :fields="videoFields" :items="videoList" striped fixed small size="sm">
                <template #cell(lectDtlTitle)="lectDtlTitle">
                    {{ lectDtlTitle.value }}
                </template>
                <template #cell(times)="times">
                    {{ times.value }}
                </template>
                <template #cell(lectDtlIndex)="index">
                    {{ index.value }}
                </template>
            </b-table>
        </b-container>

        <hr class="my-4">

        <b-row cols="1" cols-md="2" class="w-100">
            <b-col>
                <!-- 제목 입력 -->
                <b-form-group
                label="동영상 제목"
                label-for="video-title"
                description="동영상의 제목을 입력해주세요"
                class="mb-5"
                >
                    <b-form-input id="video-title"
                        v-model="form.lectDtlTitle"
                        required :state="state" trim
                        class="form-control-lg"
                        :disabled="form.lectNum == 0"
                    ></b-form-input>
                </b-form-group>
            </b-col>
            <b-col>
                <b-form-group
                label="동영상 재생 인덱스"
                label-for="video-index"
                description="동영상의 인덱스를 선택해주세요"
                >
                    <b-form-select
                    v-model="form.lectDtlIndex"
                    required
                    :options="indexOptions"
                    :disabled="form.lectNum == 0"
                    ></b-form-select>
                </b-form-group>
            </b-col>
            <b-col>
                <!-- 프로그레스 바 -->
                <b-progress max="100">
                    <b-progress-bar variant="warning" :value="progress" animated :label="progress+'%'"></b-progress-bar>
                </b-progress>

                <!-- 동영상 업로드 -->
                <div class="mb-5">
                    <label for="video-file" class="form-label"></label>
                    <input 
                        class="form-control-lg form-control" 
                        type="file" 
                        id="video-file" 
                        accept="video/mp4,video/mkv, video/x-m4v,video/*"
                        @change="fileChange"
                        :disabled="form.lectNum == 0"
                        >
                </div>
            </b-col>
            <b-col>
                <b-form @submit.prevent="uploadVideo">
                    <b-button 
                    class="w-100 py-3 bedu-bg-custom-blue fs-5"
                    type="submit"
                    >
                        등록하기
                    </b-button>
                </b-form>
            </b-col>
        </b-row>
    </div>
</template>


<script>
import axios from 'axios'


export default{
    name:'fileUp',
    data() {
        return {
            keyword : '',
            form: {
                lectNum : 0,
                lectDtlTitle: '',
                lectDtlTime: 0,
                lectDtlIndex : 0,
            },
            videoFile : null,
            totalLists : [],
            lists:[],
            lectFields: [
                {
                    key : 'checkbox',
                    label : '',
                },
                {
                    key : 'lectNum',
                    sortable: true,
                    label : 'No'
                },
                {
                    key : 'title',
                    sortable: true,
                    label : '제목'
                },
                {
                    key : 'teacher',
                    sortable: true,
                    label : '강사'
                },
            ],
            progress : 0,
            videoList : [],
            videoFields : [
                {
                    key : 'lectDtlTitle',
                    label : '제목',
                    sortable : true,
                },
                {
                    key : 'times',
                    label : '재생 시간',
                    sortable : true,
                },
                {
                    key : 'lectDtlIndex',
                    label : '재생순서',
                    sortable : true,
                },
            ],
            indexOptions : [],
        }
    },
    methods: {
        /** 입력된 검색어가 포함된 제목을 가진 강의 목록 반환 무한스크롤X */
        getLectureList(){
            this.lists = this.totalLists.filter((item)=> item.title.includes(this.keyword))
        },
        /** 파일 변경, 업로드시 데이터 바인딩을 위한 메서드 */
        fileChange(e){
            this.videoFile = e.target.files[0];
            var video = document.createElement('video');
            video.preload = 'metadata';
            
            var self = this;

            video.onloadedmetadata = function() {
                self.form.lectDtlTime =  Math.round(video.duration);
            }

            if(this.videoFile != undefined){ 
                video.src = URL.createObjectURL(this.videoFile)
            }

        },
        uploadVideo(){
        
            // 데이터 공백 체크
            if(this.form.lectNum == 0){
                this.$swal({
                    icon : 'info',
                    text : '강의를 선택해주세요'
                })
                return;
            }
            if(this.form.videoTime<=0){
                this.$swal({
                    icon : 'info',
                    text : '동영상 재생 시간을 확인해주세요'
                })
                return;
            }
            if(this.videoFile == null){
                this.$swal({
                    icon : 'info',
                    text : '동영상 파일을 확인해주세요'
                })
                return;
            }
            // 공백 체크 종료

            const fileSize =  this.videoFile.size // 업로드하는 파일 크기
            
            const chunkSize = 1024*1024; // 1mb 
            
            const totalChunk = Math.floor(fileSize/chunkSize)+1; // 청크 갯수

            let currentChunk = 0; // 현재 청크 번호


            /** 업로드된 파일을 10mb로 나누어 업로드 하는 재귀함수 */
            const fileUpload = () => {


                const begin = currentChunk * chunkSize;
                const end = Math.min(begin+chunkSize, fileSize);

                const chunk = this.videoFile.slice(begin,end)

                const formData = new FormData();
                formData.append("lectNum",this.form.lectNum);
                formData.append("lectDtlTitle", this.form.lectDtlTitle);
                formData.append("lectDtlTime",this.form.lectDtlTime);
                formData.append("videoFile",chunk,this.videoFile.name);
                formData.append("chunkNumber",currentChunk);
                formData.append("totalChunk",totalChunk);
                formData.append("userNum",this.$store.getters.getUsernum)
                formData.append("lectDtlIndex", this.form.lectDtlIndex)

                axios.post('/api/admin/fileManage/uploadFormAction',formData,{
                    headers:{
                        "Content-Type" : "multipart/form-data"
                    }
                })
                .then((res)=>{
                    currentChunk++;
                    if(res.status == 206){ // 부분만 완료 되었으면 제귀함수 호출
                        this.progress = Math.round(currentChunk / (totalChunk-1) * 100 )
                        fileUpload();
                    } else if (res.status == 200){ // 전체 완료시 종료, 이후 데이터 처리 코드는 추후 작성
                        this.$swal({
                            title : 'Success',
                            icon : 'success',
                            text : '강의가 성공적으로 등록되었습니다',
                        })
                        .then(()=>{
                            this.getVideoList()
                        })
                        .finally(()=>{
                            return;
                        })
                    }
                })
                .catch((err)=>{
                    this.$swal({
                        title:'Error!',
                        icon : 'error',
                        text : '동영상을 업로드 하는 중에 문제가 발생했습니다'
                    })
                    console.log(err)
                })
            }

            fileUpload(); // 제귀함수 호출
        },
        /** 선택된 강의에 따른 동영상 조회 메서드 */
        getVideoList() { 
            this.$nextTick(()=>{
                this.$axiosSend('get', '/api/lect/getVideoList', {num: this.form.lectNum})
                .then((res) => {
                    if(this.$isNotEmptyArray(res.data)){
                        this.indexOptions = [];
                        this.videoList = res.data;
                        if(res.data.length){
                            for(var i =0; i < res.data.length; i++){
                                this.indexOptions.push(i+1)
                            }
                            this.indexOptions.push(this.indexOptions.length+1)
                        } else {
                            this.indexOptions.push(1);
                        }
                    } else {
                        this.$swal({
                            title : 'ERROR!',
                            icon : 'error',
                            text : '데이터를 불러오는데 에러가 발생했습니다.'
                        })
                        return;
                    }
                })
                .catch((err) => {
                    console.log(err)
                });
            })
            },
        /** 모든 강의 목록을 조회하는 메서드 */
        getTotalLecture(){
            this.$axiosSend('get','/api/admin/fileManage/getTotalLecture')
            .then((res)=>{
                this.totalLists = res.data.item;
                this.lists = res.data.item;
            })
            .catch((err)=>{
                console.log(err)
            })
        },
    },
    created(){
        this.getTotalLecture();
    },
    mounted() {
    },
}

</script>