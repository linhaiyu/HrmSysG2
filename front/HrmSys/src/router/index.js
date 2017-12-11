import Vue from 'vue'
import Router from 'vue-router'
import hrmIndex from '@/page/hrmIndex'
import hrmDept from '@/page/hrmDept'
import listDept from '@/page/listDept'
import addDept from '@/page/addDept'
import hrmJob from '@/page/hrmJob'
import listJob from '@/page/listJob'
import addJob from '@/page/addJob'
import hrmTrain from '@/page/hrmTrain'
import listTrain from '@/page/listTrain'
import addTrain from '@/page/addTrain'
import hrmNotice from '@/page/hrmNotice'
import listNotice from '@/page/listNotice'
import addNotice from '@/page/addNotice'
import hrmDocument from '@/page/hrmDocument'
import listDocument from '@/page/listDocument'
import addDocument from '@/page/addDocument'

Vue.use(Router)

export default new Router({
    routes: [{
            path: '/',
            component: hrmIndex
        },
        {
            path: '/hrmIndex',
            component: hrmIndex
        },
        {
            path: '/hrmDept',
            component: hrmDept,
            // 配置子路由
            children: [{
                    path: '',
                    component: listDept
                }, // 路径为 /hrmDept
                {
                    path: 'listDept',
                    component: listDept
                }, // 路径为 /hrmDept/listDept
                {
                    path: 'addDept',
                    component: addDept
                } // 路径为 /hrmDept/addDept
            ]
        },
        {
            path: '/hrmJob',
            component: hrmJob,
            // 配置子路由
            children: [{
                    path: '',
                    component: listJob
                }, 
                {
                    path: 'listJob',
                    component: listJob
                },
                {
                    path: 'addJob',
                    component: addJob
                } 
            ]
        },
        {
            path: '/hrmTrain',
            component: hrmTrain,
            // 配置子路由
            children: [{
                    path: '',
                    component: listTrain
                }, 
                {
                    path: 'listTrain',
                    component: listTrain
                }, 
                {
                    path: 'addTrain',
                    component: addTrain
                } 
            ]
        },
        {
            path: '/hrmNotice',
            component: hrmNotice,
            // 配置子路由
            children: [{
                    path: '',
                    component: listNotice
                },
                {
                    path: 'listNotice',
                    component: listNotice
                },
                {
                    path: 'addNotice',
                    component: addNotice
                }
            ]
        },
        {
            path: '/hrmDocument',
            component: hrmDocument,
            // 配置子路由
            children: [{
                    path: '',
                    component: listDocument
                },
                {
                    path: 'listDocument',
                    component: listDocument
                },
                {
                    path: 'addDocument',
                    component: addDocument
                }
            ]
        }

    ]
})