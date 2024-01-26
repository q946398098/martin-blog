<template>

  <el-dialog v-model="dialogVisible" title="Article" width="30%" draggable>
    <template #footer>
      <span class="dialog-footer">
        <el-form ref="form"
                 :model="articleForm"
                 label-width="150px">
           <el-input v-model="articleForm.cd_article" type="hidden"></el-input>

            <el-form-item label="Title:">
                    <el-input v-model="articleForm.ds_article" />
            </el-form-item>

            <el-form-item label="Content:">
              <myEditor></myEditor>
            </el-form-item>

        </el-form>

        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addArticleForm">
          Confirm
        </el-button>
      </span>
   </template>
  </el-dialog>

  <div class="filter">
    <div class="filter-title"></div>
    <el-form :inline="true" ref="form" :model="filter_form" label-width="80px">

          <el-form-item label="Title" prop="Title" >
            <el-input v-model="filter_form.ds_article"  @input="handleFilterInput" type="text" placeholder="account" clearable></el-input>
          </el-form-item>

          <el-form-item label="Content" prop="Content" >
            <el-input v-model="filter_form.ds_content"  @input="handleFilterInput" type="text" placeholder="Name" clearable></el-input>
          </el-form-item>
<!--          <el-form-item>-->
<!--            <el-button type="primary" @click="onQuery">Query</el-button>-->
<!--          </el-form-item>-->
    </el-form>
  </div>

  <div class="toolbar">
      <el-row>
        <el-button type="warmer" @click="addArticle" :icon="Plus" circle />
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


      <el-table-column label="Title" width="180">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.ds_article }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Content" width="180">
        <template #default="scope">
           <span>{{ scope.row.ds_content }}</span>
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
import {ElNotification } from 'element-plus'
import {ref} from "vue";
import myEditor from '../components/Editor.vue';

export default {
  components: {
    myEditor
  },
  data() {
    return {
      tableData: [],
      dialogVisible:false,
      Delete,
      Plus,
      delRecids: [],
      articleForm:{
        cd_article:-1,
        ds_content:'',
        ds_article:'',
      },
      filter_form:{
        ds_article:'',
        ds_content:''
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
    handleFilterInput(){
      this.fetchData();
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
      axios.delete('http://localhost:8080/article/batchDel',{
          data:{
            recids : this.delRecids
          }
       }).then(response => {
         if (response.data.code != 200){
            ElNotification({
             message: response.data.msg,
             type: 'error',
           })
         }else{
           ElNotification({
             message: '删除成功',
             type: 'success',
           })
         }
        thisObj.fetchData();
       }).catch(error => {
            console.error(error);
       });
    },
    addArticle(){
      this.articleForm = {
        cd_article:-1,
        ds_content:this.createAccount(),
        ds_article:this.createAccount(),
      };
      this.dialogVisible  = !this.dialogVisible;
      console.log('addArticle')
    },

    addArticleForm(){
      let thisObj = this;
      axios.post("http://localhost:8080/article/upsert",this.articleForm).then(response => {
        // 请求成功的处理逻辑
        this.dialogVisible  = !this.dialogVisible;
        if (response.data.code == 200){
          ElNotification({
            type:'success',
            message:response.data.msg
          })
          thisObj.fetchData();
        }
      }).catch(error => {
        // 请求失败的处理逻辑
        console.error(error);
      });
    },

    handleSelectionChange(v) {
        this.delRecids = [];
        v.forEach((i,k)=>{
          this.delRecids.push(i.cd_article);
          console.log(i.cd_article,k);
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
      console.log(this.filter_form);
      this.fetchData();
    },

    handleDelete(index , row) {
      let recid  = row.cd_article;
      console.log('recid',recid);
      var thisObj = this;
      axios.delete('http://localhost:8080/article/delete/'+recid)
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
      this.articleForm = Object.assign({},this.articleForm,row);
      console.log(this.articleForm);
      this.dialogVisible = !this.dialogVisible;
    },

    fetchData(target_page) {

      if (!target_page){
        target_page = this.currentPage;
      }
      var thisObj = this;
      let url = 'http://localhost:8080/article/getListByPage/'+ target_page+ '/' + this.pageSize;
      // 使用你自己的后台请求方法获取数据
      // 这里使用 axios 作为示例
      axios.post(url,{
        filter:thisObj.filter_form
       })
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