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
                        <b-button class="fs-5 px-1 py-2 px-lg-5 bedu-bg-custom-blue w-100" type="submit">검색</b-button>
                    </div>
                </div>
            </div>
            
            <!-- 메인 컨테이너-->
            <b-container>
                <!-- 검색된 강의 목록 컨테이너-->
                <!-- <div class="w-50"> -->
                    <div class="p-4">
                        <b-container class="border rounded-4 pt-3 pb-5 scroll-y">
                            <b-form-group
                                class="fs-4 text-center"
                            >
                            <p class="text-center fs-3 mt-2 mb-4">조회된 강의 목록</p>
                            <b-table hover :fields="lectFields" :items="lists.filter((item)=>item.total>0)" class="fs-6" fixed small striped size="sm">
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
                            </b-form-group>
                        </b-container>
                    </div>
                <!-- </div> -->

                <!-- 우측 컨테이너-->
                <div>
                    <!-- 재생목록 컨테이너 -->
                    <b-container class="pt-4">
                        <b-container class="border rounded-4 pt-3 scroll-y video-container">
                            <p class="text-center fs-3">재생목록</p>
                            <b-table :fields="videoFields" :items="videoList" striped fixed small size="sm">
                                <template #cell()="checkbox">
                                    <b-form-radio 
                                    v-model="form"
                                    name="indexOptions"
                                    :value="checkbox.item"
                                    ></b-form-radio>
                                </template>
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
                    </b-container>
                    <!-- 폼 컨테이너 -->
                    <b-container class="py-4">
                        <b-form @submit.prevent="deleteVideo">
                            <!-- 제목 입력 -->
                            <b-form-group
                            label="동영상 제목"
                            label-for="video-title"
                            description="동영상의 제목을 입력해주세요"
                            class="mb-5"
                            disabled
                            v-model="form.lectDtlTitle"
                            >
                                <b-form-input id="video-title"
                                    v-model="form.lectDtlTitle"
                                    required :state="state" trim
                                    class="form-control-lg"
                                    disabled
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group
                            label="동영상 재생 인덱스"
                            label-for="video-index"
                            description="동영상의 인덱스를 선택해주세요"
                            >
                                <b-form-select
                                v-model="form.lectDtlIndex"
                                required
                                :options="indexOptions"
                                disabled
                                ></b-form-select>
                            </b-form-group>

                            <b-button 
                                class="w-100 py-3 bg-danger fs-5"
                                type="submit"
                            >
                                삭제하기
                            </b-button>
                        </b-form>
                    </b-container>
                </div>
            </b-container>
        {{ form }}
    </div>
</template>


<script>
export default{
    name:'fileUp',
    data() {
        return {
            keyword : '',
            form: {
                lectDtlNum : 0,
                lectNum : 0,
                lectDtlTitle: '',
                times: 0,
                lessonUrl : '',
                lectDtlIndex : 0,
            },
            videoFile : null,
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
            totalLists : [],
            progress : 0,
            videoList : [],
            videoFields : [
                {
                    key : 'checkbox',
                    label : ''
                },
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
        /** 동영상 삭제 메서드 */
        deleteVideo(){
            if(this.form.lectNum == 0){
                this.$swal({
                    icon : 'info',
                    text : '강의를 선택해주세요'
                })
                return;
            }

            this.$swal({
                title : '정말 삭제하시겠습니까?',
                icon : 'warning',
                text : '한번 삭제한 강의는 복구할 수 없습니다.',
                showCancelButton : true,
                confirmButtonText : '삭제',
                cancelButtonText : '취소',
            })
            .then((result)=>{
                if(result.isConfirmed){
                    this.$axiosSend('get','/api/admin/fileManage/deleteFile',{num : this.form.lectDtlNum})
                    .then(()=>{
                        this.$swal({
                            title : 'Success',
                            icon : 'Success',
                            text : '강의가 삭제되었습니다'
                        })
                        .then(()=>{
                            this.getVideoList()
                        })
                    })
                    .catch((err)=>{
                        console.log(err)
                    })
                }
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
    },
    created(){
        this.getTotalLecture();
    },
}

</script>