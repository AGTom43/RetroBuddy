<script setup lang="ts">

import { useModalStore } from "../stores/modal-store.ts";
import { storeToRefs } from "pinia";
import { useBoardStore } from "../stores/board-store.ts";

const modalStore = useModalStore();

const { stickyNoteEditModalStatus, stickyNoteToEditId, stickyNoteModalBody, stickyNoteModalTitle } = storeToRefs(modalStore)

const { closeStickyNoteEditModal } = modalStore

const boardStore = useBoardStore()

const { activeBoardId, stickyNotesOnBoard } = storeToRefs(boardStore)

function editStickyNote(): void {
  const stickyNoteToEdit = stickyNotesOnBoard.value.find(stickyNote => stickyNote.id === stickyNoteToEditId.value);

  if (stickyNoteToEdit) {
    fetch(`http://localhost:8080/api/board/${activeBoardId.value}/sticky-note/${stickyNoteToEditId.value}/update`, {
      body: JSON.stringify({
        title: stickyNoteModalTitle.value,
        body: stickyNoteModalBody.value,
        positionX: stickyNoteToEdit.positionX,
        positionY: stickyNoteToEdit.positionY,
      }),
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      }
    })
  }

  closeStickyNoteEditModal();
}

</script>

<template>

  <dialog :open="stickyNoteEditModalStatus">
    <h1>Update sticky note</h1>

    <div class="input__wrapper">
      <div class="input__container">
        <label for="sticky-note-title-edit-input">Title</label>
        <input v-model="stickyNoteModalTitle" type="text" id="sticky-note-title-edit-input"/>
      </div>

      <div class="input__container">
        <label for="sticky-note-body-edit-input">Body</label>
        <textarea v-model="stickyNoteModalBody" id="sticky-note-body-edit-input"/>
      </div>
    </div>

    <div class="button__container">
      <button class="button button--primary" @click="editStickyNote">Update</button>
      <button class="button" @click="closeStickyNoteEditModal">Cancel</button>
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
