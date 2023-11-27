// 작성자 이준원


export default {
    // state에서 닉네임 값을 가져오는 getter
    getNickname: state => state.nickname,

    // state에서 이메일 값을 가져오는 getter
    getEmail: state => state.email,

    // state에서 사용자 번호 값을 가져오는 getter
    getUsernum: state => state.usernum,

    // state에서 CLS 값을 가져오는 getter
    getCls: state => state.cls,

    // state에서 UDY 값을 가져오는 getter
    getUdy: state => state.udy,

    // state에서 에러 상태 값을 가져오는 getter
    getErrorState: state => state.errorState,

    // state에서 인증 상태 값을 가져오는 getter
    getIsAuth: state => state.isAuth,

    // state에서 CbnumList 값을 가져오는 getter
    getCbnumList: state => state.cbnumList,

    // state에서 QsbnumList 값을 가져오는 getter
    getQsbnumList: state => state.qsbnumList,

    // state에서 구독 정보를 가져오는 getter
    getSubInfo : state => state.subInfo,
}