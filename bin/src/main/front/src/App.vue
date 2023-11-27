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
                    this.$store.commit('CBNUMLIST', decodedToken.cbnumList);
                    this.$store.commit('IS_AUTH', true);
                    
                    this.$axiosSend('get','/api/lect/getMyPageList',{
                            userNum : this.$store.getters.getUsernum,
                    })
                    .then((res)=>{
                        this.$store.commit('LESSONS',res.data.item)
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
            this.$store.commit('IS_AUTH', false);
            this.$store.commit('NICKNAME', null);
            this.$store.commit('USERNUM', null);
            this.$store.commit('EMAIL', null);
            this.$store.commit('CLS', null);
            this.$store.commit('LESSONS', null);
            this.$store.commit('CBNUMLIST', null);
        }
    }
}
</script>

<style>

</style>
