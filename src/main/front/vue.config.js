const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
<<<<<<< HEAD
=======
  filenameHashing: false, // build 파일 해싱 제거
>>>>>>> ab2c6e0056c5bcd6ab1742269bc39d507d1f36ae
  devServer: {
    historyApiFallback: true,
    proxy: {
      // axios 사용시 URL을 "/api/" 로 시작하도록 해주세요
      // 개발 서버의 Mapping URL도 /api/ 로 시작되도록 해주세요
      "/api/": { 
        target: "http://localhost:8090",
        ws : false,
      }
    }
  }
})
