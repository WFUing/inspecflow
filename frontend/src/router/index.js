import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/assign',
      name: 'assign',
      component: () => import('../views/AssignView.vue')
    },
    {
      path: '/task',
      name: 'task',
      component: () => import('../views/TaskView.vue')
    },
    {
      path: '/supervise/:id',
      name: 'supervise',
      component: () => import('../views/SuperviseView.vue')
    },
    {
        path: '/flowable-task',
        name: 'flowable-task',
        component: () => import('../views/FlowableTaskView.vue')
    }
  ]
})

export default router
