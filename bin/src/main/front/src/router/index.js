import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'main',
        component: () => import('../views/HomeView.vue')
    }
    ,
    {
        path: '/course',
        name: 'course',

        component: () => import('../views/lecture/LectureCourse.vue')
    }
    , 
    {
        path : '/lectureDetail',
        name : 'lectureDetail',

        component : () => import('@/views/lecture/LectureDetail.vue')
    }
    ,
    {
        path : '/lectureField',
        name : 'lectureField',

        component : () => import('@/views/lecture/LectureField.vue')
    }
    ,{
        path : '/lectureCategories/:index',
        name : 'lectureCategories',

        component : () => import('@/views/lecture/LectureCategories.vue')
    },
    {
        path : '/lectureSearch',
        name : 'lectureSearch',

        component : () => import('@/views/lecture/LectureSearch.vue')
    },
    {
        path : '/lectureLesson',
        name : 'lectureLesson',

        component : () => import('@/views/lecture/LectureLesson.vue')
    },
    {
        path : '/lecturePayment',
        name : 'lecturePayment',

        component : () => import('@/views/lecture/LecturePayment.vue')
    }
    ,
    {
        path: '/review',
        name: 'review',
        component: () => import('../views/Review/StudyReview.vue')
    }
    ,
    {
        path: '/review/reviewWrite',
        name: 'reviewWrite',
        component: () => import('../views/Review/ReviewWrite.vue')
    }
    ,
    {
        path: '/companyStudy',
        name: 'companyStudy',
        component: () => import('../views/CompanyStudy.vue')
    }
    ,
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/user/LoginView.vue')
    }
    ,
    {
        path: '/regist',
        name: 'regist',
        component: () => import('../views/user/RegistView.vue'),
    }
    ,
    {
        path: '/mypage',
        name: 'mypage',
        component: () => import('../views/user/MypageView.vue'),
    }
    ,
    {
        path: '/comm/freBd',
        name: 'freeBoard',
        component: () => import('../views/comm/FreeBoardView.vue')
    }
    ,
    {
        path: '/comm/freBdWrite',
        name: 'freeBoardWrite',
        component: () => import('../views/comm/FreeBoardWrite.vue')
    }
    ,
    {
        path: '/comm/freBdDetail/:num',
        name: 'freeBoardDetail',
        component: () => import('../views/comm/FreeBoardDetail.vue')
    }
    ,
    {
        path: '/comm/freBdEdit/:num',
        name: 'freeBoardEdit',
        component: () => import('../views/comm/FreeBoardEdit.vue')
    }

    ,
    {
        path: '/comm/qna',
        name: 'qnaBoard',
        component: () => import('../views/comm/QnaBoardView.vue')
    }
    ,
    {
        path: '/comm/qnaWrite',
        name: 'qnaBoardwrite',
        component: () => import('../views/comm/QnaBoardWrite.vue')
    }
    ,
    {
        path: '/comm/qnaDetail/:num',
        name: 'qnaBoarddetail',
        component: () => import('../views/comm/QnaBoardDetail.vue')
    }
    ,
    {
        path: '/comm/qnaEdit/:num',
        name: 'qnaBoardedit',
        component: () => import('../views/comm/QnaBoardEdit.vue')
    }
    ,
    {
        path: '/csc',
        name: 'csc',
        component: () => import('../views/Notice/CscView.vue')
    }
    ,
    {
        path: '/inquiry',
        name: 'inquiry',
        component: () => import('../views/Notice/inquiry.vue')
    }
    ,
    {
        path: '/tou',
        name: 'tou',
        component: () => import('../views/Notice/TouView.vue')
    }
    ,
    {
        path: '/privacy',
        name: 'privacy',
        component: () => import('../views/Notice/PrivacyView.vue')
    }
    ,
    {
        path: '/fileManage',
        name: 'fileManage',
        component: () => import('../views/adminPage/FileManage.vue')
    },
    {
        path: '/lectureManage',
        name : 'lectureManage',
        component:() => import('@/views/adminPage/LectureManage.vue')
    },
    {
        path: '/adminPage',
        name: 'adminPage',
        component: () => import('../views/adminPage/AdminPage.vue')
    },
    {
        path: '/membership',
        name: 'membership',

        component : () => import('@/views/Membership.vue')

    },
    {
        path: '/mypageAll',
        name: 'mypageAll',

        component : () => import('@/views/user/MypageUserLectureDetail.vue')

    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
    // eslint-disable-next-line
    scrollBehavior (to, from, savedPosition) {
        if (to.name === 'regist' || to.name === 'login') {
            return null; // 회원가입과 로그인은 자동스크롤 방지
        }
        return { top: 0 }
    }
})

export default router
