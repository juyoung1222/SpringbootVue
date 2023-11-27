<template>
    <div class="container w-75 mt-5 mb-3">
        <div class="mb-3">
            <h1>커뮤니티</h1>
        </div>
        <hr>
        <b-container class="justify-content-start text-start">
            <h5>
                {{ community.writer }}
            </h5>
            {{ community.writeDate }} 
            <h2 class="mt-3 mb-5 fw-bold">
                {{ community.title }}
            </h2>
            <div v-html="community.contents"></div>

            <b-container class="ms-auto text-end">
                <font-awesome-icon :icon="['fas', 'eye']" /> {{ community.view }}
                <font-awesome-icon :icon="['fas', 'thumbs-up']" /> 
                <text class="fw-bold ms-2">
                    {{ community.heart }}
                </text>
            </b-container>
            <b-button type="submit" class="btn-custom ms-2" :to="'/questionwriter'+'/'+community.num">글수정</b-button>
            <b-button type="submit" class="btn-custom ms-2">삭제</b-button>
            <hr class="my-5">
        </b-container>
    </div>
</template>

<script>
import router from '@/router';

export default{
    
    data() {
        return {
            result : 0,
            community : {
                title : '',
                contents : '',
                writer : '',
                writeDate : '',
                view : 0,
                heart : 0,
            }
        }
    },

    methods: {
        communityRead(num){ // 게시글 데이터 조회
            this.$axios.get('/api/community/detail',{
                params : {
                    num : num,
                }
            })
            .then(response=>{
                alert('성공');
                this.community = response.data;
            })
            .catch((error)=>{console.log(error)})
        },
        communitydelete(num){
            alert('게시글을 삭제합니다.');
            this.$axios.get('/question/boardDelete',{
                params : {
                    num : num,
                }
            })
            .then(res=>{
                this.result = res.data;
                this.$swal('Success','글삭제완료!','success'),
                router.push({
                        name:"question"
                })
                
            })
            .catch(error=>{
                console.log(error)
            })
        },

        communityedit(num){
            alert(num, '!');
        }

    },
    mounted() {
        const num = this.$route.params.num;
        this.communityRead(num);
    },
}
</script>
<style scoped>
.category{
    align-items: center;
    display: flex;
}
.category::before{
    line-height: 0px;
    height: 1px;
    background : black;
    margin : 0px 10px;
    flex-grow: 1;
    content : "";
}
.category::after{
    line-height: 0px;
    height: 1px;
    background : black;
    margin : 0px 10px;
    flex-grow: 6;
    content : "";
}

.thisCategory{
    color : #3498db;
}

blockquote {
    background-color: skyblue;
    margin : 10px;
}

</style>