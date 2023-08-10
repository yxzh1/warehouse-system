<!--个人中心-->
<template>
  <div style="">
    <h1 style="">欢迎来到仓库管理系统</h1>
    <el-descriptions :title=" '欢迎你! ' + user.name" :column="2" size="40" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>账号
        </template>
        {{user.no}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-phone"></i>电话
        </template>
        {{user.phone}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-outline"></i>性别
        </template>
        {{user.sex}}
      </el-descriptions-item>
    </el-descriptions>
    <DateUtils></DateUtils>
  </div>
</template>

<script>
import DateUtils from "@/components/DateUtils";

export default {
  name: "Home",
  components:{DateUtils},
  data(){
    //排错 router报的错
    // const VueRouterPush = VueRouter.prototype.push
    // VueRouter.prototype.push = function push (to){
    //   return VueRouterPush.call(this,to).catch(err=>err)
    // }
    return{
      user:{}
    }
  },
  computed:{

  },
  methods:{
    init(){
      this.user = JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  beforeMount() {
    this.init()
  }

}
</script>

<style scoped>

</style>