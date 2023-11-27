<!-- 작성자 우지원 -->


<template>
    <div>
        <b-container class="py-5 d-flex">
            <!-- 왼쪽 장바구니 목록 컨테이너 -->
            <b-container class="w-75 me-auto">
                <!-- 장바구니 목록 컨테이너 상단 전체선택,삭제 -->
                <p class="fs-3 fw-bold">수강 바구니</p>
                <div class="d-flex">
                    <div class="form-check me-auto">
                        <input class="form-check-input" type="checkbox" id="check-all" @change="selectAll" v-model="allChecked">
                        <label class="form-check-label" for="check-all">
                            전체선택
                        </label>
                        {{ paymentList.length }}  / {{ carts.length }}
                    </div>
                    <b-button @click="removeFromCart(paymentList)" variant="danger">
                        선택삭제 <font-awesome-icon :icon="['fas', 'xmark']" />
                    </b-button>
                </div>
                <hr>
                <!-- 장바구니 목록  -->
                <div>
                    <b-row v-for="(cart, index ) in carts" :key="index" class="mb-3 border p-1 rounded-2">
                        <b-col cols="12" md="1" class="align-items-center justify-content-center d-block d-md-flex mb-2 mb-md-0">
                            <b-form-checkbox class="align-self-center text-center" :id="'cart-check-'+index" v-model="paymentList" :value="cart"></b-form-checkbox>
                        </b-col>
                        <b-col cols="4">
                            <div class="ratio ratio-16x9">
                                <b-img :src="cart.thumbnail"></b-img>
                            </div>
                        </b-col>
                        <b-col cols="8" md="4" class="align-self-center">
                            <div>{{ cart.title }}</div>
                            <div class="text-secondary">{{ cart.teacher }}</div>
                        </b-col>
                        <b-col cols="6" md="1" class="align-items-center justify-content-center d-flex">
                            <font-awesome-icon :icon="['fas', 'xmark']" role="button" @click="removeFromCart([cart])"/>                            
                        </b-col>
                        <b-col cols="6" md="2" class="d-flex">
                            <div class="vr h-100"></div>
                            <div class="align-self-center w-100 text-end">
                                <span class="text-danger fw-bold">
                                    {{ cart.price }} 
                                </span>
                                &nbsp;원
                            </div>
                        </b-col>
                    </b-row>
                </div>
            </b-container>

            <!-- 오른쪽 결제 내용 박스 -->
            <b-container class="w-25 ms-auto py-5 d-none d-xl-block">
                <b-container class="border rounded-3 py-3 mb-2">
                    <p class="fw-bold">구매자 정보</p>
                    <p>
                        <span>닉네임 :</span> {{ userInfo.nickname }}
                    </p>
                </b-container>
                <b-container class="border rounded-3 py-3">
                    <div class="d-flex mb-3">
                        <span class="me-auto fw-bold fs-5">총 결제 금액</span>
                        <span>
                            {{ getCurrencyPrice }}
                        </span>
                    </div>
                    <b-button class="w-100 bedu-bg-custom-blue" @click="cashOut">결제하기</b-button>
                </b-container>
            </b-container>

            <b-container class="position-fixed bottom-0 w-100 p-3 bg-white start-0 d-flex shadow-lg">
                <div class="ms-4 text-center align-self-center">
                    <span class="fw-bold">
                        {{ getCurrencyPrice }}
                    </span>
                    원
                </div>
                <b-button class="ms-auto px-5" @click="cashOut">결제하기</b-button>
            </b-container>
        </b-container>
    </div>
</template>

<script>
import '@/assets/css/lectureStyle.css';
import axios from 'axios';

export default{
    name : 'lecturePayment',
    data() {
        return {
            total : 0, // 수강 바구니에 있는 강의 전체 갯수 (int)
            carts : [], // 수강 바구니 강의 목록 (array)
            paymentList : [], // 결제할 강의 목록
            userInfo : { // 사용자 정보
                userNum : this.$store.state.usernum,
                email : '',
                password : '',
                nickname : this.$store.state.nickname,
                regDate : '',
                cls : '',
                urd : '',
                udy : 0

            }, 
            totalPrice : 0,
            allChecked : false,
        }
    },
    methods: {
        getCarts(){ /** 장바구니 조회 */
            this.$axiosSend('get','/api/lect/getCart',{
                userNum : this.$store.state.usernum
            })
            .then((res)=>{
                this.carts = res.data.item;
            })
            .catch((err)=>{
                console.log(err)
            })
        },
        removeFromCart(item){ /** 장바구니에서 삭제 메서드 */
            
            /** 아무것도 체크하지 않고 선택 삭제시 바로 리턴 */
            if(item.length == 0){
                return;
            }

            /** 장바구니에서 지울 강의 고유번호만 파라미터로 전달하기위해 리스트 생성 */
            const arg = [];
            for(var i =0;i<item.length;i++){
                arg.push(item[i].lectNum);
            }

            this.$axiosSend('post','/api/lect/removeFromCart'
            ,{
                userNum : this.userInfo.userNum,
                list : arg.join(","),
            }
            )
            .then(()=>{
                this.getCarts();
                this.paymentList=[];
            })
            .catch((err)=>{
                console.log(err)
            })
        },
        /** 전체 선택  */
        selectAll(){ 
            if(this.paymentList == this.carts){
                this.paymentList = [];
            } else {
                this.paymentList = this.carts;
            }
        },
        /** 결제 핸들러 */
        cashOut(){
            /*
            
            ...결제 API 호출 과정...
            완료 되었다는 가정 하에 코드 작성

            */
            let result = true;

            const args = [];
            for(var i =0;i<this.paymentList.length;i++){
                args.push({
                    userName : this.$store.getters.getNickname,
                    lectNum : this.paymentList[i].lectNum,
                    lectName : this.paymentList[i].title
                })
            }
            console.log(args)
            console.log(JSON.stringify(args))

            if(result){
                axios.post('/api/lect/addToMyPage',JSON.stringify(args),{
                headers:{
                    'Content-Type': 'application/json',
                }
                })
                .then((res)=>{
                    if(res.data == 1 ){
                        this.$axiosSend('get','/api/lect/getMyPageList',{
                            userName : this.$store.getters.getNickname,
                    })
                    .then((res)=>{
                        this.$store.commit('LESSONS',res.data.item)
                        this.$swal({
                        title : '감사합니다',
                        icon : 'success',
                        text : '결제가 성공적으로 완료되었습니다'
                        })
                        .then(()=>{
                            this.removeFromCart(this.paymentList);
                        })
                        }
                    )
                    .catch((err)=>{
                        console.log(err)
                    })
                    }

                })
                .catch((err)=>{
                    console.log(err)
                    this.$swal({
                        title : '결제 중에 문제가 발생했습니다',
                        icon: 'error',
                        text : '결제 중에 문제가 발생했습니다. 다시 시도해주세요'
                    })
                })
            }

        }
    },
    created() {

    },
    mounted() {
        this.getCarts();
    },
    computed:{
        getCurrencyPrice(){ /** 가격을 원화 형태로 변경하여 리턴 */
            return this.totalPrice.toLocaleString('ko-KR');
        }
    },
    watch:{
        paymentList:function(){ /** 체크박스 변경될 때마다 결제해야할 가격, 전체 체크 박스의 변경 */
            this.totalPrice = 0;
            for(var i =0; i < this.paymentList.length;i++){
                this.totalPrice +=  Number(this.paymentList[i].price.replace(",",""));
            }

            if(this.paymentList.length == this.carts.length){
                this.allChecked = true;
            } else {
                this.allChecked = false;
            }
        }
    }
}

</script>