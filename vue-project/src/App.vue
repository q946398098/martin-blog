<script setup lang="ts">
import Main from './components/Main.vue'
import HeaderVue from './components/Header.vue'
</script>

<template>
  <div>
    <nav class="header">
      <HeaderVue></HeaderVue>
    </nav>
    <nav class="left">
      <Main></Main>
    </nav>
    <div class="right">
      <router-view/>
    </div>
  </div>
</template>

<style>
</style>
