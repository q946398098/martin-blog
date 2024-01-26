<template>
  <div ref="editorContainer"></div>
</template>

<script>
import { ref, onMounted } from 'vue';
import EditorJS from '@editorjs/editorjs';

export default {
  name: 'MyEditor',
  setup() {
    const editorContainer = ref(null);
    let editorInstance;

    onMounted(() => {
      console.log(4);
      editorInstance = new EditorJS({
        holder: editorContainer.value,
        // 配置项：
        // 可以配置工具栏、默认的块类型、插件等
      });
    });

    // 使用 editorInstance 对象进行编辑器的操作，例如保存内容等

    return {
      editorContainer,
    };
  },
};
</script>