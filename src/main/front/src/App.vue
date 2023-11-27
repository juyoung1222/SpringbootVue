<template>
    <BeduView/>
</template>

<script>
import BeduView from '@/components/BeduView'
import jwt_decode from 'jwt-decode';

export default {
    name: 'App',
    components: {
        BeduView
    },
    created() {
        this.checkToken()
    },
    methods: {
        checkToken() {
            const token = localStorage.getItem('user_token');
            if (token) {
                try {
                    const decodedToken = jwt_decode(token);
                    this.$store.commit('NICKNAME', decodedToken.nickname);
                    this.$store.commit('USERNUM', decodedToken.usernum);
                    this.$store.commit('EMAIL', decodedToken.email);
                    this.$store.commit('CLS', decodedToken.cls);
                    this.$store.commit('UDY', decodedToken.udy);
                    this.$store.commit('CBNUMLIST', decodedToken.cbnumList);
                    this.$store.commit('QSBNUMLIST', decodedToken.qsbnumList);
                    this.$store.commit('SUBINFO', decodedToken.subInfo);
                    this.$store.commit('IS_AUTH', true);
                    
                    // 구독 정보 저장
                    this.$axiosSend('get','/api/membership/getSubInfo',{
                        nickname : this.$store.getters.getNickname
                    })
                    .then((res)=>{
                        this.$store.commit('SUBSCRIBE',res.data.item)
                    })
                    .catch((err)=>{
                        console.log(err)
                    })

                } catch (error) {
                    console.error('Invalid token:', error);
                    this.logout();
                }
            } else {
                this.logout();
            }
        },
        logout() {
            localStorage.removeItem('cbnumList')
            localStorage.removeItem('qsbnumList')
            
            this.$store.commit('IS_AUTH', false);
            this.$store.commit('NICKNAME', null);
            this.$store.commit('USERNUM', null);
            this.$store.commit('EMAIL', null);
            this.$store.commit('CLS', null);
            this.$store.commit('UDY', null);
            this.$store.commit('LESSONS', null);
            this.$store.commit('CBNUMLIST', null);
            this.$store.commit('QSBNUMLIST', null);
            this.$store.commit('SUBSCRIBE',null);
            this.$store.commit('SUBINFO',null);
        }
    }
}
</script>

<style>

</style>
