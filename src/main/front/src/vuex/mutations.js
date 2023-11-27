// 작성자 이준원


import * as types from './mutation_types'

export default {
    [types.EMAIL](state, email) {
        state.email = email
    },
    [types.NICKNAME](state, nickname) {
        state.nickname = nickname
    },
    [types.USERNUM](state, usernum) {
        state.usernum = usernum
    },
    [types.ERROR_STATE](state, errorState) {
        state.errorState = errorState
    },
    [types.IS_AUTH](state, isAuth) {
        state.isAuth = isAuth
    },
    [types.CLS](state, cls) {
        state.cls = cls
    },
    [types.UDY](state, udy) {
        state.udy = udy
    },
    [types.LESSONS](state, lessons) {
        state.lessons = lessons
    },
    [types.CBNUMLIST](state, cbnumList) {
        state.cbnumList = cbnumList
    },
    [types.QSBNUMLIST](state, qsbnumList) {
        state.qsbnumList = qsbnumList
    },
    [types.SUBINFO](state, subInfo) {
        state.subInfo = subInfo
    },
    [types.SUBSCRIBE](state, subscribe){
        state.subscribe = subscribe
    },
    [types.CBNUMLIST_REMOVE](state, commNum) {
        // 'state.cbnumList' 배열에서 'commNum' 값을 가진 요소의 인덱스를 찾습니다.
        const index = state.cbnumList.indexOf(commNum);
        // 인덱스가 -1보다 크다면 배열에서 해당 요소를 제거합니다.
        if (index > -1) {
          state.cbnumList.splice(index, 1);
        }
        // 변경된 'state.cbnumList' 배열을 문자열로 변환하여 'cbnumList'라는 localStorage 항목에 저장합니다.
        localStorage.setItem('cbnumList', JSON.stringify(state.cbnumList));
    },
    [types.CBNUMLIST_ADD](state, num) {
        // 'state.cbnumList' 배열에 'num'이 포함되어 있지 않다면 추가합니다.
        if (!state.cbnumList.includes(num)) {
          state.cbnumList.push(num);
          // 변경된 'state.cbnumList' 배열을 문자열로 변환하여 'cbnumList'라는 localStorage 항목에 저장합니다.
          localStorage.setItem('cbnumList', JSON.stringify(state.cbnumList));
        }
    }
}