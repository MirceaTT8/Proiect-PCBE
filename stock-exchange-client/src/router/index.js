import { createRouter, createWebHistory } from 'vue-router';
import DashboardView from '@/views/DashboardView.vue';
import ProfileView from '@/views/ProfileView.vue';
import PortfolioView from '@/views/PortfolioView.vue';
import OrderView from '@/views/OrderView.vue';
import NotFoundView from '@/views/NotFoundView.vue';
import TransactionView from "@/views/TransactionView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Dashboard',
            component: DashboardView,
        },
        {
            path: '/portfolio',
            name: 'Portfolio',
            component: PortfolioView,
        },
        {
            path: '/transactions',
            name: 'Transactions',
            component: TransactionView,
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
        {
            path: "/profile",
            name: "ProfileView",
            component: ProfileView,
        },
    ]
});

export default router;