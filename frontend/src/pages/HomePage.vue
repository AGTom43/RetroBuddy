<script setup lang="ts">

import { ref } from "vue";
import router from "../router.ts";
import { useBoardStore } from "../stores/board-store.ts";

const boardStore = useBoardStore();
const { setIsHomePage } = boardStore;

const searchParams = new URLSearchParams(window.location.search);

interface Board {
  name: string,
  defaultColour: string,
  boardId: string
}

let allBoards = ref<Board[]>([]);

console.log("CREATE BOARD")
const url = new URL("/", window.location.href);
for (let entry of searchParams.entries()) {
  url.searchParams.set(entry[0], entry[1]);
}

function getAllBoards() {
  console.log("GETTING ALL BOARDS")
  fetch(`http://localhost:8080/api/board`)
      .then(response => response.json())
      .then((response: Board[]) => {
        allBoards.value = response;
      });
}

function redirect(boardId: string) : void {
  setIsHomePage(false);
  router.push({ path: `/board/${boardId}` });
}

getAllBoards();

</script>

<template>
  <div class="board-list">
    <v-card
        class="mx-auto"
        max-width="1000"
    >
      <v-list max-height="1000px">
        <v-list-item
            v-for="(board, index) in allBoards"
            :key="index"
            @click="redirect(board.boardId)"
        >
            <v-list-item-title>{{ board.name }}</v-list-item-title>
          <template v-slot:prepend>
            <v-icon
                icon="mdi-eyedropper"
                :color="board.defaultColour"
                variant="plain"
            />
          </template>
        </v-list-item>
      </v-list>
    </v-card>
  </div>

</template>

<style scoped lang="scss">

.board-list {
  padding-top: 100px;
}


</style>
