<template>

  <el-dialog v-model="dialogVisible" title="User" width="30%" draggable>
    <template #footer>
      <span class="dialog-footer">
        <el-form ref="form"
                 :model="userForm"
                 :rules="rules"
                 label-width="150px">

           <el-input v-model="userForm.cd_user" type="hidden"></el-input>

          <el-form-item label="Name:">
                    <el-input v-model="userForm.ds_user" />
            </el-form-item>

            <el-form-item label="Account:">
                    <el-input v-model="userForm.ds_account" />
            </el-form-item>

            <el-form-item label="Password:">
                    <el-input v-model="userForm.ds_password" />
            </el-form-item>
        </el-form>

        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addUserForm">
          Confirm
        </el-button>
      </span>
   </template>
  </el-dialog>

  <div class="filter">
    <div class="filter-title"></div>
    <el-form :inline="true" ref="form" :model="filter_form" label-width="80px">
          <el-form-item label="Age:" prop="age" >
            <el-input  v-model.number="filter_form.age" type="text" placeholder="age" clearable></el-input>
          </el-form-item>

          <el-form-item label="Account" prop="account" >
            <el-input v-model.number="filter_form.account" type="text" placeholder="account" clearable></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onQuery">Query</el-button>
          </el-form-item>

    </el-form>
  </div>

  <div class="toolbar">
      <el-row>
        <el-button type="warmer" @click="addUser" :icon="Plus" circle />
        <el-button type="danger" @click="batchDel" :icon="Delete" circle />
      </el-row>
  </div>

  <div class="content">
    <el-table
        :height="tableHeight"
        ref="multipleTableRef"
        @selection-change="handleSelectionChange"
        :data="tableData"
        style="width: 100%">

      <el-table-column type="selection" width="55" />


      <el-table-column label="Name" width="180">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.ds_user }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Account" width="180">
        <template #default="scope">
           <span>{{ scope.row.ds_account }}</span>
        </template>
      </el-table-column>

      <!-- 操作 -->
      <el-table-column label="Operations">
        <template #default="scope">

          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
            Edit
          </el-button>

          <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
            Delete
          </el-button>

        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        :total="total"  :current-page="currentPage" :page-size="pageSize"  @current-change="handlePageChange">
    </el-pagination>
  </div>
</template>

<script type="ts">

import axios from 'axios';
import {Plus,Delete} from '@element-plus/icons-vue';


export default {
  data() {
    return {
      tableData: [],
      dialogVisible:false,
      Delete,
      Plus,
      delRecids: [],
      userForm:{
        cd_user:-1,
        ds_password:this.createAccount(),
        ds_user:this.createUserName(),
        ds_account:this.createAccount(),
      },
      filter_form:{
        age:'',
        account:''
      },
      multipleSelection:[],
      tableHeight:'auto',
      total: '',
      filterKey: '',
      currentPage: 1,
      pageSize: 15
    };
  },

  mounted() {
    // 在组件挂载完成后，调用后台接口获取数据
    this.fetchData();
    this.calTableHeight();
    window.addEventListener('resize',this.calTableHeight);
  },
  methods: {
    createAccount(){
      let accountNumber = '';
      for (var i = 0; i < 9; i++) {
        accountNumber += Math.floor(Math.random() * 10);
      }
      return accountNumber;
    },
    createUserName(){
      var surnames = ['赵', '钱', '孙', '李', '周', '吴', '郑', '王', '冯', '陈', '楮', '卫', '蒋', '沈', '韩', '杨', '朱', '秦', '尤', '许', '何', '吕', '施', '张', '孔', '曹', '严', '华', '金', '魏', '陶', '姜', '戚', '谢', '邹', '喻', '柏', '水', '窦', '章', '云', '苏', '潘', '葛', '奚', '范', '彭', '郎', '鲁', '韦', '昌', '马', '苗'];
      var names = ['伟', '芳', '娜', '强', '军', '洋', '霞', '磊', '勇', '艳', '杰', '婷', '超', '秀英', '亮', '露', '平', '刚', '丽', '明', '静', '华', '浩', '慧', '辉', '萍', '鹏', '英', '健', '美', '新', '华', '波', '莉', '凯', '萌', '宇', '志', '秀兰', '建华', '丹', '文', '福', '荣'];
      var randomSurnameIndex = Math.floor(Math.random() * surnames.length);
      var randomNameIndex = Math.floor(Math.random() * names.length);
      var surname = surnames[randomSurnameIndex];
      var name = names[randomNameIndex];
      var fullName = surname + name;
      return fullName;
    },

    batchDel(){
        console.log(this.delRecids);
      var thisObj = this;
      axios.delete('http://localhost:8080/user/batchDel',{
          data:{
            recids : this.delRecids
          }
       }).then(response => {
        console.log(response.data);
        // thisObj.fetchData();
       }).catch(error => {
            console.error(error);
       });
    },
    addUser(){

      this.userForm = {
        cd_user:-1,
        ds_password:this.createAccount(),
        ds_user:this.createUserName(),
        ds_account:this.createAccount(),
      };
      this.dialogVisible  = !this.dialogVisible;
      console.log('addUser')
    },
    addUserForm(){
      let thisObj = this;
      axios.post("http://localhost:8080/user/upsert",this.userForm).then(response => {
        // 请求成功的处理逻辑
        this.dialogVisible  = !this.dialogVisible;
        thisObj.fetchData();
      }).catch(error => {
        // 请求失败的处理逻辑
        console.error(error);
      });
    },
    handleSelectionChange(v) {
        this.delRecids = [];
        v.forEach((i,k)=>{
          this.delRecids.push(i.cd_user);
          console.log(i.cd_user,k);
        })
    },
    calTableHeight(){
      const screenHeight = window.innerHeight;
      console.log('屏幕高度：', screenHeight);
      // 可以将 screenHeight 存储在 data 中，以便在模板中使用
      // this.tableData = screenHeight;
      this.tableHeight = (screenHeight - 250);
    },
    onQuery(){
      console.log('onQuery')
    },
    handleDelete(index , row) {
      let recid  = row.cd_user;
      console.log('recid',recid);
      var thisObj = this;
      axios.delete('http://localhost:8080/user/delete/'+recid)
          .then(function (response) {
            console.log(response.data);
            thisObj.fetchData();
          })
          .catch(function (error) {
            console.log(error);
       });
    },

    handlePageChange(v){
      console.log('handlePageChange',v);
      this.fetchData(v)
    },

    handleEdit(index,row){
      this.userForm = Object.assign({},this.userForm,row);
      console.log(this.userForm);
      this.dialogVisible = !this.dialogVisible;
    },

    fetchData(target_page) {

      if (!target_page){
        target_page = this.currentPage;
      }
      var thisObj = this;
      let url = 'http://localhost:8080/user/getUsersByPage/'+ target_page+ '/' + this.pageSize;
      // 使用你自己的后台请求方法获取数据
      // 这里使用 axios 作为示例
      axios.get(url)
          .then(response => {
            // 将获取到的数据赋值给 tableData
            thisObj.tableData = response.data.list;
            thisObj.total = response.data.total;
            thisObj.currentPage = response.data.pageNum;
          })
          .catch(error => {
            console.error(error);
          });
    }
  }
};
</script>