const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,

  devServer: {//代理服务器
    proxy: {
      '/api': {//路径有这个的会跨域访问
        target: 'http://localhost:8082',//要访问的路径
        pathRewrite: { '^/api': '' },//访问前把/api换成/
        ws: true,
        // changeOrigin: true //假报地址
      },
    },
  },
  publicPath:'./',
})
