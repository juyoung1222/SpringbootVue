<!-- 작성자 우지원 -->


<template>
    <div>
        <b-container>
            <p class="text-center fs-4 fw-bold">검색하기</p>
            <b-form-input class="mb-5" v-model="keyword" @input="filtering"></b-form-input>
            <div class="vh-50 w-100 border rounded mb-5 text-truncate overflow-auto">
                <b-table :items="showList" :fields="listFields" hover fixed small striped size="sm">
                    <template #cell()="checkbox">
                        <b-form-radio
                        @change="lectSelect(checkbox.item)"
                        name="selected"
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
                    <template #cell(korCategory)="cate">
                        {{ cate.value }}
                    </template>
                </b-table>
            </div>
        </b-container>
        <hr>
        <b-container>
            <p class="fs-4 fw-bold text-center">선택된 강의 정보</p>
            <b-row cols="1" cols-md="4">
                <b-col>
                    <b-form-group
                        label="강의번호"
                    >
                        <b-form-input v-model="targetLect.lectNum" readonly></b-form-input>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group
                        label="강의명"
                    >
                        <b-form-input v-model="targetLect.title" readonly></b-form-input>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group
                        label="강사명"
                    >
                    <b-form-input v-model="targetLect.teacher" readonly></b-form-input>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-form-group
                        label="카테고리"
                    >
                        <b-form-input v-model="targetLect.cateKor" readonly></b-form-input>
                    </b-form-group>
                </b-col>
            </b-row>
            <div class="text-end">
                <b-button class="bedu-bg-custom-blue px-4 me-2" @click="lectDelete">삭제</b-button>
                <b-button class="px-4">취소</b-button>
            </div>
        </b-container>
    </div>
</template>

<script>
export default{
    name : 'lectureDelete',
    data() {
        return {
            lectList : [],
            showList : [],
            listFields: [
                {
                    key: 'checkbox',
                    label : ''
                },
                {
                    key : 'lectNum',
                    label : 'No',
                    sortable : true,
                },
                {
                    key : 'title',
                    label : '제목',
                    sortable: true,
                },
                {
                    key: 'teacher',
                    label : '강사',
                    sortable : true,
                },
                {
                    key : 'korCategory',
                    label : '소분류'
                },
            ],
            targetLect : {
                lectNum : '',
                teacher : '',
                title : '',
                thumbnail : '',
                lectDesc : '',
                cateCode : '',
                cateKor : '',
            },
        }
    },
    methods: {
        filtering(){
            this.showList = this.lectList.filter((item)=> item.title.includes(this.keyword))
        },
        /** 강의 조회 */
        getLectureList(){
            this.$axiosSend('get','/api/lect/getAllLectures')
            .then((res)=>{
                this.lectList = res.data.item
                this.showList = res.data.item
                this.targetLect.lectNum = '';
                this.targetLect.cateKor = '';
                this.targetLect.title = '';
                this.targetLect.teacher = '';
            })
            .catch((err)=>{
                console.log(err)
            })
        },
        lectSelect(item){
            this.targetLect.lectNum = item.lectNum;
            this.targetLect.thumbnail = item.thumbnail;
            this.targetLect.lectDesc = item.lectDesc;
            this.targetLect.cateCode = item.category;
            this.targetLect.cateKor = item.korCategory;
            this.targetLect.title = item.title;
            this.targetLect.teacher = item.teacher;

        },
        lectDelete(){
            if(this.targetLect.lectNum == '' || this.targetLect.lectNum == 0 || this.targetLect.lectNum == null){
                this.$swal({
                    title : '강의를 선택해주세요',
                    icon : 'info',
                    text : '강의가 선택되지 않았습니다.'
                })
                return;
            }
            this.$swal({
                title : '정말 삭제하시겠습니까?',
                text : '강의를 삭제하면 복구할 수 없습니다',
                showCancelButton : true,
                cancelButtonText : '아니오',
                confirmButtonText : '예'
            })
            .then((result)=>{
                if(result.isConfirmed){
                    this.$axiosSend('get','/api/admin/lectManage/lectDelete',{
                        num : this.targetLect.lectNum,
                        thumbnail : this.targetLect.thumbnail,
                        lectDesc : this.targetLect.lectDesc,
                    })
                    .then(()=>{
                        this.$swal({
                            title : 'success',
                            icon : 'success',
                            text : '강의가 삭제되었습니다'
                        })
                    })
                    .finally(()=>{
                        this.getLectureList();
                    })
                }
            })
        }
    },
    computed:{

    },
    mounted() {
        
    },
    created() {
        this.getLectureList();
    },
}

</script>