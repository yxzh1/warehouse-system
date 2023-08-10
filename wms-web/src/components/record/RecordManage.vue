<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入物品名" style="width: 200px;"
                suffix-icon="el-icon-search" size="small" @keyup.enter.native="loadPost"></el-input>

      <el-select v-model="storage" clearable placeholder="请选择仓库">
        <el-option
            v-for="item in storageData"
            :key="item.id+'-storage'"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodsType" clearable placeholder="请选择分类">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id+'-goodsType'"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <el-button type="primary" style="margin-left: 5px;" size="small" @click="loadPost">查询</el-button>
      <el-button type="success" style="margin-left: 5px;" size="small" @click="resetParam">重置</el-button>
    </div>
    <el-table :data="tableData" :header-cell-style="{background:'#f3f6fd',color:'#555'}" border>
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="goodsName" label="物品名" width="180">
      </el-table-column>
      <el-table-column prop="storageName" label="仓库" width="180" >
      </el-table-column>
      <el-table-column prop="goodstypeName" label="分类" width="180" >
      </el-table-column>
      <el-table-column prop="adminIdName" label="操作人" width="180" >
      </el-table-column>
      <el-table-column prop="userIdName" label="申请人" width="180" >
      </el-table-column>
      <el-table-column prop="count" label="数量" width="180" >
      </el-table-column>
      <el-table-column prop="createtime" label="操作时间" width="180" >
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="180">
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
  name: "RecordManage",
  data(){
    return {
      tableData: [],
      storageData: [],
      goodstypeData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',
      remark:'',
      storage:'',
      goodsType:'',
      centerDialogVisible:false,
      form:{
        id:'',
        name:'',
        storage:'',
        goodsType:'',
        count:'',
        remark:''
      }
    }
  },
  methods:{
    //重置按钮功能
    resetParam(){
      this.name=''
      this.loadPost()
    },
    loadPost(){
      this.$axios.post(this.$httpsUrl+'/record/listPageP',{
        "pageSize":this.pageSize,
        "pageNum":this.pageNum,
        "param":{
          name: this.name,
          storage: this.storage+'',
          goodsType: this.goodsType+'',
        }
      }).then(res=>res.data).then(res=>{
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