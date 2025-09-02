<script setup lang="ts">

import StickyNote from "./StickyNote.vue";
import { useBoardStore, StickyNote as StickyNoteType } from "../stores/board-store.ts";
import { storeToRefs } from "pinia";
import { Stomp } from "@stomp/stompjs";

interface StickyNoteList {
  stickyNotes: StickyNoteType[];
}

const boardStore = useBoardStore();
const { activeBoardId, stickyNotesOnBoard, defaultColour } = storeToRefs(boardStore);
console.log("STICKY NOTES ON BOARD")
console.log(stickyNotesOnBoard)

const socket = new WebSocket('ws://localhost:8080/sticky-note-websocket');

const stompClient = Stomp.over(socket);

stompClient.connect({login: "username", password: "password"}, (frame: any) => {
  stompClient.subscribe(`/topic/board/${activeBoardId.value}/sticky-note/created`, (stickyNote) => {
    const stickyNoteObject: StickyNoteType = JSON.parse(stickyNote.body);
    stickyNotesOnBoard.value = [...stickyNotesOnBoard.value, stickyNoteObject];
  });

  stompClient.subscribe(`/topic/board/${activeBoardId.value}/sticky-note/deleted`, (message) => {
    const stickyNoteId = JSON.parse(message.body).id;

    const index = stickyNotesOnBoard.value.findIndex(stickyNote => stickyNote.id === stickyNoteId);
    if (index >= 0) {
      stickyNotesOnBoard.value.splice(index, 1);
    }
  });

  stompClient.subscribe(`/topic/board/${activeBoardId.value}/sticky-note/updated`, (message) => {
    const stickyNoteJson = JSON.parse(message.body);

    const index = stickyNotesOnBoard.value.findIndex(stickyNote => stickyNote.id === stickyNoteJson.id);
    if (index >= 0) {
      stickyNotesOnBoard.value[index].title = stickyNoteJson.title;
      stickyNotesOnBoard.value[index].body = stickyNoteJson.body;
      stickyNotesOnBoard.value[index].colour = stickyNoteJson.colour;
      stickyNotesOnBoard.value[index].positionX = stickyNoteJson.positionX;
      stickyNotesOnBoard.value[index].positionY = stickyNoteJson.positionY;
    }

    stickyNotesOnBoard.value = [...stickyNotesOnBoard.value];
  });
})

function getStickyNotes() {

  fetch(`http://localhost:8080/api/board/${activeBoardId.value}/sticky-note`)
      .then(response => response.json())
      .then(response => response as StickyNoteList)
      .then(stickyNoteList => {
        stickyNotesOnBoard.value = [];
        console.log(stickyNoteList)
        stickyNoteList.stickyNotes.forEach(stickyNote =>
            stickyNotesOnBoard.value = [...stickyNotesOnBoard.value, stickyNote])
      })
}

getStickyNotes()

function createStickyNote(event: MouseEvent): void {

  fetch(`http://localhost:8080/api/board/${activeBoardId.value}/sticky-note/create`, {
    body: JSON.stringify({
      title: "",
      body: "",
      positionX: event.clientX,
      positionY: event.clientY,
      colour: defaultColour.value
    }),
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    }
  })

}



</script>

<template>
  <div class="container" @dblclick.stop="createStickyNote">
      <template v-for="stickyNote of stickyNotesOnBoard">
        <StickyNote :id="stickyNote.id"
                    :title="stickyNote.title"
                    :body="stickyNote.body"
                    :positionX="stickyNote.positionX"
                    :positionY="stickyNote.positionY"
                    :colour="stickyNote.colour"
        />
      </template>
  </div>

</template>

<style scoped lang="scss">
.container {
  height: 100%;
  max-width: 100%;
  width: 100%;
}

</style>
