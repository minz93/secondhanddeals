
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import PostPostManager from "./components/listers/PostPostCards"
import PostPostDetail from "./components/listers/PostPostDetail"

import OfferOfferManager from "./components/listers/OfferOfferCards"
import OfferOfferDetail from "./components/listers/OfferOfferDetail"

import DealDealManager from "./components/listers/DealDealCards"
import DealDealDetail from "./components/listers/DealDealDetail"



export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/posts/posts',
                name: 'PostPostManager',
                component: PostPostManager
            },
            {
                path: '/posts/posts/:id',
                name: 'PostPostDetail',
                component: PostPostDetail
            },

            {
                path: '/offers/offers',
                name: 'OfferOfferManager',
                component: OfferOfferManager
            },
            {
                path: '/offers/offers/:id',
                name: 'OfferOfferDetail',
                component: OfferOfferDetail
            },

            {
                path: '/deals/deals',
                name: 'DealDealManager',
                component: DealDealManager
            },
            {
                path: '/deals/deals/:id',
                name: 'DealDealDetail',
                component: DealDealDetail
            },




    ]
})
