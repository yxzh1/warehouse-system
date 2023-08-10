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
      <el-button type="primary" style="margin-left: 5px;" size="small" @click="add">新增</el-button>
      <el-button type="primary" style="margin-left: 5px;" size="small" @click="inGoods">入库</el-button>
      <el-button type="primary" style="margin-left: 5px;" size="small" @click="outGoods">出库</el-button>
    </div>
    <el-table :data="tableData" :header-cell-style="{background:'#f3f6fd',color:'#555'}"
              border
              highlight-current-row
              @current-change="selectCurrentChange"
    >
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="name" label="物品名" width="180">
      </el-table-column>
<!--      <el-table-column prop="storage" label="仓库" width="180" :formatter="formatStorage">-->
      <el-table-column prop="storageName" label="仓库" width="180" >
      </el-table-column>
<!--      <el-table-column prop="goodsType" label="分类" width="180" :formatter="formatType">-->
      <el-table-column prop="goodstypeName" label="分类" width="180" >
      </el-table-column>
      <el-table-column prop="count" label="数量" width="180">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="180">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="edit(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？"
              style="margin-left: 5px;"
              @confirm="del(scope.row.id)"
          >
            <el-button slot="reference" size="small" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
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

    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>

      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="物品名" prop="name">
          <!--    调整文本框大小 例如  :span="11" 根据数字来-->
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="仓库" prop="storage">
          <!--    调整文本框大小 例如  :span="11" 根据数字来-->
          <el-col :span="20">
            <el-select v-model="form.storage"  placeholder="请选择仓库">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>


        <el-form-item label="分类" prop="goodsType">
          <!--    调整文本框大小 例如  :span="11" 根据数字来-->
          <el-col :span="20">
            <el-select v-model="form.goodsType"  placeholder="请选择分类">
              <el-option
                  v-for="item in goodstypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="数量" prop="count">
          <!--    调整文本框大小 例如  :span="11" 根据数字来-->
          <el-col :span="20">
            <el-input v-model="form.count"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="centerDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>

    <el-dialog
        title="出入库"
        :visible.sync="inDialogVisible"
        width="30%"
        center>

      <el-dialog
          width="70%"
          title="用户选择"
          :visible.sync="innerVisible"
          append-to-body>

        <select-user @doselectUser="doselectUser"></select-user>

        <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmUser">确 定</el-button>
        <el-button @click="innerVisible = false">取 消</el-button>
      </span>
      </el-dialog>

      <el-form ref="form1" :rules="rules1" :model="form1" label-width="80px">
        <el-form-item label="物品名" >
          <!--    调整文本框大小 例如  :span="11" 根据数字来-->
          <el-col :span="20">
            <el-input v-model="form1.goodsname" readonly></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="申请人" >
          <!--    调整文本框大小 例如  :span="11" 根据数字来-->
          <el-col :span="20">
            <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="数量" prop="count">
          <!--    调整文本框大小 例如  :span="11" 根据数字来-->
          <el-col :span="20">
            <el-input v-model="form1.count"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form1.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doInGoods">确 定</el-button>
        <el-button @click="inDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import SelectUser from "@/components/goods/SelectUser";
