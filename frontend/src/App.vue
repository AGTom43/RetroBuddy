<script setup lang="ts">
import BoardPage from "./pages/BoardPage.vue";
import StickyNoteCreatorModal from "./components/StickyNoteCreatorModal.vue";
import { useBoardStore } from "./stores/board-store.ts";
import { ref } from "vue";
import StickyNoteDeletionModal from "./components/StickyNoteDeletionModal.vue";
import StickyNoteEditModal from "./components/StickyNoteEditModal.vue";
import Navbar from "./components/Navbar.vue";
import HomePage from "./pages/HomePage.vue";

interface Board {
  name: string,
  defaultColour: string,
  boardId: string
}

const loading = ref(true);
const homePage = ref(true);

const boardStore = useBoardStore();

const { setActiveBoard, setAsDefault } = boardStore;

const searchParams = new URLSearchParams(window.location.search);

function getBoard(activeBoardId: string): void {
  fetch(`http://localhost:8080/api/board/${activeBoardId}/get`)
      .then(response => response.json())
      .then(response => response as Board)
      .then(board => {
        setAsDefault(board.defaultColour);
      })
}

if (searchParams.has('board')) {
  homePage.value = false;
  console.log("USING BOARD FROM URL")
  let boardId = searchParams.get('board');
  setActiveBoard(searchParams.get('board')!);
  if (boardId) {
    getBoard(boardId);
  }
  loading.value = false;
} else {

  loading.value = false;
  homePage.value = true;
}

</script>

<template>
  <Navbar/>
  <router-view/>
</template>

<style scoped>
</style>
