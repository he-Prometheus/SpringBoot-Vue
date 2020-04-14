* # springboot-vue

  > #### 前后端分离的小demo
  >
  > 前端使用Vue+axios+Boostrap+elementui
  >
  > 前端地址：https://github.com/he-Prometheus/Vue-SpringBoot.git
  >
  > 后端使用SpringBoot+SpringData
  >
  > 后端地址：https://github.com/he-Prometheus/SpringBoot-Vue.git
  >
  > 该demo使用SpringBoot完成cros跨域，axios设置成跨域时携带cookie(axios.defaults.withCredentials = true)。
  >
  > ​
  >
  > 因为使用了栅格样式，所以需要安装boostrap/jquery/popper.js
  >
  > 参考：<https://blog.csdn.net/tom_wong666/article/details/82795641>
  >
  > 表单提交时使用elementui提供的表单，需安装element-ui
  >
  > 异步提交使用axios，同时使用qs查询字符串解析和序列化字符串,需要安装axios和qs
  >
  > ​
  >
  > 该vue项目使用vue init webpack生成，看起来文件很多，但只要关注以下几个地方
  >
  > ```
  > ---------builde
  >       ------------webpack.base.conf.js
  > ---------src
  >       ------------ components（目录）
  >       ------------ router
  > 	  ------------ index.js
  >       ------------ App.Vue
  >       ------------ main.js
  > ---------package.json
  > ```
  >
  > ​
  >
  > demo实现的功能有:登录、注册、首页展示、分类展示、模糊查询、列表管理（增删改查）
  >
  > ​
  >
  > ​

  ​

  ​

  ​

  ## Build Setup

  ```bash
  # install dependencies
  npm install

  # serve with hot reload at localhost:8080
  npm run dev

  # build for production with minification
  npm run build

  # build for production and view the bundle analyzer report
  npm run build --report

  # run unit tests
  npm run unit

  # run e2e tests
  npm run e2e

  # run all tests
  npm test
  ```

  For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

