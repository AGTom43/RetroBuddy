<script setup lang="ts">

import BoardView from "../components/BoardView.vue";
import { useBoardStore } from "../stores/board-store.ts";
import StickyNoteDeletionModal from "../components/StickyNoteDeletionModal.vue";

interface Props {
  boardId: string
}

const props = defineProps<Props>()

const boardStore = useBoardStore();

const { setActiveBoard } = boardStore;

setActiveBoard(props.boardId)


</script>

<template>
  <div class="layout">
    <div class="content">
      <BoardView :boardId/>
      <StickyNoteDeletionModal/>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.layout {
  display: flex;
  width: 100%;
}
.content {
  background: url("../assets/dot-texture.jpg") repeat;
  background-size: 15px;
  width: 100%;
  height: 100vh;
  flex: 1;
}
</style>
