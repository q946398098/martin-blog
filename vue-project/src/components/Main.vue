<template>
  <div>
    <el-scrollbar style="height: 100vh;">
     <el-menu
            active-text-color="#ffd04b"
            class="el-menu-vertical-demo"
            default-active="2"
            @open="handleOpen"
            @close="handleClose">
          <el-sub-menu index="1">
            <template #title>
              <el-icon><location /></el-icon>
              <span>用戶管理</span>
            </template>
              <el-menu-item index="1-1">
                <router-link to = '/user/edit'>用戶列表</router-link>
              </el-menu-item>
          </el-sub-menu>
       <el-sub-menu index="2">
         <template #title>
           <el-icon><location /></el-icon>
           <span>文章管理</span>
         </template>
         <el-menu-item index="2-1">
           <router-link to = '/article/edit'>文章管理</router-link>
         </el-menu-item>
       </el-sub-menu>

       <el-sub-menu index="3">
         <template #title>
           <el-icon><location /></el-icon>
           <span>評論管理</span>
         </template>
         <el-menu-item index="3-1">評論列表</el-menu-item>
       </el-sub-menu>
       </el-menu>
    </el-scrollbar>
  </div>
</template>

<script lang="ts" setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'

import { ref } from 'vue'


const isCollapse = ref(true)


const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>
