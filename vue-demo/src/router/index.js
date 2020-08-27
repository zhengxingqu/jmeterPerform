import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import XnScript from "../components/XnScript";
import ScriptResult from "../components/ScriptResult";
import ReportImage from "../components/ReportImage";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/script',
      name: 'XnScript',
      component: XnScript
    },
    {
      path: '/scriptResult',
      name: 'ScriptResult',
      component: ScriptResult
    },
    {
      path: '/report',
      name: 'ReportImage',
      component: ReportImage
    }
  ]
})
