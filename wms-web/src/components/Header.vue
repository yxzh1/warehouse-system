<template>
  <div style="display: flex;line-height:60px">
    <div style="margin-top: 8px;">
      <i :class="icon" style="font-size: 24px;cursor: pointer;" @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 32px;">
      <span>欢迎来到仓库管理系统</span>
    </div>

    <el-dropdown>
      <div>
        <span>{{user.name}}</span><i class="el-icon-arrow-down" style=" margin-left: 5px;"></i>
      </div>

      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>

</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      user : JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  methods:{
    toUser(){
      console.log("to_user")
      this.$router.push("/Home")
    },
    logout(){
      this.$confirm('确定退出?','提示',{
        confirmButtonText: '确定',
        type: 'warning',
        center: true
      })
          .then(()=>{
            //点确定走这里
            this.$message({
              type: 'success',
              message: '退出登录成功'
            })
            this.$router.push('/')
            sessionStorage.clear()
          })
          .catch(()=>{
            //点取消走这里
            this.$message({
              type: 'info',
              message: '取消退出'
            })
          })
    },
    collapse(){
      this.$emit('doCollapse')
    }
  },
  props:{
    icon:String
  },
  created() {
    this.$router.push("/Home")
  }
}
</script>

<style scoped>

</style>