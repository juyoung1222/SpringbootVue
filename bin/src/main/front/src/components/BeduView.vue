<template>
    <header>
        <PageHeader/> <!-- 헤더 컴포넌트 -->
    </header>
    <div id="wrap">
        <div class="content-wrap">
            <router-view/> <!-- 페이지 이동이 표시될 곳 -->
        </div>
    </div>
    <footer>
        <PageFooter/> <!-- 푸터 컴포넌트 -->
        <b-link :to="test"></b-link>
    </footer>
</template>
  
<script>
    import PageHeader from '@/components/PageHeader'
    import PageFooter from '@/components/PageFooter'
    
    export default {
        name: 'App',
        components: {
        PageFooter,
        PageHeader
        },
        
    }
</script>
  
<style>
    nav a.router-link-exact-active {
        color: #42b983;
    }
  
</style>
  