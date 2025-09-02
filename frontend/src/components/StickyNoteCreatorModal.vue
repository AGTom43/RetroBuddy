<script setup lang="ts">

import { useModalStore } from "../stores/modal-store.ts";
import { storeToRefs } from "pinia";
import { useBoardStore } from "../stores/board-store.ts";

const modalStore = useModalStore();

const { stickyNoteModalStatus, stickyNoteCreationPositionX, stickyNoteCreationPositionY, stickyNoteModalTitle, stickyNoteModalBody } = storeToRefs(modalStore)

const { closeStickyNoteModal } = modalStore

const boardStore = useBoardStore()

const { activeBoardId, defaultColour } = storeToRefs(boardStore)

function createStickyNote(): void {

  fetch(`http://localhost:8080/api/board/${activeBoardId.value}/sticky-note/create`, {
    body: JSON.stringify({
      title: stickyNoteModalTitle.value,
      body: stickyNoteModalBody.value,
      positionX: stickyNoteCreationPositionX.value,
      positionY: stickyNoteCreationPositionY.value,
      colour: defaultColour.value
    }),
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    }
  })

  closeStickyNoteModal();
}

</script>

<template>

  <dialog :open="stickyNoteModalStatus">
    <h1>Create new sticky note</h1>

    <div class="input__wrapper">
      <div class="input__container">
        <label for="sticky-note-title-input">Title</label>
        <input v-model="stickyNoteModalTitle" type="text" id="sticky-note-title-input"/>
      </div>

      <div class="input__container">
        <label for="sticky-note-body-input">Body</label>
        <textarea v-model="stickyNoteModalBody" id="sticky-note-body-input"/>
      </div>
    </div>

    <div class="button__container">
      <button class="button button--primary" @click="createStickyNote">Create</button>
      <button class="button" @click="closeStickyNoteModal">Cancel</button>
    </div>
  </dialog>

</template>

<style scoped lang="scss">

dialog {
  padding: 0;
}

h1 {
  background-color: #efefef;
  margin: 0;
  padding: 1rem;
  border-bottom: 2px solid black;
  margin-bottom: 1rem;
  text-align: center;
  font-size: 1.25rem;
}

.input{
  &__wrapper{
    margin-bottom: 1rem;
    padding-right: 1rem;
  }
  &__container{
    display: flex;
    gap: 1rem;
    & *{
      flex: 1;
      width: 100%;
    }
    & label{
      flex: 0.25;
      max-width: 50px;
      text-align: right;
    }
  }
}

.button__container {
  text-align: center;
  padding: 0 1rem 1rem 0;
}

.button{
  padding: 0.5rem 1rem;

  &:not(:last-child){
    margin-right: 0.25rem;
  }
}

</style>
