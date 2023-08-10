import Vue from 'vue'
import Vuex from 'vuex'
import router,{resetRouter} from "@/router";
import createPersistedState from "vuex-persistedstate"
Vue.use(Vuex)
// 由于 store 中的状态是响应式的，在组件中调用 store 中的状态简单到仅需要在计算属
function addNewRoute(menuList) {
    let routes = router.options.routes
    console.log(routes)
    routes.forEach(routeItem=>{
        if(routeItem.path=="/Index"){
            menuList.forEach(menu=>{
                let childRoute = {
                    path: '/'+menu.menuClick,
                    name: menu.menuName,
                    meta:{
                        title:menu.menuName
                    },
                    component:()=>import('../components/'+menu.menuComponent)
                }
                routeItem.children.push(childRoute)
            })
        }
    })
    resetRouter()
    router.addRoutes(routes)
    // {
    //     path: '/Admin',
    //         name: 'admin',
    //     meta:{
    //     title:'管理员管理'
    // },
    //     component:()=>import('../components/admin/AdminManage'),
    // },
    // {
    //     path: '/User',
    //         name: 'user',
    //     meta:{
    //     title:'用户管理'
    // },
    //     component:()=>import('../components/user/UserManage'),
    // }
}

// 性中返回即可。触发变化也仅仅是在组件的 methods 中提交 mutation。
export default new Vuex.Store({
    state: {
        menu: []
    },
    mutations: {
        setMenu(state,menuList) {
            state.menu = menuList

            addNewRoute(menuList)
        }
    },
    // actions: {
    //     addNAsync(context) {
    //         setTimeout(() => {
    //             context.commit('add')
    //         }, 1000)
    //     }
    // },
    getters: {
        getMenu(state) {
            return state.menu
        }
    },
    plugins: [createPersistedState({
        //默认存储到localStorage    想要存储到sessionStorage如下
                storage: window.sessionStorage,
                reducer(val) {
                    return { // 只储存state中的user
                        menu: val.base
                    }
                }
    })]
})