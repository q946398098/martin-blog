import { createRouter, createWebHistory } from 'vue-router'
import EditUser from '../views/EditUser.vue'
import EditArticle from '../views/EditArticle.vue'
import Editor from '../components/Editor.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/user',
      children:[
        {
          path:'edit',
          component:EditUser
        }
      ],
    },
    {
      path:'/article',
      children:[
        {
          path:'edit',
          component:EditArticle
        }
      ],
    },
    {
      path:'/article',
      children:[
        {
          path:'ceshi',
          component:Editor
        }
      ],
    },
  ]
})

export default router
