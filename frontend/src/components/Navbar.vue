<script setup lang="ts">

import router from "../router.ts";
import { useBoardStore } from "../stores/board-store.ts";
import RetroBuddyIcon from "../assets/RetroBuddy.png";
import { storeToRefs } from "pinia";
import { ref, watch } from "vue";

const boardStore = useBoardStore();
const { createBoard, setActiveBoard, setIsHomePage } = boardStore;
const { isHomePage, activeBoardId } = storeToRefs(boardStore);

interface Board {
  name: string,
  defaultColour: string,
  boardId: string
}

function gotToHomePage() : void {
  setIsHomePage(true);
  router.push({ path: `/` })
}

function newBoard() : void {
  createBoard()
      .then(value => value.boardId)
      .then(boardId => {
        setIsHomePage(false);
        setActiveBoard(boardId);
        router.push({ path: `/board/${boardId}`})
      })
}

let boardName = ref<string>("");
let newName = ref<string>("");

function fetchBoardName(activeBoardId1: string): void {
  fetch(`http://localhost:8080/api/board/${activeBoardId1}/get`)
      .then(response => response.json())
      .then(response => response as Board)
      .then(response => {
        console.log(response);
        return response
      })
      .then(board => {
        boardName.value = board.name;
      })
  console.log("BOARD NAME");
}

function getBoardName() : string {
  console.log(isHomePage.value)
  if (isHomePage.value) {
    return "RetroBuddy";
  }
  else {
    console.log("BOARD NAME");
    fetchBoardName(activeBoardId.value!)
    return boardName.value;
  }
}

function saveBoardName(name: string) : void {
  boardName.value = name;
  fetch(`http://localhost:8080/api/board/${activeBoardId.value}/edit`, {
    body: JSON.stringify({
      name: name
    }),
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    }
  })
}

</script>

<template>
  <v-card
      class="mx-auto"
      max-width="448"
  >
    <v-layout>
      <v-app-bar
          color="primary"
          density="compact"
      >
        <template v-slot:prepend>
              <v-icon-btn color="primary" @click="gotToHomePage">
                <v-avatar :image="RetroBuddyIcon"></v-avatar>
              </v-icon-btn>
        </template>

        <v-app-bar-title>{{getBoardName()}}</v-app-bar-title>

        <v-menu>
          <template #activator="{ props }">
            <v-icon-btn
                icon="mdi-pencil"
                variant="plain"
                v-bind="props"
                color="white"
            />
          </template>

          <v-card width="300" @click.stop="">
            <v-card-item>
              <v-text-field v-model="newName" :placeholder="boardName" @click.stop=""></v-text-field>
              <v-btn @click="saveBoardName(newName)">Save</v-btn>
            </v-card-item>
          </v-card>

        </v-menu>

        <template v-slot:append>
          <v-btn icon="mdi-plus-thick" @click="newBoard"></v-btn>
        </template>
      </v-app-bar>

      <v-main>
        <v-container fluid>
          <v-row dense>
            <v-col
                v-for="n in 8"
                :key="n"
                cols="3"
            >
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </v-layout>
  </v-card>
</template>

<style scoped lang="scss">

</style>