export default {
  name: "GoodsManage",
  components: {SelectUser},
  data(){
    let checkCount = (rule,value,callback)=>{
      if(value>9999){
        return callback(new Error('数量输入过大'))
      }else{
        return callback()
      }
    };
    return {
      tableData: [],
      storageData: [],
      goodstypeData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',
      remark:'',
      storageName:'',
      goodstypeName:'',
      storage:'',
      goodsType:'',
      centerDialogVisible:false,
      inDialogVisible:false,
      innerVisible:false,
      currentRow:{},
      tempUser:{},
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      rules: {
        name: [
          {required: true, message: '请输入物品名', trigger: 'blur'},
          {min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur'}
        ],
        storage: [
          {required: true, message: '请输入仓库名', trigger: 'blur'}
        ],
        goodsType: [
          {required: true, message: '请选择分类', trigger: 'blur'}
        ],
        count: [
          {required: true, message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数', trigger: 'blur'},
          {validator: checkCount, trigger: 'blur'}
        ]
      },
      rules1:{

      },
      form:{
        id:'',
        name:'',
        storage:'',
        goodsType:'',
        count:'',
        remark:''
      },
      form1:{
        goods:'',
        goodsname:'',
        count:'',
        username:'',
        userId:'',
        adminId:'',
        remark:'',
        action:'1'
      },

    }
  },
  methods:{
    //formatter的4个参数：row：整行数据； column：当前列对象； cellValue：当前单元格的值； index：行号（从0开始）
    // formatStorage(row,column, cellValue){
    //   let temp = this.storageData.find(item=>{
    //      return item.id = cellValue
    //   })
    //   return temp && temp.name
    // },
    // formatType(row){
    //   let temp = this.goodstypeData.find(item=>{
    //     return item.id = row.goodsType
    //   })
    //   return temp && temp.name
    // },
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    selectUser(){
      this.innerVisible=true
    },
    confirmUser(){
      this.form1.username = this.tempUser.name
      this.form1.userId = this.tempUser.id
      this.innerVisible = false
    },
    doselectUser(val){
      console.log(val)
      this.tempUser = val
    },
    edit(row){
      //展示表单  false为隐藏
      this.centerDialogVisible=true
      this.$nextTick(()=>{
        //赋值到表单
        this.form.id=row.id
        this.form.name=row.name
        this.form.storage=row.storage
        this.form.goodsType=row.goodsType
        this.form.count=row.count
        this.form.remark=row.remark
      })
    },
    del(id){
      this.$axios.get(this.$httpsUrl+'/goods/del?id='+id).then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },
    //新增按钮中 清除表单未提交的数据
    resetForm() {
      this.$refs.form.resetFields();
    },
    resetInForm() {
      this.$refs.form1.resetFields();
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpsUrl+'/goods/saveOrMod',this.form).then(res=>res.data).then(res=>{
            if(res.code==200){
              this.$message({
                message: '操作成功',
                type: 'success'
              });
              this.centerDialogVisible=false
              this.loadPost()
            }else{
              this.$message({
                message: '操作失败',
                type: 'error'
              });
            }
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });

    },
    doInGoods(){
      this.$refs.form1.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpsUrl+'/record/save',this.form1).then(res=>res.data).then(res=>{
            if(res.code==200){
              this.$message({
                message: '操作成功',
                type: 'success'
              });
              this.inDialogVisible=false
              this.loadPost()
              // this.resetInForm()
            }else{
              this.$message({
                message: '操作失败',
                type: 'error'
              });
            }
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });

    },
    inGoods(){
      if(!this.currentRow.id){
        alert('请选择记录')
        return ;
      }
      this.inDialogVisible=true
      this.$nextTick(()=>{
        this.resetInForm()
      })
      this.form1.goodsname = this.currentRow.name
      this.form1.goods = this.currentRow.id
      this.form1.adminId = this.user.id
      this.form1.action='1'
    },
    outGoods(){
      if(!this.currentRow.id){
        alert('请选择记录')
        return ;
      }
      this.inDialogVisible=true
      this.$nextTick(()=>{
        this.resetInForm()
      })
      this.form1.goodsname = this.currentRow.name
      this.form1.goods = this.currentRow.id
      this.form1.adminId = this.user.id
      this.form1.action='2'
    },
    add(){
      this.centerDialogVisible=true
      this.$nextTick(()=>{
        this.resetForm()
      })
    },
    //重置按钮功能
    resetParam(){
      this.name=''
      this.loadPost()
    },
    loadPost(){
      this.$axios.post(this.$httpsUrl+'/goods/listPageP',{
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
    loadStorage(){
      this.$axios.get(this.$httpsUrl+'/storage/list').then(res=>res.data).then(res=>{
        if(res.code==200){
          this.storageData=res.data
        }else{
          alert('获取数据失败')
        }
      })
    },
    loadGoodsType(){
      this.$axios.get(this.$httpsUrl+'/goodstype/list').then(res=>res.data).then(res=>{
        if(res.code==200){
          this.goodstypeData=res.data
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
    this.loadStorage()
    this.loadGoodsType()
    this.loadPost()
  }
}
</script>

<style scoped>

</style>