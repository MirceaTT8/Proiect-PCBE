import {createMemoryHistory, createRouter } from 'vue-router'
import PortfolioView from '@/views/PortfolioView.vue'
import OrderView from '@/views/OrderView.vue'
import UserView from '@/views/UserView.vue'
import StockView from '@/views/StockView.vue'

const routes = [
    { path: '/portfolios', name: 'Portfolios', component: PortfolioView },
    { path: '/orders', name: 'Orders', component: OrderView },
    { path: '/users', name: 'Users', component: UserView },
    { path: '/stocks', name: 'Stocks', component: StockView },
    { path: '/', redirect: '/users' },
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})

export default router