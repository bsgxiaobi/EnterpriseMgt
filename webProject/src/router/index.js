import { createRouter, createWebHistory } from 'vue-router'
// import Login from '@/views/Login.vue'
import {
	dashboardRoutes,
	mySelfRoutes,
	customerRoutes,
	personalRoutes,
	purchaseRoutes,
	salesRoutes,
	warehouseRoutes,
	financeRoutes
} from './childrenRoutes.js';
import Layout from '@/layout/Index.vue';
const routes = [
	{
		path: '/login',
		name: 'Login',
		component: ()=>import('@/views/Login/Login.vue')
	},
	{
		path: '/',
		name: 'Index',
		component: Layout,
		redirect:'/dashboard/workplace'
	},
	dashboardRoutes,
	mySelfRoutes,
	customerRoutes,
	personalRoutes,
	purchaseRoutes,
	salesRoutes,
	warehouseRoutes,
	financeRoutes
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
