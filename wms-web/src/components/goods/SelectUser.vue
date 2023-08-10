<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入名字" style="width: 200px;"
                suffix-icon="el-icon-search" size="small" @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 5px;" size="small">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px;" size="small" @click="loadPost">查询</el-button>
      <el-button type="success" style="margin-left: 5px;" size="small" @click="resetParam">重置</el-button>
    </div>
    <el-table :data="tableData" :header-cell-style="{background:'#f3f6fd',color:'#555'}"
              border
              highlight-current-row
              @current-change="selectCurrentChange"
    >
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="no" label="账号" width="180">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="180">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'success'"
              disable-transitions>{{scope.row.sex == 1 ? '男':'女'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{scope.row.roleId == 0 ? '超级管理员':
              (scope.row.roleId === 1 ? '管理员' : '普通用户')}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" >
      </el-table-column>
    </el-table>
    <!--    分页-->
    <el-pagination
        style="text-align: center"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "SelectUser",
  data(){
    return {
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',
      sex:'',
      sexs:[
        {value:'1',label:'男'},
        {value:'0',label:'女'}
      ],
      centerDialogVisible:false,
    }
  },
  methods:{
    selectCurrentChange(val) {
      //this.currentRow = val;
      this.$emit("doselectUser",val)
    },
    //新增按钮中 清除表单未提交的数据
    resetForm() {
      this.$refs.form.resetFields();
    },
    //重置按钮功能
    resetParam(){
      this.name=''
      this.sex=''
      this.loadPost()
    },
    loadPost(){
      this.$axios.post(this.$httpsUrl+'/user/listPageP',{
        "pageSize":this.pageSize,
        "pageNum":this.pageNum,
        "param":{
          name:this.name,
          sex:this.sex,
          roleId:'2'
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert('获取数据失败')
        }
      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1 //保险 避免回不到第一页 偶尔可能出现这种问题
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    }
  },
  beforeMount() {
    this.loadPost()
  }
}
</script>

<style scoped>

</style>