// 작성자 이준원


import axios from 'axios'

// 이메일과 비밀번호로 사용자 정보를 가져오는 함수
const getUserInfo = (email, password) => {
    const reqData = {
        'email': email,
        'password': password
    }

    return axios.post('/api/login', reqData, {
        headers: {
            'Content-type': 'application/json'
        }
    })
}

// 로그인 시간을 설정하는 함수
const setLoginTime = () => {
    const currentTime = new Date().getTime()
    const loginTime = currentTime + 3600000; // 1시간(3,600,000 밀리초) 추가
    localStorage.setItem('login_time', loginTime.toString())
}

// 클릭 이벤트 핸들러
// 클릭 이벤트가 발생할때 로그인 시간을 연장해줌
const handleClick = () => {
    const userToken = localStorage.getItem('user_token')
    if (userToken) {
        setLoginTime()
    }
}

// 클릭 이벤트 리스너 등록
document.addEventListener('click', handleClick)

// 주기적인 로그아웃 확인을 위한 함수
const checkLogoutTime = () => {
    const loginTime = localStorage.getItem('login_time')
    if (loginTime) {
        const currentTime = new Date().getTime()
        if (currentTime >= parseInt(loginTime, 10)) {
            // 로그아웃 처리
            localStorage.removeItem('user_token')
            localStorage.removeItem('login_time')
            localStorage.removeItem('cbnumList')
            localStorage.removeItem('qsbnumList')
            // 로그아웃시 새로고침
            window.location.reload()
        }
    }
}

// 로그인 함수
export default {
    async doLogin(email, password) {
        try {
            const getUserInfoPromise = getUserInfo(email, password)
            const [userInfoResponse] = await Promise.all([getUserInfoPromise])
            console.log(userInfoResponse.data.length)
            if (userInfoResponse.data.length === 0) {
                // 사용자 정보가 없을 경우 'notFound' 반환
                return 'notFound'
            } else {
                // 로그인 성공 시 로그인 시간 기록
                setLoginTime()
                localStorage.setItem('user_token', userInfoResponse.data.user_token)
                return userInfoResponse
            }
        } catch (err) {
            console.error(err)
        }
    }
}

// 주기적인 로그아웃 확인 설정 (예: 1초마다 확인)
setInterval(checkLogoutTime, 1000)