import { createRouter, createWebHistory } from 'vue-router';
import DashboardView from '@/views/DashboardView.vue';
import ProfileView from '@/views/ProfileView.vue';
import PortfolioView from '@/views/PortfolioView.vue';
import OrderView from '@/views/OrderView.vue';
import NotFoundView from '@/views/NotFoundView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/:stockId?',
            name: 'dashboard',
            component: DashboardView,
        },
        {
            path: '/my',
            name: 'profile',
            component: ProfileView,
        },
        {
            path: '/portfolios',
            name: 'portfolios',
            component: PortfolioView,
        },
        {
            path: '/orders',
            name: 'orders',
            component: OrderView,
        },
        {
            path: '/:catchAll(.*)',
            name: 'not-found',
            component: NotFoundView,
        },
    ]
});

export default router;