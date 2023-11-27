import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import store from './vuex/store'

/** Bootstrap, Bootstrap-vue-3 */
import { BootstrapVue3 } from 'bootstrap-vue-3'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

/** fontawesome import */
import { library } from '@fortawesome/fontawesome-svg-core'
import { fab } from '@fortawesome/free-brands-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(fab, fas, far)

/** axios 기본 URL */
// 기본 로컬용 주소
axios.defaults.baseURL="http://localhost:8090";
// API연동 테스트용 POSTMAN 주소
// axios.defaults.baseURL="https://cb1a9118-62cc-4506-99e4-100f77b0f337.mock.pstmn.io";


/** sweetalert 임포트 */
import VueSweetalert2 from 'vue-sweetalert2'
import 'sweetalert2/dist/sweetalert2.min.css'

/** axios, router 전역 변수로 사용 */
const app = createApp(App);
// axios, router는 공통유틸 함수를 이용하도록 변경
// app.config.globalProperties.$axios = axios;
app.config.globalProperties.$router = router;


/** CkEditor 임포트 */
import CKEditor from "@ckeditor/ckeditor5-vue";

/** 폰트, 컬러 3종(black, yellow, blue) 임포트 */
import '@/assets/css/common.css'

// 공통유틸 임포트
import commonUtils from './utils/commonUtils'


app
.use(router)
.use(BootstrapVue3)
.use(CKEditor)
.use(store)
.use(VueSweetalert2)
.use(commonUtils)
.component("font-awesome-icon",FontAwesomeIcon) 
.mount('#app')