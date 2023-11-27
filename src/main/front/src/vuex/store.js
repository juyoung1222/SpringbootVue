// 작성자 이준원


import {createStore} from "vuex";
import getters from "./getters";
import mutations from "./mutations";
import actions from "./actions";    

// 사용자 로그인 상태 확인
const isLoggedIn = () => {
    const token = localStorage.getItem('user_token');
    return !!token;
};

export default createStore({
    // Vuex 스토어 설정
    state: {
        // 로그인 여부
        isLogin: isLoggedIn(),
        // 닉네임
        nickname: null,
        // 이메일
        email: null,
        // 사용자 번호
        usernum: null,
        // CLS
        cls: null,
        // 탈퇴여부
        udy: null,
        // 수강 목록
        lessons : null,
        // 사용자가 자유게시판 좋아요버튼 클릭 유무 리스트
        cbnumList: null,
        // 사용자가 질문게시판 좋아요버튼 클릭 유무 리스트
        qsbnumList: null,
        // 구독 정보
        subscribe : null,

        subInfo : null,

    },
    mutations,
    getters,
    actions    
});