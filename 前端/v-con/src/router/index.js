import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'

export const constantRoutes = [
  // 登录路由
    {
        path: '/',
        name: 'Login',
        component: () => import('@/page/Login.vue'),
        meta:{requiresAuth:false},

    },
    // 系统主界面
    {
        path: '/user',
        name: 'User',
        component: () => import('@/page/User.vue'),
        meta:{requiresAuth:true},//这里用来登录验证的判断
        // 3个子路由
        children: [{
            path: 'table',
            name: 'Table',
            component: () => import('@/page/MainTable.vue'),
        },{
            path: 'census',
            name: 'Census',
            component: () => import('@/page/Census.vue'),
        },
        {
            path: 'list',
            name: 'List',
            component: () => import('@/page/List.vue'),
        },],
    }

]
export const router = createRouter({
    history: createWebHistory(), //history模式
    routes: constantRoutes, //上面的变量

})

// 全局路由守卫，每次发生跳转前都会运行
router.beforeEach((to, from, next) => { 
    
      if (to.matched.some(record => record.meta.requiresAuth)) { //搜的，获取meta里面这个属性的值，为true进去
        if (sessionStorage.getItem("login")) {//以Chrome浏览器为例，F12应用-存储-本地存储空间，这个只存在这一个标签页，如果关了就销毁了。
          next();//下个路由
          return;
        } else {
          next("/");//直接进就会跳到登录页，为什么是"/",可以看上面的路由路径配置
          return;
        }
      } else {//meta中的那个属性为flase,即登录页面不做拦截
        next();
        return;
      }
    });
export default router


