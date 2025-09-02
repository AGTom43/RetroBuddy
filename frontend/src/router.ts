import { createRouter, createWebHistory } from 'vue-router'

import BoardPage from '../src/pages/BoardPage.vue'
import App from "./App.vue";
import HomePage from "./pages/HomePage.vue";

const routes = [
    {
        path: '/',
        name: 'HomePage',
        component: HomePage,
    },
    {
        path: '/board/:boardId',
        name: 'Board',
        component: BoardPage,
        props: true
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
